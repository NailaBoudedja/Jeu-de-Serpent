package ProjetPOO2.joueur;

import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import controleur.controleur;
import javafx.application.Platform;

public class JoueurTourParTour implements Joueur {

    protected String nom;
    protected Serpent serpent;
    protected Point pointCliqué;
    protected controleur ctr;

    protected long dernierDeplacement = 0;
    protected long intervalleDeplacement = 1000; // en millisecondes

    protected boolean aClique;
    protected boolean aAppuyer;

    public JoueurTourParTour(String nom, Serpent serpent) {
        this.nom = nom;
        this.serpent = serpent;
        aClique = false;
        aAppuyer = false;
        // initialiser le premier point
        this.pointCliqué = serpent.getpositionCourante().copierPoint();

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Serpent getSerpent() {
        return serpent;
    }

    public void setSerpent(Serpent serpent) {
        this.serpent = serpent;
    }

    public void setControleur(controleur ctr) {
        this.ctr = ctr;

    }

    public boolean aClique() {
        return aClique;
    }

    public void reinitialiserClic() {
        aClique = false; // Réinitialiser après avoir traité le clic
    }

    public void clicSurTerrain(Point positionClic) {
        pointCliqué = positionClic;
        aClique = true;
        // Direction nouvelleeDirection = convertirDirection(positionClic);

    }

    public void deplacerVersPointCliqué() {
        if (pointCliqué != null) {
            deplacer(pointCliqué, serpent);
            Platform.runLater(() -> {
                ctr.mettreAJourUI(this);
            });
            ajusterIntervalleDeplacement(serpent, intervalleDeplacement);
        }
    }

    public boolean aAppuye() {
        return aAppuyer;
    }

}
