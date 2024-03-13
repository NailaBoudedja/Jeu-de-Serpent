package ProjetPOO2.joueur;

import java.util.ArrayList;
import java.util.List;

public class Jeu {

    private List<Partie> parties;
    Partie nouvellePartie;

    public Jeu() {
        // a laisser
        // Ajoutez d'autres initialisations si nécessaire
        this.parties = new ArrayList<>();
    }

    public List<Partie> getParties() {
        return parties;
    }

    public void LancerPartie() {
        parties.add(nouvellePartie);
    }

    public Partie getPartie(){

        return nouvellePartie;
    }

    public void setPartie(Partie partie){
        nouvellePartie = partie;
    }

    public void DemarrerPartie(){
        // lancement de la partie
        nouvellePartie.commencer();

    }

}
