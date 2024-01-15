package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.CardRepo;
import org.if22b179.apps.mtcg.repository.UserRepo;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
public class TransactionService {

    private final CardRepo cardRepo;
    private final UserRepo userRepo;
    private final int PACKAGE_COST = 5;

    public List<Card> purchasePackage(String username, String packageId) {
        Optional<User> user = userRepo.findById(username);
        // Aktualisieren Sie die Münzen des Benutzers
        if(user.isEmpty() || user.get().getCoins() < PACKAGE_COST)
            return Collections.emptyList();

        userRepo.updateCoins(username, user.get().getCoins() - PACKAGE_COST);

        List<Card> cards = cardRepo.findCardsByPackageId(packageId);

        for (Card card : cards) {
            card.setOwnerUsername(username); // Setzt den neuen Besitzer
            cardRepo.update(card); // Aktualisiert die Karte in der Datenbank
        }

        return cards; // Gibt die aktualisierten Karten zurück
    }

}
