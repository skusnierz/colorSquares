import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class BoardSquares {

    protected List<Vector2D> obstaclePositions = new ArrayList<Vector2D>();
    protected List<Gamer> gamers = new ArrayList<Gamer>();
    private List<Vector2D[]> obstacleShapes = new ArrayList<Vector2D[]>();
    private int numberOfOpponents;
    protected Gamer humanGamer;
    protected int numberOfWidthSquares;
    protected int numberOfHeightSquares;

    BoardSquares(int numberOfOpponents, int numberOfHeightSquares, int numberOfWidthSquares) {
        this.numberOfOpponents = numberOfOpponents;
        this.numberOfHeightSquares = numberOfHeightSquares;
        this.numberOfWidthSquares = numberOfWidthSquares;
    }

    protected void addOpponents() {
        int counter = 0;
        while(counter < this.numberOfOpponents) {
            Vector2D startPosition = new Vector2D(new Random().nextInt(this.numberOfHeightSquares), new Random().nextInt(this.numberOfWidthSquares));
            if ( !isOccupied(startPosition) ) {
                gamers.add(new Gamer(startPosition, new Color( new Random().nextInt(255), new Random().nextInt(255), new Random().nextInt(255)), this));
                counter++;
            }
        }
    }

    protected void addObstaclesPosition() {
        Vector2D[] firstShape = {new Vector2D(1, 0), new Vector2D(2, 0), new Vector2D(3, 0)};
        Vector2D[] secondShape = {new Vector2D(1, 0), new Vector2D(2, 0), new Vector2D(3, 0), new Vector2D(3, 1)};
        Vector2D[] thirdShape = {new Vector2D(1, 0), new Vector2D(0, 1), new Vector2D(1, 1)};
        this.obstacleShapes.add(firstShape);
        this.obstacleShapes.add(secondShape);
        this.obstacleShapes.add(thirdShape);
        for(int i = 0; i < 10; i++ ) {
            int numberOfShape = new Random().nextInt(this.obstacleShapes.size());
            int startShapeX =  new Random().nextInt(this.numberOfHeightSquares);
            int startShapeY =  new Random().nextInt(this.numberOfWidthSquares);
            Vector2D startVec = new Vector2D(startShapeX, startShapeY);
            if(!isOccupied(startVec)) this.obstaclePositions.add(startVec);
            Arrays.stream(this.obstacleShapes.get(numberOfShape)).forEach(
                    vec -> {
                        if(isOccupied(startVec.add(vec, this.numberOfHeightSquares, this.numberOfWidthSquares))) return;
                        this.obstaclePositions.add(startVec.add(vec, this.numberOfHeightSquares, this.numberOfWidthSquares));
                    }
            );
        }
    }

    public boolean isOccupied(Vector2D position) {
        for(Gamer gamer : this.gamers) {
            for(Vector2D pos: gamer.positions) {
                if(pos.getX() == position.getX() && pos.getY() == position.getY()) return true;
            };
        };

        for( Vector2D pos : this.obstaclePositions) {
            if(pos.getX() == position.getX() && pos.getY() == position.getY()) return true;
        }

        return false;
    }

    public void addHumanGamer(Vector2D position) {

        if(isOccupied(position)) return;
        if(humanGamer != null) this.gamers.remove(humanGamer);
        humanGamer = new Gamer(position, Color.GREEN, this);
        this.gamers.add(humanGamer);
    }

    public void clearObstacles() {
        this.obstaclePositions.clear();
    }

    public void  clearGamers() {
        this.gamers.clear();
    }

    public void addNewObstaclePoint(Vector2D position) {
        this.obstaclePositions.add(position);
    }

    public int nextTurn() {
        int sum = 0;
        int counter = 0;
        boolean[] haveTurn = new boolean[gamers.size()];
        while( counter < this.gamers.size() ) {
            int index = new Random().nextInt(this.gamers.size());
            if(haveTurn[index] == false) {
                haveTurn[index] = true;
                sum += this.gamers.get(index).expandMap();
                counter++;
            }
        }
        return sum;
    }

    public ArrayList<String>  getScores() {
        Gamer winner = this.humanGamer;
        ArrayList<String> scores = new ArrayList<String>();
        int counter = 1;
        scores.add("Your Score : " + humanGamer.positions.size());
        for(Gamer gamer: this.gamers) {
            if(winner.positions.size() < gamer.positions.size()) winner = gamer;
            if(gamer != humanGamer) {
                scores.add("Score " + counter + " PC Player : " + gamer.positions.size());
            }
            counter++;
        }
        if(winner.equals(humanGamer)) scores.add("You win !");
        else scores.add("You lose, try again!");
        return scores;
    }

    public void tryAgain() {
        for(Gamer gamer: this.gamers) {
            gamer.positions.clear();
            gamer.obstaclesPosition.clear();
            gamer.positions.add(gamer.startedPosition);
            gamer.obstaclesPosition.add(gamer.startedPosition);
        }
    }

}
