package webserver.ecommerce.data.entity.repository;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import webserver.ecommerce.data.entity.UserEntity;

@Repository
@Primary
public interface UserRepository extends BaseRepository<UserEntity, Long> {

    public UserEntity getByUsername(String username);
}
