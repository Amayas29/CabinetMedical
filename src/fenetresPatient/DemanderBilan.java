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
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import consultation.Consultation;
import documents.Analyse;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import utilisateur.Utilisateur;

public class DemanderBilan extends JDialog {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JTextField nomText;
	private JTextField prenomText;
	private JTextField autreText;
	private Vector<Analyse> analyses = new Vector<Analyse>();
	private JButton supprimer;
	private JButton ajouterAutre;
	private JList<String> listeAnalyses;
	private JComboBox<String> analyseBox;
	private Vector<String> nomAnalyses = new Vector<String> ();
	private String[] s = new String[] {"Groupage Sanguin", "FNS avec equilibre leucocytaire",
			"Glycemie e jeun", "Hemoglobine glyquee (HbAlc)",
			"Cholesterol total", "HDL Cholesterol", "LDL Cholesterol",
			"Triglycerides", "Uree sanguine", "Creatinine", "Acide urique",
			"Bilirubine : Total-conjuguee-libre", "Transaminases (ASAT, ALAT)", "CPK",
			"Phosphatases alcalines", "Taux de prothrombine (TP)", "TCK-INR", "VS, CRP, Fibrinogene",
			"Fer serique", "Natremie (Na+)", "Kaliemie (K+)", "Calcemie", "Phosphoremie",
			"Magnesemie", "ECBU", "Chimie des urines", "Proteinurie des 24H", "Microalbuminurie"};

	public DemanderBilan(Utilisateur doc,Patient patient,Consultation consu, JFrame mere) {
		setModal(true);

		for(String st : s) {
			nomAnalyses.add(st);
		}

		setTitle("M\u00E9dic : Demander Bilan");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 700, 680);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel medic = new Panel();
		medic.setLayout(null);
		medic.setBackground(new Color(48, 49, 52));
		medic.setBounds(0, 0, 694, 83);
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

