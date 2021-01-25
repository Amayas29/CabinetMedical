package outils;

/***
 * Une enum pour la cle admin
 * 
 */
public enum CleAdmin {

	CLE("admin");
	
	private String cle;

	private CleAdmin(String nom) {
		this.cle = nom;
	}

	public String getCle() { return cle; }
	
}