import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class GameResultFrame {

    public JButton tryAgainButton = new JButton("Try Again");
    private JFrame gameResults = new JFrame("Game Result");
    GameResultFrame(ArrayList<String> scores) {


        gameResults.setSize(400,400);
        gameResults.setLayout(null);

        int x = 20, y = 30;
        for( String score: scores ) {
            JLabel Score = new JLabel(score);
            Score.setBounds(x, y, 250,30);
            gameResults.add(Score);
            y+=30;
        }


        tryAgainButton.setBounds(110, 300, 180,30);

        gameResults.add(tryAgainButton);
        gameResults.setLocationRelativeTo(null);
        gameResults.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        gameResults.setVisible(true);

    }

    public void exit() {
        gameResults.dispatchEvent(new WindowEvent(gameResults, WindowEvent.WINDOW_CLOSING));
    }
}
