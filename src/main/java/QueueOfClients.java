import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicInteger;

public class QueueOfClients implements Runnable, Comparable<QueueOfClients> {

    private BlockingQueue<Client> queue;
    private AtomicInteger waitingPeriod;
    private AtomicInteger time;
    private AtomicInteger timeLimit;


    public QueueOfClients(){
        queue = new LinkedBlockingDeque<>();
        queue.clear();
        waitingPeriod = new AtomicInteger();
        waitingPeriod.getAndSet(0);
        time = new AtomicInteger();
        time.getAndSet(0);
        timeLimit = new AtomicInteger();
        timeLimit.getAndSet(0);
    }

    public void addClient(Client newClient){
        queue.add(newClient);
        waitingPeriod.getAndAdd(newClient.getServiceTime());
    }

    @Override
    public synchronized void run(){

        while (time.get() < timeLimit.get()){
            try {
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            if(!queue.isEmpty()) {

                if(queue.peek().getServiceTime() > 1){
                    int beforeServiceTime = queue.peek().getServiceTime();
                    queue.peek().setServiceTime(beforeServiceTime - 1);
                    waitingPeriod.decrementAndGet();
                }else if (queue.peek().getServiceTime() == 1){
                    waitingPeriod.decrementAndGet();
                    queue.remove();
                }
            }

        }
    }

    public BlockingQueue<Client> getQueue() {
        return queue;
    }

    public void setQueue(BlockingQueue<Client> queue) {
        this.queue = queue;
    }

    public AtomicInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    public void setWaitingPeriod(AtomicInteger waitingPeriod) {
        this.waitingPeriod = waitingPeriod;
    }

    public void setTime(int t) {
        time.getAndSet(t);
    }

    public void setTimeLimit(int t) {
        timeLimit.getAndSet(t);
    }

    @Override
    public int compareTo(QueueOfClients o) {
        return this.waitingPeriod.get() - o.waitingPeriod.get();
    }
}
