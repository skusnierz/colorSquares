public class Vector2D {

    private int x;
    private int y;

    public String toString() {
        return this.x+" "+this.y;
    }
    public Vector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D add( Vector2D other, int numberHeightSquares, int numberWidthSquares) {
        int x = other.getX() + this.x;
        int y = other.getY() + this.y;
        if(x < 0) x = numberHeightSquares - 1;
        if(x > numberHeightSquares - 1) x = 0;
        if(y < 0) y = numberWidthSquares - 1;
        if(y > numberWidthSquares - 1) y = 0;
        return new Vector2D(x, y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (!(other instanceof Vector2D))
            return false;
        Vector2D that = (Vector2D) other;
        return this.x == that.x && this.y == that.y;
    }
}
