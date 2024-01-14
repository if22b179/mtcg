package org.if22b179.apps.mtcg.controller;

import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

public class PackageController extends Controller{
    @Override
    public boolean supports(String route) {
        return route.startsWith("/packages");
    }

    @Override
    public Response handle(Request request) {

        // hier jz nur schauen ob methode post ist und dann response liefern
        return null;
    }
}
