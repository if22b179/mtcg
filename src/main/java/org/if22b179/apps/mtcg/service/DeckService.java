package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.repository.CardRepo;

import java.util.List;

@Data
public class DeckService {

    private final CardRepo cardRepo;

    public void updateDeck(String username, List<String> cardID){
        cardRepo.updateDeck(username,cardID);
    }

    public List<Card> getDeck(String username){
        return cardRepo.getDeck(username);
    }
}
