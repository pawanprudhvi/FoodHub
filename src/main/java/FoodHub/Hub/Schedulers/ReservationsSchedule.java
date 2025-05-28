package FoodHub.Hub.Schedulers;

import FoodHub.Hub.Entity.TableReservations;
import FoodHub.Hub.Repository.ReservationsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ReservationsSchedule {


    @Autowired
    ReservationsRepo reservations;


    @Scheduled(cron = "*/10 * * * * *")
    public void insertReservationsRecord()
    {
        LocalDate today= LocalDate.now();
        for(int i=0;i<7;i++)
        {
            LocalDate nextday=today.plusDays(i);
            TableReservations tablereserve=new TableReservations();
            tablereserve.setReservationDate(nextday);
            boolean reserve=reservations.existsByReservationDate(nextday);
            if(!reserve) {
                reservations.save(tablereserve);
            }

        }
    }

}
