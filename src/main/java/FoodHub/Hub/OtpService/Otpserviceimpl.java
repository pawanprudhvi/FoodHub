package FoodHub.Hub.OtpService;


import org.springframework.stereotype.Service;

@Service
public class Otpserviceimpl {

    public int getOtp()
    {
        return (int)(Math.random()*900000);
    }
}
