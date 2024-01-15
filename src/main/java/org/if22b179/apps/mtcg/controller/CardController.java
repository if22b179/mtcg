package org.if22b179.apps.mtcg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.service.CardService;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

import java.util.List;

@Data
public class CardController extends Controller {

    private final CardService cardService;

    @Override
    public boolean supports(String route) {
        return route.startsWith("/cards");
    }

    @Override
    public Response handle(Request request) {
        if (request.getMethod().equals("GET")) {
            return handleGet(request);
        }
        return status(HttpStatus.BAD_REQUEST, "URL Methode falsch");
    }

    private Response handleGet(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String username = request.getAuthorization().substring("Bearer ".length(), request.getAuthorization().indexOf("-mtcgToken"));
            List<Card> userCards = cardService.getUserCards(username);
            String responseBody = objectMapper.writeValueAsString(userCards);
            return status(HttpStatus.OK, responseBody);
        } catch (Exception e) {
            return status(HttpStatus.BAD_REQUEST, "Invalid request");
        }
    }
}
