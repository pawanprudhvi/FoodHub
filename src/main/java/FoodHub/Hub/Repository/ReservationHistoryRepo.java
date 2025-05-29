package FoodHub.Hub.Repository;

import FoodHub.Hub.Entity.ReservationHistory;
import FoodHub.Hub.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationHistoryRepo extends JpaRepository<ReservationHistory,Long> {
    List<ReservationHistory> findByUser(UserEntity user);
}
