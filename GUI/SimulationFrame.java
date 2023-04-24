package GUI;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.CountDownLatch;


public class SimulationFrame implements ActionListener {
    private final CountDownLatch latch = new CountDownLatch(1);
    JFrame frame, textFrame;
    JPanel panel, buttonPanel, textPanel, spinnerPanel;
    JSpinner j1, j2, j3, j4, j5, j6, j7;
    JLabel l1, l2, l3, l4, l5, l6, l7;
    JButton start;
    JTextArea textArea;
    int[] spinnerValues = new int[7];

    public SimulationFrame() {
        frame = new JFrame("Queues Management Application");
        frame.setSize(380, 400);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel(new BorderLayout());
        textPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        spinnerPanel = new JPanel(new GridLayout(7, 1, 10, 10));
        buttonPanel = new JPanel(new FlowLayout());

        l1 = new JLabel("Number of Clients (N):");
        l1.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.TYPE1_FONT, 14));
        j1 = new JSpinner(new SpinnerNumberModel(4, 0, 2000, 1));
        j1.setPreferredSize(new Dimension(80, 40));
        l2 = new JLabel("Number of Queues (Q):");
        l2.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.TYPE1_FONT, 14));
        j2 = new JSpinner(new SpinnerNumberModel(2, 0, 1000, 1));
        j2.setPreferredSize(new Dimension(80, 40));
        l3 = new JLabel("<html>Simulation Interval (t<sub>simulation</sub><sup>MAX</sup>):</html>");
        l3.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.TYPE1_FONT, 14));
        j3 = new JSpinner(new SpinnerNumberModel(60, 0, 2000, 1));
        j3.setPreferredSize(new Dimension(80, 40));
        l4 = new JLabel("<html>Minimum Arrival Time (t<sub>arrival</sub><sup>MIN</sup>):</html>");
        l4.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.TYPE1_FONT, 14));
        j4 = new JSpinner(new SpinnerNumberModel(2, 0, 100, 1));
        j4.setPreferredSize(new Dimension(80, 40));
        l5 = new JLabel("<html>Maximum Arrival Time (t<sub>arrival</sub><sup>MAX</sup>):</html>");
        l5.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.TYPE1_FONT, 14));
        j5 = new JSpinner(new SpinnerNumberModel(30, 0, 1000, 1));
        j5.setPreferredSize(new Dimension(80, 40));
        l6 = new JLabel("<html>Minimum Service Time (t<sub>service</sub><sup>MIN</sup>):</html>");
        l6.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.TYPE1_FONT, 14));
        j6 = new JSpinner(new SpinnerNumberModel(2, 0, 100, 1));
        j6.setPreferredSize(new Dimension(80, 40));
        l7 = new JLabel("<html>Maximum Service Time (t<sub>service</sub><sup>MAX</sup>):</html>");
        l7.setFont(new Font(Font.SANS_SERIF, Font.ITALIC | Font.TYPE1_FONT, 14));
        j7 = new JSpinner(new SpinnerNumberModel(4, 0, 100, 1));
        j7.setPreferredSize(new Dimension(80, 40));

        textPanel.add(l1);
        textPanel.add(l2);
        textPanel.add(l3);
        textPanel.add(l4);
        textPanel.add(l5);
        textPanel.add(l6);
        textPanel.add(l7);
        spinnerPanel.add(j1);
        spinnerPanel.add(j2);
        spinnerPanel.add(j3);
        spinnerPanel.add(j4);
        spinnerPanel.add(j5);
        spinnerPanel.add(j6);
        spinnerPanel.add(j7);

        start = new JButton("Start");
        start.setPreferredSize(new Dimension(150, 30));
        start.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        buttonPanel.add(start);
        start.addActionListener(this);

        panel.add(textPanel, BorderLayout.WEST);
        panel.add(spinnerPanel, BorderLayout.EAST);
        panel.add(buttonPanel, BorderLayout.PAGE_END);
        frame.add(panel);

        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        //get the values from the spinners
        if(event.getSource()==start) {
            spinnerValues[0] = (int) j1.getValue();
            spinnerValues[1] = (int) j2.getValue();
            spinnerValues[2] = (int) j3.getValue();
            spinnerValues[3] = (int) j4.getValue();
            spinnerValues[4] = (int) j5.getValue();
            spinnerValues[5] = (int) j6.getValue();
            spinnerValues[6] = (int) j7.getValue();
            latch.countDown();
            start.setEnabled(false);
        }
    }

    public int[] getAndShowResults() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //create a new window to display the simulation results
        textFrame = new JFrame("Simulation Results");
        textFrame.setBounds(0, 0, 400, 200);
        textArea = new JTextArea();
        textArea.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 12));
        textFrame.add(textArea);
        frame.setVisible(false);
        textFrame.setVisible(true);
        return spinnerValues;
    }

    public JTextArea getTextArea() {
        return textArea;
    }
}