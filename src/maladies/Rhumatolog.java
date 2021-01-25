package maladies;

public enum Rhumatolog implements MaladiesInterface {

	ArthropathiesMicro("Arthropathies microcristallines"), 
	Arthrose("Arthrose"),
	CancerOs("Cancer des os"),
	Fibromyalgie("Fibromyalgie"),
	SyndromeGilb("Syndrome de Gilbert"),
	LombalgieChron("Lombalgie chronique"),
	Myasthenie("Myasthenie"),
	Osteoporose("Osteoporose"),
	PolyarthriteRhum("Polyarthrite rhumatoede"),
	RhumatismePsor("Rhumatisme psoriasique"),
	Scoliose("Scoliose");

	private String nom;

	private Rhumatolog(String nom) { this.nom = nom; }

	public String getNom() { return nom;}

}