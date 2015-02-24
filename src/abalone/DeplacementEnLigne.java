package abalone;

import java.awt.Color;
import java.util.ArrayList;

public class DeplacementEnLigne extends Deplacement {

	public DeplacementEnLigne(Plateau p) {
		super(p);
	}
	
	@Override
	/**
	 * Appelle les fonctions utiles pour v�rifier le d�placement et ajoute un point au joueur s'il a sorti une boule de l'adversaire.
	 * Retourne vrai ou faux en fonction de si le d�placement est possible.
	 * @param indexCase 
	 * @param direction 
	 * @param nbBoules
	 * @param joueur
	 * return boolean
	 */
	public boolean deplacementPossible(int indexCase, TypeDirection direction, int nbBoules, Joueur joueur) {
		
		this.setNouveauDeplacement(direction, joueur.getCouleur());
		
		if (this.deplacer(indexCase, nbBoules)) {
			if (this.adversaireBouleSortie) {
				joueur.ajoutPoint();
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Effectue le d�placement des boules et v�rifie les r�gles de gestion, retourne vrai si le d�placement est possible et a �t� effectu�.
	 * Ne g�re que les d�placements en ligne.
	 * Dans un d�placement en ligne, une premi�re boule est pouss�e pour pousser les autres, d'o� le fait qu'il n'y ait besoin que d'un seul index.
	 * @param indexCase
	 * @param nbBoules
	 * @return boolean
	 */
	public boolean deplacer(int indexCase, int nbBoules) {
		
		int indexCourant = indexCase;
		int indexSuivant = 0;
		TypeCase typeTmp = TypeCase.VIDE;
		TypeCase type = null;
		Color couleur = null;
		boolean deplacementTermine = false;
		int nbCasesParcourues = 0;
		int i = 0;
		
		/* 
		 * Boucle while qui v�rifie en fonction du nbBoules choisi, si elles appartiennent bien au joueur, s'il ne fait pas sortir ses propres du plateau, 
		 * et s'il y a bien une boule � la position choisie. Effectue aussi le d�placement de ces boules.
		 */
		while (i < nbBoules && !deplacementTermine) {
			
			indexSuivant = this.calculterIndexApresDeplacement(indexCourant);
			type = this.listeCasesAvantCoup.get(indexCourant).getType();
			couleur = this.getCouleurByTypeCase(type);

			if (sortDuPlateau(indexCourant, indexSuivant)) {
				this.messageCoupImpossible = "L'une de vos boules sort du plateau, veuillez refaire votre choix.";
				return false;
			}
			if (this.listeCasesAvantCoup.get(indexSuivant).getType() == TypeCase.VIDE) {
				deplacementTermine = true;
			}
			if (type == TypeCase.VIDE && i == 0) {
				this.messageCoupImpossible = "Il n'y a aucune boule � cette position, veuillez refaire votre choix;";
				return false;
			}
			
			if (couleur != this.couleurDuJoueurCourant) {
				this.messageCoupImpossible = "Vous tentez de jouer une boule de vos adversaires, veuillez refaire votre choix.";
				return false;
			}
			
			if (i==0) {
				this.listeCasesApresCoup.set(indexCourant, new Case(typeTmp));
			}
			
			this.listeCasesApresCoup.set(indexSuivant, new Case(type));
			indexCourant = indexSuivant;
			i++;
		}
		
		/*
		 * Boucle while avec cas de Sumito, qui v�rifie donc si la pouss�e est possible.
		 * Si oui, effectue le d�placement
		 */
		while (!deplacementTermine) {
			nbCasesParcourues++;
			if (nbCasesParcourues >= nbBoules) {
				this.messageCoupImpossible = "Sumito pouss�e impossible (inf�riorit� num�rique ou �galit�), veuillez refaire votre choix;";
				return false;
			}

			indexSuivant = this.calculterIndexApresDeplacement(indexCourant);
			type = this.listeCasesAvantCoup.get(indexCourant).getType();
			couleur = this.getCouleurByTypeCase(type);
			
			if (couleur == this.couleurDuJoueurCourant) {
				//Cas o� l'on pousse des boules de l'adversaire mais qu'il y a une boule � nous dans le bloc qu'on essaye de pousser
				this.messageCoupImpossible = "Sumito pouss�e impossible (vous essayez de pousser une de vos boules), veuillez refaire votre choix.";
				return false;
			} else if (sortDuPlateau(indexCourant, indexSuivant)) {
				this.setAdversaireBouleSortie(true);
				deplacementTermine = true;
			}
			if (!deplacementTermine && this.listeCasesAvantCoup.get(indexSuivant).getType() == TypeCase.VIDE) {
				deplacementTermine = true;
			}
			if (!this.adversaireBouleSortie) {
				this.listeCasesApresCoup.set(indexSuivant, new Case(type));
			}
			
			if (!deplacementTermine) {
				typeTmp = this.listeCasesAvantCoup.get(indexSuivant).getType();
			}
			indexCourant = indexSuivant;
			
		}
		
		this.plateau.setListeCases(listeCasesApresCoup);
		return true;
	}

	@Override
	//N'est utilis�e que pour le d�placement en fl�che
	public boolean deplacementPossible(TypeDirection deplacement, Joueur joueur,
			ArrayList<Integer> listeIndex) {
		// TODO Auto-generated method stub
		return false;
	}
}
