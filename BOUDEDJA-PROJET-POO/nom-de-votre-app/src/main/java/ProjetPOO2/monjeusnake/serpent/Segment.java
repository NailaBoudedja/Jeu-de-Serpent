package ProjetPOO2.monjeusnake.serpent;

import ProjetPOO2.monjeusnake.Point;

public abstract class Segment {
    private Point position; // ses coordonées
    private Point oldPosition;
    private Serpent serpent; // le serpent auquel apartient ce segment

    public Segment(Point position) { // constrcuteur
        this.position = position;
        oldPosition = position;
    }

    public Point getPosition() {
        return position;
    }

    // le serpent est donnée apres et non pas lors de la constrcution du segment
    public void setPosition(Point position) {
        // sauvgarder l'encienne position pour pouvoir decaler tout le serpent par la
        // suite
        oldPosition = this.position;
        this.position = position;
    }

    public Serpent getSerpent() {
        return serpent;
    }

    public void setSerpent(Serpent serpent) {
        this.serpent = serpent;
    }

    public Point getOldPosition() {
        return oldPosition;
    }

    public void deplacerVers(Point nouvellePosition) {
        position = nouvellePosition;
    }

    public abstract void deplacer();

    // Dans la classe Segment
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Segment otherSegment = (Segment) obj;
        return this.position.equals(otherSegment.getPosition());
    }

}
