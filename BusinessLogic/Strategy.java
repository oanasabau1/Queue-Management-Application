package BusinessLogic;

import Model.Client;
import Model.Server;

import java.util.List;

public interface Strategy {
    void addClient(List<Server> servers, Client t);
}
