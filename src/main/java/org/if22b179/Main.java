package org.if22b179;

import org.if22b179.apps.mtcg.MtcgApp;
import org.if22b179.server.Server;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(new MtcgApp());
        server.start();
    }
}
