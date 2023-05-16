package webserver.ecommerce.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webserver.ecommerce.business.dto.AddressDto;
import webserver.ecommerce.business.dto.ProductDto;
import webserver.ecommerce.business.services.AddressServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "http://localhost:8080")
public class AddressController {

    @Autowired
    private AddressServices addressServices;


    @GetMapping("/getAllAdresses")
    public List<AddressDto> getAllAddresses() {
        return addressServices.getAll();
    }

    @PostMapping(value = "/createAddress", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto dto){
        return addressServices.create(dto);
    }

    @DeleteMapping("/deleteAddress")
    public ResponseEntity<Map<String, Boolean>> deleteAddress(@RequestParam Long id){
        return addressServices.delete(id);
    }

    @PutMapping("/updateAddress")
    public ResponseEntity<AddressDto> updateAddress(@RequestParam Long id,@RequestBody AddressDto dto){
        return addressServices.updateById(id, dto);
    }

    @GetMapping("/getAddressById")
    public ResponseEntity<AddressDto> getAdressById(@RequestParam Long id) {
        return addressServices.getById(id);
    }
}
