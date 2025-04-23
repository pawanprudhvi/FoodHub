package FoodHub.Hub.UserServiceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class DatabaseService {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    public void truncateUsers(String tablename)
    {
        String query = "TRUNCATE" +" "+"TABLE "+ tablename +" RESTART IDENTITY";
        entityManager.createNativeQuery(query).executeUpdate();
    }
}

