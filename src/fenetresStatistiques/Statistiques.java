package fenetresStatistiques;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import consultation.Consultation;
import documents.Bon;
import fenetresPrincipales.FenetreDocteur;
import maladies.MaladieNonChronique;
import maladies.MaladieNonChroniqueNombre;
import patient.Patient;
import utilisateur.Types;
import utilisateur.Utilisateur;

public class Statistiques extends JFrame {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JPanel panelDessin; 
	private JButton pred;
	private JButton proch;
	private Calendar date = Calendar.getInstance();
	private DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private JLabel affichageTemps = new JLabel();

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Statistiques frame = new Statistiques(Utilisateur.getUtilisateur(Types.Docteur.getType(), "amayas", "amayas"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Statistiques(Utilisateur doc) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				FenetreDocteur fenDoc = new FenetreDocteur(doc);
				fenDoc.setVisible(true);
			}
		});
		setTitle("Medic : Statistiques");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel medic = new Panel();
		medic.setLayout(null);
		medic.setBackground(new Color(48, 49, 52));
		medic.setBounds(0, 0, 994, 83);
		contentPane.add(medic);

		JLabel label = new JLabel("Un logiciel pour vous");
		label.setForeground(new Color(62, 122, 189));
		label.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label.setBounds(66, 54, 147, 20);
		medic.add(label);

		JLabel label_1 = new JLabel("Medic");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 50));
		label_1.setBounds(24, -33, 228, 122);
		medic.add(label_1);

		JLabel label_2 = new JLabel();
		label_2.setIcon(new ImageIcon("Images/Outils/gmail.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(670, 3, 44, 28);
		medic.add(label_2);

		JLabel label_3 = new JLabel("Medic.contact@gmail.com");
		label_3.setForeground(new Color(250, 239, 197));
		label_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_3.setBounds(720, 1, 168, 30);
		medic.add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("Images/Outils/facebook.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(670, 24, 44, 28);
		medic.add(label_4);

		JLabel label_5 = new JLabel("Medic");
		label_5.setForeground(new Color(250, 239, 197));
		label_5.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_5.setBounds(720, 22, 139, 30);
		medic.add(label_5);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon("Images/Outils/site.png"));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(670, 46, 44, 28);
		medic.add(label_6);

		JLabel label_7 = new JLabel("www.Medic.com");
		label_7.setForeground(new Color(250, 239, 197));
		label_7.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_7.setBounds(720, 44, 139, 30);
		medic.add(label_7);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(663, 0, 10, 83);
		medic.add(separator);

		Panel panelPrincipale = new Panel();
		panelPrincipale.setLayout(null);
		panelPrincipale.setForeground(Color.BLACK);
		panelPrincipale.setBackground(new Color(57, 113, 177));
		panelPrincipale.setBounds(0, 80, 1007, 593);
		contentPane.add(panelPrincipale);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(57, 113, 177));
		panel.setBounds(23, 74, 961, 499);
		panelPrincipale.add(panel);
		panel.setLayout(null);

		panelDessin = new JPanel();
		panelDessin.setBounds(0, 41, 961, 462);
		panel.add(panelDessin);
		panelDessin.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelDessin.setBackground(new Color(57, 113, 177));
		panelDessin.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

		pred = new JButton("Semaine pr\u00E9c\u00E9dente");
		pred.setEnabled(false);
		pred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(tabbedPane.getSelectedIndex() == 1) { // Histo maladie
					date.add(Calendar.DATE, -7);
					dessinerHistoMaladies(getSemaine(df.format(date.getTime())));
				}
				else if(tabbedPane.getSelectedIndex() == 2) { // Histo consu
					date.add(Calendar.DATE, -7);
					dessinerHistoConsu(getSemaine(df.format(date.getTime())));
				}
				else if(tabbedPane.getSelectedIndex() == 4) { // Courbe revenu
					DateFormat d = new SimpleDateFormat("MM/yyyy");
					date.add(Calendar.MONTH, -1);
					dessinerCourbeRevenu(d.format(date.getTime()));
				}
			}
		});
		pred.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		pred.setBackground(new Color(250, 239, 197));
		pred.setBounds(35, 5, 170, 31);
		panel.add(pred);

		proch = new JButton("Semaine prochaine");
		proch.setEnabled(false);
		proch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(tabbedPane.getSelectedIndex() == 1) { // Histo maladie
					date.add(Calendar.DATE, 7);
					dessinerHistoMaladies(getSemaine(df.format(date.getTime())));
				}
				else if(tabbedPane.getSelectedIndex() == 2) { // Histo consu
					date.add(Calendar.DATE, 7);
					dessinerHistoConsu(getSemaine(df.format(date.getTime())));
				}
				else if(tabbedPane.getSelectedIndex() == 4) { // Courbe revenu
					DateFormat d = new SimpleDateFormat("MM/yyyy");
					date.add(Calendar.MONTH, 1);
					dessinerCourbeRevenu(d.format(date.getTime()));
				}
			}
		});
		proch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		proch.setBackground(new Color(250, 239, 197));
		proch.setBounds(219, 5, 170, 31);
		panel.add(proch);
		affichageTemps.setForeground(new Color(250, 239, 197));
		affichageTemps.setHorizontalAlignment(SwingConstants.CENTER);

		affichageTemps.setFont(new Font("Century Gothic", Font.BOLD, 14));
		affichageTemps.setBounds(453, 6, 279, 30);
		panel.add(affichageTemps);

		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tabbedPane.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {

				date = Calendar.getInstance();
				switch (tabbedPane.getSelectedIndex()) {
				case 0:
					pred.setEnabled(false);
					proch.setEnabled(false); 
					dessinerCerculaireHF();
					break;
				case 1:
					pred.setText("Semaine pr\u00E9c\u00E9dente");
					pred.setEnabled(true);

					proch.setText("Semaine prochaine");
					proch.setEnabled(true);

					dessinerHistoMaladies(getSemaine(df.format(date.getTime())));
					break;
				case 2:
					pred.setText("Semaine pr\u00E9c\u00E9dente");
					pred.setEnabled(true);

					proch.setText("Semaine prochaine");
					proch.setEnabled(true);

					dessinerHistoConsu(getSemaine(df.format(date.getTime())));
					break;
				case 3:
					pred.setEnabled(false);
					proch.setEnabled(false);

					dessinerHistoAges();
					break;
				case 4:
					pred.setText("Mois pr\u00E9c\u00E9dent");
					pred.setEnabled(true);

					proch.setText("Mois prochain");
					proch.setEnabled(true);

					DateFormat mois = new SimpleDateFormat("MM/yyyy");
					dessinerCourbeRevenu(mois.format(date.getTime()));
					break;
				default:
					viderPanel();
					break;
				}
			}

		});
		tabbedPane.setBounds(23, 40, 950, 29);
		panelPrincipale.add(tabbedPane);

		JButton repartHF = new JButton("Repartition Homme / Femme");
		repartHF.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tabbedPane.addTab("Repartition Homme / Femme", repartHF);

		JButton histoMaladies = new JButton("Repartition des patients selon les maladies par semaine");
		histoMaladies.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tabbedPane.addTab("Repartition des patients selon les maladies par semaine", histoMaladies);

		JButton histoConsu = new JButton("Courbe de Frequentation du cabinet par semaine");
		histoConsu.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tabbedPane.addTab("Courbe de frequantations du cabinet par semaine", histoConsu);

		JButton histoAgesPatients = new JButton("Repartition des patients selon leur eges");
		histoAgesPatients.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tabbedPane.addTab("Repartition des patients selon leur eges", histoAgesPatients);

		JButton courbeRevenuMois = new JButton("Courbe des revenus par mois");
		courbeRevenuMois.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tabbedPane.addTab("Courbe des revenus par mois", courbeRevenuMois);

		tabbedPane.setSelectedIndex(-1);
	}

	private void dessinerCourbeRevenu(String mois) {

		affichageTemps.setText("Le : " + mois);

		List<Integer> points = new ArrayList<Integer>();
		List<Float> valeurs = new ArrayList<Float>();

		Vector<Bon> bons = Bon.getBon("%/".concat(mois));

		int nbJours = getNbJours(mois);

		double[] revenusTotal = new double[nbJours + 1];

		if(bons != null) {
			for(int i = 0; i < bons.size(); i++) {
				int j = Integer.valueOf(bons.get(i).getDate().substring(0, 2));
				revenusTotal[j] += bons.get(i).getMontantTotal();
			}
		}

		for(int i = 1; i <= nbJours; i++) {		
			points.add(i);
			valeurs.add((float) revenusTotal[ i ]);
		}

		Graphique graphique = Graphique.getCourbe("Courbe des revenus par mois", "Revenu", "Jours", new Color(57, 113, 177),
				valeurs, points, "Courbe des revenus par mois", false);

		panelDessin.removeAll();
		panelDessin.setLayout(new BorderLayout());
		panelDessin.setBounds(0, 41, 961, 462);
		panelDessin.add(graphique, BorderLayout.CENTER);
		panelDessin.repaint();
		panelDessin.validate();
	}

	private int getNbJours(String mois) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, Integer.valueOf(mois.substring(3, 5)));
		c.set(Calendar.MONTH, Integer.valueOf(mois.substring(0, 2)) - 1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	private void dessinerHistoAges() {

		List<String> categories = new ArrayList<String>();
		categories.add("");

		affichageTemps.setText("");

		List<String> series = new ArrayList<String>();
		List<Float> valeurs = new ArrayList<Float>();

		Vector<Patient> patients = Patient.rechercherPatient("1 = 1");

		Vector<Integer> ages = new Vector<Integer>();

		if(patients != null) {

			for(Patient p : patients) {
				ages.add(getAge(p));
			}

			ages.sort(new Comparator<Integer>() {

				@Override
				public int compare(Integer o1, Integer o2) {
					return o1 - o2;
				}
			});

			for(int age : ages) {
				if(series.contains(String.valueOf(age) + " ans")) {
					valeurs.set(series.indexOf(String.valueOf(age) + " ans"), valeurs.get(series.indexOf(String.valueOf(age) + " ans")) + 1);
				}
				else {
					series.add(String.valueOf(age) + " ans");
					valeurs.add((float) 1);
				}
			}
		}

		Graphique graphique = Graphique.getHistorgramme("Repartition des patients selon leur eges (en annee)", "Nombre de patients", "eges",
				new Color(57, 113, 177), valeurs, series, categories );

		panelDessin.removeAll();
		panelDessin.setLayout(new BorderLayout());
		panelDessin.setBounds(0, 5, 961, 503);
		panelDessin.add(graphique, BorderLayout.CENTER);
		panelDessin.repaint();
		panelDessin.validate();
	}

	private int getAge(Patient patient) {

		Calendar aujourdhui = Calendar.getInstance();
		Calendar dateDeNaissance = Calendar.getInstance();

		String d = patient.getDateNaissance();

		dateDeNaissance.set(Calendar.YEAR, Integer.valueOf(d.substring(6, 10)));
		dateDeNaissance.set(Calendar.MONTH, Integer.valueOf(d.substring(3, 5)) - 1);
		dateDeNaissance.set(Calendar.DAY_OF_MONTH, Integer.valueOf(d.substring(0, 2)));

		long timeBetween = aujourdhui.getTime().getTime() - dateDeNaissance.getTime().getTime();
		double yearsBetween = timeBetween / 3.15576e+10;
		int age = (int) Math.floor(yearsBetween);
		return age;
	}

	private void dessinerHistoConsu(Vector<String> semaine) {

		List<String> categories = new ArrayList<String>();
		categories.add("");
		List<Float> valeurs = new Vector<Float>();
		List<String> series = new Vector<String>();

		if(semaine != null) {

			for(String date : semaine) {
				valeurs.add((float) Consultation.getNombreConsultations(date));
				String d = date;
				if(d.length() != 8) {
					d = "0".concat(d);
				}
				d = d.substring(0,2).concat("/").concat(d.substring(2, 4)).concat("/").concat(d.substring(4, 8));
				series.add(d);
			}
		}

		Graphique graphique = Graphique.getHistorgramme("Courbe de Frequentation du cabinet par semaine",
				"Nombre de consultations ", " Jours de la semaine",new Color(57, 113, 177),valeurs, series, categories);

		panelDessin.removeAll();
		panelDessin.setLayout(new BorderLayout());
		panelDessin.setBounds(0, 41, 961, 462);
		panelDessin.add(graphique, BorderLayout.CENTER);
		panelDessin.repaint();
		panelDessin.validate();

	}

	private void viderPanel() {
		panelDessin.removeAll();
		panelDessin.repaint();
		panelDessin.validate();
	}
	private void dessinerCerculaireHF() {

		affichageTemps.setText("");
		List<String> series = new ArrayList<String>();
		series.add("Homme");
		series.add("Femme");

		List<Float> valeurs = new ArrayList<Float>();
		int nombreHomme = 0;
		int nbPatients = 0;
		Vector<Patient> patients = Patient.rechercherPatient("1 = 1");
		if(patients != null) {
			nbPatients = patients.size();
			for(Patient p: patients) {
				if(p.getSexe().equals("Homme")) {
					nombreHomme ++;
				}
			}
		}

		valeurs.add((float)nombreHomme);
		valeurs.add((float)(nbPatients - nombreHomme));

		Graphique graphique = Graphique.getCirulaire("Repartition des patients Hommes / Femmes", new Color(57, 113, 177),
				valeurs, series);

		panelDessin.removeAll();
		panelDessin.setLayout(new BorderLayout());
		panelDessin.setBounds(0, 5, 961, 503);
		panelDessin.add(graphique, BorderLayout.CENTER);
		panelDessin.repaint();
		panelDessin.validate();
	}
	private void dessinerHistoMaladies(Vector<String> semaine) {

		List<String> categories = new ArrayList<String>();
		categories.add("");
		List<String> series = new ArrayList<String>();
		List<Float> valeurs = new ArrayList<Float>();

		Vector<MaladieNonChroniqueNombre> maladies = MaladieNonChronique.getMaladieNonChronique(semaine);

		if(maladies != null) {
			for(MaladieNonChroniqueNombre m :maladies) {
				series.add(m.getMaladieNonChronique().getNom());
				valeurs.add((float) m.getNombre());
			}
		}

		Graphique graphique = Graphique.getHistorgramme("Repartition des patients selon les maladies", "Nombre de patients", "Maladies", 
				new Color(57, 113, 177), valeurs, series, categories);

		panelDessin.removeAll();
		panelDessin.setLayout(new BorderLayout());
		panelDessin.setBounds(0, 41, 961, 462);
		panelDessin.add(graphique, BorderLayout.CENTER);
		panelDessin.repaint();
		panelDessin.validate();
	}

	private Vector<String> getSemaine(String date) {
		Vector<String> semaines = new Vector<String>();
		Calendar c = Calendar.getInstance();

		c.set(Calendar.YEAR, Integer.valueOf(date.substring(6, 10)));
		c.set(Calendar.MONTH, Integer.valueOf(date.substring(3, 5)) - 1);
		c.set(Calendar.DAY_OF_MONTH, Integer.valueOf(date.substring(0, 2)));

		while(c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
			c.set(Calendar.DAY_OF_MONTH, c.get(Calendar.DAY_OF_MONTH) - 1);
		}

		affichageTemps.setText("Semaine du " + df.format(c.getTime()));
		String d;

		for(int i = 1; i <= 7; i++) {
			d = df.format(c.getTime());
			d = d.substring(0, 2).concat(d.substring(3, 5)).concat(d.substring(6, 10));
			if(d.charAt(0) == '0') {
				d = d.substring(1);
			}
			semaines.add(d);
			c.add(Calendar.DATE, 1);	
		}
		if(semaines.isEmpty()) {
			semaines = null;
		}
		return semaines;
	}
}
