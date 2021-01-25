package maladies;

import java.util.Vector;
/***
 * Une classe des maladies non chroniques pour les statistiques
 *
 */
public class MaladieNonChroniqueNombre {

	private MaladieNonChronique maladieNonChronique;
	private int nombre;

	public MaladieNonChroniqueNombre(MaladieNonChronique maladieNonChronique, int nombre) {
		this.maladieNonChronique = maladieNonChronique;
		this.nombre = nombre;
	}
	public MaladieNonChronique getMaladieNonChronique() { return maladieNonChronique; }
	public void setMaladieNonChronique(MaladieNonChronique maladieNonChronique) { this.maladieNonChronique = maladieNonChronique; }

	public int getNombre() { return nombre; }
	public void setNombre(int nombre) { this.nombre = nombre; }

	public static void trier(Vector<MaladieNonChroniqueNombre> vec) {
		if(vec != null) {
			for(int i = 0; i < vec.size() - 1; i++) {
				for(int j = i+1; j < vec.size(); j++) {
					if(vec.get(i).getMaladieNonChronique().getNom().equals(vec.get(j).getMaladieNonChronique().getNom())) {
						vec.get(i).setNombre(vec.get(i).getNombre() + 1);
						vec.remove(j);
						j--;
					}
				}
			}
		}
	}
}