package org.if22b179.apps.mtcg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.service.DeckService;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

import java.util.Arrays;
import java.util.List;

@Data
public class DeckController extends Controller{

    private final DeckService deckService;

    @Override
    public boolean supports(String route) {
        return route.startsWith("/deck");
    }

    @Override
    public Response handle(Request request) {
        if (request.getMethod().equals("GET")) {
            return handleGet(request);
        }

        if (request.getMethod().equals("PUT")){
            return handlePut(request);
        }
        return status(HttpStatus.BAD_REQUEST, "URL Methode falsch");
    }

    private Response handlePut(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String username = request.getAuthorization().substring("Bearer ".length(), request.getAuthorization().indexOf("-mtcgToken"));
            List<String> cardIds = Arrays.asList(objectMapper.readValue(request.getBody(), String[].class));
            deckService.updateDeck(username, cardIds);
            return status(HttpStatus.OK, "Deck erfolgreich aktualisiert");
        } catch (Exception e) {
            return status(HttpStatus.BAD_REQUEST, "Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

    private Response handleGet(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String username = request.getAuthorization().substring("Bearer ".length(), request.getAuthorization().indexOf("-mtcgToken"));
            List<Card> deck = deckService.getDeck(username);
            String jsonResponse = objectMapper.writeValueAsString(deck);
            return status(HttpStatus.OK, jsonResponse);
        } catch (Exception e) {
            return status(HttpStatus.BAD_REQUEST, "Ein Fehler ist aufgetreten: " + e.getMessage());
        }
    }

}
