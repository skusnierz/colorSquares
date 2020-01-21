import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Gamer {
    private int Points;
    protected Color color;
    protected List<Vector2D> positions = new ArrayList<Vector2D>();
    protected List<Vector2D> obstaclesPosition = new ArrayList<Vector2D>();
    private BoardSquares boardSquares;
    protected Vector2D startedPosition;

    Gamer(Vector2D startedPosition, Color color, BoardSquares boardSquares) {
        this.positions.add(startedPosition);
        this.startedPosition = startedPosition;
        this.obstaclesPosition.add(startedPosition);
        this.color = color;
        this.boardSquares = boardSquares;
    }

    public int expandMap() {
        List<Vector2D> positionsToAdd = new ArrayList<Vector2D>();
        Vector2D[] possibleMoves = {new Vector2D(1,0), new Vector2D(-1,0), new Vector2D(0,1), new Vector2D(0, -1) };
        this.obstaclesPosition.removeIf(position -> {
            for ( Vector2D possibleMove : possibleMoves ) {
                Vector2D nextPoint = position.add(possibleMove, this.boardSquares.numberOfHeightSquares, this.boardSquares.numberOfWidthSquares);
                if( !this.boardSquares.isOccupied(nextPoint) && !positionsToAdd.contains(nextPoint)) {
                    positionsToAdd.add(nextPoint);
                }
            }
            return true;
        });

        for( Vector2D posToAdd : positionsToAdd ) {
            this.positions.add(posToAdd);
            this.obstaclesPosition.add(posToAdd);
        }

        return positionsToAdd.size();
    }
}