		Panel panelPrincipale = new Panel();
		panelPrincipale.setLayout(null);
		panelPrincipale.setForeground(Color.BLACK);
		panelPrincipale.setBackground(new Color(57, 113, 177));
		panelPrincipale.setBounds(0, 80, 694, 572);
		contentPane.add(panelPrincipale);

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(32, 33, 35)));
		panel_2.setBackground(new Color(57, 113, 177));
		panel_2.setBounds(31, 90, 632, 376);
		panelPrincipale.add(panel_2);
		panel_2.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(45, 175, 542, 193);
		panel_2.add(scrollPane);

		listeAnalyses = new JList<String>();
		listeAnalyses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				supprimer.setEnabled(listeAnalyses.getSelectedIndex() != -1);
			}
		});

		listeAnalyses.setSelectionBackground(new Color(250, 239, 197));
		listeAnalyses.setSelectionForeground(new Color(48, 49, 52));
		listeAnalyses.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		listeAnalyses.setBorder(null);
		listeAnalyses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeAnalyses.setBackground(new Color(57, 113, 177));
		scrollPane.setViewportView(listeAnalyses);

		supprimer = new JButton();
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(listeAnalyses.getSelectedIndex() != -1) {
					for(Analyse an: analyses) {
						if(an.getNom().equals(listeAnalyses.getSelectedValue())) {
							analyses.remove(an);
							break;
						}
					}
					majListeAnalyses(analyses);
					miseAJourBox(analyseBox, analyses);;
				}
			}
		});
		supprimer.setEnabled(false);
		supprimer.setBackground(new Color(250, 239, 197));
		supprimer.setIcon(new ImageIcon("Images/Outils/supprimer.png"));
		supprimer.setBounds(552, 145, 35, 28);
		panel_2.add(supprimer);

		JLabel lblNomDeLanalyse = new JLabel("Autre analyse");
		lblNomDeLanalyse.setForeground(new Color(32, 33, 35));
		lblNomDeLanalyse.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNomDeLanalyse.setBounds(24, 74, 130, 30);
		panel_2.add(lblNomDeLanalyse);

		autreText = new JTextField();
		autreText.setDocument(new JTextFieldLimit(27));
		autreText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				ajouterAutre.setEnabled(!autreText.getText().trim().equals(""));
			}
		});
		autreText.setForeground(Color.WHITE);
		autreText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		autreText.setColumns(10);
		autreText.setBorder(null);
		autreText.setBackground(new Color(57, 113, 177));
		autreText.setBounds(29, 102, 360, 30);
		panel_2.add(autreText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(29, 131, 360, 7);
		panel_2.add(separator_2);

		JButton ajouterAnalyse = new JButton();
		ajouterAnalyse.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajouterAnalyse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(analyseBox.getSelectedIndex() != -1) {
					analyses.addElement(new Analyse(consu.getCodeConsu(), analyseBox.getSelectedItem().toString()));
					majListeAnalyses(analyses);
					miseAJourBox(analyseBox, analyses);
				}
				ajouterAnalyse.setEnabled(false);
			}
		});
		ajouterAnalyse.setEnabled(false);
		ajouterAnalyse.setBackground(new Color(250, 239, 197));
		ajouterAnalyse.setBounds(427, 40, 35, 28);
		panel_2.add(ajouterAnalyse);

		ajouterAutre = new JButton();
		ajouterAutre.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajouterAutre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				boolean existe = false;
				for(int i = 0; i < analyses.size(); i++) {
					if(autreText.getText().trim().equals(analyses.get(i).getNom())) {
						existe = true;
						break;
					}
				}
				if(existe) {
					autreText.setText("");
					ajouterAutre.setEnabled(false);
					return;
				}
				if(!autreText.getText().trim().equals("")) {
					analyses.addElement(new Analyse(consu.getCodeConsu(), autreText.getText().trim()));
					majListeAnalyses(analyses);
					miseAJourBox(analyseBox, analyses);
				}
				autreText.setText("");
				ajouterAutre.setEnabled(false);
			}
		});
		ajouterAutre.setEnabled(false);
		ajouterAutre.setBackground(new Color(250, 239, 197));
		ajouterAutre.setBounds(427, 103, 35, 28);
		panel_2.add(ajouterAutre);

		JLabel lblNomDeLanalyse_1 = new JLabel("Nom de l'analyse");
		lblNomDeLanalyse_1.setForeground(new Color(32, 33, 35));
		lblNomDeLanalyse_1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNomDeLanalyse_1.setBounds(24, 6, 130, 30);
		panel_2.add(lblNomDeLanalyse_1);

		analyseBox = new JComboBox<String>();
		analyseBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ajouterAnalyse.setEnabled(analyseBox.getSelectedIndex() != -1);
			}
		});
		analyseBox.setModel(new DefaultComboBoxModel<String>(nomAnalyses));

		analyseBox.setSelectedIndex(-1);
		analyseBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		analyseBox.setBackground(new Color(250, 239, 197));
		analyseBox.setBounds(29, 39, 360, 30);
		panel_2.add(analyseBox);

		JLabel lblNomPatient = new JLabel("Nom patient");
		lblNomPatient.setForeground(new Color(32, 33, 35));
		lblNomPatient.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNomPatient.setBounds(31, 6, 121, 30);
		panelPrincipale.add(lblNomPatient);

		nomText = new JTextField();
		nomText.setForeground(new Color(250, 239, 197));
		nomText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomText.setEditable(false);
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setBackground(new Color(57, 113, 177));
		nomText.setBounds(31, 32, 300, 30);
		nomText.setText(patient.getNom());
		panelPrincipale.add(nomText);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(31, 63, 300, 5);
		panelPrincipale.add(separator_4);

		JLabel lblPrenomPatient = new JLabel("Prenom patient");
		lblPrenomPatient.setForeground(new Color(32, 33, 35));
		lblPrenomPatient.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPrenomPatient.setBounds(362, 6, 121, 30);
		panelPrincipale.add(lblPrenomPatient);

		prenomText = new JTextField();
		prenomText.setForeground(new Color(250, 239, 197));
		prenomText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		prenomText.setEditable(false);
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(57, 113, 177));
		prenomText.setBounds(362, 32, 300, 30);
		prenomText.setText(patient.getPrenom());
		panelPrincipale.add(prenomText);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(362, 63, 300, 5);
		panelPrincipale.add(separator_5);

		JButton sauvegarder = new JButton("Sauvegarder");
		sauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(analyses.isEmpty()) {
					MessageDialog.showMessageDialog(DemanderBilan.this, "Le bilan ne contient aucune information",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				consu.demanderBilan(analyses);
				dispose();
			}
		});
		sauvegarder.setForeground(new Color(250, 239, 197));
		sauvegarder.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		sauvegarder.setBackground(new Color(33, 34, 36));
		sauvegarder.setBounds(393, 470, 270, 85);
		panelPrincipale.add(sauvegarder);

	}	

	private void majListeAnalyses(Vector<Analyse> analyses) {
		listeAnalyses.removeAll();  
		Vector<String> noms = new Vector<String>();
		for(Analyse ana : analyses) {
			noms.add(ana.getNom());
		}
		listeAnalyses.setListData(noms);
	}
	private void miseAJourBox(JComboBox<String> box, Vector<Analyse> analyses) {
		box.removeAllItems();
		nomAnalyses = new Vector<String>();
		for(String st : s) {
			nomAnalyses.add(st);
		}
		for(int j = 0; j < nomAnalyses.size(); j++) {
			boolean existe = false;
			int i = 0;
			while(i < analyses.size() && !existe) {
				if(nomAnalyses.get(j).equals(analyses.get(i).getNom())) {
					existe = true;
				}
				i++;
			}
			if(!existe) {
				box.addItem(nomAnalyses.get(j));
			}
		}
		box.setSelectedIndex(-1);
	}
}