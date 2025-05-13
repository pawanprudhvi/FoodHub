package FoodHub.Hub.DTO;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    public String getToken() {
        return token;
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
