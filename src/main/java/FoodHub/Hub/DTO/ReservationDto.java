package FoodHub.Hub.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReservationDto {

    private LocalDate reservationDate;

    public ReservationDto(LocalDate reservationDate, LocalDateTime reservationTime, int count) {
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.count = count;
    }

    @Override
    public String toString() {
        return "ReservationDto{" +
                "reservationDate=" + reservationDate +
                ", reservationTime=" + reservationTime +
                ", count=" + count +
                '}';
    }

    public ReservationDto() {
    }

    public LocalDateTime getReservationTime() {
        return reservationTime;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public int getCount() {
        return count;
    }
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime reservationTime;
    private int count;
}
