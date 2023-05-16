package webserver.ecommerce.data.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver.ecommerce.data.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends BaseRepository<CategoryEntity, Long> {
}
