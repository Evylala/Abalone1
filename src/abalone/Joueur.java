package abalone;

import java.awt.Color;

public abstract class Joueur {
	
	protected String nom;
	protected int score;
	protected Deplacement deplacementEnLigne;
	protected Deplacement deplacementEnFleche;
	protected Color couleur;
	
	public Joueur(String nom, Deplacement deplLigne, Deplacement deplFleche) {
		this.nom = nom;
		this.score = 0;
		this.deplacementEnLigne = deplLigne;
		this.deplacementEnFleche = deplFleche;
		this.couleur = null;
	}
	
	public int getScore() {
		return this.score;
	}
	
	public String getNom() {
		return this.nom;
	}
	
	public Color getCouleur() {
		return this.couleur;
	}
	
	public void setCouleur(Color couleur) {
		this.couleur = couleur;
	}
	
	public void ajoutPoint() {
		this.score++;
	}
	
	public abstract void deplacer(Plateau p);
	
}
