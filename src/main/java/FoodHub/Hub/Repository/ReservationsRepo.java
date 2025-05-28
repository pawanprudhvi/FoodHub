package FoodHub.Hub.Repository;

import FoodHub.Hub.Entity.TableReservations;
import FoodHub.Hub.Entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ReservationsRepo extends JpaRepository<TableReservations,Long> {
    boolean existsByReservationDate(LocalDate reservationDate);

    @Query("Select r from TableReservations r where r.tables.tableId=:tableId AND r.reservationTime= :reservationTime")
    List<TableReservations> findConflicts(@Param("tableId") Long tableId,
                                          @Param("reservationTime") LocalDateTime reservationTime);

    TableReservations findByReservationDate(LocalDate reservationDate);

    List<TableReservations> findByUser(UserEntity user);
}
