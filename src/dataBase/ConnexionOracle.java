package dataBase;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;
/***
 * Classe pour la connection a la base de donnees Oracle
 * 
 */
public final class ConnexionOracle {

	/**
	 * Methode de connexion e la base de donnes
	 * @return une Connexion
	 */
	public static Connection connecteBD() {

		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch(Exception e) {
			JOptionPane.showMessageDialog(null, "le pilote n'a pu etre charge", "Erreur de pilote", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return null;
		}

		try {
			// TODO
			String url = "";
			String util = "";
			String mdp = "";
			Connection connection = DriverManager.getConnection(url,util,mdp);
			return connection;

		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, exception, "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
			return null;
		}	
	}
}