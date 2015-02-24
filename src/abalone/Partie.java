package abalone;

import java.awt.Color;

public class Partie {

	private Joueur joueur1;
	private Joueur joueur2;
	private Joueur joueurCourant;
	private Joueur joueurPrecedent;
	private Plateau plateau;
	int SCORE_GAGNANT = 6;
	
	public Partie(Joueur j1, Joueur j2, Plateau p) {
		this.joueur1 = j1;
		this.joueur2 = j2;
		this.joueurCourant = this.joueur1;
		this.joueurPrecedent = this.joueur2;
		this.plateau = p;
	}
	
	public void lancerPartie() {
		
		Joueur joueurTmp = null;
		this.plateau.initialiserPlateau();
		this.joueurCourant.setCouleur(Color.BLACK);
		this.joueurPrecedent.setCouleur(Color.WHITE);
		
		while (!finPartie(this.joueurPrecedent)) { // Vérification que le joueur précédent n'a pas gagné
			this.deplacer();
			System.out.println("Déplacement effectué.");
			this.plateau.afficher();
			joueurTmp = this.joueurPrecedent;
			this.joueurPrecedent =  this.joueurCourant;
			this.joueurCourant = joueurTmp;
			System.out.println("\n"+this.afficherScore());
		}
		
		System.out.println("La partie est terminée !\nLe gagnant est : " + this.joueurPrecedent.getNom());
	}
	
	public void deplacer() {
		this.joueurCourant.deplacer(this.plateau);
	}
	
	public boolean finPartie(Joueur j) {
		if (j.getScore() < SCORE_GAGNANT) {
			return false;
		} else {
			return true;
		}
	}
	
	public String afficherScore() {
		return this.joueur1.getNom() + " : " + this.joueur1.getScore() + " point(s).\n" + this.joueur2.getNom() + " : " + this.joueur2.getScore() + " point(s).";
	}
	
}
