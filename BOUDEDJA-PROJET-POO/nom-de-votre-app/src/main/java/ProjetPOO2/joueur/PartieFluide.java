package ProjetPOO2.joueur;

import java.util.ArrayList;
import java.util.List;

import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.SegmentTete;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import ProjetPOO2.nourriture.Nourriture;
import ProjetPOO2.terrain.Terrain;
import ProjetPOO2.monjeusnake.serpent.Segment;
import controleur.controleur;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;

public class PartieFluide implements Partie {

    private List<Joueur> joueurs;
    private Terrain terrain;
    private boolean enCours;
    private controleur ctr;
    private AnimationTimer gameLoop;

    public PartieFluide(Terrain terrain) {
        this.joueurs = new ArrayList<>();
        this.terrain = terrain;
        this.enCours = false;
        initialiserNourriture(terrain);
        initialiserJoueurs(joueurs);
    }

    @Override
    public void initialiserJoueurs(List<Joueur> joueurs) {
        Point position1 = new Point(2, 6);
        Serpent serpentJoueur1 = initialiserSerpent(position1);
        JoueurFluide joueur1 = new JoueurFluide("Joueur 1", serpentJoueur1);
        joueurs.add(joueur1);
    }

    @Override
    public Serpent initialiserSerpent(Point positionInit) {
        List<Segment> segments = initialiserSegments(positionInit);
        Serpent serpent = new Serpent(segments, positionInit, this);
        for (Segment e : segments) { // avoir l'instance de serpent dans mes segments
            e.setSerpent(serpent);
        }
        return serpent;
    }

    @Override
    public void commencer() {
        enCours = true;

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Mise à jour du jeu à chaque frame
                for (Joueur joueur : joueurs) {
                    ((JoueurFluide) joueur).deplacerSiNecessaire(now / 1000000);

                }

                // Mise à jour de l'interface utilisateur via le contrôleur

                // Vérifier si la partie doit se terminer
                if (joueurs.isEmpty()) {
                    this.stop();
                    enCours = false;
                }
            }
        };

        gameLoop.start();

    }

    public void retirerJoueur(Joueur joueur) {
        joueurs.remove(joueur);
    }

    public void terminer() {
        if (gameLoop != null) {
            gameLoop.stop();
        }
        enCours = false;
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
