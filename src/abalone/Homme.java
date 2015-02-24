package abalone;

import java.util.ArrayList;
import java.util.Scanner;

public class Homme extends Joueur {

	Scanner sc = new Scanner(System.in);
	
	public Homme(String nom, Deplacement deplLigne, Deplacement deplFleche) {
		super(nom, deplLigne, deplFleche);
	}
	
	@Override
	/**
	 * Le joueur effectue un déplacement : il choisit le mouvement, la boule et la direction souhaités.
	 * @param plateau
	 */
	public void deplacer(Plateau plateau) {
		
		boolean deplacementOK = false;
		int nbBoules = 0;
		int indexCase = 0;
		TypeDeplacement typeDeplacement = null;
		TypeDirection direction = null;
		Deplacement deplacement = null;
		ArrayList<Integer> listeIndex = new ArrayList<Integer>();
		int i = 0;
		
		while(deplacementOK == false) {
			i=0;
			System.out.println(("\n\nAu tour de "+nom));
			
			typeDeplacement = this.choixDeplacement();
			listeIndex.clear();
			//Cas du déplacement en ligne
			if (typeDeplacement == TypeDeplacement.EN_LIGNE) {
				deplacement = this.deplacementEnLigne;
				nbBoules = this.choixNombreBoulesADeplacer(1);
				indexCase = this.choixCase(plateau);
				direction = this.choixDirection();
				//Vérification si le déplacement est possible dans la classe "Deplacement"
				deplacementOK = deplacement.deplacementPossible(indexCase, direction, nbBoules, this);
			
			//Cas du déplacement en flèche
			} else {
				deplacement = this.deplacementEnFleche;
				nbBoules = this.choixNombreBoulesADeplacer(2);
				
				while (i < nbBoules) {
					System.out.println("Choix boule " + (i+1) );
					indexCase = this.choixCase(plateau);
					if (!listeIndex.contains(indexCase)) {
						listeIndex.add(indexCase);
					} else {
						i = -1;
						listeIndex.clear();
						System.out.println("Vous avez entré deux fois la même boule, veuillez recommencer;");
					}
					i++;
				}
				direction = this.choixDirectionEnFleche();
				//Vérification si le déplacement est possible dans la classe "Deplacement"
				deplacementOK = deplacement.deplacementPossible(direction, this, listeIndex);
			}
			
			if (!deplacementOK) {
				System.out.println(deplacement.getMessageErreur());
				plateau.afficher();
			}
		}
  	}
	
	public TypeDeplacement choixDeplacement() {
		TypeDeplacement deplacement = null;
		int choix = 0;
		
		System.out.println("Saisissez le mouvement souhaité");
		System.out.println("1 : EN LIGNE, 2 : EN FLECHE");
		choix = sc.nextInt();
		while (choix<1 || choix >2) {
			System.out.println("Saisie invalide");
			System.out.println("1 : EN LIGNE, 2 : EN FLECHE");
			choix = sc.nextInt();
		}
		switch (choix) {
			case 1 :
				deplacement = TypeDeplacement.EN_LIGNE;
				break;
			case 2 :
				deplacement = TypeDeplacement.EN_FLECHE;
				break;
		}
		return deplacement;
	}
	
	/**
	 * 
	 * @param nbBoulesMin (Vaut 1 si mouvement en ligne, 2 si mouvement en flèche)
	 * @return int
	 */
	public int choixNombreBoulesADeplacer(int nbBoulesMin) {
		int nbBoules = 0;
		System.out.println("Combien de boules souhaitez-vous déplacer (Entre " + nbBoulesMin + " et 3):");
		nbBoules = sc.nextInt();
		while (nbBoules<nbBoulesMin || nbBoules >3) {
			System.out.println("Saisie invalide");
			System.out.println("Combien de boules souhaitez-vous déplacer (Entre " + nbBoulesMin + " et 3):");
			nbBoules = sc.nextInt();
		}
		
		return nbBoules;
	}
	
	public int choixCase(Plateau plateau) {
		
		int num_lig = 0;
		int num_col = 0;
		int num_col_max = 0;
		
		System.out.println("Choisissez une boule\nNuméro de ligne (Entre 1 et " + plateau.NB_LIG + ") :");
		num_lig = sc.nextInt();
		while (num_lig < 1 || num_lig > plateau.NB_LIG) {
			System.out.println("Numéro de ligne invalide");
			System.out.println("Resaissez (Entre 1 et 9):");
			num_lig = sc.nextInt();
		}
		
		num_col_max = plateau.getNombreCasesSurLigne(num_lig);
		System.out.println("Numéro de colonne (Entre 1 et "+ num_col_max + ") :");
		num_col = sc.nextInt();
		while (num_col < 1 || num_col > num_col_max) {
			System.out.println("Numéro de colonne invalide");
			System.out.println("Resaissez (Entre 1 et " + num_col_max + ") :");
			num_col = sc.nextInt();
		}
		
		return plateau.getIndexCase(num_lig, num_col);
	}
	
	public TypeDirection choixDirection() {
		int choix = 0;
		TypeDirection direction = null;
		
		System.out.println("Saisissez la direction souhaitée");
		System.out.println("1 : HAUT_GAUCHE, 2 : HAUT_DROIT, 3 : DROITE, 4 : GAUCHE, 5 : BAS_GAUCHE, 6 : BAS_DROIT");
		choix = sc.nextInt();
		
		while (choix>6 || choix<1) {
			System.out.println("Choix incorrect, resaisissez,");
			System.out.println("1 : HAUT_GAUCHE, 2 : HAUT_DROIT, 3 : DROITE, 4 : GAUCHE, 5 : BAS_GAUCHE, 6 : BAS_DROIT");
			choix = sc.nextInt();
		}
		
		switch (choix) {
			case 1 :
				direction = TypeDirection.HAUT_GAUCHE;
				break;
			case 2 :
				direction = TypeDirection.HAUT_DROIT;
				break;
			case 3 :
				direction = TypeDirection.DROITE;
				break;
			case 4 : 
				direction = TypeDirection.GAUCHE;
				break;
			case 5 :
				direction = TypeDirection.BAS_GAUCHE;
				break;
			case 6 :
				direction = TypeDirection.BAS_DROIT;
				break;
		}
		
		return direction;
	}
	
	public TypeDirection choixDirectionEnFleche() {
		int choix = 0;
		TypeDirection direction = null;
		
		System.out.println("Saisissez la direction souhaitée");
		System.out.println("1 : HAUT_GAUCHE, 2 : HAUT_DROIT, 5 : BAS_GAUCHE, 6 : BAS_DROIT");
		choix = sc.nextInt();
		
		while ((choix>6 || choix<1) || choix == 3 || choix == 4) {
			System.out.println("Choix incorrect, resaisissez,");
			System.out.println("1 : HAUT_GAUCHE, 2 : HAUT_DROIT, 5 : BAS_GAUCHE, 6 : BAS_DROIT");
			choix = sc.nextInt();
		}
		
		switch (choix) {
			case 1 :
				direction = TypeDirection.HAUT_GAUCHE;
				break;
			case 2 :
				direction = TypeDirection.HAUT_DROIT;
				break;
			case 3 :
				direction = TypeDirection.DROITE;
				break;
			case 4 : 
				direction = TypeDirection.GAUCHE;
				break;
			case 5 :
				direction = TypeDirection.BAS_GAUCHE;
				break;
			case 6 :
				direction = TypeDirection.BAS_DROIT;
				break;
		}
		
		return direction;
	}
	
}

