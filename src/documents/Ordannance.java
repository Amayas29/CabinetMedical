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
 * L'ordonnance qui comporte le patient et un vecteur de medicament prescrit
 * 
 */
public class Ordannance extends Doc {

	private Patient patient;
	private Vector<Medicament> medic;

	public Ordannance(long code, String date,Patient patient, Vector<Medicament> medic) {
		super(code,date);
		this.patient = patient;
		this.medic = medic;
	}

	/***
	 * Enregisterer dans la base de donnes
	 */
	@Override
	public void stocker() {
		String sql = "INSERT INTO Ordannance Values (?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.getCode()));
			requete.setString(2, this.getDate());

			requete.execute();

			requete.close();
			connection.close();

			for(int i = 0; i < medic.size(); i++) {
				medic.get(i).stocker();
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * retourner l'ordonnance prescrit dans une consultation
	 * @param code
	 * @return
	 */
	public static Ordannance getOrdannance(long code) {

		Ordannance ordnc = null;
		String sql ="SELECT * FROM Ordannance WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			if(resultat.next()) {
				ordnc = new Ordannance(code, resultat.getString("dateConsu"),
						null, Medicament.getMedicament(code));

			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ordnc;
	}
	
	/***
	 * Imprimer l'ordonnance
	 */
	@Override
	public void imprimer(Consultation consu) {
		Document doc = new Document();
		doc.setPageSize(new Rectangle(400, 600));
		doc.setMargins(25, 25, 15, 5);
		try {

			BaseColor noir = new BaseColor(48, 49, 52);
			com.itextpdf.text.Font ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 5,
					com.itextpdf.text.Font.NORMAL, noir);

			Paragraph ligne = new Paragraph(" ", ft);

			String nom = "Fichier/ordannance_".concat(String.valueOf(consu.getCodeConsu())).concat(".pdf");

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
			p = new Paragraph("ORDONNANCE", ft);
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);

			doc.add(ligne);

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.UNDERLINE, noir);

			com.itextpdf.text.Font ftn =  new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.NORMAL, noir);

			int debut = 0;
			int page = 1;
			for(int i = 0; i < consu.getTraitement().getMedic().size(); i++) {
				Medicament medicament = consu.getTraitement().getMedic().get(i);
				p = new Paragraph();

				String numero = " -".concat(String.valueOf(i+1)).concat(" ");
				Phrase n = new Phrase(numero, ftn);
				p.add(n);

				Phrase medicamentPhrase = new Phrase(medicament.getNom(), ft);
				p.add(medicamentPhrase);

				Paragraph qte = new Paragraph(medicament.getQuantite(), ftn);
				qte.setAlignment(Element.ALIGN_RIGHT);
				qte.setSpacingAfter(-12);
				qte.setSpacingBefore(4);
				doc.add(qte);

				p.add(ligne);

				Phrase nbPrise = new Phrase("    ".concat(medicament.getNbPriseParTemps()), ftn);
				p.add(nbPrise);

				doc.add(p);
				doc.add(ligne);

				if(page == 1 && i == 6) {
					doc.add(ligne);
					doc.add(sep);
					p = new Paragraph(String.valueOf(page), ftn);
					p.setSpacingBefore(-7.7f);
					p.setAlignment(Element.ALIGN_RIGHT);
					doc.add(p);
					debut = 7;
					page++;
				}
				else if((i+1-debut) % 11 == 0) {
					doc.add(ligne);
					doc.add(sep);
					p = new Paragraph(String.valueOf(page), ftn);
					p.setSpacingBefore(-7.7f);
					p.setAlignment(Element.ALIGN_RIGHT);
					doc.add(p);
					debut = i + 1;
					page++;
				}
				if(i+1 == consu.getTraitement().getMedic().size()) {
					int j = i + 1;
					if(j == debut) {
						break;
					}
					while(j < debut+11) {
						p = new Paragraph();

						numero = "      " ;
						n = new Phrase(numero, ftn);
						p.add(n);

						medicamentPhrase = new Phrase("      ", ftn);
						p.add(medicamentPhrase);

						qte = new Paragraph("      ", ftn);
						qte.setAlignment(Element.ALIGN_RIGHT);
						qte.setSpacingAfter(-12);
						qte.setSpacingBefore(4);
						doc.add(qte);

						p.add(ligne);

						nbPrise = new Phrase("      ", ftn);
						p.add(nbPrise);

						doc.add(p);
						doc.add(ligne);

						if(page == 1 && j == 6) {
							doc.add(sep);
							p = new Paragraph(String.valueOf(page), ftn);
							p.setSpacingBefore(-7.7f);
							p.setAlignment(Element.ALIGN_RIGHT);
							doc.add(p);
							break;
						}
						else if((j+1-debut) % 11 == 0) {
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

	///////////////////////////////// GETTERS ET SETTERS /////////////////////////////////////////////////////////////

	public Patient getPatient() { return patient; }
	public void setPatient(Patient patient) { this.patient = patient; }

	public Vector<Medicament> getMedic() { return medic; }
	public void setMedic(Vector<Medicament> medic) { this.medic = medic; }	
}