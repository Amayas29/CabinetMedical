package documents;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;
/***
 * La classe qui represente les medicament : comporte le code de la consultation le nom du medicament la quantite et le nombre de prise par temps
 * 
 */
public class Medicament {

	private long code;
	private String nom;
	private String quantite;
	private String nbPriseParTemps;

	public Medicament(long code,String nom, String quantite, String nbPriseParTemps) {
		this.code = code;
		this.nom = nom;
		this.quantite = quantite;
		this.nbPriseParTemps = nbPriseParTemps;
	}
	
	/***
	 * Enregisterer dans la base de donnes
	 * 
	 */
	public void stocker() {

		String sql ="INSERT INTO Medicament VALUES (?,?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));
			requete.setString(2, nom);
			requete.setString(3, quantite);
			requete.setString(4, nbPriseParTemps);

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

	/***
	 * Retourne les medicament prescrit lors d'une consultation
	 * @param code
	 * @return
	 */
	public static Vector<Medicament> getMedicament(long code){
		Vector<Medicament> medic = new Vector<Medicament>();

		String sql ="SELECT * FROM Medicament WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				Medicament med = new Medicament(code,resultat.getString("nom"),
						resultat.getString("quantite"),resultat.getString("nbprise"));

				medic.add(med);
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(medic.size() == 0) {
			medic = null;
		}
		return medic;
	}

	//////////////////////////////////// GETTERS ET SETTERS //////////////////////////////////////////
	
	public long getCode() { return code; }
	public void setCode(long code) { this.code = code; }

	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }

	public String getQuantite() { return quantite; }
	public void setQuantite(String quantite) { this.quantite = quantite; }

	public String getNbPriseParTemps() { return nbPriseParTemps; }
	public void setNbPriseParTemps(String nbPriseParTemps) { this.nbPriseParTemps = nbPriseParTemps; }	
}