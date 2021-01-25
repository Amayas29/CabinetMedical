package documents;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import consultation.Consultation;
import patient.Patient;

/***
 * La classe super de tous les documents : comporte la date et le code de la consultation dans laquel les documents ont �t� generer
 * 
 */
public abstract class Doc {

	private long code;
	private String date;

	public Doc(long code,String date) {
		this.code = code;
		this.date = date;
	}
	
	/***
	 * imprimer le document
	 */
	public abstract void imprimer(Consultation consu);
	
	/***
	 * Enregisterer le document dans la base de donn�s
	 */
	public abstract void stocker();

	public long getCode() { return code;}
	public void setCode(long code) {this.code = code;}

	public String getDate() { return date; }
	public void setDate(String date) { this.date = date; }

	/***
	 * retroune l'age du patient � partir de sa date de naissance
	 * @param patient
	 * @return
	 */
	public String getAge(Patient patient) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
			Date dateAuj;
			dateAuj = sdf.parse(sdf.format(Calendar.getInstance().getTime()));
			Date dateNaiss = sdf.parse(patient.getDateNaissance());
			long diffMilli = Math.abs(dateAuj.getTime() - dateNaiss.getTime());
			long diff_long = TimeUnit.DAYS.convert(diffMilli, TimeUnit.MILLISECONDS);
			diff_long /= 365;
			if(diff_long == 0) {
				diff_long = TimeUnit.DAYS.convert(diffMilli, TimeUnit.MILLISECONDS);
				if(diff_long > 30) {
					return String.valueOf(diff_long/30 + " mois");
				}
				else {
					return String.valueOf(diff_long + " jours");
				}	
			}
			else {
				return String.valueOf(diff_long + " ans");
			}
		} catch (ParseException e) {
			return "18 ans";
		}
	}
}