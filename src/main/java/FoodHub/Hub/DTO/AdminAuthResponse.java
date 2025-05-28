package FoodHub.Hub.DTO;

public class AdminAuthResponse {
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AdminAuthResponse(String adminName, String token) {
        this.adminName = adminName;
        this.token = token;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    private String adminName;
    private String token;
}
