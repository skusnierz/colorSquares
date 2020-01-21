import java.awt.*;
import javax.swing.*;

public class Main extends JPanel{

    public static void main(String[] args){
        Toolkit.getDefaultToolkit().setDynamicLayout(false);
        JFrame frame = new JFrame("Inputs Parameters");
        frame.setSize(350,350);
        InputParametersFrame inputParametersFrame = new InputParametersFrame();
        frame.add(inputParametersFrame.getInputJPanel());
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
