import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Scanner;

public class AutoClicker 
{

    public static void main(String[] args) 
    {
        
        try 
        {

            Scanner key = new Scanner(System.in);
            Robot robot = new Robot();

            System.out.println("Interval between clicks (ms) [Recommended minimum: 10ms / Recommended: 100ms]:");
            int delay = key.nextInt();
            key.nextLine(); 

            System.out.println("Type 's' + ENTER to START, 'f' + ENTER to STOP. Ctrl+C to exit or close the CMD.");

            Thread clickThread = null;
            boolean isClicking = false;

            while (true) 
            {

                
                String cmd = key.nextLine().trim();
				cmd = cmd.toLowerCase();

                if (cmd.equalsIgnoreCase("s") && !isClicking) 
                {
                    
                    isClicking = true;
                    clickThread = new Thread(() -> 
                    {

                        try 
                        {

                            while (!Thread.currentThread().isInterrupted()) 
                            {

                                robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
                                robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
                                Thread.sleep(delay);

                            }

                        } 
                        catch (InterruptedException e) 
                        {
                            
                        }

                    });

                    clickThread.start();
                    System.out.println("Autoclicker STARTED. Press 'f' + ENTER to stop.");

                } 
                else if (cmd.equalsIgnoreCase("f") && isClicking) 
                {
                    
                    isClicking = false;
                    if (clickThread != null) 
                    {

                        clickThread.interrupt();
                        clickThread.join();

                    }

                    System.out.println("Autoclicker PAUSED. Press 's' + ENTER to restart.");

                }
                

            }

        } 
        catch (Exception e) 
        {

            e.printStackTrace();

        }

    }

}
