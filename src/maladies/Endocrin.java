package maladies;

public enum Endocrin implements MaladiesInterface {
	
	Cancer("Cancer de la thyroede"),
	Diabete("Diabete"),
	HyperaldosteronismePrim("Hyperaldosteronisme primaire"), 
	Hypothyroedie("Hypothyroedie"),
	InsuffisancePanc("Insuffisance pancreatique"),
	Mucoviscidose("Mucoviscidose"),
	Obesite("Obesite");

	private String nom;
	
	private Endocrin(String nom) { this.nom = nom; }

	public String getNom() { return nom;}
	
}