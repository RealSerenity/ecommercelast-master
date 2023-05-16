package webserver.ecommerce.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import webserver.ecommerce.data.enums.OrderStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Log4j2
@Builder
@Entity
@Table(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity userEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;

    @Column(name = "status",nullable = false)
    private OrderStatus status;

    @Column(name = "date",nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "amount",nullable = false)
    private Integer amount;

    @Column(name = "totalPrice",nullable = false)
    private Double totalPrice;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private AddressEntity address;

    public OrderEntity(Long id, UserEntity userEntity, ProductEntity productEntity, OrderStatus status, LocalDateTime orderDate, Integer amount, Double totalPrice, AddressEntity address) {
        this.id = id;
        this.userEntity = userEntity;
        this.productEntity = productEntity;
        this.status = status;
        this.orderDate = orderDate;
        this.amount = amount;
        this.totalPrice = totalPrice;
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", userId=" + userEntity.getId() +
                ", productId=" + productEntity.getId() +
                ", sellerId=" + productEntity.getSeller().getId() +
                ", status=" + status +
                ", orderDate=" + orderDate +
                ", amount=" + amount +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
