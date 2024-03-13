package ProjetPOO2.nourriture;

import ProjetPOO2.monjeusnake.Point;
import java.util.Random;

public class Nourriture {
    private Point position;

    public Nourriture(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void respawn(int largeurTerrain, int hauteurTerrain) {
        Random random = new Random();
        int x = random.nextInt(largeurTerrain);
        int y = random.nextInt(hauteurTerrain);
        this.position = new Point(x, y);
    }
}