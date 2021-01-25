package documents;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
/***
 * Certificat medical comporte le nom du medecin l'observation et le type
 * 
 */
public final class CertificatMedical extends Doc {

	private String nomMedcin;
	private String obs;	
	private String type;

	public CertificatMedical(long code,String date, String nomMedcin, String obs, String type) {
		super(code,date);
		this.nomMedcin = nomMedcin;
		this.obs = obs;
		this.type = type;
	}

	/***
	 * Imprimer le certificat medical
	 */
	@Override
	public void imprimer(Consultation consu) {
		Document doc = new Document();
		doc.setPageSize(new Rectangle(380, 480));
		try {

			BaseColor noir = new BaseColor(48, 49, 52);
			com.itextpdf.text.Font ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 5,
					com.itextpdf.text.Font.NORMAL, noir);

			Paragraph ligne = new Paragraph(" ", ft);

			String nom = "Fichier/certificatMedical_".concat(String.valueOf(consu.getCodeConsu())).concat(".pdf");

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

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.NORMAL, noir);

			doc.add(ligne);
			doc.add(ligne);

			com.itextpdf.text.Font ftn =  new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.NORMAL, noir);

			p = new Paragraph("Fourni le " +this.getDate(), ftn);
			p.setAlignment(Element.ALIGN_RIGHT);
			doc.add(p);

			doc.add(ligne);
			doc.add(ligne);

			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 15,
					com.itextpdf.text.Font.UNDERLINE, noir);
			p = new Paragraph("CERTIFICAT MEDICAL : " + getType(), ft);
			p.setAlignment(Element.ALIGN_CENTER);
			doc.add(p);

			doc.add(ligne);
			p = new Paragraph(" ");
			doc.add(p);


			ft = new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.TIMES_ROMAN, 12,
					com.itextpdf.text.Font.UNDERLINE, noir);


			p = new Paragraph(getObs(), ftn);
			doc.add(p);

			doc.close();
			Desktop.getDesktop().open(new File(nom));
		} catch (DocumentException | IOException e1) {
			e1.printStackTrace();
		} 
	}
	
	/***
	 * L'enregistrer dans la base de donnes
	 */
	@Override
	public void stocker() {
		String sql = "INSERT INTO CertificatMedical Values (?,?,?,?)";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(this.getCode()));
			requete.setString(2, this.getDate());
			requete.setString(3, this.getObs());
			requete.setString(4, this.getType());

			requete.execute();

			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	/***
	 * Retourner le certificat medical d'une consultation
	 * @param code
	 */
	public static CertificatMedical getCertificatMedical(long code) {

		CertificatMedical certif = null;
		String sql ="SELECT * FROM CertificatMedical WHERE code = ?";
		Connection connection = ConnexionOracle.connecteBD();
		PreparedStatement requete;
		ResultSet resultat;

		try {
			requete = connection.prepareStatement(sql);

			requete.setString(1, String.valueOf(code));

			resultat = requete.executeQuery();

			if(resultat.next()) {
				certif = new CertificatMedical(code, resultat.getString("dateConsu")
						, null, resultat.getString("obs"), resultat.getString("typeC"));

			}
			resultat.close();
			requete.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return certif;
	}

	////////////////////////////////////// GETTERS ET SETTERS ///////////////////////////////////////////////////////////
	
	public String getNomMedcin() { return nomMedcin; }
	public void setNomMedcin(String nomMedcin) { this.nomMedcin = nomMedcin; }

	public String getObs() { return obs; }
	public void setObs(String obs) { this.obs = obs; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
}