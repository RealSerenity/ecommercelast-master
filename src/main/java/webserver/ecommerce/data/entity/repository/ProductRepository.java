package webserver.ecommerce.data.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver.ecommerce.business.dto.ProductDto;
import webserver.ecommerce.data.entity.CategoryEntity;
import webserver.ecommerce.data.entity.ProductEntity;
import webserver.ecommerce.data.entity.UserEntity;

import java.util.List;

@Repository
public interface ProductRepository extends BaseRepository<ProductEntity, Long> {

    ProductEntity findFirstByCategoryOrderByPriceDesc(CategoryEntity category);
    List<ProductEntity> getProductEntitiesByCategory(CategoryEntity category);

    List<ProductEntity> getProductEntitiesBySeller(UserEntity seller);
}
