package ProjetPOO2.monjeusnake;

public class Point {
    private double x;
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    @Override
    public String toString() {
        return "Point [x=" + x + ", y=" + y + "]";
    }

    public double getY() {
        return y;
    }

    public Point ajouter(Direction other) {
        switch (other) {

            case HAUT:
                return new Point(this.x, this.y + 1);

            case BAS:
                return new Point(this.x, this.y - 1);

            case DROITE:
                return new Point(this.x + 1, this.y);

            case GAUCHE:
                return new Point(this.x - 1, this.y);

            default:
                return new Point(this.x, this.y);
        }
    }

    public Point ajouter(double deltaX, double deltaY) {
        return new Point(this.x + deltaX, this.y + deltaY);
    }

    public double distance(Point other) {
        double deltaX = this.x - other.x;
        double deltaY = this.y - other.y;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Point otherPoint = (Point) obj;
        return this.x == otherPoint.x && this.y == otherPoint.y;
    }

    public Point copierPoint() {
        return new Point(this.x, this.y);
    }
}
