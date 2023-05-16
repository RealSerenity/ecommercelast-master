package webserver.ecommerce.data.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver.ecommerce.data.entity.FeedbackEntity;
import webserver.ecommerce.data.entity.ProductEntity;

import java.util.List;

@Repository
public interface FeedbackRepository extends BaseRepository<FeedbackEntity, Long> {

    List<FeedbackEntity> getFeedbackEntitiesByProductEntity(ProductEntity product);
}
