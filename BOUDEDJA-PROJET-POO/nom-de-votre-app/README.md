Le projet se compile et s'exécute avec la commande "mvn javafx:run".

Le jeu comporte quatre modes.
----------------------------

Une fois la première fenêtre affichée, on clique sur "Nouvelle Partie" et on choisit le mode souhaité. Pour démarrer la partie, il faut cliquer sur le bouton "Démarrer", et cela pour tous les modes.

Mode Fluide : un seul serpent est présent dans le jeu. La partie commence en cliquant sur "Démarrer". Le serpent se déplace et change de direction à chaque clic sur le terrain.

Mode 2 Joueurs sur Clavier : la partie se joue à deux joueurs. Le joueur 1 utilise les touches W, X, C, D et le joueur 2 les touches K, L, M, O pour se déplacer.

Mode Tour par Tour : la partie se joue également à deux joueurs. Chacun joue à son tour en cliquant sur la direction souhaitée.

Mode Avec IA : cette partie est similaire au mode Tour par Tour, à la différence que le deuxième joueur est une IA.

La partie se termine en cliquant sur "Quitter Partie".

Le jeu se termine en cliquant sur "Quitter".

Remarque : le terrain est sans bordures. Les conditions de défaite sont l'autocollision et la collision avec un autre serpent.

Architecture du code :
---------------------

Le code est divisé en trois grandes parties :

    Les Contrôleurs :
        Deux contrôleurs sont présents, un pour le jeu et un autre pour la partie.
        Un Dessinateur pour gérer l'interface graphique.

    Le Noyau du Jeu (ProjetPOO2) :

        Joueur : comprend deux grandes parties :
        - Interface Joueur et les classes qui l'implémentent.
        - Interface Partie et les classes qui l'implémentent.

        MonJeuSnake : contient trois parties :
        - Serpent :
        - Classe abstraite Segment avec deux classes filles : SegmentCorps et SegmentTête.
        - Classe Serpent qui utilise Segment.
        - Direction : une énumération de quatre directions.
        - Point : une classe représentant les coordonnées sur la grille de jeu.

        Nourriture :
            Classe Nourriture utilisant Point.

        Terrain : contient une classe Terrain utilisée par Partie.

    Ressources :
        Contient les fichiers FXML des deux contrôleurs.
        Deux images pour le jeu qui n'ont pas été chargées.

