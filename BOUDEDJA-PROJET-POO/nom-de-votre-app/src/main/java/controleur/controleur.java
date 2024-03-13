package controleur;

import ProjetPOO2.monjeusnake.Direction;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;

import ProjetPOO2.monjeusnake.Point;
import ProjetPOO2.monjeusnake.serpent.Serpent;

import java.io.IOException;
import java.util.List;

import ProjetPOO2.joueur.Jeu;
import ProjetPOO2.joueur.Joueur;
import ProjetPOO2.joueur.Joueur2AuClavier;
import ProjetPOO2.joueur.JoueurFluide;
import ProjetPOO2.joueur.JoueurIA;
import ProjetPOO2.joueur.JoueurTourParTour;
import ProjetPOO2.joueur.Partie;
import ProjetPOO2.joueur.Partie2JoueursClavier;
import ProjetPOO2.joueur.PartieAvecJoueurIA;
import ProjetPOO2.joueur.PartieFluide;
import ProjetPOO2.joueur.PartieTourParTour;

public class controleur {

    private Jeu jeu;

    int cellSize = 20;

    @FXML
    private Text Joueur1;

    @FXML
    private Text Joueur2;

    @FXML
    private Canvas Plateau;

    @FXML
    private Button Quitter;

    @FXML
    private Canvas Serpent1;

    @FXML
    private Canvas Serpent2;

    @FXML
    private Text nomJoueur1;

    @FXML
    private Text nomJoueur2;

    @FXML
    private Button Demarrer;

    @FXML
    private Canvas nourriture;

    private Dessinateur dessinateur;

    @FXML
    public void initialize() {

        Serpent1.setMouseTransparent(true);
        Serpent2.setMouseTransparent(true);
        nourriture.setMouseTransparent(true);
        setupClickHandler();

        Platform.runLater(() -> {
            Plateau.getScene().setOnKeyPressed(this::handleKeyPress);
        });
    }

    public void handleKeyPress(KeyEvent event) {
        Partie partie = jeu.getPartie();
        if (partie instanceof Partie2JoueursClavier) {

            List<Joueur> joueurs = partie.getJoueur();

            if (joueurs.size() >= 2) {

                switch (event.getCode()) {
                    // Contrôles pour le Serpent 1
                    case L:
                        ((Joueur2AuClavier) joueurs.get(0)).AppueSurClavier(Direction.HAUT);
                        break;
                    case O:
                        ((Joueur2AuClavier) joueurs.get(0)).AppueSurClavier(Direction.BAS);
                        break;
                    case K:
                        ((Joueur2AuClavier) joueurs.get(0)).AppueSurClavier(Direction.GAUCHE);
                        break;
                    case M:
                        ((Joueur2AuClavier) joueurs.get(0)).AppueSurClavier(Direction.DROITE);
                        break;

                    // Contrôles pour le Serpent 2
                    case X:
                        ((Joueur2AuClavier) joueurs.get(1)).AppueSurClavier(Direction.HAUT);
                        ;
                        break;
                    case D:
                        ((Joueur2AuClavier) joueurs.get(1)).AppueSurClavier(Direction.BAS);
                        break;
                    case W:
                        ((Joueur2AuClavier) joueurs.get(1)).AppueSurClavier(Direction.GAUCHE);
                        break;
                    case C:
                        ((Joueur2AuClavier) joueurs.get(1)).AppueSurClavier(Direction.DROITE);
                        break;
                }
            }

        }
    }

