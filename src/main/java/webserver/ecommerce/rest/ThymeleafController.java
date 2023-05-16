package webserver.ecommerce.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webserver.ecommerce.business.dto.*;
import webserver.ecommerce.business.services.*;
import webserver.ecommerce.security.JwtUtils;

import javax.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Controller
@RequestMapping("/")
public class ThymeleafController {

    @Autowired
    private CategoryServices categoryServices;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private OrderServices orderServices;

    @Autowired
    private AddressServices addressServices;

    @Autowired
    private FeedbackServices feedbackServices;

    @GetMapping(value = {"/home", "/"})
    public String home(Model model, Authentication authentication) {
        boolean authenticated=false;
        if(authentication!=null){
            authenticated=true;
        }
        model.addAttribute("auth", authenticated);
        model.addAttribute("categories", categoryServices.getAll());
        model.addAttribute("productService", productServices);
        model.addAttribute("userService", userServices);
        return "home";
    }


    @GetMapping(value = {"/kategori_sayfasi"})
    public String category_page(Model model, @RequestParam("id") Long id, Authentication authentication) {
        boolean authenticated=false;
        if(authentication!=null){
            authenticated=true;
        }
        model.addAttribute("auth",authenticated);
        model.addAttribute("categories", categoryServices.getAll());
        model.addAttribute("products", productServices.getProductsByCategory(categoryServices.getById(id).getBody()));
        model.addAttribute("userService", userServices);
        model.addAttribute("currentCategory", categoryServices.getById(id).getBody());
        return "kategoriSayfasi";
    }

    @GetMapping(value = {"/hesap_sayfasi"})
    public String hesap_sayfasi(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("seller", userServices.getUserByUsername(userDetails.getUsername()).getBody().isSeller());
        model.addAttribute("userId", userServices.getUserByUsername(userDetails.getUsername()).getBody().getId());
        return "hesap";
    }

    @GetMapping(value = {"/product_yayınla"})
    public String product_form(Model model) {
        model.addAttribute("categoryService", categoryServices);
        model.addAttribute("productDto", ProductDto.builder().build());
        return "productform";
    }

    @PostMapping(value = {"/product_yayınla"})
    public String product_yayinla(@ModelAttribute("productDto") ProductDto productDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        productDto.setSellerId(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId());
        productServices.create(productDto);
        return "redirect:/satici_urunleri";
    }

    @GetMapping(value = {"/satici_urunleri"})
    public String satici_urunleri(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("products", productServices.getProductsOfSeller(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId()));
        model.addAttribute("categoryService", categoryServices);
        return "saticiurunleri";
    }

    @GetMapping(value = {"/siparis_sayfasi"})
    public String siparis_sayfasi(Model model, @RequestParam("productId") Long productId, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        model.addAttribute("categories", categoryServices.getAll());
        model.addAttribute("user", userServices.getUserByUsername(userDetails.getUsername()).getBody());
        model.addAttribute("product", productServices.getById(productId).getBody());

        model.addAttribute("addressServices", addressServices);
        model.addAttribute("userService", userServices);
        model.addAttribute("orderService", orderServices);

        model.addAttribute("adresDto", AddressDto.builder().build());
        model.addAttribute("orderDto", OrderDto.builder().amount(0).build());

        return "siparisSayfasi";
    }

    @PostMapping(value = {"/siparis_onay"})
    public String siparis_onay(@ModelAttribute("orderDto") OrderDto orderDto, @RequestParam("productId") Long productId, Authentication authentication) {

        if(orderDto.getAmount() == null || orderDto.getAmount()==0){
            return "redirect:/siparis_sayfasi?productId="+productId;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        orderDto.setUserId(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId());
        orderDto.setProductId(productId);
        orderServices.create(orderDto);
        return "redirect:/siparisler";
    }

    @PostMapping(value = {"/satici_hesap_onay"})
    public String satici_hesap_onay(Authentication authentication, @ModelAttribute("onayRequestDto") OnayRequestDto onayRequestDto) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        if(onayRequestDto.getOnayMessage().equals("Satıcı hesabımı onaylıyorum")){
            userServices.addSellerRoleToUser(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId());
        }else{
            return "redirect:/satici_hesap_onay";
        }
        return "redirect:/hesap_sayfasi";
    }

