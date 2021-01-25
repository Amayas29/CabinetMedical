package fenetresPatient;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import com.toedter.calendar.JDateChooser;

import consultation.Consultation;
import fenetresPrincipales.FenetreDocteur;
import fenetresPrincipales.FenetreSecretaire;
import fenetresTemps.GestionRdv;
import gestionTemps.Rdv;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import patient.PatientDetaille;
import utilisateur.Docteur;
import utilisateur.Utilisateur;

public class RechercherPatient extends JFrame {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JTextField codeText;
	private JTextField prenomText;
	private JTextField nomText;
	private final JButton afficherBtn = new JButton("Afficher tout");
	private JTable table;
	private JScrollPane scrollPane;
	private JButton fichePatientBtn;
	private JComboBox<String> sexeBox;
	private JDateChooser naissanceDate;
	private JDateChooser inscriDate;
	private JLabel aucun; 
	private JButton dossierBtn;
	private JButton consulter;
	private JButton ajouterRdv ;
	private static boolean cons; 

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					RechercherPatient frame = new RechercherPatient(new Docteur("", ""), false, null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public RechercherPatient(Utilisateur util,boolean consultation, Rdv rdv) {
		RechercherPatient.cons = false;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				if(!RechercherPatient.cons && rdv == null) {
					if(util instanceof Docteur) {
						FenetreDocteur docFen = new FenetreDocteur(util);
						docFen.setVisible(true);
					}
					else {
						FenetreSecretaire secFen = new FenetreSecretaire(util);
						secFen.setVisible(true);
					}
				}
				if(rdv != null) {
					GestionRdv grdv = new GestionRdv("Gestion Rdv", util, null, false);
					grdv.setVisible(true);
				}
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				miseAJourTable(Patient.rechercherPatient("1 = 1"),util,consultation);
			}
		});

		setTitle("Medic : Rechercher un patient");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 700);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel medic = new Panel();
		medic.setLayout(null);
		medic.setBackground(new Color(48, 49, 52));
		medic.setBounds(0, 0, 894, 83);
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
		panelPrincipale.setBounds(0, 80, 894, 593);
		contentPane.add(panelPrincipale);

		naissanceDate = new JDateChooser();
		naissanceDate.setForeground(Color.BLACK);
		naissanceDate.setBorder(null);
		naissanceDate.setDateFormatString("dd/MM/yyyy");
		naissanceDate.setBackground(new Color(57, 113, 177));
		((Component) naissanceDate.getDateEditor()).setBackground(new Color(57, 113, 177));
		((JComponent) naissanceDate.getDateEditor()).setBorder(null);
		((Component) naissanceDate.getDateEditor()).addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				((Component) naissanceDate.getDateEditor()).setForeground(Color.black);
			}
		});

		aucun = new JLabel("Aucun patient n'a ete trouve");
		aucun.setVisible(false);

		dossierBtn = new JButton();
		dossierBtn.setVisible(false);
		dossierBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String code = table.getModel().getValueAt(ligne, 0).toString();
					Patient p = Patient.getPatient(Integer.valueOf(code));
					PatientDetaille patientDetaille = PatientDetaille.getPatientDetaille(p);
					DossierPatient dossier = new DossierPatient(util, patientDetaille, RechercherPatient.this);
					dossier.setVisible(true);
				}
			}
		});

		consulter = new JButton("Consulter patient");
		consulter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String code = table.getModel().getValueAt(ligne, 0).toString();
					RechercherPatient.cons = true;
					dispose();
					Consultation consu = new Consultation(Integer.valueOf(code), util.getNomPrenom(), "");
					ConsulterPatient consuPat = new ConsulterPatient(util, Patient.getPatient(Integer.valueOf(code)), consu);
					consuPat.setVisible(true);
				}
			}
		});
		consulter.setEnabled(false);
		consulter.setVisible(consultation);
		consulter.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		consulter.setBackground(new Color(250, 239, 197));
		consulter.setBounds(727, 224, 144, 57);
		panelPrincipale.add(consulter);
		dossierBtn.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		dossierBtn.setBackground(new Color(250, 239, 197));
		dossierBtn.setBounds(570, 250, 144, 31);
		panelPrincipale.add(dossierBtn);

		aucun.setHorizontalAlignment(SwingConstants.CENTER);
		aucun.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		aucun.setBounds(188, 345, 518, 30);
		panelPrincipale.add(aucun);
		naissanceDate.setBounds(386, 208, 300, 30);
		panelPrincipale.add(naissanceDate);

		JLabel lblCode = new JLabel("Code");
		lblCode.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCode.setForeground(new Color(32, 33, 35));
		lblCode.setBounds(48, 30, 111, 30);
		panelPrincipale.add(lblCode);

		codeText = new JTextField();
		codeText.setForeground(Color.WHITE);
		codeText.setBackground(new Color(57, 113, 177));
		codeText.setBorder(null);
		codeText.setDocument(new JTextFieldLimit(9));
		codeText.setBounds(48, 53, 300, 30);
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
		prenomText.setDocument(new JTextFieldLimit(25));
		prenomText.setForeground(Color.WHITE);
		prenomText.setColumns(10);
		prenomText.setBorder(null);
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
		nomText.setDocument(new JTextFieldLimit(25));
		nomText.setForeground(Color.WHITE);
		nomText.setColumns(10);
		nomText.setBorder(null);
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

		JLabel lblInscri = new JLabel("Date d'inscription");
		lblInscri.setForeground(new Color(32, 33, 35));
		lblInscri.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblInscri.setBounds(48, 178, 132, 30);
		panelPrincipale.add(lblInscri);
		afficherBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				miseAJourTable(Patient.rechercherPatient("1 = 1"),util,consultation);
			}
		});
		afficherBtn.setBounds(727, 95, 144, 31);
		panelPrincipale.add(afficherBtn);
		afficherBtn.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		afficherBtn.setBackground(new Color(250, 239, 197));

		JButton btnRechercher = new JButton("Rechercher");
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vide()) {
					MessageDialog.showMessageDialog(RechercherPatient.this, "Veuillez remplir au moins un formulaire ","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				if(!valideDate()) {
					MessageDialog.showMessageDialog(RechercherPatient.this, "Veuillez introduire une date correcte","Erreur",MessageDialog.MESSAGE_ERREUR);
					viderTout();
					return;
				}
				if(!valideCode()) {
					MessageDialog.showMessageDialog(RechercherPatient.this, "Le code doit etre numerique","Erreur",MessageDialog.MESSAGE_ERREUR);
					viderTout();
					return;
				}
				miseAJourTable(Patient.rechercherPatient(getRecherche()),util,consultation);
			}
		});
		btnRechercher.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		getRootPane().setDefaultButton(btnRechercher);
		btnRechercher.setBackground(new Color(250, 239, 197));
		btnRechercher.setBounds(727, 53, 144, 31);
		panelPrincipale.add(btnRechercher);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(22, 286, 849, 288);
		scrollPane.setBackground(new Color(57, 113, 177));
		scrollPane.setBorder(null);
		panelPrincipale.add(scrollPane);

		fichePatientBtn = new JButton("Fiche du patient");
		fichePatientBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String code = table.getModel().getValueAt(ligne, 0).toString();
					FichePatient fiche = new FichePatient(util, Patient.getPatient(Integer.valueOf(code)),RechercherPatient.this, false);
					fiche.setVisible(true);
				}
			}
		});
		fichePatientBtn.setEnabled(false);
		fichePatientBtn.setVisible(!consultation);
		fichePatientBtn.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		fichePatientBtn.setBackground(new Color(250, 239, 197));
		fichePatientBtn.setBounds(727, 250, 144, 31);
		panelPrincipale.add(fichePatientBtn);

		sexeBox = new JComboBox<String>();
		sexeBox.setModel(new DefaultComboBoxModel<String>(new String[] {"Homme", "Femme"}));
		sexeBox.setSelectedIndex(-1);
		sexeBox.setBackground(new Color(250, 239, 197));
		sexeBox.setBounds(386, 60, 300, 30);
		panelPrincipale.add(sexeBox);

		inscriDate = new JDateChooser();
		((Component) inscriDate.getDateEditor()).addMouseListener(new MouseAdapter() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				((Component) inscriDate.getDateEditor()).setForeground(Color.black);
			}
		});
		inscriDate.setDateFormatString("dd/MM/yyyy");
		inscriDate.setBorder(null);
		inscriDate.setBackground(new Color(57, 113, 177));
		((Component) inscriDate.getDateEditor()).setBackground(new Color(57, 113, 177));
		((JComponent) inscriDate.getDateEditor()).setBorder(null);

		inscriDate.setBounds(48, 208, 300, 30);	
		panelPrincipale.add(inscriDate);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(48, 239, 290, 7);
		panelPrincipale.add(separator_2);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(386, 239, 290, 7);
		panelPrincipale.add(separator_5);

		ajouterRdv = new JButton("Ajouter Rdv");
		ajouterRdv.setEnabled(false);
		ajouterRdv.setVisible(rdv != null);
		ajouterRdv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String code = table.getModel().getValueAt(ligne, 0).toString();
					rdv.setCodePatient(Integer.valueOf(code));
					rdv.stocker();
					dispose();
				}
			}
		});
		ajouterRdv.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		ajouterRdv.setBackground(new Color(250, 239, 197));
		ajouterRdv.setBounds(570, 250, 144, 31);
		panelPrincipale.add(ajouterRdv);

		miseAJourTable(Patient.rechercherPatient("1 = 1"),util,consultation);
	}
	private boolean vide() {
		String dateN = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText();
		String dateI = ((JTextField)inscriDate.getDateEditor().getUiComponent()).getText();
		return codeText.getText().trim().equals("") && nomText.getText().trim().equals("") 
				&& prenomText.getText().trim().equals("") && sexeBox.getSelectedIndex() == -1
				&& dateN.trim().equals("") && dateI.trim().equals("");
	}

	@SuppressWarnings("deprecation")
	private boolean valideDate() {
		try {
			String date = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText();
			Calendar c = Calendar.getInstance();
			if(!date.equals("")) {
				c.setTime(new Date(date));
			}
			date = ((JTextField)inscriDate.getDateEditor().getUiComponent()).getText();
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

	public void miseAJourTable(Vector<Patient> donnes,Utilisateur util,Boolean consultation) {
		viderTout();
		Vector<String> nomColonne = new Vector<String>(); 
		nomColonne.add("Code");
		nomColonne.add("Nom");
		nomColonne.add("Prenom");
		nomColonne.add("Date de naissance");
		nomColonne.add("Sexe");
		nomColonne.add("Date d'inscription");
		Vector<Vector<String>> v = new Vector<Vector<String>>();
		if(donnes != null) {
			for(Patient p : donnes) {
				Vector<String> d = new Vector<String>();
				d.addElement(String.valueOf(p.getCode()));
				d.addElement(p.getNom());
				d.addElement(p.getPrenom());
				d.addElement(p.getDateNaissance());
				d.addElement(p.getSexe());
				d.addElement(p.getDateInscription());
				v.addElement(d);
			}
		}

		aucun.setVisible(donnes == null);

		table = new JTable(v,nomColonne) {
			private static final long serialVersionUID = -6481828476820530197L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(table.getSelectedRow() != -1) {
					fichePatientBtn.setEnabled(true);
					dossierBtn.setVisible(util instanceof Docteur && !consultation);
					consulter.setEnabled(true);
					int ligne = table.getSelectedRow();
					String code = table.getModel().getValueAt(ligne, 0).toString();
					if(Patient.getPatient(Integer.valueOf(code)).isDossierAjoute()) {
						dossierBtn.setText("Dossier medical");
					}
					else {
						dossierBtn.setText("Ajouter les details");
					}
				}
			}
		});
		table.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN, 12));
		table.getTableHeader().setOpaque(false);
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(new Color(48, 49, 52));
		headerRenderer.setForeground(new Color(250, 239, 197));

		for (int i = 0; i < table.getModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
		}
		table.setRowHeight(25);
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		table.setBorder(null);
		table.setBackground(new Color(57, 113, 177));
		table.setSelectionBackground(new Color(250, 239, 197));
		table.setSelectionForeground(new Color(48, 49, 52));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {

				if(table.getSelectedRow() != -1) {
					fichePatientBtn.setEnabled(true);
					ajouterRdv.setEnabled(true);
					dossierBtn.setVisible(util instanceof Docteur && !consultation);
					consulter.setEnabled(true);
					int ligne = table.getSelectedRow();
					String code = table.getModel().getValueAt(ligne, 0).toString();
					if(Patient.getPatient(Integer.valueOf(code)).isDossierAjoute()) {
						dossierBtn.setText("Dossier medical");
					}
					else {
						dossierBtn.setText("Ajouter les details");
					}
				}
			}
		});
		consulter.setEnabled(false);
		fichePatientBtn.setEnabled(false);
		dossierBtn.setVisible(false);
		ajouterRdv.setEnabled(false);

		int taille ;
		for(int i = 0 ; i < table.getColumnCount() ; i++) {
			if(i == 0 || i == 4) {
				taille = 100;
			}
			else {
				taille = 165;
			}
			table.getColumnModel().getColumn(i).setMinWidth(taille);
			table.getColumnModel().getColumn(i).setMaxWidth(taille);
			table.getColumnModel().getColumn(i).setPreferredWidth(taille);
		}

		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
	}

	public void viderTout() {
		codeText.setText("");
		nomText.setText("");
		prenomText.setText("");
		sexeBox.setSelectedIndex(-1);
		naissanceDate.setDate(null);
		naissanceDate.setDateFormatString("dd/MM/yyyy");
		inscriDate.setDate(null);
		inscriDate.setDateFormatString("dd/MM/yyyy");
	}

	private String getRecherche(){
		String sql = "";
		boolean exist = false;
		if(!codeText.getText().trim().equals("")){
			if(exist) {
				sql = sql.concat("AND ");
			}
			exist = true;
			sql = sql.concat("code = '").concat(codeText.getText().trim().toLowerCase()).concat("' ");
		}
		if(!nomText.getText().trim().equals("")){
			if(exist) {
				sql = sql.concat("AND ");
			}
			exist = true;
			sql = sql.concat("nom = '").concat(nomText.getText().trim().toUpperCase()).concat("' ");
		}
		if(!prenomText.getText().trim().equals("")){
			if(exist) {
				sql = sql.concat("AND ");
			}
			exist = true;
			sql = sql.concat("prenom = '").concat(prenomText.getText().trim().toLowerCase()).concat("' ");
		}
		if(sexeBox.getSelectedIndex() != -1){
			if(exist) {
				sql = sql.concat("AND ");
			}
			exist = true;
			sql = sql.concat("sexe = '").concat(sexeBox.getSelectedItem().toString().trim()).concat("' ");
		}
		String date = ((JTextField)naissanceDate.getDateEditor().getUiComponent()).getText();
		if(!date.trim().equals("")){
			if(exist) {
				sql = sql.concat("AND ");
			}
			exist = true;
			sql = sql.concat("dateNaiss = '").concat(date.trim().toLowerCase()).concat("' ");
		}
		date = ((JTextField)inscriDate.getDateEditor().getUiComponent()).getText();
		if(!date.trim().equals("")){
			if(exist) {
				sql = sql.concat("AND ");
			}
			exist = true;
			sql = sql.concat("DateInscri = '").concat(date.trim().toLowerCase()).concat("' ");
		}		

		return sql;
	}
}