package webserver.ecommerce.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Builder
public class ProductDto implements Serializable {
    Long id;
    Long sellerId;
    Long categoryId;
    String name;
    Double price;
    Integer stock;
}
