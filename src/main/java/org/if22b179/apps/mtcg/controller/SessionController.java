package org.if22b179.apps.mtcg.controller;

import lombok.Data;
import org.if22b179.apps.mtcg.service.SessionService;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;
@Data
public class SessionController extends Controller{

    private final SessionService sessionService;
    @Override
    public boolean supports(String route) {
        return route.startsWith("/sessions");
    }

    @Override
    public Response handle(Request request) {
       /* if ("POST".equals(request.getMethod())) {
            return login(request);
        } else {
            return status(HttpStatus.BAD_REQUEST, "URL Methode falsch");
        }
    }

    public boolean login(String username, String password) {
        // Überprüfen der Benutzerdaten
        User user = userRepo.findByUsernameAndPassword(username, password);

        // Überprüfen, ob ein Benutzer gefunden wurde und das eingegebene Passwort korrekt ist
        if (user != null && user.getPassword().equals(password)) {
            return true;
        }

        return false; // Gibt false zurück, wenn der Benutzer nicht gefunden wurde oder das Passwort falsch ist

        */
        return null;
    }


}
