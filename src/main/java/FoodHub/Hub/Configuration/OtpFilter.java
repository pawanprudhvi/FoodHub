//package FoodHub.Hub.Configuration;
//
//import FoodHub.Hub.OtpService.Otpserviceimpl;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.AuthenticationConverter;
//import org.springframework.security.web.authentication.AuthenticationFilter;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//public class OtpFilter  extends UsernamePasswordAuthenticationFilter {
//
//    private final AuthenticationManager authenticationManager;
//    private final Otpserviceimpl otpServiceimpl;
//    public OtpFilter(AuthenticationManager authenticationManager, Otpserviceimpl otpservice) {
//        this.authenticationManager=authenticationManager;
//        this.otpServiceimpl=otpservice;
//    }
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
//            throws AuthenticationException {
//
//        HttpSession session = request.getSession(false);
//        String otp = request.getParameter("otp");
//
//        if (session == null || session.getAttribute("email") == null) {
//            throw new BadCredentialsException("Session expired or email missing.");
//        }
//
//        String email = (String) session.getAttribute("email");
//
//        if (!otpServiceimpl.validateOtp(email, otp)) {
//            throw new BadCredentialsException("Invalid OTP");
//        }
//
//        UsernamePasswordAuthenticationToken authRequest =
//                new UsernamePasswordAuthenticationToken(email, null);
//        return authenticationManager.authenticate(authRequest);
//    }
//}
//}
