package maladies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;
import documents.GeneratriceCode;

/***
 * Autre Maladie Chronique une classe qui represente les maladies chronique non existante dans les Enums
 * elle comporte le code du patient, un code sequentiel le nom et eventuellement un commentaire sur la maladie
 * 
 */
public class AutreMaladieChronique extends Maladie {

	private int codePatient;

	public AutreMaladieChronique(String nom, String commentaire, int codePatient) {
		super(-1, nom, commentaire);
		this.codePatient = codePatient;
	}

	/***
	 * Enregistrer dans la base de donnes
	 */
	public void stocker() {

		String sql = "INSERT INTO AutreMaladieChronique Values(?,?,?,?)";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(getCodePatient()));
			setCodeSeq(GeneratriceCode.generer("AutreMaladieChronique", getCodePatient()));
			requete.setString(2, String.valueOf(getCodeSeq()));
			requete.setString(3, getNom());
			requete.setString(4, getCommentaire());

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/***
	 * Retourner toutes les autres maladies chronique d'un patient donne par son code
	 * @param code
	 * @return
	 */
	public static Vector<AutreMaladieChronique> getAutreMaladieChronique(int code) {

		Vector<AutreMaladieChronique> vec = new Vector<AutreMaladieChronique>();

		String sql = "SELECT * FROM AutreMaladieChronique WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				AutreMaladieChronique mal = new AutreMaladieChronique(resultat.getString("nom"),
						resultat.getString("commentaire"), code);
				mal.setCodeSeq(Integer.valueOf(resultat.getString("codeSeq")));
				vec.addElement(mal);
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(vec.isEmpty()) {
			vec = null;
		}
		return vec;
	}

	/***
	 * Supprimer toutes les autres maladies chroniques d'un patient
	 * @param codePatient
	 */
	public static void supprimer(int codePatient) {
		String sql = "DELETE FROM AutreMaladieChronique WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(codePatient));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	///////////////////////////////// GETTERS ET SETTERS //////////////////////////////////////
	
	public int getCodePatient() { return codePatient; }
	public void setCodePatient(int codePatient) { this.codePatient = codePatient; }

	/***
	 * Savoir si le patient est atteins de maladies chroniques
	 * @param code
	 * @return
	 */
	public static boolean isMaladeChronique(int code) {
		String sql = "SELECT * FROM AutreMaladieChronique WHERE code = ?";

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

}