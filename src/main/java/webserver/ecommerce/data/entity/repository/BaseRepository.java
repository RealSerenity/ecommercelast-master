package webserver.ecommerce.data.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<TEntity, Long> extends JpaRepository<TEntity, Long> {

}
