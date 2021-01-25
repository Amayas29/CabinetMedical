package maladies;

public enum Orl implements MaladiesInterface{

	ApneeSom("Apnee du sommeil"),
	Asthme("Asthme"),
	BronchiteChr("Bronchite chronique"),
	CancerPoum("Cancer du poumon"),
	CancerOrl("Cancer ORL"),
	DilatationBron("Dilatation des bronches"),
	FibrosePul("Fibrose pulmonaire"),
	Vertiges("Vertiges");

	private String nom;

	private Orl(String nom) { this.nom = nom; }

	public String getNom() { return nom;}

}