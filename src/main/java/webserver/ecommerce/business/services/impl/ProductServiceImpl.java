package webserver.ecommerce.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.dto.ProductDto;
import webserver.ecommerce.business.services.CategoryServices;
import webserver.ecommerce.business.services.ProductServices;
import webserver.ecommerce.business.services.UserServices;
import webserver.ecommerce.data.entity.ProductEntity;
import webserver.ecommerce.data.entity.repository.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl extends ServicesBaseImpl<ProductEntity, ProductDto> implements ProductServices {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryServices categoryServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper) {
        super(ProductEntity.class, ProductDto.class, modelMapper);
    }


    @Override
    public ResponseEntity<ProductDto> create(ProductDto productDto) {
        List<ProductDto> list = new ArrayList<>();
        if(getAll().size()>0)
        {
           list = getAll().stream().filter(productDto1 -> productDto.getName().contains(productDto1.getName())).collect(Collectors.toList());
        }
        if(list.size()>0){
            Double maxPrice = list.get(0).getPrice();
            for (ProductDto dto : list) {
                if (dto.getPrice() > maxPrice) {
                    maxPrice = dto.getPrice();
                }
            }
            if (productDto.getPrice() > maxPrice * 1.3d) {
                System.out.println("Fiyat aralığı çok fazla!!!");
                return ResponseEntity.badRequest().header("Inappropriate price", "Applicable max price = " + maxPrice).body(productDto);
            }
        }

        return super.create(productDto);
    }

    @Override
    public ResponseEntity<ProductDto> updateById(Long id, ProductDto productDto) {
        ProductEntity newEntity = dtoToEntity(productDto);
        ProductEntity oldEntity = dtoToEntity(getById(id).getBody());

        oldEntity.setSeller(newEntity.getSeller());
        oldEntity.setCategory(newEntity.getCategory());
        oldEntity.setName(newEntity.getName());
        oldEntity.setStock(newEntity.getStock());
        oldEntity.setPrice(newEntity.getPrice());

        newEntity = productRepository.save(oldEntity);
        return ResponseEntity.ok(entityToDto(newEntity));
    }

    @Override
    public boolean decreaseStockAmount(Long id, Integer decreaseAmount) {
        ProductDto dto = getById(id).getBody();
        if (dto.getStock()<decreaseAmount){
            return false;
        }
        dto.setStock(dto.getStock()-decreaseAmount);
        updateById(id, dto);
        return true;
    }

    @Override
    public List<ProductDto> getProductsByCategory(CategoryDto category) {
        return productRepository.getProductEntitiesByCategory(categoryServices.dtoToEntity(category)).stream().map(this::entityToDto).toList();
    }

    @Override
    public List<ProductDto> getProductsOfSeller(Long sellerId) {
        return productRepository.getProductEntitiesBySeller(userServices.dtoToEntity(userServices.getById(sellerId).getBody())).stream().map(this::entityToDto).toList();
    }

    @Override
    public ProductDto entityToDto(ProductEntity productEntity) {
        ProductDto dto = super.entityToDto(productEntity);
        dto.setSellerId(productEntity.getSeller().getId());
        dto.setCategoryId(productEntity.getCategory().getId());
        return dto;
    }

    @Override
    public ProductEntity dtoToEntity(ProductDto productDto) {
        ProductEntity entity = super.dtoToEntity(productDto);
        entity.setCategory(categoryServices.dtoToEntity(categoryServices.getById(productDto.getCategoryId()).getBody()));
        entity.setSeller(userServices.dtoToEntity(userServices.getById(productDto.getSellerId()).getBody()));
        return entity;
    }
}
