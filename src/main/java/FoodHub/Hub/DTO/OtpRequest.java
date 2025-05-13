package FoodHub.Hub.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;


@Getter
@Setter
@NoArgsConstructor // Add this for Spring's data binding
@AllArgsConstructor // Add this if you use constructors to create instances
public class OtpRequest {
    private String emailId;
    private String password;
    private String otp;

    public String getEmailId() {
        return emailId;
    }

    @Override
    public String toString() {
        return "OtpRequest{" +
                "emailId='" + emailId + '\'' +
                ", password='" + password + '\'' +
                ", otp='" + otp + '\'' +
                '}';
    }

    public OtpRequest(String emailId, String password, String otp) {
        this.emailId = emailId;
        this.password = password;
        this.otp = otp;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
