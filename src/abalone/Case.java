package abalone;

public class Case {
	
	TypeCase type;
	
	public Case(TypeCase type) {
		this.type = type;
	}
	
	public TypeCase getType() {
		return this.type;
	}
	
	public void setType(TypeCase type) {
		this.type = type;
	}
	
	public boolean caseVide() {
		return this.type == TypeCase.VIDE;
	}
	
	/**
	 * Est appelée pour l'affichage des cases sur le plateau
	 */
	public String toString() {
		if (this.type == TypeCase.VIDE) {
			return "-";
		} else if (this.type == TypeCase.BOULE_BLANCHE) {
			return "X";
		} else {
			return "O";
		}
	}
}
