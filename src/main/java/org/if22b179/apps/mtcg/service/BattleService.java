package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.CardRepo;
import org.if22b179.apps.mtcg.repository.UserRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Data
public class BattleService {

    private final CardRepo cardRepo;
    private final UserRepo userRepo;
    private final int MAX_ROUNDS = 100;
    private final int wins = 4;

    public String runBattle(String user1, String user2) {
        List<Card> user1Deck = cardRepo.getDeck(user1);
        List<Card> user2Deck = cardRepo.getDeck(user2);
        StringBuilder battleLog = new StringBuilder();
        int user1Wins = 0;
        int user2Wins = 0;
        int rounds = 0;
        while (rounds < MAX_ROUNDS && user1Wins <= wins && user2Wins <= wins) {
            rounds++;
            Card user1Card = pickRandomCard(user1Deck);
            Card user2Card = pickRandomCard(user2Deck);
            battleLog.append(String.format("Round %d: %s (Damage: %f) vs %s (Damage: %f)\n",
                    rounds, user1Card.getName(), user1Card.getDamage(),
                    user2Card.getName(), user2Card.getDamage()));
            if (user1Card.getDamage() > user2Card.getDamage()) {
                user1Wins++;
                user2Deck.remove(user2Card);
                user1Deck.add(user2Card);
                battleLog.append(String.format("%s wins the round!\n", user1));
            } else if (user2Card.getDamage() > user1Card.getDamage()) {
                user2Wins++;
                user1Deck.remove(user1Card);
                user2Deck.add(user1Card);
                battleLog.append(String.format("%s wins the round!\n", user2));
            } else {
                battleLog.append("The round is a draw.\n");
            }
        }
        System.out.println("nach while");
        String result;
        if (user1Deck.size() > user2Deck.size()) {
            Optional<User> tmp = userRepo.findById(user1);
            Optional<User> tmp2 = userRepo.findById(user2);
            result = user1 + " has won!";
            userRepo.changeElo(tmp.get().getUsername(),tmp.get().getEloValue()+3);
            userRepo.win(tmp.get().getUsername());
            userRepo.changeElo(tmp2.get().getUsername(),tmp2.get().getEloValue()-5);
            userRepo.loss(tmp2.get().getUsername());
        } else if (user2Deck.size() > user1Deck.size()) {
            Optional<User> tmp = userRepo.findById(user1);
            Optional<User> tmp2 = userRepo.findById(user2);
            result = user2 + " has won!";
            userRepo.changeElo(tmp2.get().getUsername(),tmp2.get().getEloValue()+3);
            userRepo.win(tmp2.get().getUsername());
            userRepo.changeElo(tmp.get().getUsername(),tmp.get().getEloValue()-5);
            userRepo.loss(tmp.get().getUsername());
        } else {
            result = "The battle ended in a draw.";
        }
        battleLog.append(result);

        return battleLog.toString();
    }

    private Card pickRandomCard(List<Card> deck) {
        Collections.shuffle(deck);
        return deck.get(0); // Nimm die oberste Karte nach dem Mischen
    }


}
