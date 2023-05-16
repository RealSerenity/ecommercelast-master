package webserver.ecommerce.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import webserver.ecommerce.data.enums.OrderStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Builder
public class OrderDto implements Serializable {
    Long id;
    Long userId;
    Long productId;
    Long addressId;
    OrderStatus status;
    LocalDateTime orderDate;
    Integer amount;
    Double totalPrice;
}
