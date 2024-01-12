package org.if22b179.apps.mtcg.controller;

import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

public interface Controller {

    boolean supports(String route);

    Response handle(Request request);
}
