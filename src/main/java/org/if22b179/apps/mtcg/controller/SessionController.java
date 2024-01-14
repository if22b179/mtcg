package org.if22b179.apps.mtcg.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.service.SessionService;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

import java.io.IOException;

@Data
public class SessionController extends Controller{

    private final SessionService sessionService;
    @Override
    public boolean supports(String route) {
        return route.startsWith("/sessions");
    }

    @Override
    public Response handle(Request request) {
       if ("POST".equals(request.getMethod())) {
            return login(request);
        } else {
            return status(HttpStatus.BAD_REQUEST, "URL Methode falsch");
        }
    }

    public Response login(Request request) {
        try {
            // Verwenden des User-Objekts direkt für die Anmeldedaten
            ObjectMapper objectMapper = new ObjectMapper();
            User userCredentials = objectMapper.readValue(request.getBody(), User.class);
            boolean loginSuccess = sessionService.login(userCredentials);

            if (loginSuccess) {
                return status(HttpStatus.OK, "Login erfolgreich");
            } else {
                return status(HttpStatus.NOT_FOUND, "Login fehlgeschlagen: Benutzername oder Passwort falsch");
            }
        } catch (IOException e) {
            return status(HttpStatus.BAD_REQUEST, "Fehler bei der Verarbeitung der Anfrage: " + e.getMessage());
        }
    }

}



