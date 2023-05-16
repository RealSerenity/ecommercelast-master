package webserver.ecommerce.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webserver.ecommerce.business.dto.AddressDto;
import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.services.AddressServices;
import webserver.ecommerce.business.services.CategoryServices;
import webserver.ecommerce.business.services.UserServices;
import webserver.ecommerce.data.entity.AddressEntity;
import webserver.ecommerce.data.entity.CategoryEntity;
import webserver.ecommerce.data.entity.repository.AddressRepository;

import java.util.List;

@Service
public class AddressServiceImpl extends ServicesBaseImpl<AddressEntity, AddressDto> implements AddressServices {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    public AddressServiceImpl(ModelMapper modelMapper) {
        super(AddressEntity.class, AddressDto.class, modelMapper);
    }

    @Override
    public ResponseEntity<AddressDto> updateById(Long id, AddressDto addressDto) {
        AddressEntity newEntity = dtoToEntity(addressDto);
        AddressEntity oldEntity = dtoToEntity(getById(id).getBody());

        oldEntity.setAddressContent(addressDto.getAddressContent());

        newEntity = addressRepository.save(oldEntity);
        return ResponseEntity.ok(entityToDto(newEntity));
    }


    @Override
    public List<AddressDto> getAddressesOfUser(Long userId) {
        return addressRepository.getAddressEntitiesByUser(userServices.dtoToEntity(userServices.getById(userId).getBody())).stream().map(this::entityToDto).toList();
    }

    @Override
    public AddressDto entityToDto(AddressEntity addressEntity) {
       AddressDto dto = super.entityToDto(addressEntity);
       dto.setUserId(addressEntity.getId());
       return dto;
    }



    @Override
    public AddressEntity dtoToEntity(AddressDto addressDto) {
        AddressEntity entity = super.dtoToEntity(addressDto);
        entity.setUser(userServices.dtoToEntity(userServices.getById(addressDto.getUserId()).getBody()));
        return entity;
    }

}
