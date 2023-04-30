package BusinessLogic;

import GUI.SimulationFrame;
import Model.Client;
import Model.Server;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationManager implements Runnable {
    public int timeLimit;
    public int maxProcessingTime;
    public int minProcessingTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfServers;
    public int numberOfClients;
    private SelectionPolicy selectionPolicy = SelectionPolicy.SHORTEST_TIME;
    private Scheduler scheduler;
    private SimulationFrame frame;
    private List<Client> generatedClients;

    public SimulationManager() {
        this.frame = new SimulationFrame();
        setSpinnerValues(frame.getAndShowResults());
        this.scheduler = new Scheduler(numberOfServers, numberOfClients);
        generatedClients = Collections.synchronizedList(new ArrayList<>());
        scheduler.changeStrategy(selectionPolicy);
        generateNRandomClients();
    }

    public void setSpinnerValues(int[] spinnerValues) {
        this.numberOfClients = spinnerValues[0];
        this.numberOfServers = spinnerValues[1];
        this.timeLimit = spinnerValues[2];
        this.minArrivalTime = spinnerValues[3];
        this.maxArrivalTime = spinnerValues[4];
        this.minProcessingTime = spinnerValues[5];
        this.maxProcessingTime = spinnerValues[6];
    }

    public void generateNRandomClients() {
        Random random = new Random();
        int arrival;
        int service;
        for (int i = 0; i < numberOfClients; i++) {
            service = random.nextInt(maxProcessingTime - minProcessingTime) + minProcessingTime;
            arrival = random.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            generatedClients.add(new Client(service, arrival));
        }
        //for my simulation to work, I need to sort the clients by their arrival time to be processed
        generatedClients.sort((Comparator.comparing(Client::getArrivalTime)));
        for (int i = 0; i < numberOfClients; i++)
            generatedClients.get(i).setID(i + 1);
    }

    private String showSimulationResult() {
        StringBuilder sb = new StringBuilder("Waiting clients: ");
        for (Client client : generatedClients) {
            sb.append("(").append(client.getID() + 1).append(",").append(client.getArrivalTime()).append(",").append(client.getServiceTime()).append(")  ");
        }
        sb.append("\n");
        for (int i = 0; i < scheduler.getMaxNoServers(); i++) {
            Server server = scheduler.getServers().get(i);
            sb.append("Queue ").append(i + 1).append(": ");
            if (server.getQueueLength() == 0) {
                sb.append("closed");
            } else {
                Client[] clients = server.getClients();
                for (int j = 0; j < server.getQueueLength(); j++) {
                    sb.append("(").append(clients[j].getID() + 1).append(",").append(clients[j].getArrivalTime()).append(",").append(clients[j].getServiceTime()).append(")  ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public void run() {
        int currentTime = 0;
        int totalServiceTime = 0;
        int peakHour = 0;
        int maxQueueLength = 0;
        StringBuilder logBuilder = new StringBuilder();
        while (currentTime < timeLimit) {
            for (Iterator<Client> iterator = generatedClients.iterator(); iterator.hasNext(); ) {
                Client client = iterator.next();
                if (client.getArrivalTime() == currentTime) {
                    scheduler.dispatchClient(client);
                    iterator.remove();
                } else if (client.getArrivalTime() > currentTime) {
                    break;
                }
            }
            for (int i = 0; i < scheduler.getMaxNoServers(); i++) {
                Server server = scheduler.getServers().get(i);
                int queueLength = server.getQueueLength();
                if (queueLength > maxQueueLength) {
                    maxQueueLength = queueLength;
                    peakHour = currentTime;
                }
                Client[] clients = server.getClients();
                for (int j = 0; j < queueLength; j++) {
                    totalServiceTime += clients[j].getServiceTime();
                }
            }
            String display = "Time " + currentTime + "\n" + showSimulationResult();
            System.out.println(display);
            logBuilder.append(display).append("\n");
            writeLogToFile(display);
            String currentDisplay = "Time " + currentTime + "\n" + showSimulationResult();
            frame.getTextArea().setText(currentDisplay + "\n");
            currentTime++;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        double averageWaitingTime = (double) scheduler.averageWaitingTime / numberOfClients;
        double averageServiceTime = (double)  totalServiceTime / numberOfClients;
        String statistics = "Average waiting time: " + averageWaitingTime + "\n" + "Average service time: "
                + averageServiceTime + "\n" + "Peak hour: " + peakHour;
        System.out.println(statistics);
        logBuilder.append(statistics).append("\n");
        writeLogToFile(statistics);
        frame.getTextArea().append(statistics);
        writeLogToFile(logBuilder.toString());
    }

    private void writeLogToFile(String log) {
        try (FileWriter fileWriter = new FileWriter("log.txt", true)) {
            fileWriter.write(log);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SimulationManager gen = new SimulationManager();
        Thread t = new Thread(gen);
        t.start();
    }
}
