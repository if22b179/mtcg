package org.if22b179.apps.mtcg.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.service.TransactionService;
import org.if22b179.server.http.HttpMethod;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

import java.util.List;

@Data
public class TransactionController extends Controller{

    private final TransactionService transactionService;
    @Override
    public boolean supports(String route) {
        return route.startsWith("/transactions/packages");
    }

    @Override
    public Response handle(Request request) {

        if(!request.getMethod().equals("POST"))
            return status(HttpStatus.BAD_REQUEST,"Falsche Methode");

        return handlePost(request);
    }

    private Response handlePost(Request request){

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String username = request.getAuthorization().substring("Bearer ".length(), request.getAuthorization().indexOf("-mtcgToken"));
            Card packageID = objectMapper.readValue(request.getBody(), Card.class);

            List<Card> purchasedCards = transactionService.purchasePackage(username, packageID.getPackageId());

            String responseBody = objectMapper.writeValueAsString(purchasedCards); // Konvertiert die Liste in einen JSON-String
            return status(HttpStatus.OK, responseBody);
        } catch (Exception e) {
            return status(HttpStatus.BAD_REQUEST, "Invalid request");
        }

    }
}
