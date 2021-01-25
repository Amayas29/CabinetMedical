package patient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;
import maladies.AutreMaladieChronique;
import maladies.MaladiesChroniques;
/***
 * La classe Detaillee d'un patient, elle contient toutes les informations detailles ainsi que le dossier medical du patient
 * 
 */
public class PatientDetaille extends Patient {

	private String taille;
	private String poids;
	private String groupeSang;
	private String tension;
	private boolean maladeChronique; // Si il est atteins de maladies chroniques
	private MaladiesChroniques maladies;
	private String tauxGlsm;
	private String vue;
	private String antMedi;

	public PatientDetaille(Patient patient, String taille, String poids, String groupeSang,
			String tension, boolean maladeChronique, Vector<String> cardio, Vector<String> endocrin,
			Vector<String> orl, Vector<String> rhumato,
			Vector<String> infectChro, Vector<AutreMaladieChronique> autreMaladie
			,String tauxGlsm,String vue,String antMedi) {

		super(patient);

		this.taille = taille;
		this.poids = poids;
		this.groupeSang = groupeSang;
		this.tension = tension;
		this.maladeChronique = maladeChronique;
		this.maladies = new MaladiesChroniques(getCode(), cardio, endocrin, orl, rhumato, infectChro, autreMaladie);
		this.tauxGlsm = tauxGlsm;
		this.vue = vue;
		this.antMedi = antMedi;
	}

	/**
	 * Enregistere les detailles du patient dans la base de donnes
	 * (C'est la meme table que les informations generales donc c'est une mise e jour)
	 */
	public void stocker() {

		if(isMaladeChronique()) {
			this.maladies.stocker();
		}

		String sql ="UPDATE Patient set taille = ?, poids = ?, groupeSang = ?, tension = ? ,maladeChronique = ?, tauxGlsm = ?, vue = ?,antMedi = ? WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, this.taille);
			requete.setString(2, this.poids);
			requete.setString(3, this.groupeSang);
			requete.setString(4, this.tension);
			if(this.maladeChronique) {
				requete.setString(5,"1");
			}
			else {
				requete.setString(5,"0");
			}
			requete.setString(6, this.tauxGlsm);
			requete.setString(7, this.vue);
			requete.setString(8, this.antMedi);
			requete.setString(9, String.valueOf(this.getCode()));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Erreur SQL : Inserer Patient Detaille :" + e.getMessage());
		}
	}

	/***
	 * Retourner tout les informations detailles d'un patient
	 * @param patient
	 * @return
	 */
	public static PatientDetaille getPatientDetaille(Patient patient) {
		if(!patient.isDossierAjoute()) {
			return new PatientDetaille(patient, null, null, null, null, false, null, null, null, null, null, null, null, null, null);
		}
		PatientDetaille patientDetaille = null;
		String sql = "SELECT * FROM Patient WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(patient.getCode()));

			resultat = requete.executeQuery();
			if(resultat.next()) {
				patientDetaille = new PatientDetaille(patient, resultat.getString("taille"), resultat.getString("poids"), 
						resultat.getString("groupeSang"),resultat.getString("tension"), 
						MaladiesChroniques.isMaladeChronique(patient.getCode()), 
						null, null, null, null,null, null, 
						resultat.getString("tauxGlsm"), resultat.getString("vue"), resultat.getString("antMedi"));

				if(patientDetaille.isMaladeChronique()) {
					MaladiesChroniques maladie = MaladiesChroniques.getMaladiesChroniques(patientDetaille.getCode());
					patientDetaille.setMaladies(maladie);
				}
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Erreur SQL : Get Patient Detaille :" + e.getMessage());
		}
		return patientDetaille;
	}

	/***
	 * supprimer le dossier medical du patient
	 */
	public void supprimer() {

		if(MaladiesChroniques.isMaladeChronique(getCode())) {
			maladies.setCode(getCode());
			maladies.supprimer();
		}

		String sql = "DELETE FROM Patient WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.getCode()));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			System.out.println("Erreur SQL : Supprimer Patient :" + e.getMessage());
		}
	}

	/***
	 * Modifier le dossier medical du patient
	 * @param patientDetaille
	 */
	public void modifier(PatientDetaille patientDetaille) {

		if(isMaladeChronique()) {
			maladies.supprimer();
		}

		this.maladies = patientDetaille.getMaladies();
		this.taille = patientDetaille.taille;
		this.poids = patientDetaille.poids;
		this.groupeSang = patientDetaille.groupeSang;
		this.tension = patientDetaille.tension;
		this.maladeChronique = patientDetaille.maladeChronique;
		this.tauxGlsm = patientDetaille.tauxGlsm;
		this.vue = patientDetaille.vue;
		this.antMedi = patientDetaille.antMedi;

		this.stocker();
	}

	//////////////////////////////////////////// GETTERS ET SETTERS ///////////////////////////////////
	
	public String getTaille() { return taille; }
	public void setTaille(String taille) { this.taille = taille; }

	public String getPoids() { return poids; }
	public void setPoids(String poids) { this.poids = poids; }

	public String getGroupeSang() { return groupeSang; }
	public void setGroupeSang(String groupeSang) { this.groupeSang = groupeSang; }

	public String getTension() { return tension; }
	public void setTension(String tension) { this.tension = tension; }

	public boolean isMaladeChronique() { return maladeChronique; }
	public void setMaladeChronique(boolean maladeChronique) { this.maladeChronique = maladeChronique; }

	public MaladiesChroniques getMaladies() { return maladies; }
	public void setMaladies(MaladiesChroniques maladies) { this.maladies = maladies; }

	public String getTauxGlsm() { return tauxGlsm; }
	public void setTauxGlsm(String tauxGlsm) { this.tauxGlsm = tauxGlsm; }

	public String getVue() { return vue; }
	public void setVue(String vue) { this.vue = vue; }

	public String getAntMedi() { return antMedi; }
	public void setAntMedi(String antMedi) { this.antMedi = antMedi; }
}