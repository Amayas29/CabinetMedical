package maladies;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import dataBase.ConnexionOracle;
/***
 * la classe des maladies chronique comporte le code du patient et 6 vecteur
 *  (5 sont les maladies dans les enums et le 6eme est les autres maladies)	
 * 
 */
public class MaladiesChroniques {

	private int code;
	private Vector<String> cardio;
	private Vector<String> endocrin;
	private Vector<String> orl;
	private Vector<String> rhumato;
	private Vector<String> infectChro;
	private Vector<AutreMaladieChronique> autreMaladie;

	public MaladiesChroniques(int code, Vector<String> cardio, Vector<String> endocrin, Vector<String> orl, Vector<String> rhumato,
			Vector<String> infectChro, Vector<AutreMaladieChronique> autreMaladie) {

		this.code = code;
		this.cardio = cardio;
		this.endocrin = endocrin;
		this.orl = orl;
		this.rhumato = rhumato;
		this.infectChro = infectChro;
		this.autreMaladie = autreMaladie;
	}

	/***
	 * Enregistrer dans la base de donnes
	 */
	public void stocker() {

		String cardioStr = getStringMaladies(this.getCardio(),CardioVasculaire.values());
		String endoStr = getStringMaladies(this.getEndocrin(),Endocrin.values());
		String orlStr = getStringMaladies(this.getOrl(),Orl.values());
		String rhumStr = getStringMaladies(this.getRhumato(),Rhumatolog.values());
		String infectStr = getStringMaladies(this.getInfectChro(),InfectieusesChroniques.values());

		String sql = "INSERT INTO MaladiesChroniques VALUES(?,?,?,?,?,?)";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.code));
			requete.setString(2, cardioStr);
			requete.setString(3, endoStr);
			requete.setString(4, orlStr);
			requete.setString(5, rhumStr);
			requete.setString(6, infectStr);

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		if(autreMaladie != null) {
			for(int i = 0; i < autreMaladie.size(); i++) {
				autreMaladie.get(i).stocker();
			}
		}
	}

	/***
	 * Retourner les maladies chroniques d'un patient donne par son code
	 * @param code
	 * @return
	 */
	public static MaladiesChroniques getMaladiesChroniques(int code) {

		MaladiesChroniques mal = null;
		String sql = "SELECT * FROM MaladiesChroniques WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();
			Vector<String> cardio = null;
			Vector<String> endocrin = null;
			Vector<String> orl = null;
			Vector<String> rhumato = null;
			Vector<String> infectChro = null;
			boolean existe = false;
			if(resultat.next()) {
				cardio = getVectorMaladies(resultat.getString("cardio"), CardioVasculaire.values());
				endocrin = getVectorMaladies(resultat.getString("endocrin"), Endocrin.values());
				orl = getVectorMaladies(resultat.getString("orl"), Orl.values());
				rhumato = getVectorMaladies(resultat.getString("rhumato"), Rhumatolog.values());
				infectChro = getVectorMaladies(resultat.getString("infectChro"), InfectieusesChroniques.values());
				existe = true;
			}
			Vector<AutreMaladieChronique> autreMaladie = AutreMaladieChronique.getAutreMaladieChronique(code);
			existe |= autreMaladie != null;
			if(existe) {
				mal = new MaladiesChroniques(code,cardio , endocrin, orl, rhumato, infectChro, autreMaladie);
			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return mal;
	}


	/***
	 * Supprimer les maladies chroniques de la base de donnes
	 */
	public void supprimer() {

		if(AutreMaladieChronique.isMaladeChronique(code)) {
			AutreMaladieChronique.supprimer(code);
			autreMaladie = null;
		}

		String sql = "DELETE FROM MaladiesChroniques WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/***
	 * Modifier les maladies chroniques d'un patient
	 * @param maladie
	 */
	public void modifier(MaladiesChroniques maladie) {

		this.supprimer();

		this.cardio = maladie.getCardio();
		this.endocrin = maladie.getEndocrin();
		this.orl = maladie.getOrl();
		this.rhumato = maladie.getRhumato();
		this.infectChro = maladie.getInfectChro();
		this.autreMaladie = maladie.getAutreMaladie();

		this.stocker();
	}

	/***
	 * Savoir si le patient est atteins de maladies chroniques 
	 * @param code
	 * @return
	 */
	public static boolean isMaladeChronique(int code) {
		String sql = "SELECT * FROM MaladiesChroniques WHERE code = ?";

		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;
		boolean trouver = false;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			trouver = resultat.next() || AutreMaladieChronique.isMaladeChronique(code);

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return trouver;

	}

	/***
	 * Retourner une chaine de caractere qui caracterise l'etat du patient par apport aux maladies dans les enums 
	 * (1 si il est atteins d'un malaide, 0 sinon)
	 * @param vec
	 * @param maladie
	 * @return
	 */
	private String getStringMaladies(Vector<String> vec,MaladiesInterface[] maladie){
		String strMald = "";
		for(int i = 0; i < maladie.length; i++) {
			if(vec.contains(maladie[ i ].getNom())){
				strMald += '1'; 
			}
			else {
				strMald += '0'; 
			}
		}
		return strMald;
	}

	private static Vector<String> getVectorMaladies(String str,MaladiesInterface[] maladie) {
		Vector<String> vec = new Vector<String>(); 
		for(int i = 0; i < str.length(); i++) {
			if(str.charAt(i) == '1') {
				vec.addElement(maladie[ i ].getNom());
			}
		}
		if(vec.isEmpty()) {
			vec = null;
		}
		return vec;
	}

	///////////////////////////////// GETTERS ET SETTERS //////////////////////////////////
	
	public int getCode() { return code; }
	public void setCode(int code) { this.code = code;}

	public Vector<String> getCardio() { return cardio; }
	public void setCardio(Vector<String> cardio) { this.cardio = cardio; }

	public Vector<String> getEndocrin() { return endocrin; }
	public void setEndocrin(Vector<String> endocrin) { this.endocrin = endocrin; }

	public Vector<String> getOrl() { return orl; }
	public void setOrl(Vector<String> orl) { this.orl = orl; }

	public Vector<String> getRhumato() { return rhumato; }
	public void setRhumato(Vector<String> rhumato) { this.rhumato = rhumato; }

	public Vector<String> getInfectChro() { return infectChro; }
	public void setInfectChro(Vector<String> infectChro) { this.infectChro = infectChro; }

	public Vector<AutreMaladieChronique> getAutreMaladie() { return autreMaladie; }
	public void setAutreMaladie(Vector<AutreMaladieChronique> autreMaladie) { this.autreMaladie = autreMaladie; }

}