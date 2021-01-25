package documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;

/***
 * La classe d'une analyse qui est represente par le code (qui est le code de la consultation) et un code sequentiel, le nom de l'analyse
 * et l'etat (soit analyse effectue ou pas)
 * 
 */
public class Analyse {

	private long code;
	private int codeSeq;
	private String nom;
	private boolean effectue;

	// Constructeur prive : pour l'utiliser seulement dans cette classe
	private Analyse(long code, int codeSeq, String nom) {
		this.code = code;
		this.codeSeq = codeSeq;
		this.nom = nom;
		this.effectue = false;
	}

	public Analyse(long code, String nom) {
		this.code = code;
		this.codeSeq = -1;
		this.nom = nom;
		this.effectue = false;
	}

	/***
	 * Stocker dans la base de donnes l'analyse
	 */
	public void stocker() {

		String sql ="INSERT INTO Analyses VALUES (?,?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		setCodeSeq(GeneratriceCode.generer("Analyses", getCode()));
		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(getCode()));
			requete.setString(2, String.valueOf(getCodeSeq()));
			requete.setString(3, this.nom);
			requete.setString(4, "0");

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Les analyses demandes lors d'une consultation
	 * @param code : le code de la consultation
	 * @return vector d'analyses
	 */
	public static Vector<Analyse> getAnalyses(long code){

		Vector<Analyse> analyses = new Vector<Analyse>();

		String sql ="SELECT * FROM Analyses WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				Analyse an = new Analyse(code, Integer.valueOf(resultat.getString("codeSeq")), 
						resultat.getString("nom"));
				an.setEffectue(resultat.getString("effectue").equals("1"));
				analyses.add(an);
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(analyses.size() == 0) {
			analyses = null;
		}
		return analyses;
	}


	////////////////////////////////////////////////////// GETTERS ET SETTERS //////////////////////////////////////////////////////
	public long getCode() { return code; }

	public int getCodeSeq() {return codeSeq; }
	public void setCodeSeq(int codeSeq) { this.codeSeq = codeSeq; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public boolean isEffectue() { return effectue; }
	public void setEffectue(boolean effectue) { this.effectue = effectue; }
}