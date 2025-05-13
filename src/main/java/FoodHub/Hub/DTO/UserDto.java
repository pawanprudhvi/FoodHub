package FoodHub.Hub.DTO;

import FoodHub.Hub.Entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserDto {

    @Autowired
    PasswordEncoder encoder;


    private String emailId;
    private String password;

    public String getEmailId() {
        return emailId;
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

    public UserDto(UserEntity user)
    {
        this.emailId=user.getEmailId();
        this.password=user.getPassword();
    }
}
