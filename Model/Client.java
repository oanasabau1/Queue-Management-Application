package Model;

public class Client {
    private int ID;
    private int arrivalTime;
    private int serviceTime;

    public Client(int serviceTime, int arrivalTime) {
        this.serviceTime = serviceTime;
        this.arrivalTime = arrivalTime;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    public void setServiceTime(int serviceTime) {
        if (serviceTime >= 0) {
            this.serviceTime = serviceTime;
        }
    }

}