    @GetMapping(value = {"/kullanici_guncellestir"})
    public String userUpdate(Model model, @ModelAttribute("userDto") UserDto userDto) {
        model.addAttribute("userDto", userDto);
        return "userUpdate";
    }


    @GetMapping(value = {"/urun_sayfasi"})
    public String urun_sayfasi(Model model, Authentication authentication, @RequestParam("productId") Long productId) {
        boolean authenticated=false;
        boolean canFeedback = false;

        if(authentication!=null){
            authenticated=true;
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            List<OrderDto> orders = orderServices.getOrdersOfUser(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId()).stream().filter(orderDto -> orderDto.getId()==productId).toList();
            if(orders.size()>0){
                canFeedback=true;
            }
        }

        model.addAttribute("auth",authenticated);
        model.addAttribute("product", productServices.getById(productId).getBody());
        model.addAttribute("userService", userServices);
        model.addAttribute("canFeedback", canFeedback);
        model.addAttribute("feedbacks", feedbackServices.getFeedbacksOfProduct(productId));
        model.addAttribute("feedbackDto", FeedbackDto.builder().build());
        return "urunsayfasi";
    }


    @PostMapping(value = {"/feedback"})
    public String feedback(Authentication authentication, @ModelAttribute("feedbackDto") FeedbackDto feedbackDto, @RequestParam("productId") Long productId) {
        System.out.println("feedback created");
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        feedbackDto.setUserId(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId());
        feedbackDto.setProductId(productId);
        feedbackServices.create(feedbackDto);
        return "redirect:/urun_sayfasi?productId="+productId;
    }

    @PostMapping(value = {"/kullanici_guncellestir"})
    public String userUpdatePost(@ModelAttribute("userDto") UserDto userDto, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserDto oldUser = userServices.getUserByUsername(userDetails.getUsername()).getBody();
        if(!userDto.getUsername().isBlank()){
            oldUser.setUsername(userDto.getUsername());
        }
        if(!userDto.getPassword().isBlank()){
            oldUser.setPassword(userDto.getPassword());
        }
        userServices.updateById(oldUser.getId(), oldUser);
        return "redirect:/login";
    }


    @PostMapping(value = {"/adres_ekle"})
    public String adres_ekle(Authentication authentication, @ModelAttribute("adresDto") AddressDto adresDto, @RequestParam("productId") Long productId) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        adresDto.setUserId(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId());
        addressServices.create(adresDto);
        return "redirect:/siparis_sayfasi?productId="+productId;
    }

    @GetMapping(value = {"/satici_hesap_onay"})
    public String satici_hesap_onay_sayfasi(Model model) {
        model.addAttribute("saticihesaptext", "'Satıcı hesabımı onaylıyorum'");
        model.addAttribute("onayRequestDto", OnayRequestDto.builder().build());
        return "saticihesaponay";
    }

    @GetMapping(value = {"/siparisler"})
    public String siparisler(Model model, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        model.addAttribute("siparisler", orderServices.getOrdersOfUser(userServices.getUserByUsername(userDetails.getUsername()).getBody().getId()));
        model.addAttribute("productServices", productServices);
        model.addAttribute("addressServices", addressServices);
        return "siparisler";
    }


    @GetMapping(value = {"/satici_sayfasi"})
    public String seller_page(Model model, @RequestParam("id") Long id) {
        model.addAttribute("seller", userServices.getById(id).getBody());
        model.addAttribute("products", productServices.getProductsOfSeller(id));
        model.addAttribute("categoryService",categoryServices);
        return "saticiSayfasi";
    }

    @GetMapping(value = {"/user_kayit"})
    public String user_kayit(Model model) {
        model.addAttribute("userDto", UserDto.builder().build());
        return "userkayit";
    }

    @PostMapping(value = {"/user_kayit"})
    public String user_kayit_post(@ModelAttribute("userDto") UserDto userDto) {
//        if(userServices.getUserByUsername(userDto.getUsername())!=null){
//            System.out.println("geçerli bir kullanıcı adı girin");
//            return "redirect:/user_kayit";
//        }
        userServices.create(userDto);
        return "redirect:/login";
    }
}
