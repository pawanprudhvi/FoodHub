package FoodHub.Hub.Repository;

import FoodHub.Hub.Entity.TableReservations;
import FoodHub.Hub.Entity.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface TableRepository extends JpaRepository<Tables,Long> {
    List<Tables> findAllByOrderByCapacityDesc();
}
