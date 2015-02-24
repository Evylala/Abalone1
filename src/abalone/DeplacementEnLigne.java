package abalone;

import java.awt.Color;
import java.util.ArrayList;

public class DeplacementEnLigne extends Deplacement {

	public DeplacementEnLigne(Plateau p) {
		super(p);
	}
	
	@Override
	/**
	 * Appelle les fonctions utiles pour vérifier le déplacement et ajoute un point au joueur s'il a sorti une boule de l'adversaire.
	 * Retourne vrai ou faux en fonction de si le déplacement est possible.
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
	 * Effectue le déplacement des boules et vérifie les règles de gestion, retourne vrai si le déplacement est possible et a été effectué.
	 * Ne gère que les déplacements en ligne.
	 * Dans un déplacement en ligne, une première boule est poussée pour pousser les autres, d'où le fait qu'il n'y ait besoin que d'un seul index.
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
		 * Boucle while qui vérifie en fonction du nbBoules choisi, si elles appartiennent bien au joueur, s'il ne fait pas sortir ses propres du plateau, 
		 * et s'il y a bien une boule à la position choisie. Effectue aussi le déplacement de ces boules.
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
				this.messageCoupImpossible = "Il n'y a aucune boule à cette position, veuillez refaire votre choix;";
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
		 * Boucle while avec cas de Sumito, qui vérifie donc si la poussée est possible.
		 * Si oui, effectue le déplacement
		 */
		while (!deplacementTermine) {
			nbCasesParcourues++;
			if (nbCasesParcourues >= nbBoules) {
				this.messageCoupImpossible = "Sumito poussée impossible (infériorité numérique ou égalité), veuillez refaire votre choix;";
				return false;
			}

			indexSuivant = this.calculterIndexApresDeplacement(indexCourant);
			type = this.listeCasesAvantCoup.get(indexCourant).getType();
			couleur = this.getCouleurByTypeCase(type);
			
			if (couleur == this.couleurDuJoueurCourant) {
				//Cas où l'on pousse des boules de l'adversaire mais qu'il y a une boule à nous dans le bloc qu'on essaye de pousser
				this.messageCoupImpossible = "Sumito poussée impossible (vous essayez de pousser une de vos boules), veuillez refaire votre choix.";
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
	//N'est utilisée que pour le déplacement en flèche
	public boolean deplacementPossible(TypeDirection deplacement, Joueur joueur,
			ArrayList<Integer> listeIndex) {
		// TODO Auto-generated method stub
		return false;
	}
}
