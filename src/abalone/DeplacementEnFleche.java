package abalone;

import java.awt.Color;
import java.util.ArrayList;

public class DeplacementEnFleche extends Deplacement {

	public DeplacementEnFleche(Plateau p) {
		super(p);
	}

	@Override
	/**
	 * Appelle les fonctions utiles pour v�rifier le d�placement et ajoute un point au joueur s'il a sorti une boule de l'adversaire.
	 * Retourne vrai ou faux en fonction de si le d�placement est possible.
	 * @param direction, 
	 * @param joueur
	 * @param listeIndex
	 * return boolean
	 */
	public boolean deplacementPossible(TypeDirection direction, Joueur joueur,
			ArrayList<Integer> listeIndex) {
		
		this.setNouveauDeplacement(direction, joueur.getCouleur());
		
		if (this.deplacer(listeIndex)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Effectue le d�placement des boules et v�rifie les r�gles de gestion, retourne vrai si le d�placement est possible et a �t� effectu�.
	 * Ne g�re que les d�placements en fl�che.
	 * @param indexCase
	 * @param nbBoules
	 * @return boolean
	 */
	public boolean deplacer(ArrayList<Integer> listeIndex) {
		int indexCourant = 0;
		int indexSuivant = 0;
		TypeCase type = null;
		Color couleur = null;
		
		TypeDirection directionBloc = this.getDirectionDuBloc(listeIndex.get(0), listeIndex.get(1));
		if (listeIndex.size()>2) {
			if (directionBloc != this.getDirectionDuBloc(listeIndex.get(1), listeIndex.get(2))) {
				this.messageCoupImpossible = "Le bloc choisi n'est pas align�, veuillez refaire votre choix;";
				return false;
			}
		}
		
		/* 
		 * Boucle qui v�rifie en fonction de la taille de la liste d'index (=nbBoules), si elles appartiennent bien au joueur, s'il ne fait pas sortir ses propres du plateau, 
		 * et s'il y a bien une boule � la position choisie. Effectue aussi le d�placement de ces boules.
		 */
		for (int i=0; i<listeIndex.size(); i++) {
			indexCourant = listeIndex.get(i);
			indexSuivant = this.calculterIndexApresDeplacement(indexCourant);
			type = this.listeCasesAvantCoup.get(indexCourant).getType();
			couleur = this.getCouleurByTypeCase(type);
			
			if (couleur != this.couleurDuJoueurCourant) {
				this.messageCoupImpossible = "Vous essayez de jouer une boule de votre adversaire, veuillez refaire votre choix;";
				return false;
			}
			if (sortDuPlateau(indexCourant, indexSuivant)) {
				this.messageCoupImpossible = "L'une de vos boules sort du plateau, veuillez refaire votre choix;";
				return false;
			}
			if (this.listeCasesAvantCoup.get(indexSuivant).getType() != TypeCase.VIDE) {
				//Impossible de pousser des boules dans un mouvement en fl�che
				this.messageCoupImpossible = "Sumito pouss�e impossible (mouvement en fl�che), veuillez refaire votre choix;";
				return false;
			}
			this.listeCasesApresCoup.set(indexCourant, new Case(TypeCase.VIDE));
			this.listeCasesApresCoup.set(indexSuivant, new Case(type));
		}
		
		this.plateau.setListeCases(listeCasesApresCoup);
		return true;
	}
	
	/**
	 * D�termine la direction d'index1 � index2
	 * @param index1
	 * @param index2
	 * @return TypeDirection
	 */
	public TypeDirection getDirectionDuBloc(int index1, int index2) {
		TypeDirection direction = null;
		int difference = index2 - index1;
		int num_lig = this.plateau.getNumeroLigneByIndex(index1);
		int nb_cases = this.plateau.getNombreCasesSurLigne(num_lig);
		
		if (difference == 1) {
			direction = TypeDirection.DROITE;
		} else if (difference == -1) {
			direction = TypeDirection.GAUCHE;
		} else if (difference > 0) { // Vers le bas
			if (num_lig >= 1 && num_lig <= 4) {
				if (difference == nb_cases) {
					direction = TypeDirection.BAS_GAUCHE;
				} else {
					direction = TypeDirection.BAS_DROIT;
				}
			} else {
				if (difference == nb_cases) {
					direction = TypeDirection.BAS_DROIT;
				} else {
					direction = TypeDirection.BAS_GAUCHE;
				}
			}
		} else { // Vers le haut
			if (num_lig >= 1 && num_lig <= 5) {
				if (difference == - nb_cases) {
					direction = TypeDirection.HAUT_GAUCHE;
				} else {
					direction = TypeDirection.HAUT_DROIT;
				}
			} else {
				if (difference == - nb_cases) {
					direction = TypeDirection.HAUT_DROIT;
				} else {
					direction = TypeDirection.HAUT_GAUCHE;
				}
			}
		}
		return direction;
	
	}
	
	@Override
	//N'est utilis�e que pour le d�placement en ligne
	public boolean deplacementPossible(int indexCase, TypeDirection direction,
			int nbBoules, Joueur joueur) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
