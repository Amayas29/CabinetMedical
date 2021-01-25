package utilisateur;

/***
 * Une enum des types de utilisateur du logiciel
 * 
 */
public enum Types {

	Docteur("Docteur"),
	Secretaire("Secretaire");
	
	String type;

	private Types(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}