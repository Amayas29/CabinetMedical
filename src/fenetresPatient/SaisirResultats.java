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

import javax.swing.ImageIcon;
import javax.swing.JButton;
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
import documents.Resultat;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import utilisateur.Utilisateur;

public class SaisirResultats extends JDialog {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JTextField nomText;
	private JTextField prenomText;
	private JTextField nomAnalyseText;
	private JButton ajouterRes;
	private JList<String> listeAnalyses;
	private JTextField resText;
	private Vector<Resultat> resultats = new Vector<Resultat>();

	public SaisirResultats(Utilisateur doc,Patient patient,Consultation consu, JFrame mere) {
		Vector<Analyse> analyses = new Vector<Analyse>();
		for(Analyse analyse : consu.getBilan().getAnalyses()) {
			analyses.add(analyse);
		}
		setModal(true);

		setTitle("M\u00E9dic : Saisir les Resultats du Bilan");
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
		scrollPane.setBounds(45, 164, 542, 204);
		panel_2.add(scrollPane);

		listeAnalyses = new JList<String>();
		listeAnalyses.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				if(listeAnalyses.getSelectedIndex() != -1) {
					nomAnalyseText.setText(listeAnalyses.getSelectedValue());
				}
				else {
					nomAnalyseText.setText("");
				}
			}
		});

		listeAnalyses.setSelectionBackground(new Color(250, 239, 197));
		listeAnalyses.setSelectionForeground(new Color(48, 49, 52));
		listeAnalyses.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		listeAnalyses.setBorder(null);
		listeAnalyses.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listeAnalyses.setBackground(new Color(57, 113, 177));
		scrollPane.setViewportView(listeAnalyses);

		JLabel nomAnalyse = new JLabel("Nom de l'analyse");
		nomAnalyse.setForeground(new Color(32, 33, 35));
		nomAnalyse.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomAnalyse.setBounds(21, 11, 130, 30);
		panel_2.add(nomAnalyse);

		nomAnalyseText = new JTextField();
		nomAnalyseText.setEditable(false);
		nomAnalyseText.setForeground(Color.WHITE);
		nomAnalyseText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomAnalyseText.setColumns(10);
		nomAnalyseText.setBorder(null);
		nomAnalyseText.setBackground(new Color(57, 113, 177));
		nomAnalyseText.setBounds(26, 39, 360, 30);
		panel_2.add(nomAnalyseText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(26, 68, 360, 7);
		panel_2.add(separator_2);

		ajouterRes = new JButton();
		ajouterRes.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajouterRes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!resText.getText().trim().equals("") && !nomAnalyseText.getText().trim().equals("")) {
					int i = 0;
					while(i < analyses.size()) {
						if(analyses.get(i).getNom().equals(nomAnalyseText.getText())) {
							break;
						}
						i++;
					}
					Resultat res = new Resultat(analyses.get(i).getCode(), analyses.get(i).getCodeSeq(), resText.getText());
					resultats.add(res);
					analyses.remove(i);
					majListeAnalyses(analyses);
				}

				resText.setText("");
				nomAnalyseText.setText("");
				listeAnalyses.setSelectedIndex(-1);
				ajouterRes.setEnabled(false);
			}
		});
		ajouterRes.setEnabled(false);
		ajouterRes.setBackground(new Color(250, 239, 197));
		ajouterRes.setBounds(470, 117, 35, 28);
		panel_2.add(ajouterRes);

		JLabel resLab = new JLabel("Resultat de l'analyse");
		resLab.setForeground(new Color(32, 33, 35));
		resLab.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		resLab.setBounds(21, 89, 130, 30);
		panel_2.add(resLab);

		resText = new JTextField();
		resText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				ajouterRes.setEnabled(!resText.getText().trim().equals("") && !nomAnalyseText.getText().trim().equals(""));
			}
		});
		resText.setDocument(new JTextFieldLimit(40));
		resText.setForeground(Color.WHITE);
		resText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		resText.setColumns(10);
		resText.setBorder(null);
		resText.setBackground(new Color(57, 113, 177));
		resText.setBounds(26, 117, 360, 30);
		panel_2.add(resText);

		JSeparator separator = new JSeparator();
		separator.setBounds(26, 146, 360, 7);
		panel_2.add(separator);

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
				if(!analyses.isEmpty()) {
					MessageDialog.showMessageDialog(SaisirResultats.this, "Veuillez d'abord saisir tous les resultats des analyses",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				consu.getBilan().chargerResultat(resultats);
				dispose();
			}
		});
		sauvegarder.setForeground(new Color(250, 239, 197));
		sauvegarder.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		sauvegarder.setBackground(new Color(33, 34, 36));
		sauvegarder.setBounds(393, 470, 270, 85);
		panelPrincipale.add(sauvegarder);

		majListeAnalyses(analyses);
	}	

	private void majListeAnalyses(Vector<Analyse> analyses) {
		listeAnalyses.removeAll();  
		Vector<String> noms = new Vector<String>();
		for(Analyse ana : analyses) {
			noms.add(ana.getNom());
		}
		listeAnalyses.setListData(noms);
	}
}