package webserver.ecommerce.bean;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import webserver.ecommerce.business.dto.*;
import webserver.ecommerce.data.entity.*;

@Configuration
public class ClassBean {

    @Bean(name = "entity")
    @Primary
    public Class<UserEntity> getUserEntityClass(){
        return UserEntity.class;
    }
    @Bean(name = "dto")
    @Primary
    public Class<UserDto> getUserDtoClass(){
        return UserDto.class;
    }
    @Bean(name = "entity")
    public Class<CategoryEntity> getCategoryEntityClass(){
        return CategoryEntity.class;
    }
    @Bean(name = "dto")
    public Class<CategoryDto> getCategoryDtoClass(){
        return CategoryDto.class;
    }
    @Bean(name = "entity")
    public Class<ProductEntity> getProductEntityClass(){
        return ProductEntity.class;
    }
    @Bean(name = "dto")
    public Class<ProductDto> getProductDtoClass(){
        return ProductDto.class;
    }
    @Bean(name = "entity")
    public Class<FeedbackEntity> getFeedbackEntityClass(){
        return FeedbackEntity.class;
    }
    @Bean(name = "dto")
    public Class<FeedbackDto> getFeedbackDtoClass(){
        return FeedbackDto.class;
    }
    @Bean(name = "entity")
    public Class<OrderEntity> getOrderEntityClass(){
        return OrderEntity.class;
    }
    @Bean(name = "dto")
    public Class<OrderDto> getOrderDtoClass(){
        return OrderDto.class;
    }
    @Bean(name = "entity")
    public Class<AddressEntity> getAddressEntityClass(){
        return AddressEntity.class;
    }
    @Bean(name = "dto")
    public Class<AddressDto> getAddressDtoClass(){
        return AddressDto.class;
    }
}
