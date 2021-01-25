package documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dataBase.ConnexionOracle;

/***
 * Une classe qui genere le code sequentiel de tous les documents e partir de leur table en BD
 * 
 */
public abstract class GeneratriceCode {

	public static int generer(String nomTable, long code) {

		String sql = "SELECT count(code) as NB , max(codeSeq) as MAX FROM ".concat(nomTable).concat(" Where code = ?");
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		int codeSeq = 0;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			if(resultat.next() && !resultat.getString("NB").equals("0")) {
				codeSeq = Integer.valueOf(resultat.getString("MAX")) + 1;	
			}
			
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return codeSeq;
	}
}