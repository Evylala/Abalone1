package abalone;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Deplacement {
	
	protected ArrayList<Case> listeCasesAvantCoup;
	protected ArrayList<Case> listeCasesApresCoup;
	protected TypeDirection direction;
	protected String messageCoupImpossible;
	protected Plateau plateau;
	protected Color couleurDuJoueurCourant;
	protected boolean adversaireBouleSortie;
	protected int decalage_ligne; 
	int NB_LIG = 9;
	int INDEX_MIN = 0;
	int INDEX_MAX = 60;
	
	public Deplacement(Plateau p) {
		this.listeCasesAvantCoup = new ArrayList<Case>();
		this.listeCasesApresCoup = new ArrayList<Case>();
		this.direction = null;
		this.messageCoupImpossible = "";
		this.plateau = p;
		this.couleurDuJoueurCourant = null;
		this.adversaireBouleSortie = false;
		this.decalage_ligne = 0;
	}
	
	public boolean adversaireBouleSortie() {
		return false;
	}
	
	public String getMessageErreur() {
		return this.messageCoupImpossible;
	}
	
	public void setListeCasesAvantCoup(ArrayList<Case> liste) {
		this.listeCasesAvantCoup = liste;
	}
	
	public void setListeCasesApresCoup(ArrayList<Case> liste) {
		this.listeCasesApresCoup = liste;
	}
	
	public void setDirection (TypeDirection deplacement) {
		this.direction = deplacement;
	}
	
	public void setPlateau(Plateau p) {
		this.plateau = p;
	}
	
	public void setCouleurDuJoueurCourant(Color couleur) {
		this.couleurDuJoueurCourant = couleur;
	}

	public void setAdversaireBouleSortie(boolean b) {
		this.adversaireBouleSortie = b;
	}
	
	public void setDecalageLigne(int d) {
		this.decalage_ligne = d;
	}
	
	public abstract boolean deplacementPossible(int indexCase, TypeDirection direction, int nbBoules, Joueur joueur); // Déplacement en ligne
	public abstract boolean deplacementPossible(TypeDirection direction, Joueur joueur, ArrayList<Integer> listeIndex); // Déplacement en flèche
	
	/**
	 * Remet tous les attributs à "zero"
	 * @param direction
	 * @param couleur
	 */
	public void setNouveauDeplacement(TypeDirection direction, Color couleur) {
		
		this.setListeCasesAvantCoup(new ArrayList<Case>(this.plateau.getListeCases()));
		this.setListeCasesApresCoup(new ArrayList<Case>(this.plateau.getListeCases()));
		this.setDirection(direction);
		this.setAdversaireBouleSortie(false);
		this.setCouleurDuJoueurCourant(couleur);
		this.setDecalageLigne(0);
	}
	
	/**
	 * Calcule de la nouvelle position d'une boule après le déplacement (renvoie donc un index)
	 * @param indexCase
	 * @return index
	 */
	public int calculterIndexApresDeplacement(int indexCase) {
		int index = 0;
		int num_lig = this.plateau.getNumeroLigneByIndex(indexCase);
		int nb_cases = this.plateau.getNombreCasesSurLigne(num_lig);
		
		if (direction == TypeDirection.HAUT_GAUCHE) {
			if (num_lig >= 1 && num_lig <= 5) {
				 index = indexCase - nb_cases;
			} else {
				 index = indexCase - nb_cases - 1;
			}
			this.decalage_ligne = -1; //Détermine après un déplacement, de combien de lignes la boule est déplacée
		} else if (direction == TypeDirection.HAUT_DROIT) {
			if (num_lig >= 1 && num_lig <= 5) {
				 index = indexCase - nb_cases + 1;
			} else {
				 index = indexCase - nb_cases;
			}
			this.decalage_ligne = -1;
		} else if (direction == TypeDirection.BAS_GAUCHE) {
			if (num_lig >= 1 && num_lig <= 4) {
				 index = indexCase + nb_cases;
			} else {
				 index = indexCase + nb_cases - 1;
			}
			this.decalage_ligne = 1;
		} else if (direction == TypeDirection.BAS_DROIT) {
			if (num_lig >= 1 && num_lig <= 4) {
				 index = indexCase + nb_cases + 1;
			} else {
				 index = indexCase + nb_cases;
			}
			 this.decalage_ligne = 1;
		} else if (direction == TypeDirection.GAUCHE) {
			index = indexCase - 1;
			this.decalage_ligne = 0;
		} else {
			index = indexCase + 1;
			this.decalage_ligne = 0;
		}
		
		return index;
	}
	
	/**
	 * Vérifie si la boule déplacée (oldIndex à newIndex) sort du plateau
	 * @param oldIndex
	 * @param newIndex
	 * @return boolean
	 */
	public boolean sortDuPlateau(int oldIndex, int newIndex) {

		if (newIndex < INDEX_MIN || newIndex > INDEX_MAX) {
			return true;
		}
		//Si les numéros de ligne sont différents, la boule n'est pas à la position où elle aurait dû être, donc elle est sortie du plateau.
		if (this.plateau.getNumeroLigneByIndex(oldIndex) + this.decalage_ligne == this.plateau.getNumeroLigneByIndex(newIndex)) {
			return false;
		}
		return true;
	}
	
	/**
	 * Retourne la couleur (blanche ou noire) en fonction du type de case 
	 * @param type
	 * @returr Color
	 */
	public Color getCouleurByTypeCase(TypeCase type) {
		if (type == TypeCase.BOULE_BLANCHE) {
			return Color.WHITE;
		} else if (type == TypeCase.BOULE_NOIRE) {
			return Color.BLACK;
		} else {
			return null;
		}
	}

}
