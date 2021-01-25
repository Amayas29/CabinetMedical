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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JDateChooser;

import fenetresPrincipales.FenetreDocteur;
import fenetresPrincipales.FenetreSecretaire;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import patient.PatientDetaille;
import utilisateur.Docteur;
import utilisateur.Utilisateur;

public class AjouterPatient extends JFrame {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JTextField codeText;
	private JTextField prenomText;
	private JTextField nomText;
	private JTextField adresseText;
	private JTextField emailText;
	private JTextField telText;
	private JTextField inscriText;
	private final JButton ajouter = new JButton("Ajouter le patient");
	private JComboBox<String> sexeBox;
	private JDateChooser naissanceDate;

	public AjouterPatient(Utilisateur util) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if(util instanceof Docteur) {
					FenetreDocteur docFen = new FenetreDocteur(util);
					docFen.setVisible(true);
				}
				else {
					FenetreSecretaire secFen = new FenetreSecretaire(util);
					secFen.setVisible(true);
				}
			}
		});

		setTitle("Medic : Ajouter Patient");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 732, 510);
		setLocationRelativeTo(null);
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
		panelPrincipale.setBounds(0, 80, 726, 402);
		contentPane.add(panelPrincipale);

		JLabel lblCode = new JLabel("Code");
		lblCode.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCode.setForeground(new Color(32, 33, 35));
		lblCode.setBounds(48, 30, 111, 30);
		panelPrincipale.add(lblCode);

		codeText = new JTextField();
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
		Calendar c = Calendar.getInstance();
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		inscriText.setText(df.format(c.getTime()));

		panelPrincipale.add(inscriText);

		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(48, 374, 290, 7);
		panelPrincipale.add(separator_9);
		ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(vide()) {
					MessageDialog.showMessageDialog(AjouterPatient.this, "Veuillez remplir tout les formulaires ","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				if(!valideDate()) {
					MessageDialog.showMessageDialog(AjouterPatient.this, "Veuillez introduire une date correcte","Erreur",MessageDialog.MESSAGE_ERREUR);		
					return;
				}
				if(!valideCode()) {
					MessageDialog.showMessageDialog(AjouterPatient.this, "Le code doit etre numerique","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				String date = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText().trim();
				Patient patient = new Patient(Integer.valueOf(codeText.getText().trim()),
						nomText.getText().trim(), prenomText.getText().trim(), sexeBox.getSelectedItem().toString(), 
						date, adresseText.getText().trim(), telText.getText().trim(), emailText.getText().trim(), inscriText.getText());

				if(patient.existe()) {
					MessageDialog.showMessageDialog(AjouterPatient.this, "Le code existe deja","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				util.ajouterPatient(patient);
				if(util instanceof Docteur) {
					DossierPatient dossierPatient = new DossierPatient(util, PatientDetaille.getPatientDetaille(patient), AjouterPatient.this);
					dossierPatient.setVisible(true);
				}
				MessageDialog.showMessageDialog(AjouterPatient.this, "Ajout effectue","Succes",MessageDialog.MESSAGE_SUCCES);
				dispose();
			}
		});
		ajouter.setBounds(460, 350, 144, 31);
		panelPrincipale.add(ajouter);
		ajouter.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		getRootPane().setDefaultButton(ajouter);
		ajouter.setBackground(new Color(250, 239, 197));

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
	}

	private boolean vide() {
		String dateN = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText();
		return codeText.getText().trim().equals("") || nomText.getText().trim().equals("") 
				|| prenomText.getText().trim().equals("") || sexeBox.getSelectedIndex() == -1
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
	private boolean valideCode() {
		String str = codeText.getText().trim();
		for(int i = 0 ; i < str.length() ; i++) {
			int car = (char) str.charAt(i);
			if( car < 48 || car > 57) {
				return false;
			}
		}
		return true;
	}
}