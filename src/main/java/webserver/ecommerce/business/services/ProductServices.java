package webserver.ecommerce.business.services;

import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.dto.ProductDto;
import webserver.ecommerce.data.entity.ProductEntity;

import java.util.List;

public interface ProductServices extends ServicesBase<ProductEntity , ProductDto> {

    public boolean decreaseStockAmount(Long id, Integer decreaseAmount);
    public List<ProductDto> getProductsByCategory(CategoryDto category);

    public List<ProductDto> getProductsOfSeller(Long sellerId);
}
