package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.repository.CardRepo;

import java.util.List;

@Data
public class CardService {

    private final CardRepo cardRepo;

    public List<Card> getUserCards(String username) {
        // Ruft die Methode in CardRepo auf, um Karten basierend auf dem ownerUsername zu finden
        return cardRepo.findCardsByOwner(username);
    }
}
