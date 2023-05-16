package webserver.ecommerce.data.entity.repository;

import org.springframework.stereotype.Repository;
import webserver.ecommerce.data.entity.AddressEntity;
import webserver.ecommerce.data.entity.CategoryEntity;
import webserver.ecommerce.data.entity.UserEntity;

import java.util.List;

@Repository
public interface AddressRepository extends BaseRepository<AddressEntity, Long>{

    List<AddressEntity> getAddressEntitiesByUser(UserEntity entity);
}
