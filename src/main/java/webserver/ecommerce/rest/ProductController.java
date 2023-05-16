package webserver.ecommerce.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webserver.ecommerce.business.dto.OrderDto;
import webserver.ecommerce.business.dto.ProductDto;
import webserver.ecommerce.business.services.ProductServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:8080")
public class ProductController {

    @Autowired
    private ProductServices productServices;

    @GetMapping("/getAllProducts")
    public List<ProductDto> getAllProducts() {
        return productServices.getAll();
    }

    @PostMapping(value = "/createProduct", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto){
        return productServices.create(dto);
    }

    @DeleteMapping("/deleteProduct")
    public ResponseEntity<Map<String, Boolean>> deleteProduct(@RequestParam Long id){
        return productServices.delete(id);
    }

    @PutMapping("/updateProduct")
    public ResponseEntity<ProductDto> updateProduct(@RequestParam Long id,@RequestBody ProductDto dto){
        return productServices.updateById(id, dto);
    }

    @GetMapping("/getProductById")
    public ResponseEntity<ProductDto> getProductById(@RequestParam Long id) {
        return productServices.getById(id);
    }
}
