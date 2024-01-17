package org.if22b179.apps.mtcg.service;

import lombok.Data;
import org.if22b179.apps.mtcg.entity.User;
import org.if22b179.apps.mtcg.repository.UserRepo;

import java.util.List;
import java.util.Optional;

@Data
public class StatsScoreboardService {

    private final UserRepo userRepo;


    public String getStats(String name){
        Optional<User> user = userRepo.findById(name);

        int gamesPlayed = user.get().getLoss()+user.get().getWins();
        int wins = user.get().getWins();
        int loss = user.get().getLoss();
        float winPercentage = wins / gamesPlayed * 100;


        return String.format("Statistiken für %s:\n" +
                        "Gespielte Spiele: %d\n" +
                        "Gewinne: %d\n" +
                        "Verluste: %d\n" +
                        "Gewinnquote: %f",
                name, gamesPlayed, wins, loss, winPercentage);
    }

    public String getScoreboard(){
        List<User> users = userRepo.getAllElo();
        StringBuilder scoreboard = new StringBuilder("Scoreboard:\n");

        for (User user : users) {
            int gamesPlayed = user.getLoss() + user.getWins();
            int wins = user.getWins();
            int loss = user.getLoss();
            double winPercentage = gamesPlayed > 0 ? (double) wins / gamesPlayed * 100 : 0;

            scoreboard.append(String.format("Benutzer: %s\n" +
                            "Gespielte Spiele: %d\n" +
                            "Gewinne: %d\n" +
                            "Verluste: %d\n" +
                            "Gewinnquote: %.2f%%\n\n",
                    user.getUsername(), gamesPlayed, wins, loss, winPercentage));
        }

        return scoreboard.toString();
    }
}
