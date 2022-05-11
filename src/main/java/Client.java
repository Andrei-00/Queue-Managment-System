public class Client implements Comparable<Client>{

    private int arrivalTime;
    private int serviceTime;
    private int id;
    private int firstInQueue;

    public Client(int id, int arrivalTime, int serviceTime) {
        this.id = id;
        this.arrivalTime = arrivalTime;
        this.serviceTime = serviceTime;
        this.firstInQueue = 0;
    }

    public int getFirstInQueue() {
        return firstInQueue;
    }

    public void setFirstInQueue(int firstInQueue) {
        this.firstInQueue = firstInQueue;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(int arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public synchronized int getServiceTime() {
        return serviceTime;
    }

    public synchronized void setServiceTime(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int compareTo (Client o){
        return  this.arrivalTime - o.getArrivalTime();
    }

}
