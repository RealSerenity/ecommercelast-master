package webserver.ecommerce.business.services;

import webserver.ecommerce.business.dto.AddressDto;
import webserver.ecommerce.data.entity.AddressEntity;

import java.util.List;

public interface AddressServices  extends ServicesBase<AddressEntity, AddressDto>{

    List<AddressDto> getAddressesOfUser(Long userId);
}
