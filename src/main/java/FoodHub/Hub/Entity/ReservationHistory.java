package FoodHub.Hub.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ReservationHistory {

    public ReservationHistory()
    {

    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long historyId;

    public ReservationHistory(Long historyId, String emailId, String username, LocalDate reservationDate, LocalDateTime reservationTime, int capacity, UserEntity user) {
        this.historyId = historyId;
        this.emailId = emailId;
        this.username = username;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.capacity = capacity;
        this.user = user;
    }

    public void setHistoryId(Long historyId) {
        this.historyId = historyId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public void setReservationTime(LocalDateTime reservationTime) {
        this.reservationTime = reservationTime;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    private String emailId;

    private String username;

    @Override
    public String toString() {
        return "ReservationHistory{" +
                "historyId=" + historyId +
                ", emailId='" + emailId + '\'' +
                ", username='" + username + '\'' +
                ", reservationDate=" + reservationDate +
                ", reservationTime=" + reservationTime +
                ", capacity=" + capacity +
                ", user=" + user +
                '}';
    }

    private LocalDate reservationDate;

    private LocalDateTime reservationTime;

    private int capacity;

    @ManyToOne
    @JsonBackReference
    @JsonIgnoreProperties("user")
    private UserEntity user;

    public Long getHistoryId() {
        return historyId;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public UserEntity getUser() {
        return user;
    }
}
