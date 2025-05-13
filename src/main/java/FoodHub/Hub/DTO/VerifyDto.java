package FoodHub.Hub.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class VerifyDto {
    @Email
    private String emailId;

    public @Email String getEmailId() {
        return emailId;
    }

    public VerifyDto(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    public void setEmailid(@Email String emailid) {
        this.emailId = emailid;
    }

    public @NotNull String getPassword() {
        return password;
    }

    public void setPassword(@NotNull String password) {
        this.password = password;
    }

    @NotNull
    private String password;
}
