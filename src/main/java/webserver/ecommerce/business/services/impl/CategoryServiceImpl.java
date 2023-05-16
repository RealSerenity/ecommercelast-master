package webserver.ecommerce.business.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.services.CategoryServices;
import webserver.ecommerce.data.entity.CategoryEntity;
import webserver.ecommerce.data.entity.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl extends ServicesBaseImpl<CategoryEntity, CategoryDto> implements CategoryServices {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper) {
        super(CategoryEntity.class, CategoryDto.class, modelMapper);
    }

    @Override
    public ResponseEntity<CategoryDto> updateById(Long id, CategoryDto dto) {
        CategoryEntity newEntity = dtoToEntity(dto);
        CategoryEntity oldEntity = dtoToEntity(getById(id).getBody());

        oldEntity.setCategoryName(newEntity.getCategoryName());
        //oldEntity.setProducts(newEntity.getProducts());

        newEntity = categoryRepository.save(oldEntity);
        return ResponseEntity.ok(entityToDto(newEntity));
    }
}
