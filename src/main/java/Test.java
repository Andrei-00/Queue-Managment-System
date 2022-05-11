import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;

public class Test extends JFrame {

    /*public static void main(String[] args) {

        Client c = new Client(0,2,5);
        Client c2 = new Client(1,3, 7);
        Client c3 = new Client(2,4, 3);
        Client c4 = new Client(3,5, 4);
        QueueOfClients q = new QueueOfClients();
        QueueOfClients q2 = new QueueOfClients();
        q.addClient(c);
        q.addClient(c2);
        ArrayList<QueueOfClients> queues= new ArrayList<>();
        queues.add(q);
        queues.add(q2);
        q2.addClient(c3);
        Collections.sort(queues);

        for(QueueOfClients qiu : queues) {
            //System.out.println(qiu.getWaitingPeriod());
        }

        //Thread t = new Thread(q);
        //t.start();


        SimulationManager simulation = new SimulationManager();
        Thread newThread = new Thread(simulation);
        newThread.start();

        JFrame frame = new View("Queue Managment System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,500);
        frame.setVisible(true);

    }*/
}
