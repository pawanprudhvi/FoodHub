package FoodHub.Hub.UserServiceImpl;


import FoodHub.Hub.Entity.UserEntity;
import FoodHub.Hub.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.SecureRandom;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EmailService {
    private static final SecureRandom random = new SecureRandom();

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    UserRepo userrepo;

    public void sendOtp(String email)
    {
        UserEntity user=userrepo.findByEmailId(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        String otp=generateOtp();
        SimpleMailMessage message=new SimpleMailMessage();
        message.setSubject("Otp for Password Rest");
        message.setTo(email);
        message.setText("Otp for Password Reset: "+otp);
        mailSender.send(message);

        user.setOtp(otp);
        user.setOtpExpiry(System.currentTimeMillis()+1000*60*60);
        userrepo.save(user);



    }

    private String generateOtp() {

            int otp = 100_000 + random.nextInt(900_000); // Range: 100000 to 999999
            return String.valueOf(otp);
        }
    }
