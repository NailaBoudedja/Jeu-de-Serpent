package ProjetPOO2.terrain;

import java.util.ArrayList;
import java.util.List;

import ProjetPOO2.nourriture.Nourriture;

public class Terrain {
    private int largeur;
    private int hauteur;
    private List<Nourriture> nourritures;

    public Terrain(int largeur, int hauteur) {
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.nourritures = new ArrayList<>();
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void ajouterNourriture(Nourriture nourriture) {
        nourritures.add(nourriture);
    }

    public List<Nourriture> getNourritures() {
        return nourritures;
    }

}
