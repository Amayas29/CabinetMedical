package maladies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;
import documents.GeneratriceCode;
/***
 * La classe des maladies non chroniques (soit les maladies diagnostique)
 * 
 */
public class MaladieNonChronique extends Maladie {

	private long codeConsu;

	public MaladieNonChronique(String nom, String commentaire, long codeConsu) {
		super(-1, nom, commentaire);
		this.codeConsu = codeConsu;
	}

	/***
	 * enregister dans la base de donnes
	 */
	public void stocker() {

		String sql = "INSERT INTO MaladieNonChronique Values(?,?,?,?)";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.getCodeConsu()));
			setCodeSeq(GeneratriceCode.generer("MaladieNonChronique", getCodeConsu()));
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
	 * retourner les maladies diagnostique lors d'une consultation
	 * @param code
	 * @return
	 */
	public static Vector<MaladieNonChronique> getMaladieNonChronique(long code) {

		Vector<MaladieNonChronique> vec = new Vector<MaladieNonChronique>();

		String sql = "SELECT * FROM MaladieNonChronique WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				MaladieNonChronique mal = new MaladieNonChronique(resultat.getString("nom"),
						resultat.getString("commentaire"), code);
				mal.setCodeSeq(Integer.valueOf(resultat.getString("codeSeq")));
				vec.add(mal);
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
	 * Retourner un vecteur des maladies diagnostique pendant une journee
	 * @param date
	 * @return
	 */
	private static Vector<MaladieNonChronique> getMaladieNonChronique(String date) {

		Vector<MaladieNonChronique> vec = new Vector<MaladieNonChronique>();

		String sql = "SELECT nom FROM maladieNonchronique WHERE code like '".concat(date).concat("%'");

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);
			resultat = requete.executeQuery();

			while(resultat.next()) {
				MaladieNonChronique mal = new MaladieNonChronique(resultat.getString("nom"),
						"", -1l);
				vec.add(mal);
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
	 * Retourner un vecteur de toute les maladies diagnostique pendant une semaine donnee
	 * @param semaine
	 * @return
	 */
	public static Vector<MaladieNonChroniqueNombre> getMaladieNonChronique(Vector<String> semaine) {

		Vector<MaladieNonChroniqueNombre> vec = new Vector<MaladieNonChroniqueNombre>();

		for(String date : semaine) {
			Vector<MaladieNonChronique> maladies = getMaladieNonChronique(date);
			if(maladies != null) {
				for(MaladieNonChronique mal : maladies) {
					vec.add(new MaladieNonChroniqueNombre(mal, 1));
				}
			}
		}

		if(vec.isEmpty()) {
			vec = null;
		}
		MaladieNonChroniqueNombre.trier(vec);
		return vec;
	}

	///////////////////////////////////////////////// GETTERS ET SETTERS ///////////////////////////////////////////

	public long getCodeConsu() { return codeConsu; }
	public void setCodeConsu(long codeConsu) { this.codeConsu = codeConsu; }
}