    private void setupClickHandler() {

        Plateau.setOnMouseClicked(event -> {
            int cellSize = 20; // Correspond à la taille de la cellule utilisée pour le dessin
            int x = (int) event.getX() / cellSize;
            int y = (int) event.getY() / cellSize;

            Point clickedPoint = new Point(x, y);
            System.out.println("Clic sur la cellule : " + clickedPoint.getX() + ", " + clickedPoint.getY());
            // Utilisez clickedPoint selon la logique de votre jeu

            Partie partie = jeu.getPartie();
            if (partie instanceof PartieFluide) {
                ((JoueurFluide) (jeu.getPartie().getJoueur().get(0))).clicSurTerrain(clickedPoint);
            }

            if (partie instanceof PartieTourParTour) {
                Joueur joueurActuel = ((PartieTourParTour) partie).getJoueurActuel();
                if (joueurActuel != null) {
                    ((JoueurTourParTour) joueurActuel).clicSurTerrain(clickedPoint);
                }
            } else if (partie instanceof PartieAvecJoueurIA) {
                Joueur joueurActuel = ((PartieAvecJoueurIA) partie).getJoueurActuel();
                if (joueurActuel != null) {
                    if (joueurActuel instanceof JoueurIA) {
                        ((JoueurIA) joueurActuel).clicSurTerrain(clickedPoint);
                    } else {
                        ((JoueurTourParTour) joueurActuel).clicSurTerrain(clickedPoint);
                    }

                }

            }
        });
    }

    @FXML
    void Demarrer(ActionEvent event) {
        jeu.DemarrerPartie();
    }

    @FXML
    void QuitterPartie(ActionEvent event) {
        jeu.getPartie().terminer();

        try {
            // Obtention du Stage actuel
            Stage stage = (Stage) Plateau.getScene().getWindow();

            // Chargement de la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/partie.fxml"));
            Parent root = loader.load();

            // Création de la nouvelle scène
            Scene scene = new Scene(root);

            // Affichage de la nouvelle scène
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setJeu(Jeu jeu) {
        this.jeu = jeu;
        // controleur dans Partie
        jeu.getPartie().setControleurPourJoueurs(this);
        jeu.getPartie().setControleur(this);
        dessinateur = new Dessinateur(jeu);
        dessinateur.drawBoard(Plateau);

        dessinateur.dessinerNourritures(jeu.getPartie().getTerrain().getNourritures(), nourriture);

        // dessinerSerpent(jeu.getPartie().getJoueur().get(0).getSerpent(), Serpent1);
        // dessinerSerpent(jeu.getPartie().getJoueur().get(1).getSerpent(), Serpent2);
        dessinateur.drawInitialeSnake(jeu.getPartie().getJoueur().get(0).getSerpent(), Serpent1);
        if (!(jeu.getPartie() instanceof PartieFluide)) {
            dessinateur.drawInitialeSnake(jeu.getPartie().getJoueur().get(1).getSerpent(), Serpent2);
        }

    }

    public void verifierCollisionsEtFinDePartie() {
        for (Joueur joueur : jeu.getPartie().getJoueur()) {
            if (joueur.getSerpent().estMort()) {
                afficherMessageDefaite(joueur.getNom());
                break;
            }
        }
    }

    private void afficherMessageDefaite(String nomJoueur) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin de la Partie");
        alert.setHeaderText(null);
        alert.setContentText("Le joueur " + nomJoueur + " a perdu !");

        alert.showAndWait();
        retournerAuMenuPrincipal();
    }

    private void retournerAuMenuPrincipal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/partie.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = (Stage) Plateau.getScene().getWindow();
            if (stage != null) {
                stage.setScene(scene);
                stage.show();
            } else {
                System.out.println("Stage est null");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("Erreur NullPointerException: " + e.getMessage());
        }
    }

    public void mettreAJourUI(Joueur joueur) {
        Serpent serpent = joueur.getSerpent();
        Canvas canvasSerpent = (joueur == jeu.getPartie().getJoueur().get(0)) ? Serpent1 : Serpent2;
        dessinateur.dessinerSerpent(serpent, canvasSerpent);
        dessinateur.dessinerNourritures(jeu.getPartie().getTerrain().getNourritures(), nourriture);
        verifierCollisionsEtFinDePartie();
    }

    // Ajoutez d'autres méthodes selon la logique de votre jeu
}
