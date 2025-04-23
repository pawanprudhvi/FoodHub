package FoodHub.Hub.UserService;

import FoodHub.Hub.Entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {


    List<UserEntity> getAllUsers();

    void truncateUsers(String tablename);

}
