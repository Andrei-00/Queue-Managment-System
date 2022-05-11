import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionListener;

public class View extends JFrame {

     private JPanel pane = new JPanel(new GridLayout(3,1));
     private JPanel titlePane = new JPanel(new FlowLayout());
     private JLabel titleLabel = new JLabel("Queues Application using " +
             "Threads and Synchronization Mechanisms");
     //8 rows
     private JPanel parametersPane = new JPanel(new GridLayout(3, 2));
     private JPanel clientsPane = new JPanel(new FlowLayout());
     private JLabel noOfClientsLabel = new JLabel("Number of clients: ");
     private JTextField noOfClientsText = new JTextField(20);
     private  JPanel queuesPane = new JPanel(new FlowLayout());
     private JLabel noOfQueues = new JLabel("Number of queues: ");
     private JTextField noOfQueuesText = new JTextField(20);
     private  JPanel simTimePane = new JPanel(new FlowLayout());
     private JLabel simTimePanel = new JLabel("Simulation time: ");
     private JTextField simTimeText = new JTextField(20);
     private  JPanel minArrivalPane = new JPanel(new FlowLayout());
     private JLabel minArrivalTime = new JLabel("Minimum arrival time: ");
     private JTextField minArrivalText = new JTextField(20);
     private  JPanel maxArrivalPane = new JPanel(new FlowLayout());
     private JLabel maxArrivalTime = new JLabel("Maximum arrival time: ");
     private JTextField maxArrivalText = new JTextField(20);
     private  JPanel minServicePane = new JPanel(new FlowLayout());
     private JLabel minServiceTime = new JLabel("Minimum service time: ");
     private JTextField minServiceText = new JTextField(20);
     private  JPanel maxServicePane = new JPanel(new FlowLayout());
     private JLabel maxServicePanel = new JLabel("Maximum service time: ");
     private JTextField maxServiceText = new JTextField(20);
     private  JPanel maxClientsPane = new JPanel(new FlowLayout());
     private JLabel maxClientsPanel = new JLabel("Maximum number of clients per queue: ");
     private JTextField maxClientsText = new JTextField(20);

    private JButton startButton = new JButton("Start");

     private JTextArea scrollText = new JTextArea(500, 500);
     private JScrollPane scrollPane = new JScrollPane(scrollText);

     Controller controller = new Controller(this);

    public View(String name){

        super(name);
        scrollPane.setAutoscrolls(true);

        titlePane.add(titleLabel);


        clientsPane.add(noOfClientsLabel);
        clientsPane.add(noOfClientsText);
        queuesPane.add(noOfQueues);
        queuesPane.add(noOfQueuesText);
        simTimePane.add(simTimePanel);
        simTimePane.add(simTimeText);
        minArrivalPane.add(minArrivalTime);
        minArrivalPane.add(minArrivalText);
        maxArrivalPane.add(maxArrivalTime);
        maxArrivalPane.add(maxArrivalText);
        minServicePane.add(minServiceTime);
        minServicePane.add(minServiceText);
        maxServicePane.add(maxServicePanel);
        maxServicePane.add(maxServiceText);
        maxClientsPane.add(maxClientsPanel);
        maxClientsPane.add(maxClientsText);

        parametersPane.add(clientsPane);
        parametersPane.add(queuesPane);
        parametersPane.add(simTimePane);
        parametersPane.add(minArrivalPane);
        parametersPane.add(maxArrivalPane);
        parametersPane.add(minServicePane);
        parametersPane.add(maxServicePane);
        parametersPane.add(maxClientsPane);
        parametersPane.add(startButton);

        DefaultCaret caret = (DefaultCaret)scrollText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        pane.add(titlePane);
        pane.add(parametersPane);
        pane.add(scrollPane);
        this.add(pane);
    }

    public void addStartListener(ActionListener b){
        startButton.addActionListener(b);
    }

    public JTextField getNoOfClientsText() {
        return noOfClientsText;
    }

    public void setNoOfClientsText(JTextField noOfClientsText) {
        this.noOfClientsText = noOfClientsText;
    }

    public JTextField getNoOfQueuesText() {
        return noOfQueuesText;
    }

    public void setNoOfQueuesText(JTextField noOfQueuesText) {
        this.noOfQueuesText = noOfQueuesText;
    }

    public JTextField getSimTimeText() {
        return simTimeText;
    }

    public void setSimTimeText(JTextField simTimeText) {
        this.simTimeText = simTimeText;
    }

    public JTextField getMinArrivalText() {
        return minArrivalText;
    }

    public void setMinArrivalText(JTextField minArrivalText) {
        this.minArrivalText = minArrivalText;
    }

    public JTextField getMaxArrivalText() {
        return maxArrivalText;
    }

    public void setMaxArrivalText(JTextField maxArrivalText) {
        this.maxArrivalText = maxArrivalText;
    }

    public JTextField getMinServiceText() {
        return minServiceText;
    }

    public void setMinServiceText(JTextField minServiceText) {
        this.minServiceText = minServiceText;
    }

    public JTextField getMaxServiceText() {
        return maxServiceText;
    }

    public void setMaxServiceText(JTextField maxServiceText) {
        this.maxServiceText = maxServiceText;
    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public JTextArea getScrollText() {
        return scrollText;
    }

    public void setScrollText(JTextArea scrollText) {
        this.scrollText = scrollText;
    }

    public JTextField getMaxClientsText() {
        return maxClientsText;
    }

    public void setMaxClientsText(JTextField maxClientsText) {
        this.maxClientsText = maxClientsText;
    }

    public static void main(String[] args) {

        JFrame frame = new View("Queue Managment System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,500);
        frame.setVisible(true);


    }
}
