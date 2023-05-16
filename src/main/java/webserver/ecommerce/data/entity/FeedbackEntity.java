package webserver.ecommerce.data.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Log4j2
@Builder
@Entity
@Table(name = "feedbacks")
public class FeedbackEntity {
    @Id
    @Column(name = "id",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private ProductEntity productEntity;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column(name = "content",nullable = false)
    private String content;

    @Column(name = "date",nullable = false)
    private LocalDateTime feedbackDate;

    public FeedbackEntity(Long id, ProductEntity productEntity, UserEntity user, String content) {
        this.id = id;
        this.productEntity = productEntity;
        this.user = user;
        this.content = content;
        this.feedbackDate = LocalDateTime.now();
    }

    public FeedbackEntity(Long id, ProductEntity productEntity, UserEntity user, String content, LocalDateTime feedbackDate) {
        this.id = id;
        this.productEntity = productEntity;
        this.user = user;
        this.content = content;
        this.feedbackDate = feedbackDate;
    }

    @Override
    public String toString() {
        return "FeedbackEntity{" +
                "id=" + id +
                ", productId=" + productEntity.getId() +
                ", userId=" + user.getId() +
                ", content='" + content + '\'' +
                ", feedbackDate=" + feedbackDate +
                '}';
    }
}
