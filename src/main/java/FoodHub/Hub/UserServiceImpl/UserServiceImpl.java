package FoodHub.Hub.UserServiceImpl;

import FoodHub.Hub.Entity.UserEntity;
import FoodHub.Hub.Repository.UserRepo;
import FoodHub.Hub.UserService.UserService;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userrepo;

    @Autowired
    DatabaseService databaseservice;

    public UserEntity saveUser(UserEntity user)
    {
        UserEntity newUser=userrepo.save(user);
        return newUser;
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
}
