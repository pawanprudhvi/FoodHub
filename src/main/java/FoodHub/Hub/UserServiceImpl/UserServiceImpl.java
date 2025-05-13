package FoodHub.Hub.UserServiceImpl;

import FoodHub.Hub.DTO.AuthenticationResponse;
import FoodHub.Hub.DTO.UserDto;
import FoodHub.Hub.Entity.Role;
import FoodHub.Hub.Entity.UserEntity;
import FoodHub.Hub.JwtUtil.JwtUtil;
import FoodHub.Hub.OtpService.Otpserviceimpl;
import FoodHub.Hub.Repository.UserRepo;
import FoodHub.Hub.UserService.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userrepo;

    @Autowired
    DatabaseService databaseservice;

    @Autowired
    JavaMailSender javamailsender;

    @Autowired
    Otpserviceimpl otpservice;
//
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtil jwtutil;

    @Autowired
    AuthenticationManager manager;

    public AuthenticationResponse saveUser(UserEntity user) throws Exception {
        if(!userrepo.existsByEmailId(user.getEmailId())) {
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
            user.setRole(Role.USER);

            UserEntity newUser = userrepo.save(user);
            String token=jwtutil.generateToken(user);
            return new AuthenticationResponse(token);
        }
        return null;



    }
    @Override
    public List<UserEntity> getAllUsers() {
        return userrepo.findAll();
    }

    @Override
    public void truncateUsers(String tablename)
    {
        databaseservice.truncateUsers(tablename);
    }

    public UserEntity getUser(Long id) {
        UserEntity user=userrepo.findById(id).orElse(null);
        return user;
    }

    public UserEntity findUserByEmail(String email) {
        UserEntity user=userrepo.findByEmailId(email).orElse(null);
        return user;

    }

    public String sendOtpToMail(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String emailid = (String) session.getAttribute("email");

        if (emailid != null) {

            String otp = String.valueOf(otpservice.getOtp());
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo((String) session.getAttribute("email"));
            message.setSubject("OTP for login");
            message.setText("Otp for login:" + " " + otp);
            javamailsender.send(message);
            return otp;
        }
        return "";
    }

    public UserEntity updateUser(Long id, Map<String,Object> user)
    {
        UserEntity currentuser=userrepo.findById(id).orElseThrow(()->new UsernameNotFoundException("Username not found"));
        user.forEach((key,value)->
        {
            Field field= ReflectionUtils.findField(UserEntity.class,key);
            ObjectMapper mapper=new ObjectMapper();
            if(field!=null)
            {
                System.out.print(field.getName()+"");
                if(key.equals("password"))
                {
                    field.setAccessible(true);
                    String encodedpassword=encoder.encode((String)value);
                    ReflectionUtils.setField(field, currentuser, mapper.convertValue(encodedpassword, field.getType()));
                }
                else {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, currentuser, mapper.convertValue(value, field.getType()));
                }
            }

        });
        return currentuser;

    }

    public AuthenticationResponse authenticsteUser(UserEntity userentity, HttpServletResponse response) throws Exception {
        manager.authenticate(new UsernamePasswordAuthenticationToken(userentity.getEmailId(),userentity.getPassword()));
        UserEntity user=userrepo.findByEmailId(userentity.getEmailId()).orElseThrow(()->new UsernameNotFoundException("User not found"));
        String token=jwtutil.generateToken(user);
        Cookie cookie=new Cookie("jwt",token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
        return new AuthenticationResponse(token);

    }

    public UserDto verifyOtp(String email, String password,String otp) {
        UserEntity user=userrepo.findByEmailId(email).orElseThrow(()->new UsernameNotFoundException("User not found"));
        System.out.println(otp);
        if(!otp.equals(user.getOtp()))
        {
           throw new RuntimeException("Not valid otp");
        }
        else {
            user.setPassword(encoder.encode(password));
            user.setOtp(null);
            user.setOtpExpiry(null);
            userrepo.save(user);
            return new UserDto(user);
        }




    }
}
