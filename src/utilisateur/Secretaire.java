package utilisateur;

import gestionTemps.Rdv;

/***
 * La classe de la Secretaire
 * 
 */
public class Secretaire extends Utilisateur {

	public Secretaire(String utilisateur, String motDePasse) {
		super(utilisateur, motDePasse, Types.Secretaire);
	}

	/***
	 * Ajouter un Rdv
	 * @param rdv
	 */
	public void ajouterRdv(Rdv rdv) {
		rdv.stocker();
	}

	/***
	 * Annuler un Rdv
	 * @param rdv
	 */
	public void annulerRdv(Rdv rdv) {
		rdv.annuler();
		rdv = null;
	}

}