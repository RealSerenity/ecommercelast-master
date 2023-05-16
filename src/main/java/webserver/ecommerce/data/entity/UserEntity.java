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
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username",nullable = false, unique = true)
    private String username;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "isSeller",nullable = false)
    private boolean isSeller;

    @JsonIgnore
    @OneToMany(mappedBy = "userEntity")
    private Set<OrderEntity> orders = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "seller")
    private Set<ProductEntity> products = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<FeedbackEntity> feedbacks = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<FeedbackEntity> addresses = new HashSet<>();


    public UserEntity(Long id, String username, String password, boolean isSeller, Set<OrderEntity> orders, Set<ProductEntity> products, Set<FeedbackEntity> feedbacks, Set<FeedbackEntity> addresses) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isSeller = isSeller;
        this.orders = orders;
        this.products = products;
        this.feedbacks = feedbacks;
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
