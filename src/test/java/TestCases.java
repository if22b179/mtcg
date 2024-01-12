import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.UserRepo;
import org.if22b179.server.http.HttpContentType;
import org.if22b179.server.http.HttpMethod;
import org.if22b179.server.http.Request;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases {


    @Test
    void saveUserInDB() {
        User user = new User();
        user.setUsername("Probe test");
        user.setPassword("test");

        UserRepo userRepo = new UserRepo();
        User savedUser = userRepo.save(user);

        assertNotNull(savedUser , "Usser sollte nicht null sein ");

    }

    @Test
    void delUser(){

        UserRepo userRepo = new UserRepo();
        assertTrue(userRepo.findById("Probe test").isPresent(), "Sollte existieren");
        userRepo.deleteById("Probe test");
        Optional<User> deletedUser = userRepo.findById("Probe test");
        assertTrue(deletedUser.isEmpty(), "User should be deleted");
    }
}

