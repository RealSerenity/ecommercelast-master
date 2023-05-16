package webserver.ecommerce.business.services.impl;

import org.hibernate.service.spi.InjectService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import webserver.ecommerce.business.dto.CategoryDto;
import webserver.ecommerce.business.services.ServicesBase;
import webserver.ecommerce.data.entity.CategoryEntity;
import webserver.ecommerce.data.entity.repository.BaseRepository;
import webserver.ecommerce.exception.ResourceNotFoundException;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Order(Ordered.LOWEST_PRECEDENCE)
public class ServicesBaseImpl<TEntity, TDto> implements ServicesBase<TEntity, TDto>{


    private final Class<TEntity> entityClass;
    private final Class<TDto> dtoClass;

    private ModelMapper modelMapper;

    public ServicesBaseImpl(@Autowired @Qualifier("entity") Class<TEntity> entityClass, @Autowired @Qualifier("dto") Class<TDto> dtoClass, ModelMapper modelMapper)
    {
        this.entityClass = entityClass;
        this.dtoClass = dtoClass;
        this.modelMapper = modelMapper;
    }

    @Autowired
    private BaseRepository<TEntity, Long> repository;


    @Override
    public List<TDto> getAll() {
        Iterable<TEntity> entities = repository.findAll();
        List<TDto> list = new ArrayList<>();
        System.out.println(entityClass.getName() + " get all");
        entities.forEach(entity -> list.add(entityToDto(entity)));
        return list;
    }

    @Override
    public ResponseEntity<TDto> create(TDto dto) {
        TEntity entity = dtoToEntity(dto);
        entity = repository.save(entity);
        System.out.println(entityClass.getName() + " created " + entity);
        dto = entityToDto(entity);
        return ResponseEntity.ok(dto);
    }

    @Override
    public ResponseEntity<Map<String, Boolean>> delete(Long id) {
        TEntity entity = dtoToEntity(getById(id).getBody());
        System.out.println(entityClass.getName() + " delete "+ entity );
        repository.delete(entity);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<TDto> updateById(Long id, TDto dto) {
      /*  TEntity entity = dtoToEntity(dto);
       Field[] fields = entityClass.getDeclaredFields();
       TEntity oldEntity = dtoToEntity(getById(id).getBody());
        for (Field field : fields) {
            try {
                System.out.println("Field name : " + field.getName() + " " + entity.getClass().getField(field.getName()));
                if(entity.getClass().getField(field.getName()) != null){
                    oldEntity.getClass().getField(field.getName()).set(field, entity.getClass().getField(field.getName()));
                }
            } catch (NoSuchFieldException e) {
                System.out.println("No such field "+ e);
            } catch (IllegalAccessException e) {
                System.out.println("Runtime error " + e);
            }
        }
        entity = repository.save(entity);
        System.out.println(entityClass.getName() + " update by id " + "\n" + entity);
        return ResponseEntity.ok(entityToDto(entity));*/
        return null;
    }

    @Override
    public ResponseEntity<TDto> getById(Long id) {
        TEntity entity = repository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(entityClass.getName() +" not exist by given id : " + id));
        System.out.println(entityClass.getName() + " get by id " + id + " " + entity);
        return ResponseEntity.ok(entityToDto(entity));
    }

    @Override
    public TDto entityToDto(TEntity entity) {
        TDto dto;
        try {
           dto = dtoClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Exception occurred while casting entityToDto " + e);
        }
        dto = modelMapper.map(entity, dtoClass);
        return dto;
    }

    @Override
    public TEntity dtoToEntity(TDto dto) {
        TEntity entity;
        try {
            entity = entityClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.out.println("Exception occurred while casting dtoToEntity " + e);
        }
        entity = modelMapper.map(dto, entityClass);
        return entity;
    }
}
