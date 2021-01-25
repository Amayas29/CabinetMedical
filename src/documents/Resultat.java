package documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;
/***
 * Le resultat d'une analyse comporte le code de la consultation et le code sequentiel qui est synchornise avec l'analyse 
 * et le resultat sous forme alphanumerique
 * 
 */
public class Resultat {

	private long code;
	private int codeSeq;
	private String resultat;

	public Resultat(long code, int codeSeq, String resultat) {
		this.code = code;
		this.codeSeq = codeSeq;
		this.resultat = resultat;
	}

	/***
	 * enregistere dans la base de donnes
	 */
	public void stocker() {

		String sql ="INSERT INTO Resultats VALUES (?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.code));
			requete.setString(2, String.valueOf(this.codeSeq));
			requete.setString(3, this.resultat);

			requete.execute();
			
			sql = "UPDATE Analyses set effectue = '1' WHERE code = ? AND codeSeq = ?";
			requete = connection.prepareStatement(sql);
			requete.setString(1, String.valueOf(this.code));
			requete.setString(2, String.valueOf(this.codeSeq));
			requete.execute();
			
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Retourner les resultats d'un bilan demande dans une consultation
	 * @param code
	 * @return
	 */
	public static Vector<Resultat> getResultat(long code){

		Vector<Resultat> resultats = new Vector<Resultat>();

		String sql ="SELECT * FROM Resultats WHERE code = ? ORDER BY codeSeq";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				
				Resultat res = new Resultat(code,Integer.valueOf(resultat.getString("codeSeq")),resultat.getString("resultat"));
				resultats.add(res);
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(resultats.size() == 0) {
			resultats = null;
		}
		return resultats;
	}

	/////////////////////////////////////// GETTERS ET SETTERS ////////////////////////////////////////////
	
	public long getCode() { return code; }
	public void setCode(long code) { this.code = code; }

	public int getCodeSeq() { return codeSeq; }
	public void setCodeSeq(int codeSeq) { this.codeSeq = codeSeq; }

	public String getResultat() { return resultat; }
	public void setResultat(String resultat) { this.resultat = resultat; }
}