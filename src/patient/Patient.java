package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;

/***
 * Une classe qui represente un patient de maniere generale (soit les informations generales sur le patient)
 * 
 */
public class Patient {

	private int code;
	private String nom;
	private String prenom;
	private String sexe;
	private String dateNaissance;
	private String adresse;
	private String telephone;
	private String email;
	private String dateInscription;

	public Patient(int code, String nom, String prenom, String sexe, String dateNaissance, String adresse,
			String telephone, String email, String dateInscription) {

		this.code = code;
		this.nom = nom;
		this.prenom =  String.valueOf(prenom.charAt(0)).toUpperCase().concat(prenom.substring(1).toLowerCase());
		this.sexe = sexe;
		this.dateNaissance = dateNaissance;
		this.adresse = adresse;
		this.telephone = telephone;
		this.email = email;
		this.dateInscription = dateInscription;
	}

	/***
	 * Consutucteur par copie
	 * @param patient
	 */
	public Patient(Patient patient) {

		this.code = patient.code;
		this.nom = patient.nom;
		this.prenom = patient.prenom;
		this.sexe = patient.sexe;
		this.dateNaissance = patient.dateNaissance;
		this.adresse = patient.adresse;
		this.telephone = patient.telephone;
		this.email = patient.email;

	}

	/***
	 * Enregistrer le patient dans la base de donnes
	 */
	public void stocker() {

		String sql ="INSERT INTO Patient (code, nom, prenom, sexe, dateNaiss, adresse, tel , email, dateInscri, drConsu ) VALUES (?,?,?,?,?,?,?,?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.code));
			requete.setString(2, this.nom.toUpperCase());
			requete.setString(3, this.prenom);
			requete.setString(4, this.sexe);
			requete.setString(5, this.dateNaissance);
			requete.setString(6, this.adresse.toUpperCase());
			requete.setString(7, this.telephone.toLowerCase());
			requete.setString(8, this.email);
			requete.setString(9, this.dateInscription);
			requete.setString(10, "01/01/1900");

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Savoir si le patient est existant dans la base de donnes
	 * @return
	 */
	public boolean existe() {

		String sql = "SELECT * FROM Patient WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		boolean trouver = false;

		try {

			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

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
	 * Retourner un vecteur de patient selon un model de recherche
	 * @param model
	 * @return
	 */
	public static Vector<Patient> rechercherPatient(String model) {

		Vector<Patient> patients = new Vector<Patient>();
		String sql = "SELECT * FROM Patient WHERE ".concat(model);
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {

			requete = connection.prepareStatement(sql); 
			resultat = requete.executeQuery();
			while(resultat.next()) {
				Patient p = new Patient(Integer.valueOf(resultat.getString(1)), resultat.getString(2), resultat.getString(3), 
						resultat.getString(4),resultat.getString(5), resultat.getString(6),
						resultat.getString(7), resultat.getString(8),resultat.getString(9));
				patients.addElement(p);
			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(patients.size() == 0) {
			patients = null;
		}
		return patients;
	}

	/***
	 * Modifier un patient
	 * @param patient
	 */
	public void modifier(Patient patient) {

		this.nom = patient.nom;
		this.prenom = patient.prenom;
		this.sexe = patient.sexe;
		this.dateNaissance = patient.dateNaissance;
		this.adresse = patient.adresse;
		this.telephone = patient.telephone;
		this.email = patient.email;

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		String sql = "UPDATE Patient set nom = ? ,prenom = ?,sexe = ?,DateNaiss = ? , adresse = ?, tel = ?, email = ? WHERE code = ?"; 

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, this.getNom().toUpperCase());
			requete.setString(2, this.getPrenom().toLowerCase());
			requete.setString(3, this.getSexe());
			requete.setString(4, this.getDateNaissance());
			requete.setString(5, this.getAdresse().toUpperCase());
			requete.setString(6, this.getTelephone().toLowerCase());
			requete.setString(7, this.getEmail());
			requete.setString(8, String.valueOf(this.getCode()));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/***
	 * Savoir si le dossier medical d'un patient a ete ajoute
	 * @return
	 */
	public boolean isDossierAjoute() {
		String sql = "SELECT * FROM Patient WHERE code = ? AND taille is not NULL";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		boolean trouver = false;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(getCode()));

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
	 * Retourner toutes les informations generales d'un patient donne par son code 
	 * @param code
	 * @return
	 */
	public static Patient getPatient(int code) {
		Vector<Patient> vec = Patient.rechercherPatient("code = ".concat(String.valueOf(code)));
		if(vec != null) {
			return vec.get(0);
		}
		return null;
	}

	/***
	 * Retourner la date de la derniere consultation d'un patient
	 * @return
	 */
	public String getDrConsu() {
		String sql = "SELECT drConsu FROM Patient WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		String date = null;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();
			if(resultat.next()) {
				date = resultat.getString("drConsu");
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return date;
	}

	/////////////////////////////////// GETTERS ET SETTERS /////////////////////////////////////////////
	public int getCode() { return code; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public String getPrenom() {	return prenom; }
	public void setPrenom(String prenom) { this.prenom = prenom; }

	public String getSexe() { return sexe; }
	public void setSexe(String sexe) { this.sexe = sexe; }

	public String getDateNaissance() { return dateNaissance; }
	public void setDateNaissance(String dateNaissance) { this.dateNaissance = dateNaissance; }

	public String getAdresse() { return adresse; }
	public void setAdresse(String adresse) { this.adresse = adresse; }

	public String getTelephone() { return telephone; }
	public void setTelephone(String telephone) { this.telephone = telephone; }

	public String getEmail() { return email; }
	public void setEmail(String email) { this.email = email; }

	public String getDateInscription() { return dateInscription; }

}