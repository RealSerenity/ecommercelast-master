package webserver.ecommerce.data.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver.ecommerce.data.entity.OrderEntity;
import webserver.ecommerce.data.entity.UserEntity;
import webserver.ecommerce.data.enums.OrderStatus;

import java.util.List;

@Repository
public interface OrderRepository extends BaseRepository<OrderEntity, Long> {

    List<OrderEntity> getOrderEntitiesByStatus(OrderStatus status);
    List<OrderEntity> getOrderEntitiesByUserEntity(UserEntity user);
}
