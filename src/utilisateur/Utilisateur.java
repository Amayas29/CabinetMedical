package utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataBase.ConnexionOracle;
import patient.Patient;
/***
 * La classe qui represente un utilisateur 
 * 
 */
public abstract class Utilisateur {

	private String utilisateur;
	private String motDePasse;
	private Types type;

	public Utilisateur(String utilisateur, String motDePasse, Types type) {

		this.utilisateur = utilisateur;
		this.motDePasse = motDePasse;
		this.type = type;
	}

	/***
	 * Pour savoir si deux utilisateur sont les memes
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Utilisateur other = (Utilisateur) obj;
		if (motDePasse == null) {
			if (other.motDePasse != null)
				return false;
		} else if (!motDePasse.equals(other.motDePasse))
			return false;
		if (type != other.type)
			return false;
		if (utilisateur == null) {
			if (other.utilisateur != null)
				return false;
		} else if (!utilisateur.equals(other.utilisateur))
			return false;
		return true;
	}

	/***
	 * Retourner un objet utilisateur (oDcteur ou Secretaire) e partir de ses informations
	 * @param type
	 * @param ut
	 * @param mdp
	 * @return
	 */
	public static Utilisateur getUtilisateur(String type, String ut, String mdp) {
		Utilisateur util = null;
		if(type.equals(Types.Docteur.getType())) {
			util = new Docteur(ut, mdp);
		}
		if(type.equals(Types.Secretaire.getType())) {
			util = new Secretaire(ut, mdp);
		}
		return util;
	}

	/***
	 * Verifier la requette de connexion : si le compte est valide pour se connecter
	 * @return
	 */
	public boolean connecter() {

		String sql ="SELECT * FROM Compte Where utilisateur = ? AND type = ? AND motDePasse = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		boolean trouver = false;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(utilisateur));
			requete.setString(2, String.valueOf(type.getType()));
			requete.setString(3, String.valueOf(motDePasse));

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

	/***
	 * Ajouter un patient (les informations generales)
	 * @param patient
	 */
	public void ajouterPatient(Patient patient) {
		patient.stocker();
	}

	/***
	 * Modifier les informations generales d'un patient
	 * @param patientAncien
	 * @param patientNouveau
	 */
	public void modifierPatient(Patient patientAncien, Patient patientNouveau) {
		patientAncien.modifier(patientNouveau);
	}

	/***
	 * Retourner le nom et prenom de l'utilisateur
	 * @return
	 */
	public String getNomPrenom() {
		Compte compte = Compte.getCompte(this);
		if(compte != null) {
			return compte.getNom().concat(" ").concat(compte.getPrenom());
		}
		return null;
	}

	//////////////////////////////////////// GETTERES ET SETTERS ///////////////////////////////////////////////

	public String getUtilisateur() { return utilisateur; }
	public void setUtilisateur(String utilisateur) { this.utilisateur = utilisateur; }

	public String getMotDePasse() { return motDePasse; }
	public void setMotDePasse(String motDePasse) { this.motDePasse = motDePasse; }

	public Types getType() { return type; }
	public void setType(Types type) { this.type = type; }
}