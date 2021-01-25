package fenetresTemps;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import com.toedter.calendar.JDayChooser;

import fenetresPatient.FichePatient;
import fenetresPatient.RechercherPatient;
import fenetresPrincipales.FenetreDocteur;
import fenetresPrincipales.FenetreSecretaire;
import gestionTemps.Rdv;
import patient.Patient;
import utilisateur.Docteur;
import utilisateur.Secretaire;
import utilisateur.Utilisateur;

public class GestionRdv extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] etat = {"Reserve", "Libre", "Passe", "-------"};
	private JPanel panel_jour;
	private JScrollPane scrollPane_jour;
	private int nombreSeance;
	private JDayChooser dayChooser;
	private int mois, annee;
	private HashMap<String, String> jours = new HashMap<String, String>();
	private JLabel affichage;
	private final String[] NOM_MOIS = {"Janvier", "Fevrier", "Mars", "Avril", "Mai", "Juin", "Juillet",
			"Aoet", "Septembre", "Octobre", "Novembre", "Decembre"};

	private boolean rech = false;

	public GestionRdv(String titre, Utilisateur util, JFrame mere, boolean agenda) {

		setTitle("Medic : " + titre);
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("MM");
		mois = Integer.valueOf(df.format(c.getTime()));
		mois -= 1;
		df = new SimpleDateFormat("yyyy");
		annee = Integer.valueOf(df.format(c.getTime()));

		setResizable(false);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 887, 642);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(57, 113, 177));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setLayout(null);
		panel.setBackground(new Color(48, 49, 52));
		panel.setBounds(0, 0, 881, 83);
		contentPane.add(panel);

		JLabel label = new JLabel("Un logiciel pour vous");
		label.setForeground(new Color(62, 122, 189));
		label.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label.setBounds(66, 54, 147, 20);
		panel.add(label);

		JLabel label_1 = new JLabel("M\u00E9dic");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 50));
		label_1.setBounds(24, -33, 228, 122);
		panel.add(label_1);

		JLabel label_2 = new JLabel();
		label_2.setIcon(new ImageIcon("Images/Outils/gmail.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(663, 3, 44, 28);
		panel.add(label_2);

		JLabel label_3 = new JLabel("Medic.contact@gmail.com");
		label_3.setForeground(new Color(250, 239, 197));
		label_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_3.setBounds(713, 1, 168, 30);
		panel.add(label_3);

		JLabel label_4 = new JLabel();
		label_4.setIcon(new ImageIcon("Images/Outils/facebook.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(663, 24, 44, 28);
		panel.add(label_4);

		JLabel label_5 = new JLabel("M\u00E9dic");
		label_5.setForeground(new Color(250, 239, 197));
		label_5.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_5.setBounds(713, 22, 139, 30);
		panel.add(label_5);

		JLabel label_6 = new JLabel();
		label_6.setIcon(new ImageIcon("Images/Outils/site.png"));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(663, 46, 44, 28);
		panel.add(label_6);

		JLabel label_7 = new JLabel("www.Medic.com");
		label_7.setForeground(new Color(250, 239, 197));
		label_7.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_7.setBounds(713, 44, 139, 30);
		panel.add(label_7);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(656, 0, 10, 83);
		panel.add(separator);

		int depart = 7;
		int fin = 18; // Exclu
		nombreSeance = (fin - depart) * 2;
		if( nombreSeance % 2 != 0) {
			nombreSeance += 1;
		}
		String[] horraires = new String[nombreSeance];
		String minute = "00";
		for(int i = 0; i < nombreSeance; i++) {
			String heure = String.valueOf(depart);
			horraires[i] = heure + ":" + minute;
			if(minute.equals("00")) {
				minute = "30";
			}
			else {
				depart++;
				minute = "00";
			}
		}

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				int jr = dayChooser.getDay();
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH,jr);
				c.set(Calendar.MONTH,mois );
				c.set(Calendar.YEAR,annee );
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				creeRdvs(horraires,Rdv.getRdv(df.format(c.getTime())),util);
			}
			@Override
			public void windowActivated(WindowEvent arg0) {
				int jr = dayChooser.getDay();
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH,jr);
				c.set(Calendar.MONTH,mois );
				c.set(Calendar.YEAR,annee );
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				creeRdvs(horraires,Rdv.getRdv(df.format(c.getTime())),util);
				rech = false;
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(util instanceof Docteur) {
					FenetreDocteur fenDoc = new FenetreDocteur(util);
					fenDoc.setVisible(true);
				}
				if(util instanceof Secretaire && !rech) {
					FenetreSecretaire fenSec = new FenetreSecretaire(util);
					fenSec.setVisible(true);
				}
			}
		});

		JPanel principales = new JPanel();
		principales.setBounds(525, 100, 300, 500);
		principales.setBackground(new Color(57, 113, 177));
		principales.setBorder(new EmptyBorder(5, 5, 5, 5));
		principales.setLayout(new GridLayout(1, 1, 10, 10));
		contentPane.add(principales);

		JButton moisProch = new JButton("Mois prochain");
		moisProch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mois += 1;
				if(mois > 11) {
					mois = 0;
					annee += 1;
					dayChooser.setYear(annee);
				}
				dayChooser.setMonth(mois);
				int jr = dayChooser.getDay();
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH,jr);
				c.set(Calendar.MONTH,mois );
				c.set(Calendar.YEAR,annee );
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				creeRdvs(horraires,Rdv.getRdv(df.format(c.getTime())),util);
			}
		});
		moisProch.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		moisProch.setBackground(new Color(250, 239, 197));
		moisProch.setBounds(305, 151, 170, 31);
		contentPane.add(moisProch);

		JButton moisPred = new JButton("Mois pr\u00E9c\u00E9dent");
		moisPred.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mois -= 1;
				if(mois < 0) {
					mois = 11;
					annee -= 1;
					dayChooser.setYear(annee);
				}
				dayChooser.setMonth(mois);
				int jr = dayChooser.getDay();
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH,jr);
				c.set(Calendar.MONTH,mois );
				c.set(Calendar.YEAR,annee );
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				creeRdvs(horraires,Rdv.getRdv(df.format(c.getTime())),util);
			}
		});
		moisPred.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		moisPred.setBackground(new Color(250, 239, 197));
		moisPred.setBounds(56, 151, 170, 31);
		contentPane.add(moisPred);

		dayChooser = new JDayChooser();
		dayChooser.setBackground(new Color(57, 113, 177));
		dayChooser.getDayPanel().setBackground(new Color(57, 113, 177));
		dayChooser.addPropertyChangeListener(new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				int jr = dayChooser.getDay();
				Calendar c = Calendar.getInstance();
				c.set(Calendar.DAY_OF_MONTH,jr);
				c.set(Calendar.MONTH,mois );
				c.set(Calendar.YEAR,annee );
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
				creeRdvs(horraires,Rdv.getRdv(df.format(c.getTime())),util);
			}
		});

		dayChooser.setBounds(56, 193, 421, 286);
		contentPane.add(dayChooser);

		String[] j = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"}; 
		for(int i = 0; i < 7; i++) {
			jours.put(DayOfWeek.of(i+1).toString(), j[i]);
		}

		scrollPane_jour = new JScrollPane();
		scrollPane_jour.setBackground(new Color(57, 113, 177));
		principales.add(scrollPane_jour);

		panel_jour = new JPanel();
		panel_jour.setLayout(new GridLayout(nombreSeance, 2, -100, 10));
		panel_jour.setBackground(new Color(57, 113, 177));
		panel_jour.setBorder(new EmptyBorder(15, 5, 5, 5));
		scrollPane_jour.setViewportView(panel_jour);

		affichage = new JLabel();
		affichage.setFont(new Font("Century Gothic", Font.BOLD, 16));
		affichage.setHorizontalAlignment(SwingConstants.CENTER);
		affichage.setBounds(181, 110, 170, 30);
		contentPane.add(affichage);

		JButton consuSansRdv = new JButton("Consulter sans Rdv ");
		consuSansRdv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				RechercherPatient rechPa = new RechercherPatient(util, true, null);
				rechPa.setVisible(true);
			}
		});
		consuSansRdv.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		consuSansRdv.setBackground(new Color(250, 239, 197));
		consuSansRdv.setBounds(345, 550, 170, 50);
		consuSansRdv.setVisible(!agenda && util instanceof Docteur);
		contentPane.add(consuSansRdv);
	}

	private void creeRdvs(String[] horraires , Vector<Rdv> rdvs,Utilisateur util) {
		panel_jour.removeAll();
		int jr = dayChooser.getDay(); 
		Calendar now  = Calendar.getInstance();
		Calendar day= Calendar.getInstance();
		day.set(Calendar.DAY_OF_MONTH,jr  );
		day.set(Calendar.MONTH,mois );
		day.set(Calendar.YEAR,annee );
		boolean inf = day.before(now);
		for(int i = 0; i < nombreSeance; i++) {
			JLabel label_i = new JLabel("  " + horraires[i]);
			label_i.setForeground(new Color(48, 49, 52));
			label_i.setFont(new Font("Century Gothic", Font.ITALIC, 15));
			label_i.setHorizontalAlignment(SwingConstants.LEFT);
			panel_jour.add(label_i);	

			JButton button_i = new JButton(etat[1]);

			if(rdvs != null) {
				for(int j = 0 ; j < rdvs.size() ; j++ ) {
					if(rdvs.get(j).getNumero()  == i+1) {
						button_i.setText(etat[0]);
						break;
					}
				}}

			if(inf) {
				button_i.setEnabled(button_i.getText().equals(etat[0]));
				if(button_i.isEnabled()) {
					button_i.setText(etat[2]);
				}
				else {
					button_i.setText(etat[3]);
				}
			}

			button_i.setFont(new Font("Century Gothic", Font.PLAIN, 12));
			button_i.setPreferredSize(new Dimension(40, 40));
			button_i.setToolTipText(String.valueOf(i+1));
			if(button_i.getText().equals("Reserve")) {
				button_i.setBackground(Color.ORANGE);
			}
			else if(button_i.getText().equals("Libre")) {
				button_i.setBackground(new Color(29, 165, 29));
			}
			else if(button_i.getText().equals("Passe")) {
				button_i.setBackground(new Color(250, 228, 151));
			}
			button_i.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {

					if(button_i.getText().equals("Reserve")) {

						if(util instanceof Secretaire) {
							int numero = Integer.valueOf(button_i.getToolTipText());
							int jr = dayChooser.getDay();
							Calendar c = Calendar.getInstance();
							c.set(Calendar.DAY_OF_MONTH,jr);
							c.set(Calendar.MONTH,mois );
							c.set(Calendar.YEAR,annee);
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
							String date = df.format(c.getTime());
							Rdv rdv = Rdv.getRdv(date, numero);
							AnnulerRdv annuler = new AnnulerRdv(GestionRdv.this, util, rdv);
							annuler.setVisible(true);}
						else {
							int numero = Integer.valueOf(button_i.getToolTipText());
							int jr = dayChooser.getDay();
							Calendar c = Calendar.getInstance();
							c.set(Calendar.DAY_OF_MONTH,jr);
							c.set(Calendar.MONTH,mois );
							c.set(Calendar.YEAR,annee);
							DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
							String date = df.format(c.getTime());
							Rdv rdv = Rdv.getRdv(date, numero);
							FichePatient fch = new FichePatient( util,Patient.getPatient(rdv.getCodePatient()), GestionRdv.this, true);
							fch.setVisible(true);
						}
					} 
					else if(button_i.getText().equals("Libre") && util instanceof Secretaire) {
						dispose();
						rech = true;
						Rdv rdv = new Rdv(-1, day,Integer.valueOf(button_i.getToolTipText()));
						RechercherPatient r = new RechercherPatient(util,false,rdv);
						r.setVisible(true);
					}
					else if(button_i.getText().equals("Passe")) {

						int numero = Integer.valueOf(button_i.getToolTipText());
						int jr = dayChooser.getDay();
						Calendar c = Calendar.getInstance();
						c.set(Calendar.DAY_OF_MONTH,jr);
						c.set(Calendar.MONTH,mois );
						c.set(Calendar.YEAR,annee);
						DateFormat df = new SimpleDateFormat("dd/MM/yyyy"); 
						String date = df.format(c.getTime());
						Rdv rdv = Rdv.getRdv(date, numero);
						FichePatient fch = new FichePatient( util,Patient.getPatient(rdv.getCodePatient()), GestionRdv.this, false);
						fch.setVisible(true);
					}

				}
			});
			panel_jour.add(button_i);
		}

		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH,jr - 1 );
		c.set(Calendar.MONTH,mois );
		c.set(Calendar.YEAR,annee );
		jr = c.get(Calendar.DAY_OF_WEEK);
		String jour = "    " + jours.get(DayOfWeek.of(jr).toString()) + " " + dayChooser.getDay() + " " + NOM_MOIS[mois] + " " + annee + "    ";
		scrollPane_jour.setBorder(new TitledBorder(null, jour, TitledBorder.CENTER, TitledBorder.TOP, null, new Color(48, 49, 52)));
		affichage.setText("<html><u>" + NOM_MOIS[mois] + " " + annee + "</u></html>");

		panel_jour.repaint();
		panel_jour.validate();
	}
}
