package consultation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Vector;

import dataBase.ConnexionOracle;
import documents.Analyse;
import documents.Bilan;
import documents.Bon;
import documents.CertificatMedical;
import documents.Medicament;
import documents.Ordannance;
import maladies.MaladieNonChronique;
import maladies.Symptome;
import patient.Patient;
/**
 * <h1> La classe de la consultation </h1>
 * <p> 
 * <ul> 
 * <li> son code qui est une concatenation entre la date et un numero sequentiel dans la journee </li>
 * <li> le code du patient </li>
 * <li> le nom du medecin </li>
 * <li> Un commentaire </li>
 * <li> Une liste de maladies diagnostiqueeees </li>
 * <li> Une liste de synmptomes </li>
 * <li> Un bon </li>
 * <li> Une ordonnance </li>
 * <li> Un bilan </li>
 * <li> Un certificat medical </li>
 * </ul>
 * </p>
 */
public class Consultation {

	private long codeConsu;
	private int codePatient;
	private String nomMedcin;
	private Vector<MaladieNonChronique> maladies;
	private Bon bon;
	private CertificatMedical certificatMedical;
	private Ordannance traitement;
	private Bilan bilan;
	private Vector<Symptome> symptomes;
	private String commentaire;

	public Consultation(int codePatient, String nomMedcin, String commentaire) {
		codeConsu = -1;
		this.codePatient = codePatient;
		this.nomMedcin = nomMedcin;
		this.commentaire = commentaire;
		maladies = null;
		bon = null;
		certificatMedical = null;
		traitement = null;
		bilan = null;
		symptomes = null;
	}

	/**
	 * Enregister le bilan demandeee
	 * @param analyses : Le vecteur des analyses
	 */
	public void demanderBilan(Vector<Analyse> analyses) {
		bilan = new Bilan(codeConsu, getDateConsu(), analyses);
	}

	/**
	 * Enregister l'ordonnance
	 * @param medic : le vecteur de meeedicaments prescrits
	 */
	public void fournirOrdannace(Vector<Medicament> medic) {
		traitement = new Ordannance(codeConsu,getDateConsu(), 
				Patient.getPatient(codePatient), medic);
	}

	/***
	 * Enregister le certificat meeedical
	 * @param obs : L'observation du certificat meeedical
	 * @param type : le type du certificat meeedical (arreeet de travail, justificatif ...)
	 */
	public void fournirCertificatMedical(String obs, String type) {
		certificatMedical = new CertificatMedical(codeConsu, getDateConsu(), nomMedcin, obs, type);
	}

	/***
	 * Enregister le bon
	 */
	public void etablirBon(double montantTotal, double montantPaye) {
		bon = new Bon(getCodeConsu(), getDateConsu(), getCodePatient(), montantTotal, montantPaye); 
	}

	/***
	 * Stocker la consultation ainsi que toutes les informations relatives (bon, ordonnance ...) dans la base de donneeees 
	 */
	public void stocker() {

		String sql = "INSERT INTO Consultation Values(?,?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			setCodeConsu(Consultation.genererCode());
			requete.setString(1,String.valueOf(getCodeConsu()));
			requete.setString(2,String.valueOf(getCodePatient()));
			requete.setString(3,getNomMedcin());
			requete.setString(4,getCommentaire());

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		setDrConsu(codePatient,getDateConsu());

		if(bon != null) {
			bon.stocker();
		}
		if(bilan != null) {
			bilan.stocker();
		}
		if(traitement != null) {
			traitement.stocker();
		}
		if(certificatMedical != null) {
			certificatMedical.stocker();
		}
		if(symptomes != null) {
			for(int i = 0; i < symptomes.size(); i++) {
				symptomes.get(i).stocker();
			}
		}
		if(maladies != null) {
			for(int i = 0; i < maladies.size(); i++) {
				maladies.get(i).stocker();
			}
		}
	}

