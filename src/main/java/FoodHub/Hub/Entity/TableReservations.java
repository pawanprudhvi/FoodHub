package FoodHub.Hub.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Entity
public class TableReservations {
    public TableReservations() {

    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Tables getTables() {
        return tables;
    }

    public void setTables(Tables table) {
        this.tables= table;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long reservationId;

    @ManyToOne
    @JsonIgnoreProperties({"reservations","password", "otp", "otpExpiry"})
    private UserEntity user;

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public TableReservations(UserEntity user, Tables tables, LocalDateTime reservationTime, LocalDate reservationDate, String status) {
        this.user = user;
        this.tables = tables;
        this.reservationTime = reservationTime;
        this.reservationDate = reservationDate;
        this.status = status;
    }

    public void setReservationTime(LocalDateTime reservationTimet) {
        this.reservationTime = reservationTimet;
    }

    public LocalDate getReservatioDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    @ManyToOne
    @JsonIgnoreProperties("reservations")
    private Tables tables;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime reservationTime;

    private LocalDate reservationDate;

    private String status;

}
