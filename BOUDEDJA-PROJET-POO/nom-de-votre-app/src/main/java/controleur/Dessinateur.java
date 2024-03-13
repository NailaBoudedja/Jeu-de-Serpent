package controleur;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ProjetPOO2.monjeusnake.serpent.Serpent;
import ProjetPOO2.joueur.Jeu;
import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.Segment;
import ProjetPOO2.monjeusnake.serpent.SegmentTete;
import ProjetPOO2.nourriture.Nourriture;
import ProjetPOO2.terrain.Terrain;
import java.util.List;

public class Dessinateur {
    private final int cellSize = 20;
    Jeu jeu;

    public Dessinateur(Jeu jeu) {
        this.jeu = jeu;
    }

    public void dessinerSerpent(Serpent serpent, Canvas serpentCanvas) {
        // Ajuster la taille du canvas en fonction de la taille du terrain
        Terrain terrain = jeu.getPartie().getTerrain();
        System.out.println(
                "dimentiosn de mon terrain " + terrain.getLargeur() * cellSize + " " + terrain.getHauteur() * cellSize);
        serpentCanvas.setWidth(terrain.getLargeur() * cellSize);
        serpentCanvas.setHeight(terrain.getHauteur() * cellSize);

        GraphicsContext gc = serpentCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, serpentCanvas.getWidth(), serpentCanvas.getHeight()); // Effacer le canvas

        for (Segment segment : serpent.getSegments()) {
            Point position = segment.getPosition();
            System.out.println("-----------------------------ma position " + position);
            double x = (position.getX()) * cellSize; // Ajustement pour le décalage
            double y = (position.getY()) * cellSize; // Ajustement pour le décalage
            System.out.println("la valeur de x et y" + x + "  " + y);
            gc.setFill(segment instanceof SegmentTete ? Color.GREEN : Color.GRAY); // Tête en vert, corps en gris
            gc.fillRect(x, y, cellSize, cellSize);
        }
    }

    public void dessinerNourritures(List<Nourriture> nourritures, Canvas canvasNourriture) {
        if (jeu != null && jeu.getPartie() != null && nourritures != null && canvasNourriture != null) {
            Terrain terrain = jeu.getPartie().getTerrain();

            // Ajuster la taille du canvas en fonction de la taille du terrain
            canvasNourriture.setWidth(terrain.getLargeur() * cellSize);
            canvasNourriture.setHeight(terrain.getHauteur() * cellSize);

            GraphicsContext gc = canvasNourriture.getGraphicsContext2D();
            // Effacer le canvas précédent
            gc.clearRect(0, 0, canvasNourriture.getWidth(), canvasNourriture.getHeight());

            for (Nourriture nourriture : nourritures) {
                Point positionNourriture = nourriture.getPosition();

                // Calculer les coordonnées de la nourriture sur le canvas
                double x = positionNourriture.getX() * cellSize;
                double y = positionNourriture.getY() * cellSize;

                // Dessiner la nourriture
                gc.setFill(Color.RED); // Couleur de la nourriture
                gc.fillRect(x, y, cellSize, cellSize); // Dessiner un carré pour la nourriture
            }
        }
    }

    public void drawBoard(Canvas Plateau) {
        if (jeu != null && jeu.getPartie() != null) {
            Terrain terrain = jeu.getPartie().getTerrain();

            // Redimensionner le canvas selon les dimensions du terrain
            int largeurPixels = terrain.getLargeur() * cellSize;
            int hauteurPixels = terrain.getHauteur() * cellSize;
            Plateau.setWidth(largeurPixels);
            Plateau.setHeight(hauteurPixels);

            GraphicsContext gc = Plateau.getGraphicsContext2D();
            gc.clearRect(0, 0, largeurPixels, hauteurPixels); // Nettoyer le canvas

            // Dessiner le plateau
            for (int x = 0; x < largeurPixels; x += cellSize) {
                for (int y = 0; y < hauteurPixels; y += cellSize) {
                    gc.strokeRect(x, y, cellSize, cellSize);
                }
            }
        }
    }

    public void drawInitialeSnake(Serpent serpent, Canvas serpentCanvas) {

        Point positionTete = serpent.getpositionCourante();
        System.out.println("Position initiale du serpent : " + positionTete);

        // Réglage de la taille du Canvas pour correspondre à la taille de la grille
        // entière
        Terrain terrain = jeu.getPartie().getTerrain();
        serpentCanvas.setWidth(terrain.getLargeur() * cellSize);
        serpentCanvas.setHeight(terrain.getHauteur() * cellSize);

        GraphicsContext gc = serpentCanvas.getGraphicsContext2D();
        // Nettoyer le canvas avant de dessiner
        gc.clearRect(0, 0, serpentCanvas.getWidth(), serpentCanvas.getHeight());

        // Dessiner la tête du serpent en ajustant les coordonnées
        double x = (positionTete.getX()) * cellSize;
        double y = (positionTete.getY()) * cellSize;
        gc.setFill(Color.GREEN); // Couleur pour la tête du serpent
        gc.fillRect(x, y, cellSize, cellSize); // Dessiner la tête à la position ajustée
    }
}
