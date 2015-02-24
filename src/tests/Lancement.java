package tests;

import abalone.*;

public class Lancement {
	
	public static void main(String[] args) {
		Plateau plateau = new Plateau();
		Deplacement deplacementLigne = new DeplacementEnLigne(plateau);
		Deplacement deplacementFleche = new DeplacementEnFleche(plateau);
		
		Joueur homme = new Homme("Bernard", deplacementLigne, deplacementFleche);
		Joueur homme2 = new Homme("Alice", deplacementLigne, deplacementFleche);
		Joueur machine = new Machine("Tom", deplacementLigne, deplacementFleche);
		Joueur machine2 = new Machine("Donald", deplacementLigne, deplacementFleche);
		
		/*IA vs IA*/
		Partie partie = new Partie(machine, machine2, plateau);
		
		/*Homme vs IA*/
		//Partie partie = new Partie(homme, machine, plateau);
		
		/*Homme vs Homme*/
		//Partie partie = new Partie(homme, homme2, plateau);
		
		
		System.out.println("---------------------------------------- Bienvenue sur le jeu Abalone ------------------------------------------\n\n");
		System.out.println("Vous possédez les boules \"O\"\n\n ");
		
		partie.lancerPartie();
		
	}
}
