package org.if22b179.apps.mtcg;

import lombok.Data;
import org.if22b179.apps.mtcg.controller.*;
import org.if22b179.apps.mtcg.repository.CardRepo;
import org.if22b179.apps.mtcg.repository.UserRepo;
import org.if22b179.apps.mtcg.service.*;
import org.if22b179.server.ServerApplication;
import org.if22b179.server.http.HttpContentType;
import org.if22b179.server.http.HttpStatus;
import org.if22b179.server.http.Request;
import org.if22b179.server.http.Response;

import java.util.ArrayList;
import java.util.List;
@Data
public class MtcgApp implements ServerApplication {

    private List<Controller> controllers = new ArrayList<>();

    public MtcgApp() {
        controllers.add(new BattleController(new BattleService(new CardRepo(),new UserRepo())));
        controllers.add(new CardController(new CardService(new CardRepo())));
        controllers.add(new DeckController(new DeckService(new CardRepo())));
        controllers.add(new PackageController(new PackagesService(new CardRepo())));
        controllers.add(new SessionController(new SessionService(new UserRepo())));
        controllers.add(new TransactionController(new TransactionService(new CardRepo(),new UserRepo())));
        controllers.add(new UserController(new UserService(new UserRepo())));
        controllers.add(new StatsScoreboardController(new StatsScoreboardService(new UserRepo())));
    }

    @Override
    public Response handle(Request request) {

        for (Controller controller: controllers) {
            if (!controller.supports(request.getRoute())) {
                continue;
            }

            return controller.handle(request);
        }

        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setContentType(HttpContentType.TEXT_PLAIN);
        response.setBody("Route " + request.getRoute() + " not found in app!");

        return response;
    }
}
