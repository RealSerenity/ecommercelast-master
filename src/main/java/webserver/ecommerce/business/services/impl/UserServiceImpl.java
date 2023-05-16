package webserver.ecommerce.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webserver.ecommerce.business.dto.UserDto;
import webserver.ecommerce.business.services.UserServices;
import webserver.ecommerce.data.entity.UserEntity;
import webserver.ecommerce.data.entity.repository.UserRepository;

import java.util.Collections;


@Service
@Order(1)
public class UserServiceImpl extends ServicesBaseImpl<UserEntity, UserDto> implements UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(ModelMapper modelMapper) {
        super(UserEntity.class, UserDto.class, modelMapper);
    }

    @Override
    public ResponseEntity<UserDto> create(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDto.setSeller(false);
        return super.create(userDto);
    }

    @Override
    public ResponseEntity<UserDto> updateById(Long id, UserDto userDto) {
        UserEntity newEntity = dtoToEntity(userDto);
        UserEntity oldEntity = dtoToEntity(getById(id).getBody());

        oldEntity.setUsername(newEntity.getUsername());

        if(!passwordEncoder.matches(newEntity.getPassword(), oldEntity.getPassword()) || oldEntity.getPassword().equals(newEntity.getPassword())){
            oldEntity.setPassword(passwordEncoder.encode(newEntity.getPassword()));
        }

        newEntity = userRepository.save(oldEntity);
        return ResponseEntity.ok(entityToDto(newEntity));
    }

    @Override
    public ResponseEntity<UserDto> getUserByUsername(String username) {
        UserEntity userEntity = userRepository.getByUsername(username);
        if(userEntity == null){
            throw new UsernameNotFoundException("User not exists");
        }
        return ResponseEntity.ok(entityToDto(userEntity));
    }

    @Override
    public UserDetails getUserDetailsByUsername(String username) {
        UserDto userDto = getUserByUsername(username).getBody();
        assert userDto != null;
        return User
                .withUsername(userDto.getUsername())
//                .roles("USER")
                .authorities(new SimpleGrantedAuthority("ROLE_USER"))
                .password(userDto.getPassword())
                .build();
    }

    @Override
    public ResponseEntity<UserDto> addSellerRoleToUser(Long id) {
        UserDto dto = getById(id).getBody();
        dto.setSeller(true);

        UserEntity newEntity = userRepository.save(dtoToEntity(dto));
        return ResponseEntity.ok(entityToDto(newEntity));
    }

    @Override
    public ResponseEntity<UserDto> getById(Long id) {
        return super.getById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserDetailsByUsername(username);
    }
}
