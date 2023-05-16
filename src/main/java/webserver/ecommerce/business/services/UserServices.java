package webserver.ecommerce.business.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import webserver.ecommerce.business.dto.UserDto;
import webserver.ecommerce.data.entity.UserEntity;

public interface UserServices extends ServicesBase<UserEntity, UserDto>, UserDetailsService{

    public ResponseEntity<UserDto> getUserByUsername(String username);

    public UserDetails getUserDetailsByUsername(String username);

    public ResponseEntity<UserDto> addSellerRoleToUser(Long id);
}
