package Model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Server implements Runnable {
    public int maxClientsPerQueue;
    private int serverID;
    private BlockingQueue<Client> clients;
    private AtomicInteger waitingPeriod;

    public Server(int maxClientsPerQueue, int serverID) {
        clients = new LinkedBlockingQueue<>(maxClientsPerQueue);
        waitingPeriod = new AtomicInteger(0);
        this.maxClientsPerQueue = maxClientsPerQueue;
        this.serverID = serverID;
    }

    public int getServerID() {
        return serverID;
    }

    public int getQueueLength() {
        return clients.size();
    }

    public int getWaitingPeriod() {
        return waitingPeriod.get();
    }

    public void addClient(Client newClient) {
        try {
            clients.put(newClient);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitingPeriod.addAndGet(newClient.getServiceTime());
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (clients.size() > 0) {
                Client firstClient = clients.element();
                firstClient.setServiceTime(firstClient.getServiceTime() - 1);
                waitingPeriod.addAndGet(-1);
                if (firstClient.getServiceTime() == 0) {
                    try {
                        clients.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Client[] getClients() {
        BlockingQueue<Client> q = new LinkedBlockingQueue<>(clients);
        return q.toArray(new Client[0]);
    }

}