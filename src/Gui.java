import javax.swing.*;
import java.awt.*;

/**
 * Created by Andrija on 5/31/16.
 */
public class Gui extends JFrame {

    JTextArea textArea;

    public Gui(){

        super("Server Console");
        textArea = new JTextArea();

        add(new JScrollPane(textArea));
        setSize(750, 1000);
        setVisible(true);

    }

    public void displayMessage(String message){

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                textArea.append(message + "\n");

            }
        });
    }

}
