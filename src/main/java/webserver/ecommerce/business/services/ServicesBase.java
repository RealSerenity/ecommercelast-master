package webserver.ecommerce.business.services;

import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ServicesBase<TEntity, TDto>{

    public List<TDto> getAll();

    public ResponseEntity<TDto> create(TDto dto);

    public ResponseEntity<Map<String, Boolean>> delete(Long id);

    public ResponseEntity<TDto> updateById(Long id, TDto dto);

    public ResponseEntity<TDto> getById(Long id);

    public TDto entityToDto(TEntity entity);
    public TEntity dtoToEntity(TDto dto);
}
