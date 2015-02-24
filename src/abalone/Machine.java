package abalone;

import java.util.ArrayList;

public class Machine extends Joueur {
	
	public Machine (String nom, Deplacement deplLigne, Deplacement deplFleche) {
		super(nom, deplLigne, deplFleche);
	}

	@Override
	/**
	 * L'IA effectue un déplacement (en ligne) : récupération la liste des cases où se situe une des boules de l'IA. 
	 * Random pour choisir un index de cette liste et donc une boule au hasard. 
	 * Random pour choisir un nombre de boules (entre 1 et 3).
	 * A partir de ça, teste si le déplacement est possible pour une direction aléatoire
	 * @param p
	 */
	public void deplacer(Plateau p) {
		boolean deplacementOK = false;
		int indexAleatoireListe = 0;
		int indexCaseChoisie = 0;
		int nbBoules = 0;
		int directionAleatoire = 0;
		Deplacement deplacement = this.deplacementEnLigne;
		TypeDirection direction = null;
		ArrayList<Integer> listeCasesIA = p.getIndexCasesNotreCouleur(this.getCouleur());
		
		while (!deplacementOK) {
			
			indexAleatoireListe = (int)(Math.random()*listeCasesIA.size()-1);
			indexCaseChoisie = listeCasesIA.get(indexAleatoireListe);
			nbBoules = 1 + (int)(Math.random()*3);
			directionAleatoire = (int)(Math.random()*5);
			direction = getDirection(directionAleatoire);
			
			deplacementOK = deplacement.deplacementPossible(indexCaseChoisie, direction, nbBoules, this);
			if (deplacementOK) {
				break;
			}
			if (deplacementOK) {
				break;
			}
			
		}
		
		System.out.println(this.getNom() + " a joué.");
	}
	
	public TypeDirection getDirection(int index) {
		switch(index) {
			case 0:
				return TypeDirection.BAS_DROIT;
			case 1:
				return TypeDirection.BAS_GAUCHE;
			case 2:
				return TypeDirection.DROITE;
			case 3: 
				return TypeDirection.GAUCHE;
			case 4:
				return TypeDirection.HAUT_DROIT;
			case 5: 
				return TypeDirection.HAUT_GAUCHE;
			default :
				return null;
				
		}
	}
	
}
