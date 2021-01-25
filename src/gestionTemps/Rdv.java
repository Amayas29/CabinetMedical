package gestionTemps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import dataBase.ConnexionOracle;

/***
 * Le Rendez-vous comporte le code du patient, la date du rdv et le numero du rdv dans la journee 
 * 
 */
public class Rdv {

	private int codePatient;
	private Calendar date;
	private int numero;

	public Rdv(int codePatient, Calendar date, int numero) {
		this.codePatient = codePatient;
		this.date = date;
		this.numero = numero;
	}

	/***
	 * Enregister le Rdv dans la base de donnes
	 */
	public void stocker() {

		String sql = "INSERT INTO Rdv VALUES(?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);
			requete.setString(1, String.valueOf(getCodePatient()));
			requete.setString(2, String.valueOf(getDate()));
			requete.setString(3, String.valueOf(getNumero()));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Annuler le Rdv et le supprimer de la base de donnes
	 */
	public void annuler() {
		String sql = "DELETE FROM Rdv WHERE dateRdv = ? AND numero = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(getDate()));
			requete.setString(2, String.valueOf(getNumero()));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Retourner tous les Rdvs d'une date donnee
	 * @param date
	 * @return
	 */
	public static Vector<Rdv> getRdv(String date){
		Vector<Rdv> rdv = new Vector<Rdv>();
		String sql = "SELECT * FROM Rdv WHERE dateRdv = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(date));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				Rdv r = new Rdv(Integer.valueOf(resultat.getString("codePatient")), getCalendar(date), Integer.valueOf(resultat.getString("numero")));
				rdv.add(r);
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(rdv.isEmpty()) {
			rdv = null;
		}
		return rdv;
	}

	/***
	 * Retourner les informations sur un rdv particulier dans la journee
	 * @param date
	 * @param numero
	 * @return
	 */
	public static Rdv getRdv(String date, int numero) {
		String sql = "SELECT * FROM Rdv WHERE dateRdv = ? AND numero = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		Rdv r = null;
		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(date));
			requete.setString(2, String.valueOf(numero));
			resultat = requete.executeQuery();
			if(resultat.next()) {
				r = new Rdv(Integer.valueOf(resultat.getString("codePatient")), getCalendar(date), numero);
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/***
	 * Retourner l'objet calendar depuis une date
	 * @param date
	 * @return
	 */
	public static Calendar getCalendar(String date) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(0, 2)));
		c.set(Calendar.MONTH, Integer.valueOf(date.substring(3, 5)) - 1);
		c.set(Calendar.YEAR, Integer.valueOf(date.substring(6, 10)));
		return c;
	}

	/////////////////////////////////// GETTERS ET SETTERS //////////////////////////////////////////////////////////
	
	public int getCodePatient() { return codePatient; }
	public void setCodePatient(int codePatient) { this.codePatient = codePatient; }

	public String getDate() { 
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return df.format(date.getTime());
	}
	public void setDate(Calendar date) { this.date = date; }

	public int getNumero() { return numero; }
	public void setNumero(int numero) { this.numero = numero; }
}