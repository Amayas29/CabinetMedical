package utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;

/***
 * La classe compte designe les comptes des utilisateurs qui sont creer dans notre logiciel
 * 
 */
public class Compte {

	private Utilisateur utilisateur;
	private String nom;
	private String prenom;

	public Compte(Utilisateur utilisateur, String nom, String prenom) {

		this.utilisateur = utilisateur;
		this.nom = nom;
		this.prenom = prenom;
	}

	/***
	 * Retourner tous les comptes existant dans le logiciel
	 * @return
	 */
	public static Vector<Compte> getComptes(){
		Vector<Compte> comptes = new Vector<Compte>();

		String sql = "SELECT * FROM Compte";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			resultat = requete.executeQuery();

			while(resultat.next()) {
				String type = resultat.getString("type");
				String ut = resultat.getString("utilisateur");
				String mdp = resultat.getString("motDePasse");
				Utilisateur util = Utilisateur.getUtilisateur(type, ut, mdp);
				Compte c = new Compte(util, resultat.getString("nom"), resultat.getString("prenom"));
				comptes.addElement(c);

			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(comptes.isEmpty()) {
			comptes = null;
		}
		return comptes;
	}

	/***
	 * Retrouner le compte d'un utilisateur
	 * @param utilisateur
	 * @return
	 */
	public static Compte getCompte(Utilisateur utilisateur) {

		String sql ="SELECT * FROM Compte Where utilisateur = ? AND type = ? AND motDePasse = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		Compte compte = null;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, utilisateur.getUtilisateur());
			requete.setString(2, utilisateur.getType().getType());
			requete.setString(3, utilisateur.getMotDePasse());

			resultat = requete.executeQuery();
			
			if(resultat.next()) {
				compte = new Compte(utilisateur, resultat.getString("nom")
						,resultat.getString("prenom"));
			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return compte;	
	}

	/***
	 * Retourner le mot de passe d'un compte connu par le nom d'utilisateur et le type d'utilisateur
	 * @param utilisateur
	 * @param type
	 * @return
	 */
	public static String getMdp(String utilisateur,String type) {

		String sql ="SELECT * FROM Compte Where type = ? AND utilisateur = ? ";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		String mdp = null;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, type);
			requete.setString(2, utilisateur);

			resultat = requete.executeQuery();
			if(resultat.next()) {
				mdp = resultat.getString("motDePasse");
			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mdp;	
	}

	/***
	 * Creer un compte pour un utilisateur et l'enregistrer dans la base de donnes
	 */
	public void creeCompte() {
		String sql ="INSERT INTO Compte Values(?,?,?,?,?)";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, utilisateur.getType().getType());
			requete.setString(2, utilisateur.getUtilisateur());
			requete.setString(3, utilisateur.getMotDePasse());
			requete.setString(4, nom.toUpperCase());
			requete.setString(5, String.valueOf(prenom.charAt(0)).toUpperCase().concat(prenom.substring(1).toLowerCase()));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/***
	 * Supprimer le compte de la base de donnes
	 */
	public void supprimer() {

		String sql ="DELETE FROM Compte WHERE type = ? AND utilisateur = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, utilisateur.getType().getType());
			requete.setString(2, utilisateur.getUtilisateur());

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Modifier le compte
	 * @param compte
	 */
	public void modifer(Compte compte) {

		this.supprimer();
		this.utilisateur.setType(compte.getUtilisateur().getType());
		this.utilisateur.setUtilisateur(compte.getUtilisateur().getUtilisateur());
		this.utilisateur.setMotDePasse(compte.getUtilisateur().getMotDePasse());;
		this.nom = compte.nom;
		this.prenom = compte.prenom; 
		this.creeCompte();
	}

	/***
	 * Savoir si le compte exite deja (un compte existant est un compte dont le type et le nom d'utilisateur figurent deja dans un autre compte)
	 * @return
	 */
	public boolean existe() {

		String sql ="SELECT * FROM Compte Where type = ? AND utilisateur = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		boolean trouver = false;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, utilisateur.getType().getType());
			requete.setString(2, utilisateur.getUtilisateur());

			resultat = requete.executeQuery();
			trouver = resultat.next();

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return trouver;
	}

	///////////////////////////////////////////// GETTERS ET SETTERS ///////////////////////////////////////////////////////
	
	public Utilisateur getUtilisateur() { return utilisateur; }
	public void setUtilisateur(Utilisateur utilisateur) { this.utilisateur = utilisateur; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public String getPrenom() { return prenom; }
	public void setPrenom(String prenom) { this.prenom = prenom; }	
}