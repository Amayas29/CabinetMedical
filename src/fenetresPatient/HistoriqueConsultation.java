package fenetresPatient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import maladies.MaladieNonChronique;
import maladies.Symptome;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import utilisateur.Utilisateur;

public class HistoriqueConsultation extends JFrame {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JTextField codeText;
	private JTextField nomText;
	private JTextField prenomText;
	private JTextField nomMaladieText;
	private JTextField commText;
	private JList<String> listeMaladie;
	private JTextArea symptomeText;
	private JList<String> listeSymptome;
	private JTextArea commentaire;
	private String code;
	private JButton btnRes;

	public HistoriqueConsultation(Utilisateur doc,Patient patient,long codeConsu, JFrame mere) {
		Consultation consu = Consultation.getConsultation(codeConsu);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				RechercherConsultation rechConsu = new RechercherConsultation(doc, patient, mere);
				rechConsu.setVisible(true);
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				if(consu.getBilan() != null && consu.getBilan().getResultat() != null) {
					btnRes.setText("Consulter les resultats du bilan");
				}	
			}
		});

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
		scrollPane.setBounds(20, 163, 370, 182);
		panel_2.add(scrollPane);

		listeMaladie = new JList<String>();
		majListeMaladies(consu.getMaladies());
		listeMaladie.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(listeMaladie.getSelectedIndex() != -1) {
					nomMaladieText.setText(listeMaladie.getSelectedValue());
					for(MaladieNonChronique mal : consu.getMaladies()) {
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

		JLabel label_8 = new JLabel("Nom de la maladie");
		label_8.setForeground(new Color(32, 33, 35));
		label_8.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_8.setBounds(12, 5, 130, 30);
		panel_2.add(label_8);

		nomMaladieText = new JTextField();
		nomMaladieText.setForeground(Color.WHITE);
		nomMaladieText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomMaladieText.setColumns(10);
		nomMaladieText.setBorder(null);
		nomMaladieText.setBackground(new Color(57, 113, 177));
		nomMaladieText.setBounds(17, 33, 360, 30);
		nomMaladieText.setEditable(false);
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
		commText.setEditable(false);
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
		scrollPane_1.setBounds(20, 165, 370, 180);
		scrollPane_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(scrollPane_1);

		listeSymptome = new JList<String>();
		listeSymptome.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(listeSymptome.getSelectedIndex() != -1) {
					symptomeText.setText(listeSymptome.getSelectedValue());
				}
			}
		});
		majListeSymptome(consu.getSymptomes());
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

		symptomeText = new JTextArea();
		symptomeText.setTabSize(4);
		symptomeText.setDocument(new JTextFieldLimit(100));
		symptomeText.setLineWrap(true);
		symptomeText.setEditable(false);
		symptomeText.setForeground(new Color(250, 239, 197));
		symptomeText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		symptomeText.setColumns(10);
		symptomeText.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		symptomeText.setBackground(new Color(57, 113, 177));
		symptomeText.setBounds(20, 40, 370, 100);
		panel.add(symptomeText);

		JButton btnOrdannance = new JButton("Consulter l'Ordonnance ");
		btnOrdannance.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(consu.getTraitement() == null) {
					MessageDialog.showMessageDialog(HistoriqueConsultation.this, "Aucune ordonnance nea ete preinscrite",
							"Bilan introuvable", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				consu.getTraitement().imprimer(consu);
			}

		});
		btnOrdannance.setForeground(new Color(250, 239, 197));
		btnOrdannance.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnOrdannance.setBackground(new Color(48, 49, 52).darker());
		btnOrdannance.setBounds(905, 78, 270, 85);
		panelPrincipale.add(btnOrdannance);

		JButton btnCertificat = new JButton("Consulter le Certificat Medical");
		btnCertificat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(consu.getCertificatMedical() == null) {
					MessageDialog.showMessageDialog(HistoriqueConsultation.this, "Aucun certificat medical n'a ete fourni",
							"Certificat medical introuvable", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				consu.getCertificatMedical().imprimer(consu);
			}
		});
		btnCertificat.setForeground(new Color(250, 239, 197));
		btnCertificat.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnCertificat.setBackground(new Color(48, 49, 52).darker());
		btnCertificat.setBounds(905, 274, 270, 85);
		panelPrincipale.add(btnCertificat);

		JButton btnBilan = new JButton("Consulter le Bilan");
		btnBilan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(consu.getBilan() == null) {
					MessageDialog.showMessageDialog(HistoriqueConsultation.this, "Aucune demande de bilan n'a ete effectuee",
							"Bilan introuvable", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				consu.getBilan().imprimer(consu);
			}
		});
		btnBilan.setForeground(new Color(250, 239, 197));
		btnBilan.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnBilan.setBackground(new Color(33, 34, 36));
		btnBilan.setBounds(905, 372, 270, 85);
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

		JButton btnBon = new JButton("Consulter le Bon");
		btnBon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				consu.getBon().imprimer(consu);
			}
		});
		btnBon.setForeground(new Color(250, 239, 197));
		btnBon.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnBon.setBackground(new Color(33, 34, 36));
		btnBon.setBounds(905, 176, 270, 85);
		panelPrincipale.add(btnBon);

		commentaire = new JTextArea();
		commentaire.setTabSize(4);
		commentaire.setLineWrap(true);
		commentaire.setForeground(new Color(250, 239, 197));
		commentaire.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		commentaire.setColumns(10);
		commentaire.setBorder(new TitledBorder(null, "      Commentaire :      ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		commentaire.setBackground(new Color(57, 113, 177));
		commentaire.setBounds(30, 450, 845, 115);
		commentaire.setText(consu.getCommentaire());
		commentaire.setEditable(false);
		panelPrincipale.add(commentaire);

		btnRes = new JButton("Saisir les R\u00E9sultats du Bilan");
		btnRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(consu.getBilan().getResultat() == null) {
					SaisirResultats res = new SaisirResultats(doc, patient, consu, HistoriqueConsultation.this);
					res.setVisible(true);
				}
				else {
					ConsulterResultats res = new ConsulterResultats(doc, patient, consu, HistoriqueConsultation.this);
					res.setVisible(true);
				}
			}
		});
		btnRes.setForeground(new Color(250, 239, 197));
		btnRes.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		btnRes.setBackground(new Color(33, 34, 36));
		btnRes.setBounds(905, 470, 270, 85);
		btnRes.setVisible(consu.getBilan() != null);
		if(consu.getBilan() != null && consu.getBilan().getResultat() != null) {
			btnRes.setText("Consulter les resultats du bilan");
		}
		panelPrincipale.add(btnRes);
	}	

	private void majListeSymptome(Vector<Symptome> symptomes) {
		listeSymptome.removeAll();
		Vector<String> vecSymp = new Vector<String>();
		if(symptomes != null) {
			for(Symptome sym : symptomes) {
				vecSymp.addElement(sym.getDescription());
			}
		}
		listeSymptome.setListData(vecSymp);

	}

	private void majListeMaladies(Vector<MaladieNonChronique> autresMaladies) {
		listeMaladie.removeAll();  
		Vector<String> noms = new Vector<String>();
		if(autresMaladies != null) {
			for(MaladieNonChronique maladie : autresMaladies) {
				noms.addElement(maladie.getNom());	
			}
		}
		listeMaladie.setListData(noms);
	}
}