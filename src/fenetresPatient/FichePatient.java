package fenetresPatient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import consultation.Consultation;
import outils.ConfirmDialog;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import utilisateur.Docteur;
import utilisateur.Secretaire;
import utilisateur.Utilisateur;

public class FichePatient extends JDialog {

	private static final long serialVersionUID = 8899534669591080050L;
	private JPanel contentPane;
	private JTextField codeText;
	private JTextField prenomText;
	private JTextField nomText;
	private JTextField adresseText;
	private JTextField emailText;
	private JTextField telText;
	private JTextField inscriText;
	private final JButton modifier = new JButton("Modifier le patient");
	private JComboBox<String> sexeBox;
	private JDateChooser naissanceDate;
	private JTextField sexeText;
	private JTextField naissanceText;
	private JSeparator sexeSepa;
	private JButton annuler;
	private JButton sauvegarder;
	private JButton supprimer;
	private static boolean C; 
	private JButton consulterHistorique;

	public FichePatient(Utilisateur util, Patient patient,JFrame mere, boolean consu) {
		FichePatient.C = consu;
		setModal(true);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(annuler.isVisible()) {
					int i = ConfirmDialog.showConfirmDialog(FichePatient.this,"Les modification ne seront pas sauvegardees. Voulez-vous quitter ?", "Attention",ConfirmDialog.OPTION_OUI_NON,ConfirmDialog.ATTENTION);
					if(i == ConfirmDialog.OPTION_OUI ) {
						dispose();
					}
					return ;
				}
				dispose();
			}
		});

		setTitle("Medic : Fiche patient");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 732, 523);
		setLocationRelativeTo(mere);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel medic = new Panel();
		medic.setLayout(null);
		medic.setBackground(new Color(48, 49, 52));
		medic.setBounds(0, 0, 726, 83);
		contentPane.add(medic);

		JLabel label = new JLabel("Un logiciel pour vous");
		label.setForeground(new Color(62, 122, 189));
		label.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label.setBounds(66, 54, 147, 20);
		medic.add(label);

		JLabel label_1 = new JLabel("M\u00E9dic");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 50));
		label_1.setBounds(24, -33, 228, 122);
		medic.add(label_1);

		JLabel label_2 = new JLabel();
		label_2.setIcon(new ImageIcon("Images/Outils/gmail.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(515, 3, 44, 28);
		medic.add(label_2);

		JLabel label_3 = new JLabel("Medic.contact@gmail.com");
		label_3.setForeground(new Color(250, 239, 197));
		label_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_3.setBounds(565, 1, 168, 30);
		medic.add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("Images/Outils/facebook.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(515, 24, 44, 28);
		medic.add(label_4);

		JLabel label_5 = new JLabel("M\u00E9dic");
		label_5.setForeground(new Color(250, 239, 197));
		label_5.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_5.setBounds(565, 22, 139, 30);
		medic.add(label_5);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon("Images/Outils/site.png"));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(515, 46, 44, 28);
		medic.add(label_6);

		JLabel label_7 = new JLabel("www.Medic.com");
		label_7.setForeground(new Color(250, 239, 197));
		label_7.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_7.setBounds(565, 44, 139, 30);
		medic.add(label_7);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(508, 0, 10, 83);
		medic.add(separator);

		Panel panelPrincipale = new Panel();
		panelPrincipale.setLayout(null);
		panelPrincipale.setForeground(Color.BLACK);
		panelPrincipale.setBackground(new Color(57, 113, 177));
		panelPrincipale.setBounds(0, 80, 726, 415);
		contentPane.add(panelPrincipale);

		JLabel lblCode = new JLabel("Code");
		lblCode.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCode.setForeground(new Color(32, 33, 35));
		lblCode.setBounds(48, 30, 111, 30);
		panelPrincipale.add(lblCode);

		codeText = new JTextField();
		codeText.setEditable(false);
		codeText.setForeground(Color.WHITE);
		codeText.setBackground(new Color(57, 113, 177));
		codeText.setBorder(null);
		codeText.setBounds(48, 53, 300, 30);
		codeText.setDocument(new JTextFieldLimit(9));
		panelPrincipale.add(codeText);
		codeText.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(48, 83, 290, 7);
		panelPrincipale.add(separator_1);

		JLabel lblSexe = new JLabel("Sexe");
		lblSexe.setForeground(new Color(32, 33, 35));
		lblSexe.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblSexe.setBounds(386, 30, 111, 30);
		panelPrincipale.add(lblSexe);

		JLabel lblPrenom = new JLabel("Prenom");
		lblPrenom.setForeground(new Color(32, 33, 35));
		lblPrenom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPrenom.setBounds(386, 104, 111, 30);
		panelPrincipale.add(lblPrenom);

		prenomText = new JTextField();
		prenomText.setForeground(Color.WHITE);
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setDocument(new JTextFieldLimit(25));
		prenomText.setBackground(new Color(57, 113, 177));
		prenomText.setBounds(386, 127, 300, 30);
		panelPrincipale.add(prenomText);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(386, 157, 290, 7);
		panelPrincipale.add(separator_3);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setForeground(new Color(32, 33, 35));
		lblNom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNom.setBounds(48, 104, 111, 30);
		panelPrincipale.add(lblNom);

		nomText = new JTextField();
		nomText.setForeground(Color.WHITE);
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setDocument(new JTextFieldLimit(25));
		nomText.setBackground(new Color(57, 113, 177));
		nomText.setBounds(48, 127, 300, 30);
		panelPrincipale.add(nomText);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(48, 157, 290, 7);
		panelPrincipale.add(separator_4);

		JLabel lblDateDeNaissance = new JLabel("Date de Naissance");
		lblDateDeNaissance.setForeground(new Color(32, 33, 35));
		lblDateDeNaissance.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblDateDeNaissance.setBounds(386, 178, 160, 30);
		panelPrincipale.add(lblDateDeNaissance);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(386, 231, 290, 7);
		panelPrincipale.add(separator_5);

		JLabel lblAdresse = new JLabel("Adresse");
		lblAdresse.setForeground(new Color(32, 33, 35));
		lblAdresse.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblAdresse.setBounds(48, 178, 111, 30);
		panelPrincipale.add(lblAdresse);

		adresseText = new JTextField();
		adresseText.setForeground(Color.WHITE);
		adresseText.setColumns(10);
		adresseText.setBorder(null);
		adresseText.setDocument(new JTextFieldLimit(30));
		adresseText.setBackground(new Color(57, 113, 177));
		adresseText.setBounds(48, 201, 300, 30);
		panelPrincipale.add(adresseText);

		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(48, 231, 290, 7);
		panelPrincipale.add(separator_6);

		JLabel lblEmail = new JLabel("Email");
		lblEmail.setForeground(new Color(32, 33, 35));
		lblEmail.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblEmail.setBounds(386, 255, 111, 30);
		panelPrincipale.add(lblEmail);

		emailText = new JTextField();
		emailText.setForeground(Color.WHITE);
		emailText.setColumns(10);
		emailText.setBorder(null);
		emailText.setDocument(new JTextFieldLimit(30));
		emailText.setBackground(new Color(57, 113, 177));
		emailText.setBounds(386, 278, 300, 30);
		panelPrincipale.add(emailText);

		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(386, 308, 290, 7);
		panelPrincipale.add(separator_7);

		JLabel lblTelephone = new JLabel("Telephone");
		lblTelephone.setForeground(new Color(32, 33, 35));
		lblTelephone.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblTelephone.setBounds(48, 255, 111, 30);
		panelPrincipale.add(lblTelephone);

		telText = new JTextField();
		telText.setForeground(Color.WHITE);
		telText.setColumns(10);
		telText.setBorder(null);
		telText.setDocument(new JTextFieldLimit(15));
		telText.setBackground(new Color(57, 113, 177));
		telText.setBounds(48, 278, 300, 30);
		panelPrincipale.add(telText);

		JSeparator separator_8 = new JSeparator();
		separator_8.setBounds(48, 308, 290, 7);
		panelPrincipale.add(separator_8);

		JLabel lblDateDinscription = new JLabel("Date d'inscription ");
		lblDateDinscription.setForeground(new Color(32, 33, 35));
		lblDateDinscription.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblDateDinscription.setBounds(48, 321, 111, 30);
		panelPrincipale.add(lblDateDinscription);

		inscriText = new JTextField();
		inscriText.setEditable(false);
		inscriText.setForeground(Color.WHITE);
		inscriText.setColumns(10);
		inscriText.setBorder(null);
		inscriText.setBackground(new Color(57, 113, 177));
		inscriText.setBounds(48, 344, 300, 30);

		panelPrincipale.add(inscriText);

		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(48, 374, 290, 7);
		panelPrincipale.add(separator_9);
		modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editable(true,util);
			}
		});

		modifier.setBounds(386, 374, 156, 31);
		panelPrincipale.add(modifier);
		modifier.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		getRootPane().setDefaultButton(modifier);
		modifier.setBackground(new Color(250, 239, 197));

		sexeBox = new JComboBox<String>();
		sexeBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Homme", "Femme"}));
		sexeBox.setSelectedIndex(-1);
		sexeBox.setBackground(new Color(250, 239, 197));
		sexeBox.setBounds(386, 60, 300, 30);
		panelPrincipale.add(sexeBox);

		naissanceDate = new JDateChooser();
		naissanceDate.setForeground(Color.BLACK);
		naissanceDate.setBorder(null);
		naissanceDate.setDateFormatString("dd/MM/yyyy");
		((Component) naissanceDate.getDateEditor()).setBackground(new Color(57, 113, 177));
		((JComponent) naissanceDate.getDateEditor()).setBorder(null);
		((Component) naissanceDate.getDateEditor()).addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				((Component) naissanceDate.getDateEditor()).setForeground(Color.black);
			}
		});
		naissanceDate.setBackground(new Color(57, 113, 177));
		naissanceDate.setBounds(386, 201, 300, 30);
		panelPrincipale.add(naissanceDate);

		sexeText = new JTextField();
		sexeText.setEditable(false);
		sexeText.setForeground(Color.WHITE);
		sexeText.setColumns(10);
		sexeText.setBorder(null);
		sexeText.setBackground(new Color(57, 113, 177));
		sexeText.setBounds(386, 53, 300, 30);
		panelPrincipale.add(sexeText);

		sexeSepa = new JSeparator();
		sexeSepa.setBounds(386, 83, 290, 7);
		panelPrincipale.add(sexeSepa);

		naissanceText = new JTextField();
		naissanceText.setForeground(Color.WHITE);
		naissanceText.setColumns(10);
		naissanceText.setBorder(null);
		naissanceText.setEditable(false);
		naissanceText.setBackground(new Color(57, 113, 177));
		naissanceText.setBounds(386, 201, 300, 30);
		panelPrincipale.add(naissanceText);

		sauvegarder = new JButton("Sauvegarder ");
		sauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(vide()) {
					MessageDialog.showMessageDialog(FichePatient.this, "Veuillez remplir tout les formulaires ","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				if(!valideDate()) {
					MessageDialog.showMessageDialog(FichePatient.this, "Veuillez introduire une date correcte","Erreur",MessageDialog.MESSAGE_ERREUR);		
					return;
				}
				String dateN = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText();
				Patient nvPatient = new Patient(patient.getCode(), nomText.getText().trim(), prenomText.getText().trim(),
						sexeBox.getSelectedItem().toString(), dateN.trim(), adresseText.getText().trim(),
						telText.getText().trim(), emailText.getText().trim(), patient.getDateInscription());

				util.modifierPatient(patient, nvPatient);
				MessageDialog.showMessageDialog(FichePatient.this, "Modification effectuee", "Succes", MessageDialog.MESSAGE_SUCCES);
				editable(false,util);
				chargerPatient(patient);
			}
		});
		sauvegarder.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		sauvegarder.setBackground(new Color(250, 239, 197));
		sauvegarder.setBounds(386, 374, 156, 31);
		panelPrincipale.add(sauvegarder);

		annuler = new JButton("Annuler");
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				editable(false,util);
				chargerPatient(patient);
			}
		});
		annuler.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		annuler.setBounds(552, 374, 156, 31);
		annuler.setBackground(new Color(250, 239, 197));
		panelPrincipale.add(annuler);

		supprimer = new JButton("Supprimer");
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = ConfirmDialog.showConfirmDialog(FichePatient.this, "Etes-vous sur de vouloir supprimer le patient ?", "Attention",
						ConfirmDialog.OPTION_OUI_NON,ConfirmDialog.ATTENTION);

				if(i == ConfirmDialog.OPTION_OUI) {
					((Docteur) util).supprimerPatient(patient);
					MessageDialog.showMessageDialog(FichePatient.this, "Suppresion effectuee", "Succes",
							MessageDialog.MESSAGE_SUCCES);
					dispose();
				}
			}
		});
		supprimer.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		supprimer.setBackground(new Color(250, 239, 197));
		supprimer.setBounds(552, 374, 156, 31);
		supprimer.setVisible(peutSupprimer(patient,util) && !consu);
		supprimer.setEnabled(peutSupprimer(patient,util));

		panelPrincipale.add(supprimer);

		JButton consulter = new JButton("Consulter patient");
		consulter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				mere.dispose();
				Consultation consu = new Consultation(patient.getCode(), util.getNomPrenom(), "");
				ConsulterPatient consulPat = new ConsulterPatient(util, patient, consu);
				consulPat.setVisible(true);
			}
		});
		consulter.setBounds(386, 333, 156, 30);
		consulter.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		consulter.setBackground(new Color(250, 239, 197));
		consulter.setVisible(consu);
		panelPrincipale.add(consulter);

		consulterHistorique = new JButton("Historique");
		consulterHistorique.setVisible(util instanceof Docteur);
		consulterHistorique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				mere.setVisible(false);
				RechercherConsultation rech = new RechercherConsultation(util, patient, mere);
				rech.setVisible(true);
			}
		});
		consulterHistorique.setBounds(552, 333, 156, 30);
		consulterHistorique.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		consulterHistorique.setBackground(new Color(250, 239, 197));
		panelPrincipale.add(consulterHistorique);
		
		editable(false,util);
		chargerPatient(patient);
	}

	private void chargerPatient(Patient patient) {
		codeText.setText(String.valueOf(patient.getCode()));
		nomText.setText(patient.getNom());
		prenomText.setText(patient.getPrenom());
		sexeText.setText(patient.getSexe());
		sexeBox.setSelectedItem(patient.getSexe());
		naissanceText.setText(patient.getDateNaissance());
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH,Integer.valueOf(patient.getDateNaissance().substring(3,5)) - 1);
		c.set(Calendar.DAY_OF_MONTH,Integer.valueOf(patient.getDateNaissance().substring(0,2)));
		c.set(Calendar.YEAR,Integer.valueOf(patient.getDateNaissance().substring(6,10)));
		naissanceDate.setDate(c.getTime());
		adresseText.setText(patient.getAdresse());
		telText.setText(patient.getTelephone());
		emailText.setText(patient.getEmail());
		inscriText.setText(patient.getDateInscription());

	}
	private void editable(boolean etat,Utilisateur util) {
		nomText.setEditable(etat);
		prenomText.setEditable(etat);
		sexeText.setVisible(!etat);
		sexeSepa.setVisible(!etat);
		sexeBox.setVisible(etat);
		naissanceText.setVisible(!etat);
		naissanceDate.setVisible(etat);
		adresseText.setEditable(etat);
		telText.setEditable(etat);
		emailText.setEditable(etat);
		annuler.setVisible(etat);
		sauvegarder.setVisible(etat);
		modifier.setVisible(!etat && !FichePatient.C);
		consulterHistorique.setVisible(util instanceof Docteur && !etat);
	}
	private boolean vide() {
		String dateN = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText();
		return  nomText.getText().trim().equals("") || prenomText.getText().trim().equals("") || sexeBox.getSelectedIndex() == -1
				|| dateN.trim().equals("") || adresseText.getText().trim().equals("") || telText.getText().trim().equals("") 
				|| emailText.getText().trim().equals("");
	}

	@SuppressWarnings("deprecation")
	private boolean valideDate() {
		try {
			String date = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText();
			Calendar c = Calendar.getInstance();
			if(!date.equals("")) {
				c.setTime(new Date(date));
			}
		} catch (java.lang.IllegalArgumentException e) {
			return false;
		}

		return true;
	}
	private boolean peutSupprimer(Patient patient, Utilisateur util){
		if(util instanceof Secretaire) {
			return false;
		}
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
			Date dateAuj;
			dateAuj = sdf.parse(sdf.format(Calendar.getInstance().getTime()));
			Date drConsu = sdf.parse(patient.getDrConsu());
			long diffMilli = Math.abs(dateAuj.getTime() - drConsu.getTime());
			long diff = TimeUnit.DAYS.convert(diffMilli, TimeUnit.MILLISECONDS);
			return diff >= 1825;
		} catch (ParseException e) {
			return false;
		}
	}
}