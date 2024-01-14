package org.if22b179.apps.mtcg.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.if22b179.apps.mtcg.entity.Card;
import org.if22b179.apps.mtcg.service.PackagesService;
import org.if22b179.server.http.HttpMethod;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;
import org.if22b179.server.http.HttpStatus;

import java.util.List;

@Data
public class PackageController extends Controller{

    private final PackagesService packagesService;
    @Override
    public boolean supports(String route) {
        return route.startsWith("/packages");
    }

    @Override
    public Response handle(Request request) {
        if (request.getMethod().equals("POST")) {
            return handlePost(request);
        }
        return status(HttpStatus.BAD_REQUEST, "URL Methode falsch");

    }

    private Response handlePost(Request request) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Card> cards = objectMapper.readValue(request.getBody(), new TypeReference<List<Card>>() {});
            int packageId = packagesService.saveCards(cards);
            String responseBody = "Package created successfully with ID: " + packageId;
            return status(HttpStatus.OK, responseBody);
        } catch (Exception e) {
            return status(HttpStatus.BAD_REQUEST, "Invalid request");
        }
    }
}
