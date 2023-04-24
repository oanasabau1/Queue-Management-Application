package BusinessLogic;

import Model.Client;
import Model.Server;

import java.util.List;

public class ConcreteStrategyQueue implements Strategy {
    @Override
    public void addClient(List<Server> servers, Client t) {
        Server selectedServer = null;
        int shortestQueueLength = Integer.MAX_VALUE;
        for (Server server : servers) {
            if (server.getQueueLength() < shortestQueueLength) {
                shortestQueueLength = server.getQueueLength();
                selectedServer = server;
            }
        }
        if (selectedServer != null) {
            selectedServer.addClient(t);
        }
    }
}