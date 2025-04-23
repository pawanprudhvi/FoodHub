package FoodHub.Hub.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class UserEntity {
    public void setUserId(long userId) {
        UserId = userId;
    }

    public long getUserId() {
        return UserId;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "UserId=" + UserId +
                ", emailId='" + emailId + '\'' +
                '}';
    }
    UserEntity()
    {

    }

    public @Email String getEmailId() {
        return emailId;
    }

    public void setEmailId(@Email String emailId) {
        this.emailId = emailId;
    }

    public UserEntity(long userId, String emailId) {
        UserId = userId;
        this.emailId = emailId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long UserId;

    @Email
    private String emailId;
}
