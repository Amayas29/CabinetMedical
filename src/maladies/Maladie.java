package maladies;

/****
 * La classe des maladies qui comporte un code sequentiel, le nom et un commentaire sur la maladie
 * 
 */
public abstract class Maladie {

	private int codeSeq;
	private String nom;
	private String commentaire;
	
	public Maladie(int codeSeq, String nom, String commentaire) {
		this.codeSeq = codeSeq;
		this.nom = nom;
		this.commentaire = commentaire;
	}

	public abstract void stocker();

	public int getCodeSeq() { return codeSeq; }
	public void setCodeSeq(int codeSeq) { this.codeSeq = codeSeq; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public String getCommentaire() { return commentaire; }
	public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
		
}