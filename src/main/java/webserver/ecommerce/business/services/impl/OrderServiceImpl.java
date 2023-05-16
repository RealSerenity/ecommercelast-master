package webserver.ecommerce.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import webserver.ecommerce.business.dto.OrderDto;
import webserver.ecommerce.business.services.AddressServices;
import webserver.ecommerce.business.services.OrderServices;
import webserver.ecommerce.business.services.ProductServices;
import webserver.ecommerce.business.services.UserServices;
import webserver.ecommerce.data.entity.OrderEntity;
import webserver.ecommerce.data.entity.repository.OrderRepository;
import webserver.ecommerce.data.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@EnableScheduling
public class OrderServiceImpl extends ServicesBaseImpl<OrderEntity, OrderDto> implements OrderServices {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ProductServices productServices;

    @Autowired
    private AddressServices addressServices;

    @Autowired
    public OrderServiceImpl(ModelMapper modelMapper) {
        super(OrderEntity.class, OrderDto.class, modelMapper);
    }

    @Override
    public ResponseEntity<OrderDto> create(OrderDto orderDto) {
        orderDto.setTotalPrice(productServices.getById(orderDto.getProductId()).getBody().getPrice() * orderDto.getAmount());
        boolean isDecreased = productServices.decreaseStockAmount(orderDto.getProductId(), orderDto.getAmount());
        if(!isDecreased){
            return ResponseEntity.badRequest().header("Stock sorunu","Yetersiz stock").body(orderDto);
        }
        orderDto.setOrderDate(LocalDateTime.now());
        orderDto.setStatus(OrderStatus.Created);
        return super.create(orderDto);
    }

    @Override
    public ResponseEntity<OrderDto> updateById(Long id, OrderDto orderDto) {
        OrderEntity newEntity = dtoToEntity(orderDto);
        System.out.println("id : " + id);
        OrderEntity oldEntity = dtoToEntity(getById(id).getBody());

        oldEntity.setAmount(newEntity.getAmount());
        oldEntity.setTotalPrice(oldEntity.getProductEntity().getPrice()*newEntity.getAmount());

        newEntity = orderRepository.save(oldEntity);
        return ResponseEntity.ok(entityToDto(newEntity));
    }

    @Override
    public ResponseEntity<OrderDto> updateOrderStatus(Long id, OrderStatus orderStatus) {
        OrderEntity oldEntity = dtoToEntity(getById(id).getBody());
        oldEntity.setStatus(orderStatus);
        return updateById(id, entityToDto(oldEntity));
    }

    @Scheduled(fixedRate = 120000, initialDelay = 120000)
    @Override
    public void confirmOrders(){
        List<OrderEntity> confirmEntities = orderRepository.getOrderEntitiesByStatus(OrderStatus.Created);
        confirmEntities.forEach(orderEntity -> {
            // any control
            if(true){
                orderEntity.setStatus(OrderStatus.Confirmed);
                System.out.println("Order with id = " + orderEntity.getId() + " confirmed.");
            }
        });
        orderRepository.saveAll(confirmEntities);
    }

    @Scheduled(fixedRate = 120000, initialDelay = 120000)
    @Override
    public void deliverOrders(){
        List<OrderEntity> shippedEntities = orderRepository.getOrderEntitiesByStatus(OrderStatus.Shipped);
        shippedEntities.forEach(orderEntity -> {
            // any control
            if(orderEntity.getOrderDate().plusDays(1L).isBefore(LocalDateTime.now())){
                orderEntity.setStatus(OrderStatus.Delivered);
                System.out.println("Order with id = " + orderEntity.getId() + " delivered.");
            }
        });
        orderRepository.saveAll(shippedEntities);
    }

    @Scheduled(cron = "0 0 20 ? * 1-6")
    @Override
    public void shipOrders(){
        List<OrderEntity> confirmEntities = orderRepository.getOrderEntitiesByStatus(OrderStatus.Confirmed);
        confirmEntities.forEach(orderEntity -> {
            // any control
            if(orderEntity.getOrderDate().plusDays(1).withHour(8).withMinute(0).withSecond(0).withNano(0).isBefore(LocalDateTime.now())){
                orderEntity.setStatus(OrderStatus.Shipped);
                System.out.println("Order with id = " + orderEntity.getId() + " shipped.");
            }
        });
        orderRepository.saveAll(confirmEntities);
    }

    @Override
    public ResponseEntity<OrderDto> cancelOrder(Long id) {
        OrderEntity orderEntity = dtoToEntity(getById(id).getBody());
        orderEntity.setStatus(OrderStatus.Canceled);
        orderRepository.save(orderEntity);
        return ResponseEntity.ok(entityToDto(orderEntity));
    }

    @Override
    public List<OrderDto> getOrdersOfUser(Long userId) {
       return orderRepository.getOrderEntitiesByUserEntity(userServices.dtoToEntity(userServices.getById(userId).getBody())).stream().map(this::entityToDto).toList();
    }

    @Override
    public OrderDto entityToDto(OrderEntity orderEntity) {
        OrderDto dto = super.entityToDto(orderEntity);
        dto.setUserId(orderEntity.getUserEntity().getId());
        dto.setProductId(orderEntity.getProductEntity().getId());
        dto.setAddressId(orderEntity.getAddress().getId());
        return dto;
    }

    @Override
    public OrderEntity dtoToEntity(OrderDto orderDto) {
        OrderEntity entity = super.dtoToEntity(orderDto);
        entity.setUserEntity(userServices.dtoToEntity(userServices.getById(orderDto.getUserId()).getBody()));
        entity.setProductEntity(productServices.dtoToEntity(productServices.getById(orderDto.getProductId()).getBody()));
        entity.setAddress(addressServices.dtoToEntity(addressServices.getById(orderDto.getAddressId()).getBody()));
        return entity;
    }
}
