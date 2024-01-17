package org.if22b179.apps.mtcg.controller;

import lombok.Data;
import org.if22b179.apps.mtcg.service.BattleService;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

import java.util.ArrayList;
import java.util.List;

@Data
public class BattleController extends Controller{

    private final BattleService battleService;
    private final List<String> waitingPlayers = new ArrayList<>();
    private String history;
    @Override
    public boolean supports(String route) {
        return route.startsWith("/battles");
    }

    public Response handle(Request request) {
        String username = request.getAuthorization().substring("Bearer ".length(), request.getAuthorization().indexOf("-mtcgToken"));

        synchronized (this) {
            if (!waitingPlayers.isEmpty()) {
                String opponentUsername = waitingPlayers.remove(0);

                history = battleService.runBattle(username, opponentUsername);

                return status(HttpStatus.OK,"Battle started between " + username + " and " + opponentUsername + "\n" + history);
            } else {
                waitingPlayers.add(username);

                return status(HttpStatus.OK,"Waiting for an opponent...");
            }
        }
    }
}
