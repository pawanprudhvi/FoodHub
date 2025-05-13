package FoodHub.Hub.Controller;


import FoodHub.Hub.DTO.*;
import FoodHub.Hub.Entity.UserEntity;
import FoodHub.Hub.JwtUtil.JwtUtil;
import FoodHub.Hub.UserService.AppUserService;
import FoodHub.Hub.UserService.UserService;
import FoodHub.Hub.UserServiceImpl.EmailService;
import FoodHub.Hub.UserServiceImpl.UserServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.http.HttpResponse;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/")
@CrossOrigin(
        origins = "http://localhost:3000",  // Your frontend origin
        allowCredentials = "true")
public class UserController {

    @Autowired
    WebClient webclient;


    @Autowired
    UserServiceImpl userserviceimpl;

    @Autowired
    JwtUtil jwtutil;

    @Autowired
    AppUserService appuserservice;

    @Autowired
    EmailService emailService;

    @Autowired
    public UserController(UserServiceImpl userserviceimpl,
                          JwtUtil jwtutil,
                          AppUserService appuserservice)
    {
        this.userserviceimpl = userserviceimpl;
        this.jwtutil = jwtutil;
        this.appuserservice = appuserservice;
    }

    @GetMapping("/allusers")
    public List<UserEntity> getAllUsers() {
        return userserviceimpl.getAllUsers();
    }
    @PostMapping("/verifyuser")
    public AuthenticationResponse verifyUser(@RequestBody UserEntity userentity, HttpServletRequest request, HttpServletResponse response) throws Exception {
            AuthenticationResponse authresponse=userserviceimpl.authenticsteUser(userentity,response);
        return authresponse;


    }

    @PostMapping("user/reset-password")
    public UserDto resetPassword(@RequestBody OtpRequest otprequest)
    {
        UserDto user=userserviceimpl.verifyOtp(otprequest.getEmailId(),otprequest.getPassword(),otprequest.getOtp());
        return user;
    }

    @GetMapping("/user/getOtp")
    public void sendOtp(@CurrentSecurityContext(expression="authentication?.name")String email)
    {
        emailService.sendOtp(email);
    }


    @GetMapping("/verifyotp/{otpNo}")
    @ResponseBody
    public String verifyotp(@PathVariable String otpNo,HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        System.out.println(session.getId()+" "+(String) session.getAttribute("generatedOtp"));

        if (session != null)
        {
            System.out.println(session.getId()+" "+(String) session.getAttribute("email"));
            System.out.println(session.getId()+" "+(String) session.getAttribute("generatedOtp"));
            String actualotp=(String)session.getAttribute("generatedOtp");  // Don't create a new session if one doesn't exist
            if(otpNo.equals(actualotp))
            {
                System.out.print("Yes they are same");
                return "Yes";
            }
            // Check if the JSESSIONID cookie was received (you might not need to explicitly check the cookie)
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                Optional<Cookie> jsessionidCookie = Arrays.stream(cookies)
                        .filter(cookie -> "JSESSIONID".equals(cookie.getName()))
                        .findFirst();
                if (jsessionidCookie.isPresent()) {

                    System.out.println("JSESSIONID Cookie Value: " + jsessionidCookie.get().getValue()+" "+jsessionidCookie.toString());
                } else {
                    System.out.println("JSESSIONID Cookie NOT found in this request (though CORS allows credentials).");
                }
            } else {
                System.out.println("No cookies found in this request.");
            }

            // Now, check for session attributes
            String userId = (String) session.getAttribute("userId"); // Example attribute
            if (userId != null) {
                System.out.println("User ID found in session: " + userId);
                // Your OTP verification logic here, potentially using the userId from the session
                return "OTP Verification Attempted for User ID: " + userId + ", OTP:";
            }
        }
        return "No";
    }



    @PostMapping("/newuser")
    public ResponseEntity<?> createUser(@RequestBody @Valid UserEntity userentity,BindingResult bindingResult) throws Exception {
        System.out.print(userentity.getEmailId() + " 1234567");
        if (bindingResult.hasErrors()) {
            // Collect all error messages
            StringBuilder errorMessages = new StringBuilder();
            bindingResult.getAllErrors().forEach(error -> {
                errorMessages.append(error.getDefaultMessage()).append("\n");
            });

            // Return BAD_REQUEST (400) if validation fails
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessages.toString());

        } else {
            AuthenticationResponse user = userserviceimpl.saveUser(userentity);

            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
    }

    @GetMapping("/lotr")
    public List<Book> lotr()
    {
        Bookresponse response = webclient.get()
                 .uri("https://openlibrary.org/search.json?q=the+lord+of+the+rings")
                 .retrieve()
                 .bodyToMono(Bookresponse.class)
                 .block();
         return response.getDocs();
    }

    @GetMapping("/user/{id}")
    public UserEntity getUser(@PathVariable Long id, HttpSession session)
    {UserEntity user = userserviceimpl.getUser(id);
        return user;
    }

    @PatchMapping("/user/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,@RequestBody Map<String,Object> user)
    {
        UserEntity userentity=userserviceimpl.getUser(id);
        if(userentity!=null)
        {
            UserEntity updatedUser=userserviceimpl.updateUser(id,user);
            return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}
