package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.repository.CardRepo;

import java.util.List;

@Data
public class PackagesService {

    private final CardRepo cardRepo;

    public int saveCards(List<Card> cards) {
        int maxPackageId = cardRepo.getMaxPackageId();
        int newPackageId = maxPackageId + 1;

        for (Card card : cards) {
            card.setPackageId(String.valueOf(newPackageId)); // Setzt die neue packageId für jede Karte
            cardRepo.save(card);
        }

        return newPackageId; // Gibt die verwendete packageId zurück
    }
}
