package webserver.ecommerce.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Builder
public class FeedbackDto implements Serializable {
    Long id;
    Long productId;
    Long userId;
    String content;
    LocalDateTime feedbackDate;
}
