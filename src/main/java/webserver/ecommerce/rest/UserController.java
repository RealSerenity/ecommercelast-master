package webserver.ecommerce.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webserver.ecommerce.business.dto.UserDto;
import webserver.ecommerce.business.services.UserServices;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:8080")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("/getAllUsers")
    public List<UserDto> getAllUsers() {
        return userServices.getAll();
    }

    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto){
        return userServices.create(dto);
    }

    @DeleteMapping("/deleteUser")
    public ResponseEntity<Map<String, Boolean>> deleteUser(@RequestParam Long id){
        return userServices.delete(id);
    }

    @PutMapping("/updateUser")
    public ResponseEntity<UserDto> updateUser(@RequestParam Long id,@RequestBody UserDto dto){
        return userServices.updateById(id, dto);
    }

    @GetMapping("/getUserById")
    public ResponseEntity<UserDto> getUserById(@RequestParam Long id) {
        return userServices.getById(id);
    }
}
