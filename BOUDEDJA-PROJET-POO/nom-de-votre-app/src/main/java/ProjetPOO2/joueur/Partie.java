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

public interface Partie {

    public default void initialiserNourriture(Terrain terrain) {
        // Logique pour initialiser la nourriture sur le terrain
        // Exemple : initialiser deux nourritures à des positions spécifiques
        Nourriture nourriture1 = new Nourriture(new Point(4, 7));
        Nourriture nourriture2 = new Nourriture(new Point(6, 5));

        terrain.ajouterNourriture(nourriture1);
        terrain.ajouterNourriture(nourriture2);
    }

    public void initialiserJoueurs(List<Joueur> joueurs);

    public default Serpent initialiserSerpent(Point positionInit) {
        // Logique pour initialiser un serpent
        // Exemple : créer et initialiser un serpent avec des segments et une position
        // spécifique
        List<Segment> segments = initialiserSegments(positionInit);
        Serpent serpent = new Serpent(segments, positionInit, this);

        for (Segment e : segments) { // avoir l'instance de serpent dans mes segments
            e.setSerpent(serpent);
        }

        // Retourner le serpent créé
        return serpent;
    }

    public default List<Segment> initialiserSegments(Point PositionInit) {
        // Logique pour initialiser les segments du serpent

        // Exemple : créer et initialiser une liste de segments (tête et corps)
        SegmentTete tete = new SegmentTete(PositionInit);
        List<Segment> segments = new ArrayList<>();
        segments.add(tete);
        return segments;
    }

    public void commencer();

    public void terminer();

    public default boolean collisionAvecAutreSerpent(Serpent serpent, List<Joueur> joueurs) {
        for (Joueur joueur : joueurs) {
            if (joueur.getSerpent() != serpent) { // Éviter de comparer le serpent avec lui-même
                Serpent autreSerpent = joueur.getSerpent();
                if (collisionEntreSerpents(serpent, autreSerpent)) {
                    return true;
                }
            }
        }
        return false;
    }

    public default boolean collisionEntreSerpents(Serpent serpent1, Serpent serpent2) {
        // Récupérer la tête et le corps de chaque serpent
        SegmentTete tete1 = (SegmentTete) serpent1.getSegments().get(0);
        List<Segment> corps1 = serpent1.getSegments().subList(1, serpent1.getSegments().size());

        SegmentTete tete2 = (SegmentTete) serpent2.getSegments().get(0);
        List<Segment> corps2 = serpent2.getSegments().subList(1, serpent2.getSegments().size());

        // Vérifier la collision entre les têtes des deux serpents
        if (tete1.getPosition().equals(tete2.getPosition())) {
            return true;
        }

        // Vérifier la collision entre la tête de serpent1 et le corps de serpent2
        for (Segment segment : corps2) {
            if (tete1.getPosition().equals(segment.getPosition())) {
                return true;
            }
        }

        // Vérifier la collision entre la tête de serpent2 et le corps de serpent1
        for (Segment segment : corps1) {
            if (tete2.getPosition().equals(segment.getPosition())) {
                return true;
            }
        }
        // Aucune collision détectée
        return false;
    }

    public default Nourriture nourritureDansCePoint(Point position, Terrain terrain) {

        List<Nourriture> nourritures = terrain.getNourritures();
        for (Nourriture nourriture : nourritures) {
            if (nourriture.getPosition().equals(position)) {
                return nourriture;
            }
        }
        return null;
    }

    public void retirerJoueur(Joueur joueur);

    public List<Joueur> getJoueur();

    public Terrain getTerrain();

    public void setControleur(controleur ctr);

    public void setControleurPourJoueurs(controleur ctr);

}
