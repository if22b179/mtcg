package org.if22b179.apps.mtcg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.service.UserService;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

import java.util.Optional;

@Data
public class UserController extends Controller{

    private final UserService userService;
    @Override
    public boolean supports(String route) { return route.startsWith("/users");}

    @Override
    public Response handle(Request request) {
        switch (request.getMethod()) {
            case "GET":
                return getUser(request);
            case "POST":
                return createUser(request);
            case "PUT":
                return updateUser(request);
            case "DELETE":
                return deleteUser(request);
            default:
                return status(HttpStatus.BAD_REQUEST, "URL Methode falsch");
        }
    }

    private Response deleteUser(Request request) {
        // Annahme: Die Benutzer-ID oder der Benutzername ist der zweite Parameter in der Route.
        // Beispiel: /users/{username}
        String[] routeParts = request.getRoute().split("/");
        if (routeParts.length < 3) {
            return status(HttpStatus.BAD_REQUEST, "Invalid URL format");
        }
        String username = routeParts[2];

        // Aufruf des UserService, um den Benutzer zu löschen
        boolean isDeleted = userService.deleteUser(username);
        if (isDeleted) {
            return status(HttpStatus.OK, "User deleted successfully");
        } else {
            return status(HttpStatus.NOT_FOUND, "User not found");
        }
    }

    private Response updateUser(Request request) {
        System.out.println("Wieso nicht ");
        try {
            // Daten aus request holen
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(request.getBody(), User.class);

            // update bei user durchführen
            User updatedUser = userService.updateUser(user);
            System.out.println("nach update");
            if (updatedUser != null) {
                // Erstellen und Zurückgeben einer erfolgreichen Antwort
                String updatedUserJson = objectMapper.writeValueAsString(updatedUser);
                return status(HttpStatus.OK, "Update erfolgreich: " + updatedUserJson);
            } else {
                // Der Benutzer wurde nicht gefunden
                return status(HttpStatus.NOT_FOUND, "User not found");
            }
        } catch (JsonProcessingException e) {
            // Fehler beim Deserialisieren der Benutzerdaten
            return status(HttpStatus.BAD_REQUEST, "Invalid user data");
        }
    }

    private Response createUser(Request request) {
        System.out.println("Drinnen in create user");
        try {
            // Deserialisieren des Benutzerobjekts aus dem Anfragekörper
            ObjectMapper objectMapper = new ObjectMapper();
            User user = objectMapper.readValue(request.getBody(), User.class);
            // Erstellen des Benutzers über den UserService
            User createdUser = userService.createUser(user);
            if (createdUser != null) {
                // Erstellen und Zurückgeben einer erfolgreichen Antwort
                String createdUserJson = objectMapper.writeValueAsString(createdUser);
                return status(HttpStatus.OK, "User Created: " +createdUserJson);
            } else {
                // Der Benutzer konnte nicht erstellt werden (z.B. weil der Benutzername bereits existiert)
                return status(HttpStatus.BAD_REQUEST, "User could not be created");
            }
        } catch (JsonProcessingException e) {
            // Fehler beim Deserialisieren der Benutzerdaten
            return status(HttpStatus.BAD_REQUEST, "Invalid user data");
        }
    }

    private Response getUser(Request request) {
        // Annahme: Die Benutzer-ID oder der Benutzername ist der zweite Parameter in der Route
        // Beispiel: /users/{username}
        String[] routeParts = request.getRoute().split("/");
        if (routeParts.length < 3) {
            return status(HttpStatus.BAD_REQUEST, "Invalid URL format");
        }
        String username = routeParts[2];

        // Abrufen des Benutzers über den UserService
        Optional<User> user = userService.getUser(username);
        if (user.isPresent()) {
            // Erfolgreiche Antwort mit den Benutzerdaten
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String userJson = objectMapper.writeValueAsString(user.get());

                return status(HttpStatus.OK, "User gefunden: " + userJson);
            } catch (JsonProcessingException e) {
                return status(HttpStatus.BAD_REQUEST, "Error processing JSON"); // andere HTTPStatus ausdenken
            }
        } else {
            // Benutzer wurde nicht gefunden
            return status(HttpStatus.NOT_FOUND, "User not found");
        }
    }
}
