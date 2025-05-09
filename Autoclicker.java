import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.InputEvent;

public class Autoclicker extends JFrame 
{

    private JSpinner delaySpinner;
    private JButton startButton;
    private JButton stopButton;
    private SwingWorker<Void, Void> clickWorker;
    private Robot robot;

    public Autoclicker() 
    {

        super("Autoclicker");
        initRobot();
        initComponents();
        layoutComponents();
        attachListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

    }

    private void initRobot() 
    {

        try 
        {

            robot = new Robot();

        } 
        catch (AWTException e) 
        {

            JOptionPane.showMessageDialog(this,
                    "Impossibile inizializzare Robot:\n" + e.getMessage(),
                    "Errore",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);

        }

    }

    private void initComponents() 
    {

        SpinnerNumberModel model = new SpinnerNumberModel(100, 10, 10000, 10);
        delaySpinner = new JSpinner(model);
        ((JSpinner.DefaultEditor) delaySpinner.getEditor()).getTextField().setColumns(5);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");
        stopButton.setEnabled(false);

    }

    private void layoutComponents() 
    {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Delay between click (ms) [min 10ms]:"), gbc);

        gbc.gridx = 1;
        panel.add(delaySpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(startButton, gbc);

        gbc.gridx = 1;
        panel.add(stopButton, gbc);

        getContentPane().add(panel);

    }

    private void attachListeners() 
    {

        startButton.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {

                startClicking();

            }

        });

        stopButton.addActionListener(new ActionListener() 
        {

            @Override
            public void actionPerformed(ActionEvent e) 
            {

                stopClicking();

            }

        });

        addWindowListener(new WindowAdapter() 
        {

            @Override
            public void windowClosing(WindowEvent e) 
            {

                stopClicking();

            }

        });

    }

    private void startClicking() 
    {
        
        final int delay = (int) delaySpinner.getValue();
        clickWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception 
            {

                while (!isCancelled())
                {

                    robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                    Thread.sleep(delay);

                }
                return null;

            }

        };

        clickWorker.execute();
        startButton.setEnabled(false);
        delaySpinner.setEnabled(false);
        stopButton.setEnabled(true);

    }

    private void stopClicking() 
    {

        if (clickWorker != null && !clickWorker.isDone()) 
        {

            clickWorker.cancel(true);

        }
        startButton.setEnabled(true);
        delaySpinner.setEnabled(true);
        stopButton.setEnabled(false);

    }

    public static void main(String[] args) 
    {

        try 
        {

            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } 
        catch (Exception ignored) 
        {

        }

        SwingUtilities.invokeLater(() -> new Autoclicker());

    }

}