	/***
	 * Modifier la date de la derniere consultation pour le patient consulteee
	 * @param codePatient
	 * @param date
	 */
	public void setDrConsu(int codePatient,String date) {
		String sql = "UPDATE Patient set drConsu = ? WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);


			requete.setString(1,date);
			requete.setString(2, String.valueOf(codePatient));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Retourner toutes les consultation d'un patient donneee par son code
	 * @param codePatient
	 * @return Vecteur de consultations
	 */
	public static Vector<Consultation> getConsultation(int codePatient) {

		Vector<Consultation> vec = new Vector<Consultation>();

		String sql = "SELECT * FROM Consultation Where codePatient = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(codePatient));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				Consultation consu = new Consultation(codePatient, resultat.getString("nomMedcin"), resultat.getString("commentaire"));
			
				consu.setCodeConsu(Long.valueOf(resultat.getString("codeConsu")));
				consu.setMaladies(MaladieNonChronique.getMaladieNonChronique(consu.getCodeConsu()));
				consu.setBon(Bon.getBon(consu.getCodeConsu()));
				consu.setBilan(Bilan.getBilan(consu.getCodeConsu()));
				consu.setTraitement(Ordannance.getOrdannance(consu.getCodeConsu()));
				consu.setCertificatMedical(CertificatMedical.getCertificatMedical(consu.getCodeConsu()));

				vec.add(consu);
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
	 * Retourner un objet consultation depuis la base de donnes
	 * @param codeConsu
	 * @return Une consulation
	 */
	public static Consultation getConsultation(long codeConsu) {

		Consultation consu = null;

		String sql = "SELECT * FROM Consultation Where codeConsu = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(codeConsu));

			resultat = requete.executeQuery();

			while(resultat.next()) {
				consu = new Consultation(Integer.valueOf(resultat.getString("codePatient")),
						resultat.getString("nomMedcin"), resultat.getString("commentaire"));

				consu.setCodeConsu(codeConsu);
				consu.setMaladies(MaladieNonChronique.getMaladieNonChronique(consu.getCodeConsu()));
				consu.setBon(Bon.getBon(consu.getCodeConsu()));
				consu.setBilan(Bilan.getBilan(consu.getCodeConsu()));
				consu.setTraitement(Ordannance.getOrdannance(consu.getCodeConsu()));
				consu.setCertificatMedical(CertificatMedical.getCertificatMedical(consu.getCodeConsu()));

			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return consu;
	}

	/***
	 * Retourne le nombre de consultation dans une date donneeee
	 * @param date
	 * @return int
	 */
	public static int getNombreConsultations(String date) {

		String sql = "SELECT count(codeConsu) as NB FROM Consultation Where codeConsu like '".concat(date).concat("%'");
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		int nb = 0;

		try {
			requete = connection.prepareStatement(sql);

			resultat = requete.executeQuery();

			if(resultat.next()) {
				nb = Integer.valueOf(resultat.getString("NB"));
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nb;
	}

	/***
	 * Generer le code de la consultation = date + numeeero sequentiel  
	 * @return code de consultation
	 */
	public static long genererCode() {

		Calendar cal = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		String s = df.format(cal.getTime());
		String date = s.substring(0,2).concat(s.substring(3,5)).concat(s.substring(6,10));
		long code = 0;

		if(date.charAt(0) == '0') {
			date = date.substring(1,date.length());
		}

		String sql = "SELECT count(codeConsu) as NB, max(codeConsu) as MAX FROM Consultation WHERE codeConsu like '".concat(date).concat("%'");
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);
			resultat = requete.executeQuery();
			if(resultat.next() && !resultat.getString("NB").equals("0")) {
				s = resultat.getString("MAX");
			}
			else {
				s = date.concat("001");
				return Long.valueOf(s);
			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		s = s.substring(s.length() - 3,s.length());
		code = Long.valueOf(s) + 1;
		s = String.valueOf(code);
		while(s.length() != 3) {
			s = "0".concat(s);
		}

		s = date.concat(s);
		code = Long.valueOf(s);
		return code;
	}

	/***
	 * Imprimer touts les documents generer par la consultation
	 */
	public void imprimer() {
		if(getBon() != null) {
			getBon().imprimer(this);
		}
		if(getTraitement() != null) {
			getTraitement().imprimer(this);
		}
		if(getBilan() != null) {
			getBilan().imprimer(this);
		}
		if(getCertificatMedical() != null) {
			getCertificatMedical().imprimer(this);
		}
	}

	//////////////////////////////////////////////////////////////////////////  GETTERS ET SETTERS  //////////////////////////////////////////////////////////////////////////

	public long getCodeConsu() {return codeConsu;}
	public void setCodeConsu(long codeConsu) {this.codeConsu = codeConsu;}

	public int getCodePatient() {return codePatient;}
	public void setCodePatient(int codePatient) {this.codePatient = codePatient;}

	public String getNomMedcin() {return nomMedcin;}
	public void setNomMedcin(String nomMedcin) {this.nomMedcin = nomMedcin;	}

	public String getDateConsu() {
		String date = String.valueOf(codeConsu).substring(0,  String.valueOf(codeConsu).length() - 3);
		if(date.length() != 8) {
			date = "0".concat(date);
		}
		date = date.substring(0,2).concat("/").concat(date.substring(2,4)).concat("/").concat(date.substring(4,8));
		return date;
	}

	public Bon getMontantPaye() {return bon;}
	public void setMontantPaye(Bon bon) {this.bon = bon;}

	public String getCommentaire() { return commentaire; }
	public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

	public Bilan getBilan() {return bilan;}
	private void setBilan(Bilan bilan) {	this.bilan = bilan;}

	public Bon getBon() { return bon; }
	private void setBon(Bon bon) { this.bon = bon;}

	public Vector<MaladieNonChronique> getMaladies() { return maladies; }
	public void setMaladies(Vector<MaladieNonChronique> maladies) { this.maladies = maladies; }

	public Vector<Symptome> getSymptomes() { return symptomes; }
	public void setSymptomes(Vector<Symptome> symptomes) { this.symptomes = symptomes; }

	public CertificatMedical getCertificatMedical() { return certificatMedical; }
	private void setCertificatMedical(CertificatMedical certificatMedical) { this.certificatMedical = certificatMedical; }

	public Ordannance getTraitement() { return traitement; }
	private void setTraitement(Ordannance traitement) { this.traitement = traitement; }
}