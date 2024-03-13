package com.votreentreprise.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;


import javafx.scene.control.Button;
import javafx.application.Platform;
import javafx.scene.layout.StackPane;

//import controleur.jouerControler;


public class App extends Application {
   @Override
    public void start(Stage primaryStage) {
        try {
            // Chargez le fichier FXML de la première scène
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/partie.fxml"));
            Parent root = loader.load();

            // Obtenez le contrôleur de la première scène (si nécessaire)
          //  jouerControler jouerControler = loader.getController();

            // Créez une nouvelle scène et configurez-la
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Votre Titre");

            // Affichez la fenêtre principale
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
            // Gérez les erreurs liées au chargement de la première scène
        }
    }

    public static void main(String[] args) {
        launch();
    }
}










