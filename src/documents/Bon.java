package documents;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import consultation.Consultation;
import dataBase.ConnexionOracle;
import patient.Patient;

/***
 * La classe Bon qui est comporte le code du patient ainsi le montant total et le montant paye
 * 
 */
public class Bon extends Doc {

	private int codePatient;
	private double montantTotal;
	private double montantPaye;

	public Bon(long code,String date, int codePatient, double montantTotal, double montantPaye) {
		super(code,date);
		this.codePatient = codePatient;
		this.montantTotal = montantTotal;
		this.montantPaye = montantPaye;
	}

	/***
	 * Imprimer le bon
	 */
	@Override
	public void imprimer(Consultation consu) {
		Document doc = new Document();
		doc.setPageSize(new Rectangle(300, 300));
		doc.setMargins(25, 25, 5, 5);
		Patient patient = Patient.getPatient(consu.getCodePatient());
		try {

			BaseColor noir = new BaseColor(48, 49, 52);
			com.itextpdf.text.Font ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 6,
					com.itextpdf.text.Font.NORMAL, noir);

			Paragraph ligne = new Paragraph(" ", ft);

			String nom = "Fichier/bon_".concat(String.valueOf(consu.getCodeConsu())).concat(".pdf");

			PdfWriter.getInstance(doc, new FileOutputStream(nom));
			doc.open();

			String entete = "DR ".concat(consu.getNomMedcin());

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 15,
					com.itextpdf.text.Font.BOLD, noir);

			Paragraph p = new Paragraph(entete, ft);
			p.setAlignment(Element.ALIGN_CENTER);
			p.setSpacingAfter(10f);
			doc.add(p);

			LineSeparator sep = new LineSeparator(3f, 100, noir, Element.ALIGN_CENTER, 0f);
			doc.add(sep);

			doc.add(ligne);
			String med = "Cabinet medicale : Medic\nRoute : Ne4\nNe d'ordre : 0345/tzo\n Tel : 0556343521";
			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
					com.itextpdf.text.Font.NORMAL, noir);
			p = new Paragraph(med, ft);
			p.setAlignment(Element.ALIGN_LEFT);
			p.setSpacingAfter(-5f);
			doc.add(p);

			String genre = "Mme ";
			if(patient.getSexe().equals("Homme")) {
				genre = "Mr ";
			}

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.NORMAL, noir);

			doc.add(ligne);

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 15,
					com.itextpdf.text.Font.UNDERLINE, noir);
			p = new Paragraph("BON", ft);
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);

			doc.add(ligne);
			doc.add(ligne);

			com.itextpdf.text.Font ftn =  new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.NORMAL, noir);

			String code = String.valueOf(this.getCode());
			if(code.length() != 11) {
				code = "0".concat(code);
			}

			p = new Paragraph(genre + patient.getNom() + " " + patient.getPrenom(), ftn);
			p.setSpacingAfter(-12);
			doc.add(p);

			p = new Paragraph("Date : " + this.getDate(), ftn);
			p.setAlignment(Element.ALIGN_RIGHT);
			p.setSpacingBefore(-6.5f);
			doc.add(p);

			doc.add(ligne);

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.UNDERLINE, noir);

			p = new Paragraph("Total e paye : " + this.getMontantTotal() + " Da", ftn);
			doc.add(p);
			p = new Paragraph("Montant paye : " + this.getMontantPaye() + " Da", ftn);
			doc.add(p);
			p = new Paragraph("Montant restant : " + this.getMontantRestant() + " Da", ftn);
			doc.add(p);

			doc.add(ligne);

			ftn =  new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 10,
					com.itextpdf.text.Font.NORMAL, noir);

			p = new Paragraph("Bon ne " + code, ftn);
			doc.add(p);
			doc.add(ligne);

			p = new Paragraph("Merci de votre visite", ftn);
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);

			doc.close();
			Desktop.getDesktop().open(new File(nom));
		} catch (DocumentException | IOException e1) {
			e1.printStackTrace();
		} 
	}

	/***
	 * Stocker le bon dans la Base de donnes
	 */
	@Override
	public void stocker() {
		String sql ="INSERT INTO Bon VALUES (?,?,?,".concat(String.valueOf(this.montantTotal)).concat(",").concat(String.valueOf(this.montantPaye)).concat(")");
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.getCode()));
			requete.setString(2, String.valueOf(this.codePatient));
			requete.setString(3,this.getDate());

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Retourner le bon de la consultation donnee par son code
	 * @param code : code de la consultation
	 */
	public static Bon getBon(long code){

		Bon bon = null;

		String sql ="SELECT * FROM Bon WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			if(resultat.next()) {
				bon = new Bon(code,resultat.getString("dateConsu"),Integer.valueOf(resultat.getString("codePatient")),
						Double.valueOf(resultat.getString("montantTotal")),Double.valueOf(resultat.getString("montantPaye")));
			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return bon;
	}

	/**
	 * Retourner tous les bons d'une date donnee
	 * @param date
	 */
	public static Vector<Bon> getBon(String date){

		Vector<Bon> bons = new Vector<Bon>();

		String sql ="SELECT * FROM Bon WHERE dateConsu like '".concat(date).concat("'");
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			resultat = requete.executeQuery();

			while(resultat.next()) {
				Bon bon = new Bon(Long.valueOf(resultat.getString("code")),resultat.getString("dateConsu"),
						Integer.valueOf(resultat.getString("codePatient")),
						Double.valueOf(resultat.getString("montantTotal")),Double.valueOf(resultat.getString("montantPaye")));

				bons.add(bon);
			}

			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace(); 
		}

		if(bons.size() == 0) {
			bons = null;
		}
		return bons;
	}

	/////////////////////////////////// GETTERS ET SETTERS ////////////////////////////////////////////////////////
	
	public double getMontantRestant() { return this.montantTotal - this.montantPaye; }

	public int getCodePatient() { return codePatient; }
	public void setCodePatient(int codePatient) { this.codePatient = codePatient; }

	public double getMontantTotal() { return montantTotal; }
	public void setMontantTotal(double montantTotal) { this.montantTotal = montantTotal; }

	public double getMontantPaye() { return montantPaye; }
	public void setMontantPaye(double montantPaye) { this.montantPaye = montantPaye; }
}