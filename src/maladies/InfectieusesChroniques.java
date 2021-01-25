package maladies;

public enum InfectieusesChroniques implements MaladiesInterface {
	
	Endocardite("Endocardite"),
	HepatiteB ("Hepatite B"),
	HepatiteC ("Hepatite C"),
	InfectionOstrArt("Infection osteo-articulaire"),
	InfectionVih ("Infection par le VIH, SIDA"),
	Lyme("Maladie de Lyme"),
	Tuberculose("Tuberculose");
	
	private String nom;

	private InfectieusesChroniques(String nom) { this.nom = nom; }

	public String getNom() { return nom;}
	
}