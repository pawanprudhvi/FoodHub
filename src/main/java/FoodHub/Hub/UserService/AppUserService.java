package FoodHub.Hub.UserService;


import FoodHub.Hub.Entity.UserEntity;
import FoodHub.Hub.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AppUserService implements UserDetailsService {
       @Autowired
       UserRepo userrepo;



    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userentity=userrepo.findByEmailId(email).orElseThrow(()->new UsernameNotFoundException("User Not found"));
        return new User(userentity.getEmailId(),userentity.getPassword(),new ArrayList<>());
    }
}
