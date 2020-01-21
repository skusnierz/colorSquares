import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ColorSquaresBoard extends JPanel {

    private int numberWidthSquares;
    private int numberHeightSquares;
    protected int squareHeight;
    protected int squareWidth;
    private int width;
    private int height;
    protected BoardSquares boardSquares;

    public ColorSquaresBoard(int numberWidthSquares, int numberHeightSquares, BoardSquares boardSquares) {
        this.numberWidthSquares = numberWidthSquares;
        this.numberHeightSquares = numberHeightSquares;
        this.boardSquares = boardSquares;
    }

    public void paint(Graphics g) {
        width = 800;
        height = 800;
        this.squareWidth = (width-200)/numberWidthSquares;
        this.squareHeight = (height-200)/numberHeightSquares;

        g.setColor(Color.blue);
        g.fillRect(100, 100, width-200, height-200);

        for(int i = 100; i < width-100; i += squareWidth){
            for(int j = 100; j < height-100; j += squareHeight){
                g.clearRect(i, j, squareWidth, squareHeight);
                g.setColor(Color.black);
                g.drawRect(i, j, squareWidth, squareHeight);
            }
        }

        this.boardSquares.obstaclePositions.forEach( ele -> {
            g.setColor(Color.black);
            g.fillRect(100+(ele.getY()*squareWidth), 100+(ele.getX()*squareHeight), squareWidth, squareHeight);
        });

        this.boardSquares.gamers.forEach( gamer -> {
            gamer.positions.forEach( pos -> {
                g.setColor(gamer.color);
                g.fillRect(100+(pos.getY()*squareWidth), 100+(pos.getX()*squareHeight), squareWidth, squareHeight);
            });
        });
    }
}
