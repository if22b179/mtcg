import org.if22b179.apps.mtcg.MtcgApp;
import org.if22b179.apps.mtcg.controller.Controller;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.UserRepo;
import org.if22b179.apps.mtcg.service.UserService;
import org.if22b179.server.http.*;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases {
    ///////////////////////////////////////////////////////////////////////////////////////
    //                                   Repo Tests
    ///////////////////////////////////////////////////////////////////////////////////////
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
    @Test
    void updateUser(){
        User user = new User();
        user.setBio("Probe bio ob es auch funkt");
        user.setImage("HierSollteEinBildSein");
        user.setName("Voller Name");
        user.setUsername("Probe test");

        UserRepo userRepo = new UserRepo();
        User updatedUser = userRepo.update(user);

        assertNotNull(updatedUser.getBio() , "Bio sollte nicht null sein ");
        assertNotNull(updatedUser.getName() , "Name sollte nicht null sein ");
        assertNotNull(updatedUser.getImage() , "Image sollte nicht null sein ");

    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //                               Service Tests
    ///////////////////////////////////////////////////////////////////////////////////////
    @Test
    void serviceCreeateUser(){
        User user = new User();
        user.setUsername("Probe");
        user.setPassword("123");
        UserRepo userRepo = new UserRepo();
        UserService userService = new UserService(userRepo);
        User saved = userService.createUser(user);

        assertNotNull(saved , "Usser sollte nicht null sein ");
    }

    @Test
    void serviceUpdateUser(){
        User user = new User();
        user.setUsername("Probe");
        user.setBio("iwas");
        user.setImage("iwas");
        user.setName("iwas");
        UserRepo userRepo = new UserRepo();
        UserService userService = new UserService(userRepo);
        User saved = userService.updateUser(user);

        assertNotNull(saved , "Usser sollte nicht null sein ");
    }

    ///////////////////////////////////////////////////////////////////////////////////////
    //                              Controller Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    void controllerCreate(){
        Request request = new Request();
        request.setMethod(HttpMethod.POST);
        request.setRoute("/users");
        request.setContentType(HttpContentType.APPLICATION_JSON.getMimeType());
        request.setBody("{\"username\":\"kienboec\", \"password\":\"daniel\"}");

        MtcgApp mtcgApp = new MtcgApp();
        Response response = mtcgApp.handle(request);

        assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
    }

    @Test
    void controllerUpdate(){
        Request request = new Request();
        request.setMethod(HttpMethod.PUT);
        request.setRoute("/users");
        request.setContentType(HttpContentType.APPLICATION_JSON.getMimeType());
        request.setBody("{\"username\":\"kienboec\",\"bio\":\"alksjdhfkajsdhflkajshdf\", \"image\":\"bild\", \"name\":\"harmbe ja\"}");

        MtcgApp mtcgApp = new MtcgApp();
        Response response = mtcgApp.handle(request);

        assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
    }

    @Test
    void conrollerDelete(){
        Request request = new Request();
        request.setMethod(HttpMethod.DELETE);
        request.setRoute("/users/kienboec");
        request.setContentType(HttpContentType.APPLICATION_JSON.getMimeType());


        MtcgApp mtcgApp = new MtcgApp();
        Response response = mtcgApp.handle(request);

        assertEquals(HttpStatus.OK.getCode(), response.getStatusCode());
    }
}

