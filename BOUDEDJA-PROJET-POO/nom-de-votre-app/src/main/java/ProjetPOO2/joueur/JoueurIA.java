package ProjetPOO2.joueur;

import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.Direction;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import ProjetPOO2.monjeusnake.serpent.Segment;
import ProjetPOO2.nourriture.Nourriture;
import controleur.controleur;
import javafx.application.Platform;

import java.util.List;

public class JoueurIA implements Joueur {

    protected String nom;
    protected Serpent serpent;
    protected Point pointCliqué;
    protected controleur ctr;

    protected long dernierDeplacement = 0;
    protected long intervalleDeplacement = 1000; // en millisecondes

    protected boolean aClique;
    protected boolean aAppuyer;

    public JoueurIA(String nom, Serpent serpent) {
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

    public void jouerTourIA() {
        Nourriture nourritureProche = trouverNourritureLaPlusProche();
        Direction direction = choisirDirection(nourritureProche);
        this.serpent.setoldDirection(direction);
        this.serpent.deplacer();
        Platform.runLater(() -> {
            ctr.mettreAJourUI(this);
        });
    }

    private Nourriture trouverNourritureLaPlusProche() {
        List<Nourriture> nourritures = serpent.getPartie().getTerrain().getNourritures();
        Point positionTete = serpent.getpositionCourante();
        Nourriture nourritureProche = null;
        double distanceMin = Double.MAX_VALUE;

        for (Nourriture nourriture : nourritures) {
            double distance = positionTete.distance(nourriture.getPosition());
            if (distance < distanceMin) {
                distanceMin = distance;
                nourritureProche = nourriture;
            }
        }
        return nourritureProche;
    }

    private Direction choisirDirection(Nourriture nourritureProche) {
        Point positionTete = serpent.getpositionCourante();
        Direction directionOptimale = null;
        double scoreOptimal = Double.NEGATIVE_INFINITY;

        for (Direction direction : Direction.values()) {
            if (peutSeDeplacerDansDirection(direction)) {
                double score = evaluerDirection(direction, positionTete, nourritureProche);
                if (score > scoreOptimal) {
                    scoreOptimal = score;
                    directionOptimale = direction;
                }
            }
        }

        return directionOptimale != null ? directionOptimale : serpent.getoldDirection();
    }

    private double evaluerDirection(Direction direction, Point positionTete, Nourriture nourritureProche) {
        Point positionProchaine = positionTete.ajouter(direction);

        // Évaluer la sécurité et la proximité à la nourriture
        double score = 0.0;
        if (nourritureProche != null) {
            double distanceNourriture = positionProchaine.distance(nourritureProche.getPosition());
            score -= distanceNourriture; // Moins la distance est grande, plus le score est élevé
        }

        // Pénaliser les directions menant à une collision imminente
        if (positionMenaceCollision(positionProchaine)) {
            score -= 1000; // Pénalisation élevée pour éviter les collisions
        }

        // Ajouter d'autres critères d'évaluation ici si nécessaire

        return score;
    }

    private boolean positionMenaceCollision(Point position) {
        // Vérifier la collision avec les murs ou les limites du terrain si nécessaire
        // ...

        // Vérifier la collision avec son propre corps
        for (Segment segment : serpent.getSegments()) {
            if (segment.getPosition().equals(position)) {
                return true;
            }
        }

        // Vérifier la collision avec les autres serpents
        List<Joueur> autresJoueurs = serpent.getPartie().getJoueur();
        for (Joueur autreJoueur : autresJoueurs) {
            Serpent autreSerpent = autreJoueur.getSerpent();
            for (Segment segment : autreSerpent.getSegments()) {
                if (segment.getPosition().equals(position)) {
                    return true;
                }
            }
        }

        return false;
    }

    // Méthode pour calculer la direction vers un point
    private Direction directionVersPoint(Point depart, Point arrivee) {
        double deltaX = arrivee.getX() - depart.getX();
        double deltaY = arrivee.getY() - depart.getY();

        if (Math.abs(deltaX) > Math.abs(deltaY)) {
            return (deltaX > 0) ? Direction.DROITE : Direction.GAUCHE;
        } else {
            return (deltaY > 0) ? Direction.BAS : Direction.HAUT;
        }
    }

    // Méthode pour vérifier si le serpent peut se déplacer dans une direction sans
    // collision
    private boolean peutSeDeplacerDansDirection(Direction direction) {
        Point positionTete = serpent.getpositionCourante();
        Point positionProchaine = positionTete.ajouter(direction);

        // Vérifier la collision avec les murs ou les limites du terrain si nécessaire
        // ...

        // Vérifier la collision avec son propre corps
        for (Segment segment : serpent.getSegments()) {
            if (segment.getPosition().equals(positionProchaine)) {
                return false;
            }
        }
        // Vérifier la collision avec les autres serpents
        List<Joueur> autresJoueurs = serpent.getPartie().getJoueur();
        for (Joueur autreJoueur : autresJoueurs) {
            Serpent autreSerpent = autreJoueur.getSerpent();
            for (Segment segment : autreSerpent.getSegments()) {
                if (segment.getPosition().equals(positionProchaine)) {
                    return false;
                }
            }
        }

        return true;
    }

    public void clicSurTerrain(Point positionClic) {
        pointCliqué = positionClic;
        aClique = true;
        // Direction nouvelleeDirection = convertirDirection(positionClic);

    }

    public boolean aAppuye() {
        return aAppuyer;
    }
}