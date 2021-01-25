package maladies;

public enum CardioVasculaire implements MaladiesInterface {

	Angine("Angine de poitrine"),
	Arythmie("Arythmie cardiaque"),
	Cardiopathie("Cardiopathie congenitale"),
	Hypercholesterolemie("Hypercholesterolemie"),
	HypertensionArt("Hypertension arterielle"),
	HypertensionArtPul("Hypertension arterielle pulmonaire"),
	InsuffisanceCar("Insuffisance cardiaque"),
	MaladieKawas("Maladie de Kawasaki"),
	Troubles("Troubles du rythme cardiaque");

	private String nom;

	private CardioVasculaire(String nom) { this.nom = nom; }

	public String getNom() { return nom;}
	
}