package webserver.ecommerce.business.services;

import org.springframework.http.ResponseEntity;
import webserver.ecommerce.business.dto.OrderDto;
import webserver.ecommerce.data.entity.OrderEntity;
import webserver.ecommerce.data.enums.OrderStatus;

import java.util.List;

public interface OrderServices extends ServicesBase<OrderEntity ,OrderDto> {

    public ResponseEntity<OrderDto> updateOrderStatus(Long id, OrderStatus orderStatus);

    public void confirmOrders();
    public void shipOrders();
    public void deliverOrders();

    public ResponseEntity<OrderDto> cancelOrder(Long id);

    public List<OrderDto> getOrdersOfUser(Long userId);
}
