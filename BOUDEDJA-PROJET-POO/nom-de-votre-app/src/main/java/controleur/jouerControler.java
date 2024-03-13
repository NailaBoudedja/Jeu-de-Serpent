package controleur;

import java.io.IOException;

import ProjetPOO2.joueur.Jeu;
import ProjetPOO2.joueur.Partie2JoueursClavier;
import ProjetPOO2.joueur.PartieAvecJoueurIA;
import ProjetPOO2.joueur.PartieFluide;
import ProjetPOO2.joueur.PartieTourParTour;
import ProjetPOO2.terrain.Terrain;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import controleur.controleur;

public class jouerControler {

    @FXML
    private TextField NomJoueur;

    @FXML
    private Button NouvellePartie;

    @FXML
    private Button ReprendrePartie;

    Jeu jeu;

    @FXML
    void Commencer(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Choix du mode de jeu");
        alert.setHeaderText("Choisissez le mode de jeu");

        ButtonType modeFluide = new ButtonType("Mode Fluide");
        ButtonType modeTourParTour = new ButtonType("Mode Tour par Tour");
        ButtonType mode2Joueurs = new ButtonType("Mode 2 Joueurs au Clavier");
        ButtonType modeAvecIA = new ButtonType("Mode avec IA"); // Nouveau mode de jeu
        ButtonType cancel = new ButtonType("Annuler");

        alert.getButtonTypes().setAll(modeFluide, modeTourParTour, mode2Joueurs, modeAvecIA, cancel);

        ButtonType result = alert.showAndWait().orElse(cancel);

        jeu = new Jeu();
        Terrain terrain = new Terrain(15, 15);

        if (result == modeFluide) {
            jeu.setPartie(new PartieFluide(terrain));
        } else if (result == modeTourParTour) {
            jeu.setPartie(new PartieTourParTour(terrain));
        } else if (result == mode2Joueurs) {
            jeu.setPartie(new Partie2JoueursClavier(terrain));
        } else if (result == modeAvecIA) {
            jeu.setPartie(new PartieAvecJoueurIA(terrain)); // Création de la partie avec IA
        } else {
            return;
        }
        jeu.LancerPartie();
        chargerNouvellePartie();
    }

    private void chargerNouvellePartie() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/nouvellePartie.fxml"));
            Parent root = loader.load();

            controleur controller = loader.getController();
            controller.setJeu(jeu);

            Scene currentScene = NouvellePartie.getScene();
            Stage currentStage = (Stage) currentScene.getWindow();

            Scene newScene = new Scene(root);
            currentStage.setScene(newScene);
            currentStage.setTitle("Nouvelle Partie");

            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur lors du chargement de nouvellePartie.fxml");
        }
    }

    @FXML
    void EcrirNomJoueur(ActionEvent event) {

    }

    @FXML
    void Reprendre(ActionEvent event) {

    }

    @FXML
    void QuitterJeu(ActionEvent event) {
        // Obtention du Stage actuel à partir du bouton (ou tout autre composant de la
        // scène)
        Stage stage = (Stage) NouvellePartie.getScene().getWindow();

        // Fermeture du Stage, ce qui fermera l'application
        stage.close();
    }

    public jouerControler getControler() {
        return this;
    }

}
