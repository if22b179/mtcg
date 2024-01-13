package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.UserRepo;

import java.util.Optional;

@Data
public class SessionService {

    private final UserRepo userRepo;

    public boolean login(String username, String password) {
        // Überprüfen der Benutzerdaten
        Optional<User> user = userRepo.findByUsernameAndPassword(username, password);

        // Überprüfen, ob ein Benutzer gefunden wurde und das eingegebene Passwort korrekt ist
        return user.isPresent() && user.get().getPassword().equals(password);// Gibt false zurück, wenn der Benutzer nicht gefunden wurde oder das Passwort falsch ist
    }
}
