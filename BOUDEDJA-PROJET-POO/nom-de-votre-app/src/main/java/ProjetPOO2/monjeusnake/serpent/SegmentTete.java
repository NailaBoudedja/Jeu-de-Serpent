// src/monjeusnake/serpent/SegmentTete.java
package ProjetPOO2.monjeusnake.serpent;

import ProjetPOO2.monjeusnake.Direction;
import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.nourriture.Nourriture;

public class SegmentTete extends Segment {

    public SegmentTete(Point position) {
        super(position);
    }

    @Override
    public void deplacerVers(Point nouvellePosition) {

        setPosition(nouvellePosition);

        // modifier la position courante du serpent
        getSerpent().setpositionCourante(nouvellePosition);
    }

    @Override
    public void deplacer() {

        // Récupérer la direction actuelle du serpent
        Direction direction = getSerpent().getoldDirection();

        // Calculer la nouvelle position en fonction de la direction
        Point nouvellePosition = getPosition().ajouter(direction);
        System.out.println("ma nouvelle position : " + nouvellePosition);

        int largeurTerrain = getSerpent().getPartie().getTerrain().getLargeur();
        int hauteurTerrain = getSerpent().getPartie().getTerrain().getHauteur();

        if (nouvellePosition.getX() < 0) {

            nouvellePosition = new Point(largeurTerrain - 1, nouvellePosition.getY());
        } else if (nouvellePosition.getX() >= largeurTerrain) {
            nouvellePosition = new Point(0, nouvellePosition.getY());
        }

        if (nouvellePosition.getY() < 0) {
            nouvellePosition = new Point(nouvellePosition.getX(), hauteurTerrain - 1);
        } else if (nouvellePosition.getY() >= hauteurTerrain) {
            nouvellePosition = new Point(nouvellePosition.getX(), 0);
        }

        // Appel de la méthode deplacerVers avec la nouvelle position
        deplacerVers(nouvellePosition);

        // Vérification des collisions avec les obstacles tiers
        if (collisionAvecObstacle()) {
            getSerpent().mourir();
        }

        // Vérification des collisions avec le serpent lui-même ou avec un autre serpent
        if (collisionAvecAutreSegment() || getSerpent().getPartie().collisionAvecAutreSerpent(getSerpent(),
                getSerpent().getPartie().getJoueur())) {
            getSerpent().mourir();
        }

        // si de la nourriture se trouve a cette position
        Nourriture nourriture = getSerpent().getPartie().nourritureDansCePoint(getPosition(),
                getSerpent().getPartie().getTerrain());

        if (nourriture != null) {
            manger(nourriture);
        }

    }

    public void deplacer(double deltaX, double deltaY) {
        // Logique spécifique pour déplacer la tête du serpent
        // (utilisez les règles du jeu pour déterminer le déplacement)

        Point nouvellePosition = getPosition().ajouter(deltaX, deltaY);

        System.out.println("ma nouvelle position : " + nouvellePosition);

        int largeurTerrain = getSerpent().getPartie().getTerrain().getLargeur();
        int hauteurTerrain = getSerpent().getPartie().getTerrain().getHauteur();

        if (nouvellePosition.getX() < 0) {

            nouvellePosition = new Point(largeurTerrain - 1, nouvellePosition.getY());
        } else if (nouvellePosition.getX() >= largeurTerrain) {
            nouvellePosition = new Point(0, nouvellePosition.getY());
        }

        if (nouvellePosition.getY() < 0) {
            nouvellePosition = new Point(nouvellePosition.getX(), hauteurTerrain - 1);
        } else if (nouvellePosition.getY() >= hauteurTerrain) {
            nouvellePosition = new Point(nouvellePosition.getX(), 0);
        }

        // Appel de la méthode deplacerVers avec la nouvelle position
        deplacerVers(nouvellePosition);

        // Vérification des collisions avec les obstacles tiers
        if (collisionAvecObstacle()) {
            getSerpent().mourir();
        }

        // Vérification des collisions avec le serpent lui-même ou avec un autre serpent
        if (collisionAvecAutreSegment() || getSerpent().getPartie().collisionAvecAutreSerpent(getSerpent(),
                getSerpent().getPartie().getJoueur())) {
            getSerpent().mourir();
        }

        // si de la nourriture se trouve a cette position
        Nourriture nourriture = getSerpent().getPartie().nourritureDansCePoint(getPosition(),
                getSerpent().getPartie().getTerrain());

        if (nourriture != null) {
            manger(nourriture);
        }

    }

    public void manger(Nourriture nourriture) {

        Serpent serpent = getSerpent(); // logique pour la croissance du serpent après avoir mangé

        Point positionTete = getPosition();

        Point positionNourriture = nourriture.getPosition(); // obtenez la position de la nourriture

        if (positionTete.equals(positionNourriture)) { // vérifiez si la tête du serpent se trouve sur le morceau de
                                                       // nourriture

            SegmentCorps nouveauSegment = new SegmentCorps(positionTete); // a modifier
            serpent.getSegments().add(nouveauSegment);

            // faites respawn de la nourriture après avoir été mangée
            nourriture.respawn(15/* largeurTerrain */, 15/* hauteurTerrain */);

        }
    }

    private boolean collisionAvecAutreSegment() { // méthode pour vérifier la collision avec un autre segment du serpent

        SegmentTete tete = (SegmentTete) getSerpent().getSegments().get(0);

        for (int i = 1; i < getSerpent().getSegments().size(); i++) {
            SegmentCorps segmentCorp = (SegmentCorps) getSerpent().getSegments().get(i);

            if (tete.getPosition().equals(segmentCorp.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public boolean collisionAvecObstacle() {
        Point positionTete = getPosition();

        int largeurTerrain = 120/* remplacez par la largeur réelle de votre terrain */;
        int hauteurTerrain = 120/* remplacez par la hauteur réelle de votre terrain */;

        if (positionTete.getX() < 0 || positionTete.getX() >= largeurTerrain ||
                positionTete.getY() < 0 || positionTete.getY() >= hauteurTerrain) {
            return true; // Collision avec une bordure (obstacle)
        }

        // Autres logiques à ajouter en fonction des règles du jeu

        return false; // Aucune collision avec un obstacle
    }
}
