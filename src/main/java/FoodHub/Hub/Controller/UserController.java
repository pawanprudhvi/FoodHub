package FoodHub.Hub.Controller;


import FoodHub.Hub.Entity.UserEntity;
import FoodHub.Hub.UserService.UserService;
import FoodHub.Hub.UserServiceImpl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class UserController {


    @Autowired
    UserServiceImpl userserviceimpl;

    @GetMapping("/allusers")
    public List<UserEntity> getAllUsers()
    {
        return userserviceimpl.getAllUsers();
    }

    @PostMapping("/newuser")
    public UserEntity createUser(@RequestBody UserEntity userentity)
    {
        System.out.print(userentity.getEmailId()+" 1234567");
        return userserviceimpl.saveUser(userentity);

    }
    @PostMapping("/truncate/{tablename}")
    public String truncateUsers(@PathVariable String tablename)
    {
        userserviceimpl.truncateUsers(tablename);
        return "Truncate successful";
    }

    @GetMapping("/user/{id}")
    public UserEntity getUser(@PathVariable Long id) {
        UserEntity user = userserviceimpl.getUser(id);
        return user;
    }

}
