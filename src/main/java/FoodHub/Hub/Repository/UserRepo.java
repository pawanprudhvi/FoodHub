package FoodHub.Hub.Repository;

import FoodHub.Hub.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByEmailId(String email);
    boolean existsByEmailId(String email);
}
