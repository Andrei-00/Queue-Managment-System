import java.util.ArrayList;
import java.util.Collections;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public class Scheduler {

    private ArrayList<QueueOfClients> queues;
    private int maxQueues;
    private  int maxClientsPerQueue;

    //initializam parametrii primiti si cream cozile
    public Scheduler(int maxQueues, int maxClientsPerQueue) {
        this.maxQueues = maxQueues;
        this.maxClientsPerQueue = maxClientsPerQueue;
        queues = new ArrayList<QueueOfClients>();

        for(int i = 0; i < maxQueues; i++){
            QueueOfClients newQueue = new QueueOfClients();
            queues.add(newQueue);
        }

    }

    public int dispatchClient(Client c, ArrayList<QueueOfClients> q) {

            int pos = 0;
            int posMin = 0;
            int min = 100000;
            for (QueueOfClients queue : q) {
                if(queue.getQueue().size() < maxClientsPerQueue) {

                    if (queue.getWaitingPeriod().get() < min) {
                        min = queue.getWaitingPeriod().get();
                        posMin = pos;
                    }
                }
                pos++;
            }
            if(min == 100000){
                return -1;
            }
            q.get(posMin).addClient(c);
            return 0;
    }

    public ArrayList<QueueOfClients> getQueues() {
        return queues;
    }

    public int getMaxQueues() {
        return maxQueues;
    }

    public int getMaxClientsPerQueue() {
        return maxClientsPerQueue;
    }

    public void setQueues(ArrayList<QueueOfClients> queues) {
        this.queues = queues;
    }

    public void setMaxQueues(int maxQueues) {
        this.maxQueues = maxQueues;
    }

    public void setMaxClientsPerQueue(int maxTasksPerQueue) {
        this.maxClientsPerQueue = maxTasksPerQueue;
    }
}
