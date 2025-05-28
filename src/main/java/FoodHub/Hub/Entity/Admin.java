package FoodHub.Hub.Entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@Valid
public class Admin implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long adminId;

    public Admin(Long adminId, String adminEmail, String adminName, String adminPassword, LocalDate dob) {
        this.adminId = adminId;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.dob = dob;
    }

    private String adminEmail;

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    private String adminName;

    private String adminPassword;

    private LocalDate dob;

    public Admin(Long adminId, String adminName, String adminPassword, LocalDate dob) {
        this.adminId = adminId;

        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.dob = dob;
    }

    public String getAdminPassword() {
        return adminPassword;
    }
    public Admin()
    {

    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Admin(Long adminId, String adminName, LocalDate dob) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.dob = dob;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public Role getRole() {
        return role;
    }

    public Admin(Long adminId, String adminEmail, String adminName, String adminPassword, LocalDate dob, Role role) {
        this.adminId = adminId;
        this.adminEmail = adminEmail;
        this.adminName = adminName;
        this.adminPassword = adminPassword;
        this.dob = dob;
        this.role = role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role!=null?List.of(new SimpleGrantedAuthority(role.name())):List.of();
    }
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public String getPassword() {
        return this.getAdminPassword();
    }

    @Override
    public String getUsername() {
        return this.adminEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
