package BusinessLogic;

import Model.Client;
import Model.Server;

import java.util.List;

public class ConcreteStrategyTime implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client t) {
        Server selectedServer = null;
        int shortestWaitingTime = Integer.MAX_VALUE;
        for (Server server : servers) {
            if (server.getWaitingPeriod() < shortestWaitingTime) {
                shortestWaitingTime = server.getWaitingPeriod();
                selectedServer = server;
            }
        }
        if (selectedServer != null) {
            selectedServer.addClient(t);
        }
    }
}