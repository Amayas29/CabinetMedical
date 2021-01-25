package fenetresPatient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import consultation.Consultation;
import fenetresPrincipales.FenetreDocteur;
import maladies.MaladieNonChronique;
import maladies.Symptome;
import outils.ConfirmDialog;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import patient.PatientDetaille;
import utilisateur.Utilisateur;

public class ConsulterPatient extends JFrame {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JButton btnDossier;
	private JTextField codeText;
	private JTextField nomText;
	private JTextField prenomText;
	private JTextField nomMaladieText;
	private JTextField commText;
	private Vector<MaladieNonChronique> maladies = new Vector<MaladieNonChronique>();
	private JLabel maladieDejaExiste;
	private JButton supprimerMaladie;
	private JButton ajouterMaladie;
	private JList<String> listeMaladie;
	private Vector<Symptome> symptomes = new Vector<Symptome>();
	private JTextArea symptomeText;
	private JList<String> listeSymptome;
	private JButton ajouterSymptome;
	private JButton supprimerSymptome;
	private JTextArea commentaire;
	private String code;

	public ConsulterPatient(Utilisateur doc,Patient patient,Consultation consu) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				FenetreDocteur docFen = new FenetreDocteur(doc);
				docFen.setVisible(true);
			}
			@Override
			public void windowClosing(WindowEvent arg0) {
				int choix = ConfirmDialog.showConfirmDialog(ConsulterPatient.this, "Les informations ne seront pas sauvegarder", "Attention", ConfirmDialog.OPTION_OUI_NON,
						ConfirmDialog.ATTENTION);
				if(choix == ConfirmDialog.OPTION_OUI) {
					dispose();
				}
			}
		});
		if(consu.getCodeConsu() == -1l) {
			consu.setCodeConsu(Consultation.genererCode());
		}
		code = String.valueOf(consu.getCodeConsu());
		if(code.length() != 11) {
			code = "0".concat(code);
		}
		setTitle("Medic : Consulter Patient");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1200, 680);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel medic = new Panel();
		medic.setLayout(null);
		medic.setBackground(new Color(48, 49, 52));
		medic.setBounds(0, 0, 1194, 83);
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
		label_2.setBounds(970, 3, 44, 28);
		medic.add(label_2);

		JLabel label_3 = new JLabel("Medic.contact@gmail.com");
		label_3.setForeground(new Color(250, 239, 197));
		label_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_3.setBounds(1020, 1, 168, 30);
		medic.add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("Images/Outils/facebook.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(970, 24, 44, 28);
		medic.add(label_4);

		JLabel label_5 = new JLabel("Medic");
		label_5.setForeground(new Color(250, 239, 197));
		label_5.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_5.setBounds(1020, 22, 139, 30);
		medic.add(label_5);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon("Images/Outils/site.png"));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(970, 46, 44, 28);
		medic.add(label_6);

		JLabel label_7 = new JLabel("www.Medic.com");
		label_7.setForeground(new Color(250, 239, 197));
		label_7.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_7.setBounds(1020, 44, 139, 30);
		medic.add(label_7);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(963, 0, 10, 83);
		medic.add(separator);

		Panel panelPrincipale = new Panel();
		panelPrincipale.setLayout(null);
		panelPrincipale.setForeground(Color.BLACK);
		panelPrincipale.setBackground(new Color(57, 113, 177));
		panelPrincipale.setBounds(0, 80, 1194, 572);
		contentPane.add(panelPrincipale);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(72, 68, 300, 5);
		panelPrincipale.add(separator_1);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(32, 33, 35)));
		panel_2.setBackground(new Color(57, 113, 177));
		panel_2.setBounds(30, 85, 410, 360);
		panelPrincipale.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(20, 175, 370, 170);
		panel_2.add(scrollPane);

		listeMaladie = new JList<String>();
		listeMaladie.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(listeMaladie.getSelectedIndex() != -1) {
					supprimerMaladie.setEnabled(true);
					nomMaladieText.setText(listeMaladie.getSelectedValue());
					for(MaladieNonChronique mal : maladies) {
						if(mal.getNom().equals(listeMaladie.getSelectedValue())) {
							commText.setText(mal.getCommentaire());
							break;
						}
					}
				}
			}
		});

		listeMaladie.setSelectionBackground(new Color(250, 239, 197));
		listeMaladie.setSelectionForeground(new Color(48, 49, 52));
		listeMaladie.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		listeMaladie.setBorder(null);
		listeMaladie.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeMaladie.setBackground(new Color(57, 113, 177));
		scrollPane.setViewportView(listeMaladie);

		ajouterMaladie = new JButton();
		ajouterMaladie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!nomMaladieText.getText().trim().equals("")) {
					boolean existe = false;
					int i = 0; 
					while(i < maladies.size() && !existe) {
						existe = maladies.get(i).getNom().equals(nomMaladieText.getText().trim()) ;
						i++;
					}
					if(!existe) {
						maladies.addElement(new MaladieNonChronique(nomMaladieText.getText().trim(),
								commText.getText().trim(), consu.getCodeConsu()));
						majListeMaladies(maladies);
					}
					else {
						maladieDejaExiste.setVisible(true);
					}
				}
				nomMaladieText.setText("");
				commText.setText("");
				ajouterMaladie.setEnabled(false);
				supprimerMaladie.setEnabled(false);
			}
		});
		ajouterMaladie.setEnabled(false);
		ajouterMaladie.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajouterMaladie.setBackground(new Color(250, 239, 197));
		ajouterMaladie.setBounds(310, 144, 35, 28);
		panel_2.add(ajouterMaladie);

		supprimerMaladie = new JButton();
		supprimerMaladie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(listeMaladie.getSelectedIndex() != -1) {
					for(MaladieNonChronique mal : maladies) {
						if(mal.getNom().equals(listeMaladie.getSelectedValue())) {
							maladies.removeElement(mal);
							break;
						}
					}
					majListeMaladies(maladies);
				}
				nomMaladieText.setText("");
				commText.setText("");
				supprimerMaladie.setEnabled(false);
				ajouterMaladie.setEnabled(false);
			}
		});
		supprimerMaladie.setEnabled(false);
		supprimerMaladie.setBackground(new Color(250, 239, 197));
		supprimerMaladie.setIcon(new ImageIcon("Images/Outils/supprimer.png"));
		supprimerMaladie.setBounds(355, 144, 35, 28);
		panel_2.add(supprimerMaladie);

		JLabel label_8 = new JLabel("Nom de la maladie");
		label_8.setForeground(new Color(32, 33, 35));
		label_8.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_8.setBounds(12, 5, 130, 30);
		panel_2.add(label_8);

		nomMaladieText = new JTextField();
		nomMaladieText.setDocument(new JTextFieldLimit(25));
		nomMaladieText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				ajouterMaladie.setEnabled(!nomMaladieText.getText().trim().equals(""));
				maladieDejaExiste.setVisible(false);
			}
		});
		nomMaladieText.setForeground(Color.WHITE);
		nomMaladieText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomMaladieText.setColumns(10);
		nomMaladieText.setBorder(null);
		nomMaladieText.setBackground(new Color(57, 113, 177));
		nomMaladieText.setBounds(17, 33, 360, 30);
		panel_2.add(nomMaladieText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(17, 62, 360, 7);
		panel_2.add(separator_2);

		JLabel label_9 = new JLabel("Commentaire");
		label_9.setForeground(new Color(32, 33, 35));
		label_9.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_9.setBounds(12, 71, 109, 30);
		panel_2.add(label_9);

		commText = new JTextField();
		commText.setDocument(new JTextFieldLimit(27));
		commText.setForeground(Color.WHITE);
		commText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		commText.setColumns(10);
		commText.setBorder(null);
		commText.setBackground(new Color(57, 113, 177));
		commText.setBounds(17, 97, 360, 30);
		panel_2.add(commText);

		JSeparator separator_7 = new JSeparator();
		separator_7.setBounds(17, 126, 360, 7);
		panel_2.add(separator_7);

		maladieDejaExiste = new JLabel("Maladie d\u00E9ja saisie");
		maladieDejaExiste.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		maladieDejaExiste.setVisible(false);
		maladieDejaExiste.setBounds(42, 143, 194, 30);
		panel_2.add(maladieDejaExiste);

		JLabel lblAutreInformations = new JLabel("Code consultation");
		lblAutreInformations.setBounds(72, 11, 121, 30);
		panelPrincipale.add(lblAutreInformations);
		lblAutreInformations.setForeground(new Color(32, 33, 35));
		lblAutreInformations.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		codeText = new JTextField();
		codeText.setEditable(false);
		codeText.setForeground(new Color(250, 239, 197));
		codeText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		codeText.setBackground(new Color(57, 113, 177));
		codeText.setBorder(null);
		codeText.setBounds(72, 37, 300, 30);
		panelPrincipale.add(codeText);
		codeText.setColumns(10);
		codeText.setText(code);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(32, 33, 35)));
		panel.setBackground(new Color(57, 113, 177));
		panel.setBounds(465, 85, 410, 360);
		panelPrincipale.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(20, 175, 370, 170);
		scrollPane_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(scrollPane_1);

		listeSymptome = new JList<String>();
		listeSymptome.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(listeSymptome.getSelectedIndex() != -1) {
					supprimerSymptome.setEnabled(true);
					symptomeText.setText(listeSymptome.getSelectedValue());
				}
			}
		});

		listeSymptome.setLocation(0, 170);
		listeSymptome.setSize(370, 145);
		listeSymptome.setSelectionBackground(new Color(250, 239, 197));
		listeSymptome.setSelectionForeground(new Color(48, 49, 52));
		listeSymptome.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		listeSymptome.setBorder(null);
		listeSymptome.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeSymptome.setBackground(new Color(57, 113, 177));
		scrollPane_1.setViewportView(listeSymptome);

		JLabel lblSynptome = new JLabel("Sympt\u00F4me");
		lblSynptome.setForeground(new Color(32, 33, 35));
		lblSynptome.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblSynptome.setBounds(20, 5, 121, 30);
		panel.add(lblSynptome);

		ajouterSymptome = new JButton();
		ajouterSymptome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!symptomeText.getText().trim().equals("")) {
					symptomes.addElement(new Symptome(consu.getCodeConsu(), symptomeText.getText().trim()));
					majListeSymptome(symptomes);
				}
				symptomeText.setText("");
				ajouterSymptome.setEnabled(false);
				supprimerSymptome.setEnabled(false);
			}
		});
		ajouterSymptome.setEnabled(false);
		ajouterSymptome.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajouterSymptome.setBackground(new Color(250, 239, 197));
		ajouterSymptome.setBounds(310, 144, 35, 28);
		panel.add(ajouterSymptome);

		supprimerSymptome = new JButton();
		supprimerSymptome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(listeSymptome.getSelectedIndex() != -1) {
					for(Symptome sym : symptomes) {
						if(sym.getDescription().equals(listeSymptome.getSelectedValue())) {
							symptomes.removeElement(sym);
							break;
						}
					}
					majListeSymptome(symptomes);
				}
				symptomeText.setText("");
				ajouterSymptome.setEnabled(false);
				supprimerSymptome.setEnabled(false);
			}
		});
		supprimerSymptome.setEnabled(false);
		supprimerSymptome.setBackground(new Color(250, 239, 197));
		supprimerSymptome.setIcon(new ImageIcon("Images/Outils/supprimer.png"));
		supprimerSymptome.setBounds(355, 144, 35, 28);
		panel.add(supprimerSymptome);

		symptomeText = new JTextArea();
		symptomeText.setTabSize(4);
		symptomeText.setDocument(new JTextFieldLimit(100));
		symptomeText.setLineWrap(true);
		symptomeText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				ajouterSymptome.setEnabled(!symptomeText.getText().trim().equals(""));
			}
		});
		symptomeText.setForeground(new Color(250, 239, 197));
		symptomeText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		symptomeText.setColumns(10);
		symptomeText.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		symptomeText.setBackground(new Color(57, 113, 177));
		symptomeText.setBounds(20, 40, 370, 100);
		panel.add(symptomeText);

		btnDossier = new JButton("");
		btnDossier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PatientDetaille patientDetaille = PatientDetaille.getPatientDetaille(patient);
				DossierPatient dossierPatient = new DossierPatient(doc, patientDetaille, ConsulterPatient.this);
				dossierPatient.setVisible(true);
			}
		});
		if(patient.isDossierAjoute()) {
			btnDossier.setText("Consulter le dossier du patient");
		}
		else {
			btnDossier.setText("Ajouter le dossier du patient");	
		}
		btnDossier.setForeground(new Color(250, 239, 197));
		btnDossier.setBounds(905, 83, 270, 85);
		panelPrincipale.add(btnDossier);
		btnDossier.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnDossier.setBackground(new Color(48, 49, 52));

		JButton btnOrdonnance = new JButton("Etablir Ordonnance");
		btnOrdonnance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EtablirOrdonnance ord = new EtablirOrdonnance(doc, consu, ConsulterPatient.this);
				ord.setVisible(true);
			}

		});
		btnOrdonnance.setForeground(new Color(250, 239, 197));
		btnOrdonnance.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnOrdonnance.setBackground(new Color(48, 49, 52).darker());
		btnOrdonnance.setBounds(905, 180, 270, 85);
		panelPrincipale.add(btnOrdonnance);

		JButton btnCertificat = new JButton("Fournir un Certificat Medical");
		btnCertificat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FournirCertificatMedical certMed = new FournirCertificatMedical(doc, consu, ConsulterPatient.this);
				certMed.setVisible(true);
			}
		});
		btnCertificat.setForeground(new Color(250, 239, 197));
		btnCertificat.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnCertificat.setBackground(new Color(48, 49, 52).darker());
		btnCertificat.setBounds(905, 374, 270, 85);
		panelPrincipale.add(btnCertificat);

		JButton btnBilan = new JButton("Demander Bilan");
		btnBilan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DemanderBilan demBilan = new DemanderBilan(doc, patient, consu, ConsulterPatient.this);
				demBilan.setVisible(true);
			}
		});
		btnBilan.setForeground(new Color(250, 239, 197));
		btnBilan.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnBilan.setBackground(new Color(33, 34, 36));
		btnBilan.setBounds(905, 277, 270, 85);
		panelPrincipale.add(btnBilan);

		JLabel lblNomPatient = new JLabel("Nom patient");
		lblNomPatient.setForeground(new Color(32, 33, 35));
		lblNomPatient.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNomPatient.setBounds(446, 11, 121, 30);
		panelPrincipale.add(lblNomPatient);

		nomText = new JTextField();
		nomText.setForeground(new Color(250, 239, 197));
		nomText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomText.setEditable(false);
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setBackground(new Color(57, 113, 177));
		nomText.setBounds(446, 37, 300, 30);
		nomText.setText(patient.getNom());
		panelPrincipale.add(nomText);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(446, 68, 300, 5);
		panelPrincipale.add(separator_4);

		JLabel lblPrenomPatient = new JLabel("Prenom patient");
		lblPrenomPatient.setForeground(new Color(32, 33, 35));
		lblPrenomPatient.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPrenomPatient.setBounds(819, 11, 121, 30);
		panelPrincipale.add(lblPrenomPatient);

		prenomText = new JTextField();
		prenomText.setForeground(new Color(250, 239, 197));
		prenomText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		prenomText.setEditable(false);
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(57, 113, 177));
		prenomText.setBounds(819, 37, 300, 30);
		prenomText.setText(patient.getPrenom());
		panelPrincipale.add(prenomText);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(819, 68, 300, 5);
		panelPrincipale.add(separator_5);

		JButton sauvegarder = new JButton("Sauvegarder");
		sauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(maladies.isEmpty() && symptomes.isEmpty() && consu.getBilan() == null && consu.getTraitement() == null
						&& consu.getCertificatMedical() == null) {
					MessageDialog.showMessageDialog(ConsulterPatient.this, "La consultation ne contient aucune informations",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				int choix = ConfirmDialog.showConfirmDialog(ConsulterPatient.this, 
						"Une fois sauvegarder les informations de la consultation ne pourront pas etre changes, etes vous sur de continuer ?",
						"Attention", ConfirmDialog.OPTION_OUI_NON, ConfirmDialog.ATTENTION);
				if(choix == ConfirmDialog.OPTION_OUI) {
					FournirBon bon = new FournirBon(doc, consu, ConsulterPatient.this);
					bon.setVisible(true);

					if(!symptomes.isEmpty()) {
						consu.setSymptomes(symptomes);
					}
					if(!maladies.isEmpty()) {
						consu.setMaladies(maladies);
					}
					consu.setCommentaire(commentaire.getText().trim());
					consu.stocker();
					consu.imprimer();
					dispose();
				}
			}
		});
		sauvegarder.setForeground(new Color(250, 239, 197));
		sauvegarder.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		sauvegarder.setBackground(new Color(33, 34, 36));
		sauvegarder.setBounds(905, 471, 270, 85);
		panelPrincipale.add(sauvegarder);

		commentaire = new JTextArea();
		commentaire.setTabSize(4);
		commentaire.setDocument(new JTextFieldLimit(300));
		commentaire.setLineWrap(true);
		commentaire.setForeground(new Color(250, 239, 197));
		commentaire.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		commentaire.setColumns(10);
		commentaire.setBorder(new TitledBorder(null, "      Commentaire :      ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		commentaire.setBackground(new Color(57, 113, 177));
		commentaire.setBounds(30, 450, 845, 115);
		panelPrincipale.add(commentaire);

	}	

	private void majListeSymptome(Vector<Symptome> symptomes) {
		listeSymptome.removeAll();
		Vector<String> vecSymp = new Vector<String>();
		for(Symptome sym : symptomes) {
			vecSymp.addElement(sym.getDescription());
		}
		listeSymptome.setListData(vecSymp);

	}

	private void majListeMaladies(Vector<MaladieNonChronique> autresMaladies) {
		listeMaladie.removeAll();  
		Vector<String> noms = new Vector<String>();
		for(MaladieNonChronique maladie : autresMaladies) {
			noms.addElement(maladie.getNom());	
		}
		listeMaladie.setListData(noms);
	}
}