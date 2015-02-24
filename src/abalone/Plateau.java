package abalone;

import java.awt.Color;
import java.util.ArrayList;

public class Plateau {
	
	private ArrayList<Case> listeCases;
	int NB_LIG = 9;
	int NB_COL_MAX = 9;
	int NOMBRE_CASES = 61;
	
	public Plateau() {
		this.listeCases = new ArrayList<Case>();
	}
	
	/*
	 * 
	 * Le plateau implémenté :
	 
		 0  1  2  3  4
	   5  6  7  8  9 10
	 11 12 13 14 15 16 17
	18 19 20 21 22 23 24 25
  26 27 28 29 30 31 32 33 34
    35 36 37 38 39 40 41 42 
      43 44 45 46 47 48 49
       50 51 52 53 54 55
         56 57 58 59 60
         
        Chaque numéro correspond à un index dans la liste (listeCases) et à une position de case.
		Une liste de taille 61 est donc créée pour implémenter le plateau.
         
    */    
	
	public ArrayList<Case> getListeCases() {
		return this.listeCases;
	}
	
	public void setListeCases(ArrayList<Case> listeCases) {
		this.listeCases = listeCases;
	}
	
	/**
	 * Crée la liste des boules et le plateau de départ
	 */
	public void initialiserPlateau() {
		for (int i=0; i<NOMBRE_CASES; i++) {
			if ((i >= 0 && i <= 10) || (i >= 13 && i <= 15)) {
				this.listeCases.add(new Case(TypeCase.BOULE_BLANCHE));
			} else if ((i>=45 && i<=47) || (i>=50 && i<=60)) {
				this.listeCases.add(new Case(TypeCase.BOULE_NOIRE));
			} else {
				this.listeCases.add(new Case(TypeCase.VIDE));
			}
		}
		
		this.afficher();
	}
	
	/**
	 * Affichage du plateau (variable tableauPlateau)
	 */
	public void afficher() {
		int decalage = 0;
		int num_lig = 0;
		
		for (int i=0; i<this.listeCases.size(); i++) {
			if (num_lig != getNumeroLigneByIndex(i)) {
				if (num_lig != 1) {
					for (int j=0; j<decalage; j++) {
						System.out.print(" ");
					}
					
				}
				System.out.println();
				num_lig = getNumeroLigneByIndex(i);
				decalage = getDecalage(getNumeroLigneByIndex(i));
				for (int j=0; j<decalage; j++) {
					System.out.print(" ");
				}
			}
			System.out.print(" " + this.listeCases.get(i).toString());
		}
		System.out.println();
	}
	
	/**
	 * Retourne l'index correspondant à la boule choisie à une ligne et une colonne données
	 * @param num_lig
	 * @param num_col
	 * @return int
	 */
	public int getIndexCase(int num_lig, int num_col) {
		int index=0;
		int i;
		
		for (i=1;i<num_lig;i++) {
			index += getNombreCasesSurLigne(i);
		}
		index += num_col - 1;
		
		return index;
	}
	
	/**
	 * retourne le nombre de cases sur une ligne donnée
	 * @param num_lig
	 * @return int
	 */
	public int getNombreCasesSurLigne(int num_lig) {
		if (num_lig >=1 && num_lig <=5) {
			return num_lig + 4;
		} else {
			return getNombreCasesSurLigne(10 - num_lig);
		}
	}
	
	/**
	 * Retourne le numéro de ligne d'une case à un certain index
	 * @param index
	 * @return int
	 */
	public int getNumeroLigneByIndex(int index) {
		for (int i=1; i<=NB_LIG+1; i++) {
			if (index < getIndexCase(i, 1)) {
				return i-1;
			}
		}
		
		return 0;
	}
	
	/**
	 * Retourne le décalage nécessaire à une ligne donnée pour l'affichage du plateau hexagonal (espace à gauche ou à droite) 
	 * @param num_lig
	 * @return int
	 */
	public int getDecalage(int num_lig) {
		if (num_lig >= 1 && num_lig <= 5) {
			return 5 - num_lig;
		} else {
			return num_lig - 5;
		}
	}
	
	/**
	 * Retourne la liste d'index des cases contenant des boules d'une couleur donnée
	 * Retourne donc toutes les positions des boules d'un joueur dans une liste
	 * N'est utilisée que pour l'IA
	 * @param couleur
	 * @return ArrayList<Integer>
	 */
	public ArrayList<Integer> getIndexCasesNotreCouleur(Color couleur) {
		TypeCase type = null;
		ArrayList<Integer> listeIndexCases = new ArrayList<Integer>();
		
		if (couleur == Color.WHITE) {
			type = TypeCase.BOULE_BLANCHE;
		} else {
			type = TypeCase.BOULE_NOIRE;
		}
		
		for (int i=0; i<this.listeCases.size(); i++) {
			if (this.listeCases.get(i).getType() == type) {
				listeIndexCases.add(i);
			}
		}
		
		return listeIndexCases;
	}

}
