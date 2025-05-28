package FoodHub.Hub.Controller;


import FoodHub.Hub.DTO.ReservationDto;
import FoodHub.Hub.Entity.TableReservations;
import FoodHub.Hub.Entity.UserEntity;
import FoodHub.Hub.Repository.ReservationsRepo;
import FoodHub.Hub.Repository.UserRepo;
import FoodHub.Hub.UserServiceImpl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    UserServiceImpl userserviceimpl;

    @Autowired
    UserRepo userrepo;

    @Autowired
    ReservationsRepo reservationRepo;

    @PostMapping("table/reserve")
    public ResponseEntity<?> reserveTable(@CurrentSecurityContext(expression="authentication?.name") String emailId, @RequestBody ReservationDto reservations, HttpServletRequest request)
    {

        List<TableReservations> tablereservations=userserviceimpl.bookTable(emailId,reservations);
        if(!tablereservations.isEmpty())
        {
            userserviceimpl.sendBookingDetailsToMail(request,emailId);
            return ResponseEntity.status(200).body(tablereservations);
        }
        else {
            throw new IllegalStateException("Sorry, no tables found");
        }


    }

    @GetMapping("/profile")
    public ResponseEntity<?> getBookingHistory(@CurrentSecurityContext(expression="authentication?.name") String emailId)
    {
        UserEntity user=userrepo.findByEmailId(emailId).orElseThrow(()->new UsernameNotFoundException("user not found for this email id"));
        List<TableReservations> reservationList=reservationRepo.findByUser(user);
        return ResponseEntity.status(200).body(reservationList);
    }

    @GetMapping("/verifytoken")
    public ResponseEntity<?> verifyToken(@CurrentSecurityContext(expression="authentication?.name") String emailId) {
        return ResponseEntity.status(200).body("user verified");
    }




}
