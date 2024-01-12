package org.if22b179.server;

import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

public interface ServerApplication {

    Response handle(Request request);
}
