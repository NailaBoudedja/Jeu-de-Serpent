package ProjetPOO2.joueur;

import java.util.ArrayList;
import java.util.List;

import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import ProjetPOO2.terrain.Terrain;
import controleur.controleur;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class PartieTourParTour implements Partie {

    private List<Joueur> joueurs;
    private Terrain terrain;
    private boolean enCours;
    private controleur ctr;
    private AnimationTimer gameLoop;
    private int joueurActuelIndex = 0;

    public PartieTourParTour(Terrain terrain) {
        this.joueurs = new ArrayList<>();
        this.terrain = terrain;
        this.enCours = false;
        initialiserNourriture(terrain);
        initialiserJoueurs(joueurs);
    }

    @Override
    public void initialiserJoueurs(List<Joueur> joueurs) {
        // Logique pour initialiser les joueurs

        Point position1 = new Point(3, 3);
        Point position2 = new Point(2, 6);

        // initialiser deux serpents avec des segments et positions
        // spécifiques
        Serpent serpentJoueur1 = initialiserSerpent(position1);
        Serpent serpentJoueur2 = initialiserSerpent(position2);

        // initialiser deux joueurs avec les serpents créés
        Joueur joueur1 = new JoueurTourParTour("Joueur 1", serpentJoueur1);
        Joueur joueur2 = new JoueurTourParTour("Joueur 2", serpentJoueur2);

        // Ajouter les joueurs à la liste des joueurs
        joueurs.add(joueur1);
        joueurs.add(joueur2);
    }

    @Override
    public void commencer() {
        enCours = true;
        jouerTour();
    }

    private void jouerTour() {
        if (!enCours)
            return;

        Joueur joueurActuel = joueurs.get(joueurActuelIndex);
        if (((JoueurTourParTour) joueurActuel).aClique()) {
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
