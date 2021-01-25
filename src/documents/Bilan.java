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
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import consultation.Consultation;
import dataBase.ConnexionOracle;
import patient.Patient;
/***
 * La classe Bilan qui est caracterisee par un vecteur des analyses, de resultats et l'etat de la demande 
 * 
 */
public class Bilan extends Doc {

	private Vector<Analyse> analyses;
	private boolean effectue;
	private Vector<Resultat> resultat;

	public Bilan(long code,String date, Vector<Analyse> analyses) {
		super(code,date);
		this.analyses = analyses;
		this.effectue = false;
		this.resultat = null;
	}

	/***
	 * Imprimer le Bilan
	 */
	@Override
	public void imprimer(Consultation consu) {
		Document doc = new Document();
		doc.setPageSize(new Rectangle(400, 600));
		doc.setMargins(25, 25, 15, 7);
		try {

			BaseColor noir = new BaseColor(48, 49, 52);
			com.itextpdf.text.Font ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 5,
					com.itextpdf.text.Font.NORMAL, noir);

			Paragraph ligne = new Paragraph(" ", ft);

			String nom = "Fichier/bilan_".concat(String.valueOf(consu.getCodeConsu())).concat(".pdf");

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
			Patient patient = Patient.getPatient(consu.getCodePatient());
			String genre = "Mme :  ";
			if(patient.getSexe().equals("Homme")) {
				genre = "Mr :  ";
			}
			String pat = "Date : ".concat(consu.getDateConsu()).concat("\n".concat(genre).concat(patient.getNom().concat(" ").concat(patient.getPrenom())))
					.concat("\n ege ".concat(getAge(patient)));
			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.NORMAL, noir);
			p = new Paragraph(pat, ft);
			p.setAlignment(Element.ALIGN_RIGHT);
			doc.add(p);

			doc.add(ligne);

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 15,
					com.itextpdf.text.Font.UNDERLINE, noir);
			p = new Paragraph("BILAN", ft);
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);

			doc.add(ligne);

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.UNDERLINE, noir);

			com.itextpdf.text.Font ftn =  new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.NORMAL, noir);

			int debut = 0;
			int page = 1;
			for(int i = 0; i < consu.getBilan().getAnalyses().size(); i++) {
				Analyse analyse = consu.getBilan().getAnalyses().get(i);
				p = new Paragraph();

				String numero = " -".concat(String.valueOf(i+1)).concat(" ");
				Phrase n = new Phrase(numero, ftn);
				p.add(n);

				Phrase medicamentPhrase = new Phrase(analyse.getNom(), ft);
				p.add(medicamentPhrase);

				p.add(ligne);

				doc.add(p);
				doc.add(ligne);

				if(page == 1 && i == 14) {
					doc.add(ligne);
					doc.add(sep);
					p = new Paragraph(String.valueOf(page), ftn);
					p.setSpacingBefore(-7.7f);
					p.setAlignment(Element.ALIGN_RIGHT);
					doc.add(p);
					debut = 15;
					page++;
				}
				else if((i+1-debut) % 23 == 0) {
					doc.add(ligne);
					doc.add(sep);
					p = new Paragraph(String.valueOf(page), ftn);
					p.setSpacingBefore(-7.7f);
					p.setAlignment(Element.ALIGN_RIGHT);
					doc.add(p);
					debut = i + 1;
					page++;
				}
				if(i+1 == consu.getBilan().getAnalyses().size()) {
					int j = i + 1;
					if(j == debut) {
						break;
					}
					while(j < debut+23) {
						p = new Paragraph();

						numero = "      " ;
						n = new Phrase(numero, ftn);
						p.add(n);

						medicamentPhrase = new Phrase("      ", ftn);
						p.add(medicamentPhrase);

						doc.add(p);
						doc.add(ligne);

						if(page == 1 && j == 14) {
							doc.add(sep);
							p = new Paragraph(String.valueOf(page), ftn);
							p.setSpacingBefore(-7.7f);
							p.setAlignment(Element.ALIGN_RIGHT);
							doc.add(p);
							break;
						}
						else if((j+1-debut) % 23 == 0) {
							doc.add(sep);
							p = new Paragraph(String.valueOf(page), ftn);
							p.setSpacingBefore(-7.7f);
							p.setAlignment(Element.ALIGN_RIGHT);
							doc.add(p);
							break;
						}
						j++;
					}
				}
			}

			doc.close();
			Desktop.getDesktop().open(new File(nom));
		} catch (DocumentException | IOException e1) {
			e1.printStackTrace();
		} 
	}

	/***
	 * Stocker dans la base de donnes
	 */
	@Override
	public void stocker() {

		String sql ="INSERT INTO Bilan VALUES (?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.getCode()));
			requete.setString(2, this.getDate());
			requete.setString(3, "0");

			requete.execute();

			requete.close();
			connection.close();

			for(int i = 0; i < analyses.size(); i++) {
				analyses.get(i).stocker();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * Charger le Bilan demande lors d'une consultation
	 * @param code : Code de la consultation
	 */
	public static Bilan getBilan(long code) {

		Bilan bilan = null;
		String sql ="SELECT * FROM Bilan WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			if(resultat.next()) {

				bilan = new Bilan(code,resultat.getString("dateConsu"),Analyse.getAnalyses(code));
				bilan.resultat = Resultat.getResultat(code);

				bilan.effectue = false;
				if(resultat.getString("effectue").equals("1")) {
					bilan.effectue = true;
				}

			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bilan;
	}

	///////////////////////////////////////// GETTERS ET SETTERS ////////////////////////////////////////////////////
	public Vector<Analyse> getAnalyses() { return analyses; }
	public void setAnalyses(Vector<Analyse> analyses) { this.analyses = analyses; }

	public boolean isEffectue() { return effectue; }
	public void setEffectue(boolean effectue) { this.effectue = effectue; }

	public Vector<Resultat> getResultat() { return resultat; }
	
	/***
	 * Charger le resultat des analyses
	 * 
	 * @param resultats : le vecteur de resultats
	 */
	public void chargerResultat(Vector<Resultat> resultats) {

		this.resultat = resultats;
		for(Resultat res : resultats) {
			res.stocker();
		}
		this.effectue = true;
		for(int i = 0 ; i < this.analyses.size(); i++) {
			this.effectue = this.effectue && this.analyses.get(i).isEffectue();
		}

		String sql ="UPDATE Bilan set effectue = ? WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			if(this.effectue) {
				requete.setString(1, "1");
			}
			else {
				requete.setString(1, "0");
			}
			requete.setString(2, String.valueOf(this.getCode()));

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}