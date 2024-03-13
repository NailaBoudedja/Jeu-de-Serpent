package ProjetPOO2.joueur;

import java.util.ArrayList;
import java.util.List;

import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import ProjetPOO2.terrain.Terrain;
import controleur.controleur;
import javafx.application.Platform;

public class PartieAvecJoueurIA implements Partie {

    private List<Joueur> joueurs;
    private Terrain terrain;
    private boolean enCours;
    private controleur ctr;
    private int joueurActuelIndex = 0;

    public PartieAvecJoueurIA(Terrain terrain) {
        this.joueurs = new ArrayList<>();
        this.terrain = terrain;
        this.enCours = false;
        initialiserNourriture(terrain);
        initialiserJoueurs(joueurs);
    }

    @Override
    public void commencer() {
        enCours = true;
        jouerTour();
    }

    @Override
    public void initialiserJoueurs(List<Joueur> joueurs) {

        Point positionIA = new Point(5, 5);
        Point position1 = new Point(3, 3);

        Serpent serpentIA = initialiserSerpent(positionIA);
        Serpent serpentJoueur1 = initialiserSerpent(position1);

        JoueurIA joueurIA = new JoueurIA("IA", serpentIA);
        Joueur joueur1 = new JoueurTourParTour("Joueur 1", serpentJoueur1);

        joueurs.add(joueur1);
        joueurs.add(joueurIA);

    }

    private void jouerTour() {
        if (!enCours)
            return;

        Joueur joueurActuel = joueurs.get(joueurActuelIndex);

        if (joueurActuel instanceof JoueurIA) {
            ((JoueurIA) joueurActuel).jouerTourIA();
            joueurActuelIndex = (joueurActuelIndex + 1) % joueurs.size();
        } else if (((JoueurTourParTour) joueurActuel).aClique()) {
            ((JoueurTourParTour) joueurActuel).deplacerVersPointCliqué();
            ((JoueurTourParTour) joueurActuel).reinitialiserClic();
            joueurActuelIndex = (joueurActuelIndex + 1) % joueurs.size();
        }

        // Planifier le prochain appel
        Platform.runLater(this::jouerTour);
    }

    public Joueur getJoueurActuel() {
        if (joueurs == null || joueurs.isEmpty()) {
            return null;
        }
        return joueurs.get(joueurActuelIndex);
    }

    @Override
    public void terminer() {
        enCours = false;
    }

    public void retirerJoueur(Joueur joueur) {
        joueurs.remove(joueur);
    }

    public List<Joueur> getJoueur() {

        return joueurs;
    }

    public Terrain getTerrain() {
        return terrain;
    }

    public void setControleur(controleur ctr) {
        this.ctr = ctr;

    }

    public void setControleurPourJoueurs(controleur ctr) {
        for (Joueur joueur : joueurs) {
            joueur.setControleur(ctr);
        }
    }

}
