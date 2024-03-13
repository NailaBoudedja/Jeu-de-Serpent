// src/monjeusnake/serpent/Serpent.java
package ProjetPOO2.monjeusnake.serpent;

import ProjetPOO2.monjeusnake.Direction;
import ProjetPOO2.monjeusnake.Point;
import java.util.List;

import ProjetPOO2.joueur.Partie;

public class Serpent {
    private List<Segment> segments;
    private Point positionCourante;
    private Direction oldDirection;
    private Partie partie;
    private boolean estMort = false;
    private double vitesse;
    // position courante

    public Serpent(List<Segment> segments, Point positionCourante, Partie partie) {
        this.segments = segments;
        this.positionCourante = positionCourante;
        // this.positionCourante = new Point(2,3);
        oldDirection = Direction.DROITE;
        this.partie = partie;
        this.vitesse = 0.1;
    }

    public List<Segment> getSegments() {
        return segments;
    }

    public void setSegments(List<Segment> segments) {
        this.segments = segments;
    }

    public Point getpositionCourante() {
        return positionCourante;
    }

    public Partie getPartie() { // pour acceder a la methode collision avec les autres serpents
        return partie;
    }

    public Direction getoldDirection() {
        return oldDirection;
    }

    public void setoldDirection(Direction dir) {
        oldDirection = dir;
    }

    public void setpositionCourante(Point positionCourante) {
        this.positionCourante = positionCourante;
    }

    public void deplacer() {

        SegmentTete tete = (SegmentTete) segments.get(0); // obtenez la tête du serpent

        tete.deplacer(); // déplacez la tête du serpent en fonction de la positionCourante

        for (int i = 1; i < segments.size(); i++) { // déplacez le reste du corps en suivant la tête

            SegmentCorps segmentCorp = (SegmentCorps) segments.get(i);
            // deplacer ce segments vers l'encienne positions de ce segments;
            segmentCorp.deplacerVers(segments.get(i - 1).getOldPosition());
        }

    }

    public void deplacer(long tempsEcoule) {
        double deltaX = calculerDeltaX(tempsEcoule);
        double deltaY = calculerDeltaY(tempsEcoule);

        // Obtenez la tête du serpent
        SegmentTete tete = (SegmentTete) segments.get(0);

        tete.deplacer(deltaX, deltaY);

        // Déplacez le reste du corps en suivant la tête
        for (int i = 1; i < segments.size(); i++) {
            SegmentCorps segmentCorp = (SegmentCorps) segments.get(i);
            // deplacer ce segments vers l'encienne positions de ce segments;
            segmentCorp.deplacerVers(segments.get(i - 1).getOldPosition());
        }

    }

    public void mourir() {

        estMort = true;
        System.out.println("Le serpent est mort !");
    }

    // Méthode pour calculer la variation de coordonnée X en fonction du temps
    // écoulé
    public double calculerDeltaX(long elapsedTime) {
        double deltaX = vitesse * elapsedTime / 1000000.0; // Diviser par 1000000 pour convertir le temps en secondes
        return deltaX;
    }

    // méthode pour calculer la variation de coordonnée Y en fonction du temps
    // écoulé
    public double calculerDeltaY(long elapsedTime) {
        double deltaY = vitesse * elapsedTime / 1000000.0; // Diviser par 1000000 pour convertir le temps en secondes
        return deltaY;
    }

    public boolean estMort() {
        return estMort;
    }
}
