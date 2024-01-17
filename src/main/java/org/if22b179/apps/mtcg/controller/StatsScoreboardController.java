package org.if22b179.apps.mtcg.controller;

import lombok.Data;
import org.if22b179.apps.mtcg.service.StatsScoreboardService;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

@Data
public class StatsScoreboardController extends Controller{

    private final StatsScoreboardService statsScoreboardService;
    @Override
    public boolean supports(String route) {
        return route.startsWith("/stats") || route.startsWith("/scoreboard");
    }

    @Override
    public Response handle(Request request) {
        String route = request.getRoute();
        String responseContent;

        if (route.startsWith("/stats")) {
            // Annahme: der Benutzername wird aus dem Request-Parameter oder Path extrahiert
            String username = request.getAuthorization().substring("Bearer ".length(), request.getAuthorization().indexOf("-mtcgToken"));
            responseContent = statsScoreboardService.getStats(username);
        } else if (route.startsWith("/scoreboard")) {
            responseContent = statsScoreboardService.getScoreboard();
        } else {
            return status(HttpStatus.BAD_REQUEST, "Route nicht gefunden");
        }

        return status(HttpStatus.OK, responseContent);
    }
}
