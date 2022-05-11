import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Controller implements ActionListener {

    private View view;
    private SimulationManager logic;
    public int timeLimit;
    public int minServiceTime;
    public int maxServiceTime;
    public int minArrivalTime;
    public int maxArrivalTime;
    public int numberOfQueues;
    public int numberOfClients;
    private int maxClientsPerQueue;

    public Controller (View v){
        v.addStartListener(this);
        this.view = v;
    }

    public void actionPerformed(ActionEvent e){

        try {
            numberOfClients = Integer.parseInt(view.getNoOfClientsText().getText());
            numberOfQueues = Integer.parseInt(view.getNoOfQueuesText().getText());
            timeLimit = Integer.parseInt(view.getSimTimeText().getText());
            minArrivalTime = Integer.parseInt(view.getMinArrivalText().getText());
            maxArrivalTime = Integer.parseInt(view.getMaxArrivalText().getText());
            minServiceTime = Integer.parseInt(view.getMinServiceText().getText());
            maxServiceTime = Integer.parseInt(view.getMaxServiceText().getText());
            maxClientsPerQueue = Integer.parseInt(view.getMaxClientsText().getText());

        }catch (NumberFormatException ex){
            System.out.println("Number format exception");
        }

        logic = new SimulationManager(this, numberOfClients, numberOfQueues, timeLimit, minArrivalTime, maxArrivalTime, minServiceTime, maxServiceTime, maxClientsPerQueue);
        Thread newThread = new Thread(logic);
        newThread.start();

    }

    public void setText(){

        view.getScrollText().setText(logic.getResult());

    }

}
