package FoodHub.Hub.UserServiceImpl;

import FoodHub.Hub.DTO.*;
import FoodHub.Hub.Entity.*;
import FoodHub.Hub.JwtUtil.JwtUtil;
import FoodHub.Hub.OtpService.Otpserviceimpl;
import FoodHub.Hub.Repository.*;
import FoodHub.Hub.UserService.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TableRepository tablerepository;

    @Autowired
    ReservationsRepo reservationsrepo;

    @Autowired
    ReservationHistoryRepo reservationHistoryRepo;


    @PreDestroy
    public void destroy() {
        System.out.print("userserviceimpl is no more");
    }



    public AuthenticationResponse saveUser(UserEntity user) throws Exception {
        if (!userrepo.existsByEmailId(user.getEmailId())) {
            String password = encoder.encode(user.getPassword());
            user.setPassword(password);
            user.setRole(Role.USER);

            UserEntity newUser = userrepo.save(user);
            String username = newUser.getUsername();
            String token = jwtutil.generateToken(user, Role.USER.toString());
            return new AuthenticationResponse(username, token);
        }
        return null;


    }

    @Override
    public List<UserEntity> getAllUsers() {
        return userrepo.findAll();
    }

    @Override
    public void truncateUsers(String tablename) {
        databaseservice.truncateUsers(tablename);
    }

    public UserEntity getUser(Long id) {
        UserEntity user = userrepo.findById(id).orElse(null);
        return user;
    }

    public UserEntity findUserByEmail(String email) {
        UserEntity user = userrepo.findByEmailId(email).orElse(null);
        return user;

    }

    public void sendBookingDetailsToMail(HttpServletRequest request, String emailid) {

        if (emailid != null) {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailid);
            message.setSubject("FoodHub booking information");
            message.setText("Reservation Confirmed,Hello, your table has been successfully booked for 20th May at 7 PM");
            javamailsender.send(message);
        }
    }

    public UserEntity updateUser(Long id, Map<String, Object> user) {
        UserEntity currentuser = userrepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        user.forEach((key, value) ->
        {
            Field field = ReflectionUtils.findField(UserEntity.class, key);
            ObjectMapper mapper = new ObjectMapper();
            if (field != null) {
                System.out.print(field.getName() + "");
                if (key.equals("password")) {
                    field.setAccessible(true);
                    String encodedpassword = encoder.encode((String) value);
                    ReflectionUtils.setField(field, currentuser, mapper.convertValue(encodedpassword, field.getType()));
                } else {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, currentuser, mapper.convertValue(value, field.getType()));
                }
            }

        });
        return currentuser;

    }

    public AuthenticationResponse authenticateUser(UserEntity userentity, HttpServletResponse response) throws Exception {
        manager.authenticate(new UsernamePasswordAuthenticationToken(userentity.getEmailId(), userentity.getPassword()));
        UserEntity user = userrepo.findByEmailId(userentity.getEmailId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtutil.generateToken(user, user.getRole().toString());
        ResponseCookie cookie = ResponseCookie.from("jwt", token)
                .httpOnly(true)// Set to true in production with HTTPS
                .sameSite("Lax") // Use "None" if you want cross-origin with credentials
                .path("/")
                .maxAge(600)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        return new AuthenticationResponse(token);

    }

    public AdminAuthResponse authenticateAdmin(Admin admin, HttpServletResponse response) throws Exception {
        System.out.print("user" + "admin.getUser");
        manager.authenticate(new UsernamePasswordAuthenticationToken(admin.getAdminEmail(), admin.getAdminPassword()));
        Admin verifyAdmin = adminRepository.findByAdminEmail(admin.getAdminEmail()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        String token = jwtutil.generateToken(verifyAdmin, verifyAdmin.getRole().toString());
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(false);
        response.addCookie(cookie);
        return new AdminAuthResponse(verifyAdmin.getAdminName(), token);

    }


    public UserDto verifyOtp(String email, String password, String otp) {
        UserEntity user = userrepo.findByEmailId(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(otp);
        if (!otp.equals(user.getOtp())) {
            throw new RuntimeException("Not valid otp");
        } else {
            user.setPassword(encoder.encode(password));
            user.setOtp(null);
            user.setOtpExpiry(null);
            userrepo.save(user);
            return new UserDto(user);
        }


    }

//    public Tables addTable(tablesDto tableRequest) {
//        Tables table=adminRepository.findById(tableRequest.());
//    }

    public Admin createadmin(Admin admin) throws Exception {
        admin.setAdminPassword(encoder.encode(admin.getAdminPassword()));
        admin.setRole(Role.ADMIN);
        Admin newAdmin = adminRepository.save(admin);
        String role = Role.ADMIN.toString();

        String token = jwtutil.generateToken(newAdmin, role);
        return newAdmin;
    }

    public Tables addTable(tablesDto tableRequest) {
        Tables table = tablerepository.save(new Tables(tableRequest.getCapacity()));
        return table;

    }

    private List<Tables> reserveTable(ReservationDto reservation) {
        int bestOverCapacity = Integer.MAX_VALUE;
        List<Tables> bestCombination = new ArrayList<>();
        System.out.print("1234 here we go");
        bestCombination=new ArrayList<>();
        System.out.println(reservation);

        AtomicInteger bestOverCapacityWrapper=new AtomicInteger(bestOverCapacity);

        List<Tables> allTables = tablerepository.findAllByOrderByCapacityDesc();
        allTables.stream().forEach(System.out::println);

        System.out.println(allTables);
        List<Tables> tables = new ArrayList<>();
        findTable(allTables, 0,reservation.getCount(), reservation, tables,bestOverCapacityWrapper,bestCombination);
        System.out.println("tables found");
        tables.stream().forEach(System.out::println);

        return bestCombination;
        }


    public List<TableReservations> bookTable(String emailId, ReservationDto reservations) {
        UserEntity user=userrepo.findByEmailId(emailId).orElseThrow(()->new UsernameNotFoundException(("user not found")));
        List<Tables> tables=reserveTable(reservations);
        List<TableReservations> reservationsconfirmed=new ArrayList<>();
        ReservationHistory reservationHistory=new ReservationHistory();

        reservationHistory.setUser(user);
        reservationHistory.setReservationDate(reservations.getReservationDate());
        reservationHistory.setReservationTime(reservations.getReservationTime());
        reservationHistory.setCapacity(reservations.getCount());
        reservationHistory.setUserName(user.getUsername());
        reservationHistory.setEmailId(user.getEmailId());
        reservationHistoryRepo.save(reservationHistory);



        if(tables!=null)
        {
            for(Tables table:tables)
            {
                TableReservations tableReservation = new TableReservations();
                tableReservation.setUser(user);
                tableReservation.setReservationDate(reservations.getReservationDate());
                tableReservation.setReservationTime(reservations.getReservationTime());
                tableReservation.setTables(table);
                tableReservation.setStatus("Booked");





                reservationsrepo.save(tableReservation);
                reservationsconfirmed.add(tableReservation);
            }
        }
        return reservationsconfirmed;
    }

    private  void findTable(List<Tables> arr,int index,int capacity,ReservationDto reservation,List<Tables> tables,AtomicInteger bestOverCapacity,List<Tables> bestCombination)
    {
        System.out.print("adding list"+arr);
        if(capacity==0)
        {
            bestCombination.clear();
            bestCombination.addAll(new ArrayList<>(tables));
            bestOverCapacity.set(0);
            return;

        }
        if(index==arr.size())
        {
            return;
        }
        if(capacity<0) {
            int overcapacity=-capacity;
            if(overcapacity<bestOverCapacity.get())
            {
                bestOverCapacity.set(overcapacity);
                bestCombination.clear();

                bestCombination.addAll(new ArrayList<>(tables));

            }
            return;


        }
        for(int i=index;i<arr.size();i++)
        {

            List<TableReservations> conflicts=reservationsrepo.findConflicts(arr.get(i).getTableId(),reservation.getReservationTime());
            if(conflicts.isEmpty()) {

                tables.add(arr.get(i));


                findTable(arr, i + 1, capacity-arr.get(i).getCapacity(), reservation, tables,bestOverCapacity,bestCombination);
                tables.removeLast();
            }



        }
    }

}
