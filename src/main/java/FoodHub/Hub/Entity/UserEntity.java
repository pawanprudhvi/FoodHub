package FoodHub.Hub.Entity;

import FoodHub.Hub.Validations.ValidPaField;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
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
@NoArgsConstructor
@AllArgsConstructor
@Valid
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserEntity implements UserDetails {
    public UserEntity(String password, long userId, String emailId) {
        this.password = password;
        UserId = userId;
        this.emailId = emailId;
    }


    @Enumerated(EnumType.STRING)
    private Role role;

    public void setUserId(long userId) {
        UserId = userId;
    }
    private String password;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role!=null?List.of(new SimpleGrantedAuthority(role.name())):List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return this.emailId;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public long getUserId() {
        return UserId;
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

    public UserEntity(Role role, String password, long userId, String emailId) {
        this.role = role;
        this.password = password;
        UserId = userId;
        this.emailId = emailId;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long UserId;

    @Email
    @NotNull
    @Column(unique=true)
    private String emailId;

    public Role getRole() {
        return role;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "role=" + role +
                ", password='" + password + '\'' +
                ", UserId=" + UserId +
                ", emailId='" + emailId + '\'' +
                ", otp='" + otp + '\'' +
                ", otpExpiry=" + otpExpiry +
                '}';
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserEntity(Role role, String password, long userId, String emailId, String otp, Long otpExpiry) {
        this.role = role;
        this.password = password;
        UserId = userId;
        this.emailId = emailId;
        this.otp = otp;
        this.otpExpiry = otpExpiry;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public void setOtpExpiry(Long otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    public String getOtp() {
        return otp;
    }

    public Long getOtpExpiry() {
        return otpExpiry;
    }

    private String otp;

    private Long otpExpiry;
}
