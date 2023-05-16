package webserver.ecommerce.business.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Builder
public class UserLoginDto {
//    @NotEmpty(message = "{registration.validation.firstName}")
    String username;
    String password;
}
