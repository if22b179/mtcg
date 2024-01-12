package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.UserRepo;

import java.util.Optional;

@Data
public class UserService {

    private final UserRepo userRepo;

    public User createUser(User user) {
        // falls ich noch logik brauche genau hier hin
        if(userRepo.findById(user.getUsername()).isEmpty()){
            return userRepo.save(user);
        }
        return user;
    }

    public Optional<User> getUser(String username) {
        // falls ich noch logik brauche genau hier hin
        return userRepo.findById(username);
    }

    public User updateUser(User user) {
        // check ob user exestiert
        if(userRepo.findById(user.getUsername()).isPresent()){
            return userRepo.update(user);
        }
        return user;
    }

    public boolean deleteUser(String username) {
        Optional<User> user = userRepo.findById(username);
        if (user.isPresent()) {
            userRepo.deleteById(username);
            // Überprüfen, ob der Benutzer tatsächlich gelöscht wurde
            return userRepo.findById(username).isEmpty();
        }
        return false; // Benutzer existierte nicht
    }

    // falls ich noch Methoden brauche genau hier hin
}

