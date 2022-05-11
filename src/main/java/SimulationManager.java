import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class SimulationManager implements Runnable{

    public int timeLimit = 60;
    public int minServiceTime = 1;
    public int maxServiceTime = 7;
    public int minArrivalTime = 2;
    public int maxArrivalTime = 40;
    public int numberOfQueues = 5;
    public int numberOfClients = 50;
    private int maxClientsPerQueue = 10000;
    private double averageWaitingTime;
    private double averageServiceTime;
    private int peakHour;
    private int noOfClientsAtPeakHour;
    private AtomicInteger t;
    private AtomicInteger ct;

    private  Scheduler scheduler;

    private ArrayList<Client> clients;
    private ArrayList<QueueOfClients> queues;

    private String result;
    Controller controller;

    //initializam parametrii(urmeaza) si cream thread-urile asociate cozilor
    public SimulationManager() {

        scheduler = new Scheduler(numberOfQueues, maxClientsPerQueue);
        queues = scheduler.getQueues();
        clients = new ArrayList<Client>();
        t = new AtomicInteger();
        t.getAndSet(timeLimit);
        ct = new AtomicInteger();
        scheduler.setMaxClientsPerQueue(maxClientsPerQueue);
        averageWaitingTime = 0;
        averageServiceTime = 0;
        peakHour = 0;
        noOfClientsAtPeakHour = 0;
        for(QueueOfClients q : this.queues){
            Thread newThread = new Thread(q);
            q.setTime(0);
            q.setTimeLimit(timeLimit);
            newThread.start();
        }

        this.generateRandomClients();
        //calculam average waiting time si average service time
        for(Client c : clients){
            averageWaitingTime += c.getArrivalTime();
            averageServiceTime += c.getServiceTime();
        }
        averageWaitingTime /= clients.size();
        averageServiceTime /= clients.size();
    }

    public SimulationManager(Controller controller, int numberOfClients, int numberOfQueues, int timeLimit, int minArrivalTime, int maxArrivalTime,
                             int minServiceTime, int maxServiceTime, int maxClientsPerQueue) {

        this.controller = controller;
        this.numberOfClients = numberOfClients;
        this.numberOfQueues = numberOfQueues;
        this.timeLimit = timeLimit;
        this.minArrivalTime = minArrivalTime;
        this.maxArrivalTime = maxArrivalTime;
        this.minServiceTime = minServiceTime;
        this.maxServiceTime = maxServiceTime;

        scheduler = new Scheduler(numberOfQueues, maxClientsPerQueue);
        queues = scheduler.getQueues();
        clients = new ArrayList<Client>();
        t = new AtomicInteger();
        t.getAndSet(timeLimit);
        ct = new AtomicInteger();
        scheduler.setMaxClientsPerQueue(maxClientsPerQueue);
        averageWaitingTime = 0;
        averageServiceTime = 0;
        peakHour = 0;
        noOfClientsAtPeakHour = 0;
        for(QueueOfClients q : this.queues){
            Thread newThread = new Thread(q);
            q.setTime(0);
            q.setTimeLimit(timeLimit);
            newThread.start();
        }

        this.generateRandomClients();
        //calculam average waiting time si average service time
        for(Client c : clients){
            averageServiceTime += c.getServiceTime();
        }
        averageServiceTime /= clients.size();
    }

    private void generateRandomClients(){
    //Generam clienti random si ii adaugam in lista
        int arrivalTime = 1;
        int serviceTime = 1;
        Random r = new Random();

        for(int i = 0; i < numberOfClients; i++){
            if(maxServiceTime != 1) {
                serviceTime = r.nextInt(maxServiceTime - minServiceTime) + minServiceTime;
            }
            if(maxArrivalTime != 1) {
                arrivalTime = r.nextInt(maxArrivalTime - minArrivalTime) + minArrivalTime;
            }
            Client newClient = new Client(i, arrivalTime, serviceTime);
            clients.add(newClient);
        }
        Collections.sort(clients);
    }

    @Override
    public synchronized void run(){
        int currentTime = 0;
        ct.getAndSet(currentTime);
        ArrayList<Client> toRemove = new ArrayList<Client>();
        result = "";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"));
            while (currentTime <= timeLimit) {
                result = "";
                ct.getAndSet(currentTime);
                if (!clients.isEmpty()) {
                    for (Client c : clients) {
                        if (c.getArrivalTime() <= currentTime) {
                            int res;
                            res = scheduler.dispatchClient(c, queues);
                            if (res == 0) {
                                toRemove.add(c);
                            }
                        }
                    }
                    clients.removeAll(toRemove);
                }

                result += "Time " + currentTime + "\n";
                result += "Waiting clients: ";
                for (Client c : clients) {
                    result += "(" + c.getId() + "," + c.getArrivalTime() + "," + c.getServiceTime() + "); ";
                }
                result += "\n";
                int k = 0;
                int nrOfEmptyQueues = 0;
                for (QueueOfClients q : queues) {
                    result += "Queue " + k + ":";

                    if (q.getQueue().isEmpty()) {
                        result += "closed";
                        nrOfEmptyQueues++;
                    } else {
                        int cnt = 0;
                        for (Client c : q.getQueue()) {
                            if(cnt == 0 && c.getFirstInQueue() == 0){
                                c.setFirstInQueue(1);
                                System.out.println("Set");
                                averageWaitingTime += c.getArrivalTime() + (currentTime - c.getArrivalTime());
                            }
                            cnt++;
                            result += "(" + c.getId() + "," + c.getArrivalTime() + "," + c.getServiceTime() + "); ";
                        }
                    }

                    k++;
                    result += "\n";
                }

                if(nrOfEmptyQueues == queues.size() && clients.isEmpty())
                {
                    break;
                }

                currentTime++;
                int nrOfClients = 0;
                for (QueueOfClients q : queues) {
                    nrOfClients += q.getQueue().size();
                    if (noOfClientsAtPeakHour < nrOfClients) {
                        peakHour = currentTime - 1;
                        noOfClientsAtPeakHour = nrOfClients;
                    }
                    q.setTime(currentTime);
                }
                System.out.println(result);
                controller.setText();
                //writer.write(result);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            averageWaitingTime /= numberOfClients;

            result += "Simulation results\n Peak hour: " + peakHour +
                    "\nAverage waiting time:" + averageWaitingTime + "\nAverage service time:" + averageServiceTime;
            System.out.println(result);
            controller.setText();
            writer.write(result);
            writer.close();
        }catch (IOException ex){
            System.out.println("IOException");
        }
    }

    public String getResult() {
        return result;
    }

}
