package FoodHub.Hub.Repository;

import FoodHub.Hub.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Long> {
  Optional<Admin> findByAdminEmail(String email);
}
