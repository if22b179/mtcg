package org.if22b179.apps.mtcg.controller;

import org.if22b179.server.http.HttpContentType;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

public abstract class Controller {

    public abstract boolean supports(String route);

    public abstract Response handle(Request request);

    protected Response status(HttpStatus httpStatus, String msg) {
        Response response = new Response();
        response.setStatus(httpStatus);
        response.setContentType(HttpContentType.APPLICATION_JSON);
        response.setBody(msg);

        return response;
    }

    // THOUGHT: more functionality e.g. ok(), json(), etc
}
