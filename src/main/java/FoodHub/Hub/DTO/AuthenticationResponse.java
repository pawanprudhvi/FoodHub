package FoodHub.Hub.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {

    private String userName;
    public String getToken() {
        return token;
    }

    public AuthenticationResponse(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }

    @Override
    public String toString() {
        return "AuthenticationResponse{" +
                "token='" + token + '\'' +
                '}';
    }

    public AuthenticationResponse(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String token;
}
