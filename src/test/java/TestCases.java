import org.if22b179.apps.mtcg.MtcgApp;
import org.if22b179.apps.mtcg.controller.Controller;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.CardRepo;
import org.if22b179.apps.mtcg.repository.UserRepo;
import org.if22b179.apps.mtcg.service.UserService;
import org.if22b179.server.http.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TestCases {
    ///////////////////////////////////////////////////////////////////////////////////////
    //                                   Repo Tests
    ///////////////////////////////////////////////////////////////////////////////////////

    @Test
    void increaseUserCoins() {
        UserRepo userRepo = new UserRepo();
        userRepo.updateCoins("altenhof", 5);
        Optional<User> user = userRepo.findById("altenhof");

        assertEquals(5, user.get().getCoins(), "Münzen sollten nach bearbeitung 5 betragen");
    }

    @Test
    void decreaseUserCoinsOnPurchase() {
        UserRepo userRepo = new UserRepo();
        userRepo.updateCoins("altenhof", -5);
        Optional<User> user = userRepo.findById("altenhof");

        assertEquals(-5, user.get().getCoins(), "Münzen sollten nach der bearbeitung -5 betragen");
    }
    @Test
    void findUser() {
        UserRepo userRepo = new UserRepo();
        Optional<User> user = userRepo.findById("altenhof");

        assertTrue(user.isPresent(), "User sollte vorhanden sein mit richtigen Anmeldeinformationen");
    }

    @Test
    void failFindUser() {
        UserRepo userRepo = new UserRepo();
        Optional<User> user = userRepo.findById("nicht drinnen ");

        assertFalse(user.isPresent(), "User sollte nicht vorhanden sein mit falschen Anmeldeinformationen");
    }
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

    ///////////////////////////////////////////////////////////////////
    //                             CardTests
    ///////////////////////////////////////////////////////////////////

    @Test
    void cardSave(){
        Card card = new Card();
        card.setId("1");
        card.setName("Probe");
        card.setDamage(70.0);
        card.setCardType(Card.CardType.MONSTER);
        card.setElementType(Card.ElementType.NORMAL);
        card.setPackageId("iwas");

        CardRepo cardRepo = new CardRepo();
        Card savedUser = cardRepo.save(card);

        assertNotNull(savedUser , "Usser sollte nicht null sein ");

    }

    @Test
    void cardFind(){

        CardRepo cardRepo = new CardRepo();
        Optional<Card> savedUser = cardRepo.findById("1");

        System.out.println(savedUser.toString());

        assertNotNull(savedUser , "Usser sollte nicht null sein ");

    }

    @Test
    void cardUpdate(){

        CardRepo cardRepo = new CardRepo();
        Optional<Card> savedUser = cardRepo.findById("1");
        savedUser.get().setOwnerUsername("admin");

        Card neu = cardRepo.update(savedUser.get());
        System.out.println(neu.toString());

        assertNotNull(neu , "Usser sollte nicht null sein ");

    }

    @Test
    void cardDel(){
        CardRepo cardRepo = new CardRepo();
        cardRepo.deleteById("1");
        Optional<Card> card = cardRepo.findById("1");
        assertNull(card, "Usser sollte null sein ");

    }

    @Test
    void cardSaveFailureWithNullCard() {
        Card card = null; // Versuch, ein null-Objekt zu speichern

        CardRepo cardRepo = new CardRepo();
        assertThrows(RuntimeException.class, () -> cardRepo.save(card), "Sollte eine Exception werfen, wenn eine null-Karte gespeichert wird");
    }

    @Test
    void cardFindNonExistent() {
        CardRepo cardRepo = new CardRepo();
        String nonExistentCardId = "nonExistentId";
        Optional<Card> nonExistentCard = cardRepo.findById(nonExistentCardId);

        assertFalse(nonExistentCard.isPresent(), "Sollte false sein, da die Karte nicht existiert");
    }

    @Test
    void cardSaveDuplicate() {
        Card card = new Card();
        card.setId("duplicateCardId"); // ID, die bereits in der Datenbank existiert
        card.setName("Goblin");
        card.setDamage(10.0);

        CardRepo cardRepo = new CardRepo();
        assertThrows(RuntimeException.class, () -> cardRepo.save(card), "Sollte eine Exception werfen, wenn eine Karte mit doppelter ID gespeichert wird");
    }

    @Test
    void cardDeleteButKeepInRepo() {
        CardRepo cardRepo = new CardRepo();
        String cardIdToDelete = "existingCardId";
        cardRepo.deleteById(cardIdToDelete);

        // Versuch, die Karte zu löschen, aber prüfe, ob sie immer noch vorhanden ist (das sollte nicht passieren)
        Optional<Card> cardStillExists = cardRepo.findById(cardIdToDelete);
        assertTrue(cardStillExists.isPresent(), "Karte sollte nicht vorhanden sein nach dem Löschen");
    }
}

