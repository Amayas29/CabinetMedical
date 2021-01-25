package maladies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;
import documents.GeneratriceCode;

public class Symptome {

	private long codeConsu;
	private int codeSeq;
	private String description;

	public Symptome(long codeConsu, String description) {
		this.codeConsu = codeConsu;
		this.codeSeq = -1;
		this.description = description;
	}

	public void stocker() {

		String sql = "INSERT INTO Symptome Values(?,?,?)";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(getCodeConsu()));
			setCodeSeq(GeneratriceCode.generer("Symptome", getCodeConsu()));
			requete.setString(2, String.valueOf(getCodeSeq()));
			requete.setString(3, getDescription());
			
			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public static Vector<Symptome> getSymptomes(long code) {

		Vector<Symptome> vec = new Vector<Symptome>();

		String sql = "SELECT * FROM Symptome WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				Symptome sym = new Symptome(code, resultat.getString("description"));
				sym.setCodeSeq(Integer.valueOf(resultat.getString("codeSeq")));
				vec.add(sym);
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

	public long getCodeConsu() { return codeConsu; }
	public void setCodeConsu(long codeConsu) { this.codeConsu = codeConsu; }

	public int getCodeSeq() { return codeSeq; }
	public void setCodeSeq(int codeSeq) { this.codeSeq = codeSeq; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

}