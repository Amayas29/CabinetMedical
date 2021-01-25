package utilisateur;

import patient.Patient;
import patient.PatientDetaille;

/***
 * La classe du docteur
 *
 */
public class Docteur extends Utilisateur {

	public Docteur(String utilisateur, String motDePasse) {
		super(utilisateur, motDePasse, Types.Docteur);
	}

	/***
	 * Supprimer un patient donnes
	 * @param patient
	 */
	public void supprimerPatient(Patient patient) {

		PatientDetaille patientDetaille = new PatientDetaille(patient, null, null, null, null, false,
				null, null, null, null, null,null,null, null,null);
		patientDetaille.supprimer();
		patient = null;
	}

	/***
	 * Modifier le dossier medical d'un patient
	 * @param patientDetailleAncient
	 * @param patientDetailleNouveau
	 */
	public void mettreAJour(PatientDetaille patientDetailleAncient,PatientDetaille patientDetailleNouveau) {
		patientDetailleAncient.modifier(patientDetailleNouveau);
	}
}