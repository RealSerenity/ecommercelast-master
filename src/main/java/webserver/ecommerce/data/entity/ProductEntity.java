package webserver.ecommerce.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Log4j2
@Builder
@Entity
@Table(name = "products")
public class ProductEntity {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "price",nullable = false)
    private Double price;
    @Column(name = "stock",nullable = false)
    private Integer stock;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id", referencedColumnName = "id")
    private CategoryEntity category;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity seller;

    @JsonIgnore
    @OneToMany(mappedBy = "productEntity")
    private Set<OrderEntity> takenOrders = new HashSet<>();


    @JsonIgnore
    @OneToMany(mappedBy = "productEntity")
    private Set<FeedbackEntity> feedbacks = new HashSet<>();


    public ProductEntity(Long id, String name, Double price, Integer stock, CategoryEntity category, UserEntity seller, Set<OrderEntity> takenOrders, Set<FeedbackEntity> feedbacks) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.seller = seller;
        this.takenOrders = takenOrders;
        this.feedbacks = feedbacks;
    }

    @Override
    public String toString() {
        return "ProductEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category=" + category.getCategoryName() +
                ", sellerId=" + seller.getId() +
                 '}';
    }
}
