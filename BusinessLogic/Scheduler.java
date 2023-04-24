package BusinessLogic;

import Model.Client;
import Model.Server;

import java.util.ArrayList;
import java.util.List;

public class Scheduler {
    public float averageWaitingTime = 0;
    private List<Server> servers = new ArrayList<>();
    private int maxNoServers;
    private int maxClientsPerServer;
    private Strategy strategy;

    public Scheduler(int maxNoServers, int maxClientsPerServer) {
        this.maxNoServers = maxNoServers;
        this.maxClientsPerServer = maxClientsPerServer;
        for (int i = 0; i < maxNoServers; i++) {
            Server server = new Server(maxClientsPerServer, i);
            servers.add(server);
            Thread serverThread = new Thread(server);
            serverThread.start();
        }
    }

    public int getMaxNoServers() {
        return maxNoServers;
    }

    public void setMaxNoServers(int maxNoServers) {
        this.maxNoServers = maxNoServers;
    }

    public int getMaxClientsPerServer() {
        return maxClientsPerServer;
    }

    public void setMaxClientsPerServer(int maxClientsPerServer) {
        this.maxClientsPerServer = maxClientsPerServer;
    }

    public Strategy getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    public void changeStrategy(SelectionPolicy policy) {
        if (policy == SelectionPolicy.SHORTEST_QUEUE) {
            strategy = new ConcreteStrategyQueue();
        } else if (policy == SelectionPolicy.SHORTEST_TIME) {
            strategy = new ConcreteStrategyTime();
        }
    }

    public void dispatchClient(Client client) {
        strategy.addClient(servers, client);
        averageWaitingTime += client.getServiceTime();
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
}
