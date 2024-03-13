package ProjetPOO2.joueur;

import ProjetPOO2.monjeusnake.Direction;
import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import controleur.controleur;

public interface Joueur {

    public void setControleur(controleur ctr);

    public String getNom();

    public void setNom(String nom);

    public Serpent getSerpent();

    public void setSerpent(Serpent serpent);

    public default void deplacer(Point position, Serpent serpent) {
        // Déplace le serpent dans la direction spécifiée
        serpent.setoldDirection(convertirDirection(position, serpent));
        serpent.deplacer();
    }

    public default Direction convertirDirection(Point position, Serpent serpent) {
        // Convertit la direction du joueur en vecteur de déplacement
        Point positionCourante = serpent.getpositionCourante();
        if (position.getX() == positionCourante.getX()) {

            if (position.getY() > positionCourante.getY()) {

                if (serpent.getoldDirection() != Direction.BAS) {
                    return Direction.HAUT;
                } else {
                    return serpent.getoldDirection();
                }
            } else {
                if (serpent.getoldDirection() != Direction.HAUT) {
                    return Direction.BAS;
                } else {
                    return serpent.getoldDirection();
                }
            }

        } else if (position.getY() == positionCourante.getY()) {

            if (position.getX() > positionCourante.getX()) {

                if (serpent.getoldDirection() != Direction.GAUCHE) {
                    return Direction.DROITE;
                } else {
                    return serpent.getoldDirection();
                }

            } else {

                if (serpent.getoldDirection() != Direction.DROITE) {
                    return Direction.GAUCHE;
                } else {
                    return serpent.getoldDirection();
                }
            }

        } else { // position eloignée
            if ((position.getX() > positionCourante.getX()) && (serpent.getoldDirection() != (Direction.GAUCHE))) {
                return Direction.DROITE;

            } else if ((position.getX() < positionCourante.getX()) && (serpent.getoldDirection() != Direction.DROITE)) {
                return Direction.GAUCHE;

            } else if ((position.getY() > positionCourante.getY()) && (serpent.getoldDirection() != Direction.BAS)) {
                return Direction.HAUT;
            } else if ((position.getY() < positionCourante.getY()) && (serpent.getoldDirection() != Direction.HAUT)) {
                return Direction.BAS;
            } else {
                return serpent.getoldDirection();
            }

        }
    }

    public default void quitterPartie(Serpent serpent) {
        // Arrêter le déplacement automatique du serpent
        // Signaler à la partie que le joueur quitte le jeu
        serpent.getPartie().retirerJoueur(this);
        // Autres nettoyages ou mises à jour de l'état du joueur si nécessaire
    }

    public default void ajusterIntervalleDeplacement(Serpent serpent, long intervalleDeplacement) {
        int nombreSegments = serpent.getSegments().size();
        // Augmenter l'intervalle de déplacement à mesure que le nombre de segments

        intervalleDeplacement = 1000 + nombreSegments * 10;
    }

}
