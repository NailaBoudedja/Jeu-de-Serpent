package ProjetPOO2.joueur;

import java.util.ArrayList;
import java.util.List;

import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import ProjetPOO2.terrain.Terrain;
import controleur.controleur;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class Partie2JoueursClavier implements Partie {

    private List<Joueur> joueurs;
    private Terrain terrain;
    private boolean enCours;
    private controleur ctr;
    private AnimationTimer gameLoop;
    private int joueurActuelIndex = 0;

    public Partie2JoueursClavier(Terrain terrain) {
        this.joueurs = new ArrayList<>();
        this.terrain = terrain;
        this.enCours = false;
        initialiserNourriture(terrain); // ajouté au terrain
        initialiserJoueurs(joueurs);
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
        if (((Joueur2AuClavier) joueurActuel).aAppuye()) {
            ((Joueur2AuClavier) joueurActuel).reinitialiserAppuye();
            joueurActuelIndex = (joueurActuelIndex + 1) % joueurs.size();
        }

        // Planifier le prochain appel
        Platform.runLater(this::jouerTour);

    }

    @Override
    public void initialiserJoueurs(List<Joueur> joueurs) {
        // Logique pour initialiser les joueurs

        Point position1 = new Point(3, 3);
        Point position2 = new Point(2, 6);

        // initialiser deux serpents avec des segments et positions
        Serpent serpentJoueur1 = initialiserSerpent(position1);
        Serpent serpentJoueur2 = initialiserSerpent(position2);

        Joueur joueur1 = new Joueur2AuClavier("Joueur 1", serpentJoueur1);
        Joueur joueur2 = new Joueur2AuClavier("Joueur 2", serpentJoueur2);

        // Ajouter les joueurs à la liste des joueurs
        joueurs.add(joueur1);
        joueurs.add(joueur2);
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

    @Override
    public List<Joueur> getJoueur() {
        return joueurs;
    }

    @Override
    public Terrain getTerrain() {
        return terrain;
    }

    @Override
    public void setControleur(controleur ctr) {
        this.ctr = ctr;

    }

    @Override
    public void setControleurPourJoueurs(controleur ctr) {
        for (Joueur joueur : joueurs) {
            joueur.setControleur(ctr);
        }
    }

}
