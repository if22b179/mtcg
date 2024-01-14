package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.UserRepo;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@Data
public class SessionService {

    private final UserRepo userRepo;

    public boolean login(User user) {
        Optional<User> savedUser = userRepo.findById(user.getUsername());

        return BCrypt.checkpw(user.getPassword(),savedUser.get().getPassword());
    }
}
