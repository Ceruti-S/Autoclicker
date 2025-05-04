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

            System.out.println("Intervallo tra i clic (ms) [minimo consigliato: 10ms / consigliato: 100ms]: ");
            int delay = key.nextInt();
            key.nextLine(); 

            System.out.println("Inserisci 's' + INVIO per AVVIARE, 'f' + INVIO per FERMARE. Ctrl+C per uscire o chiudi il CMD.");

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
                    System.out.println("Autoclicker AVVIATO. Inserisci 'f' + INVIO per fermare.");

                } 
                else if (cmd.equalsIgnoreCase("f") && isClicking) 
                {
                    
                    isClicking = false;
                    if (clickThread != null) 
                    {

                        clickThread.interrupt();
                        clickThread.join();

                    }

                    System.out.println("Autoclicker IN PAUSA. Inserisci 's' + INVIO per riavviare.");

                }
                

            }

        } 
        catch (Exception e) 
        {

            e.printStackTrace();

        }

    }

}
