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
@Table(name = "categories")
public class CategoryEntity{

    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content",nullable = false, unique = true)
    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "category")
    private Set<ProductEntity> products;

    public CategoryEntity(Long id, String categoryName, Set<ProductEntity> products) {
        this.id = id;
        this.categoryName = categoryName;
        this.products = products;
    }

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\''+
                '}';
    }
}
