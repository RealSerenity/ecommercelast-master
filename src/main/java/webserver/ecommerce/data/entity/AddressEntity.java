package webserver.ecommerce.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Data
@NoArgsConstructor
@Log4j2
@Builder
@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "stock",nullable = false)
    String addressContent;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    UserEntity user;

    public AddressEntity(Long id, String addressContent, UserEntity user) {
        this.id = id;
        this.addressContent = addressContent;
        this.user = user;
    }
}
