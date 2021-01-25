package fenetresPatient;

import java.awt.Color;
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
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import maladies.AutreMaladieChronique;
import maladies.CardioVasculaire;
import maladies.Endocrin;
import maladies.InfectieusesChroniques;
import maladies.MaladiesInterface;
import maladies.Orl;
import maladies.Rhumatolog;
import outils.ConfirmDialog;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import patient.PatientDetaille;
import utilisateur.Docteur;
import utilisateur.Utilisateur;

public class DossierPatient extends JDialog {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JTextField codeText;
	private JTextField prenomText;
	private JTextField nomText;
	private JTextField tensionText;
	private JTextField poidsText;
	private JComboBox<String> groupeSangBox;
	private JList<String> list;
	private JButton supprimer;
	private JComboBox<String> cardVascBox = new JComboBox<String>();
	private JComboBox<String> endocBox = new JComboBox<String>();
	private JComboBox<String> orlBox = new JComboBox<String>();
	private JComboBox<String> rhumBox = new JComboBox<String>();
	private JComboBox<String> infcChroBox = new JComboBox<String>();
	private JLabel lblCardiovasculaire;
	private JLabel lblEndocriniennes;
	private JLabel lblRespiratoiresEtOrl;
	private JLabel lblRhumatologiques;
	private JLabel lblInfectieusesChroniques;
	private Vector<String> maladies = new Vector<String>();
	private JButton ajoute = new JButton();
	private JLabel lblNewLabel;
	private JCheckBox maladieChroniqueCheck;
	private JTextField glycemieText;
	private JLabel lblTauxGlycemie;
	private JComboBox<String> vueBox;
	private JComboBox<String> tailleBox;
	private JTextField tailleText;
	private JTextField groupeSangText;
	private JTextField vueText;
	private JSeparator tailleSepa;
	private JSeparator groupeSangSepa;
	private JSeparator vueSepa;
	private JTextArea antMediText;
	private JPanel nonEditablePanel;
	private JPanel editablePanel;
	private JButton modifier;
	private JButton sauvegarder;
	private JList<String> listaffichage;
	private JButton annuler;
	private JTextField nomAutreText;
	private JTextField comAutreText;
	private Vector<AutreMaladieChronique> autresMaladies = new Vector<AutreMaladieChronique>();
	private JButton ajouterAutre;
	private JButton supprimerAutre;
	private JList<String> list_autre;
	private JLabel nomAutre;
	private JLabel comAutre;
	private JLabel maladieDejaExiste;
	
	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Docteur doc = new Docteur("amayas", "amayas");
					Patient patient= Patient.getPatient(2);
					DossierPatient frame = new DossierPatient(doc,PatientDetaille.getPatientDetaille(patient),null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}	

	public DossierPatient(Utilisateur doc,PatientDetaille patientDetaille,JFrame mere) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if(sauvegarder.isVisible()) {
					int i = ConfirmDialog.showConfirmDialog(DossierPatient.this,"Les modification ne seront pas sauvegardees. Voulez-vous quitter ?", "Attention",ConfirmDialog.OPTION_OUI_NON,ConfirmDialog.ATTENTION);
					if(i == ConfirmDialog.OPTION_OUI ) {
						dispose();
					}
					return ;
				}
				dispose();
			}
		});
		setModal(true);
		setTitle("Medic : Dossier Patient");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		setLocationRelativeTo(mere);
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

		JLabel label_5 = new JLabel("M\u00E9dic");
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
		panelPrincipale.setBounds(0, 80, 1194, 592);
		contentPane.add(panelPrincipale);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(57, 113, 177));
		panel.setBorder(new TitledBorder(null, "Patient : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(32, 33, 35)));
		panel.setBounds(10, 10, 1167, 100);
		panelPrincipale.add(panel);
		panel.setLayout(null);

		JLabel lblCode = new JLabel("Code");
		lblCode.setBounds(77, 19, 111, 30);
		panel.add(lblCode);
		lblCode.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCode.setForeground(new Color(32, 33, 35));

		codeText = new JTextField();
		codeText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		codeText.setEditable(false);
		codeText.setBounds(77, 47, 300, 30);
		panel.add(codeText);
		codeText.setForeground(Color.WHITE);
		codeText.setBackground(new Color(57, 113, 177));
		codeText.setBorder(null);
		codeText.setColumns(10);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(77, 76, 290, 7);
		panel.add(separator_1);

		JLabel lblSexe = new JLabel("Prenom");
		lblSexe.setBounds(811, 19, 111, 30);
		panel.add(lblSexe);
		lblSexe.setForeground(new Color(32, 33, 35));
		lblSexe.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		prenomText = new JTextField();
		prenomText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		prenomText.setEditable(false);
		prenomText.setBounds(811, 47, 300, 30);
		panel.add(prenomText);
		prenomText.setForeground(Color.WHITE);
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(57, 113, 177));

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(811, 76, 290, 7);
		panel.add(separator_2);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setBounds(444, 19, 111, 30);
		panel.add(lblNom);
		lblNom.setForeground(new Color(32, 33, 35));
		lblNom.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		nomText = new JTextField();
		nomText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomText.setEditable(false);
		nomText.setBounds(444, 47, 300, 30);
		panel.add(nomText);
		nomText.setForeground(Color.WHITE);
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setBackground(new Color(57, 113, 177));

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(444, 76, 290, 7);
		panel.add(separator_4);

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBorder(new TitledBorder(null, "D\u00E9tails sur le patient : ", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(32, 33, 35)));
		panel_1.setBackground(new Color(57, 113, 177));
		panel_1.setBounds(10, 111, 746, 170);
		panelPrincipale.add(panel_1);

		tailleBox = new JComboBox<String>();
		tailleBox.setModel(new DefaultComboBoxModel<String> (new String[] {"50", "55", "60", "65", "70", "75", "80",
				"85", "90", "95", "100", "105", "110", "115", "120", "125",
				"130", "135", "140", "145", "150", "155", "160", "165", "170",
				"175", "180", "185", "190", "195", "200", "205", "210", "215",
				"220", "225", "230", "235", "240", "245", "250"}));
		tailleBox.setSelectedIndex(-1);
		tailleBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tailleBox.setBackground(new Color(250, 239, 197));
		tailleBox.setBounds(33, 127, 200, 30);
		panel_1.add(tailleBox);

		JLabel lblTaille = new JLabel("Taille *");
		lblTaille.setForeground(new Color(32, 33, 35));
		lblTaille.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblTaille.setBounds(33, 92, 111, 30);
		panel_1.add(lblTaille);

		JLabel lblTention = new JLabel("Tension *");
		lblTention.setForeground(new Color(32, 33, 35));
		lblTention.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblTention.setBounds(33, 22, 111, 30);
		panel_1.add(lblTention);

		tensionText = new JTextField();
		tensionText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tensionText.setForeground(Color.WHITE);
		tensionText.setColumns(10);
		tensionText.setBorder(null);
		tensionText.setDocument(new JTextFieldLimit(8));
		tensionText.setBackground(new Color(57, 113, 177));
		tensionText.setBounds(34, 50, 200, 30);
		panel_1.add(tensionText);

		JSeparator separator_9 = new JSeparator();
		separator_9.setBounds(32, 79, 205, 7);
		panel_1.add(separator_9);

		JLabel lblGroupeSanguin = new JLabel("Groupe sanguin *");
		lblGroupeSanguin.setForeground(new Color(32, 33, 35));
		lblGroupeSanguin.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblGroupeSanguin.setBounds(269, 92, 111, 30);
		panel_1.add(lblGroupeSanguin);

		JLabel lblPoids = new JLabel("Poids *");
		lblPoids.setForeground(new Color(32, 33, 35));
		lblPoids.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPoids.setBounds(266, 22, 111, 30);
		panel_1.add(lblPoids);

		poidsText = new JTextField();
		poidsText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		poidsText.setForeground(Color.WHITE);
		poidsText.setColumns(10);
		poidsText.setBorder(null);
		poidsText.setDocument(new JTextFieldLimit(3));
		poidsText.setBackground(new Color(57, 113, 177));
		poidsText.setBounds(271, 50, 200, 30);
		panel_1.add(poidsText);

		JSeparator separator_12 = new JSeparator();
		separator_12.setBounds(269, 79, 205, 7);
		panel_1.add(separator_12);

		groupeSangBox = new JComboBox<String> ();
		groupeSangBox.setBackground(new Color(250, 239, 197));
		groupeSangBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		groupeSangBox.setModel(new DefaultComboBoxModel<String>(new String[] {"A +", "A -", "B +", "B -", "AB +", "AB -", "O +", "O -"}));
		groupeSangBox.setBounds(269, 127, 200, 30);
		groupeSangBox.setSelectedIndex(-1);
		panel_1.add(groupeSangBox);

		lblTauxGlycemie = new JLabel("Taux glyc\u00E9mie");
		lblTauxGlycemie.setEnabled(false);
		lblTauxGlycemie.setBounds(508, 22, 235, 30);
		panel_1.add(lblTauxGlycemie);
		lblTauxGlycemie.setForeground(new Color(32, 33, 35));
		lblTauxGlycemie.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		glycemieText = new JTextField();
		glycemieText.setEditable(false);
		glycemieText.setBounds(508, 50, 200, 30);
		panel_1.add(glycemieText);
		glycemieText.setDocument(new JTextFieldLimit(5));
		glycemieText.setForeground(Color.WHITE);
		glycemieText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		glycemieText.setColumns(10);
		glycemieText.setBorder(null);
		glycemieText.setBackground(new Color(57, 113, 177));

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(506, 79, 205, 7);
		panel_1.add(separator_3);

		JLabel lblTauxCholestrol = new JLabel("Vue ( / 10) *");
		lblTauxCholestrol.setForeground(new Color(32, 33, 35));
		lblTauxCholestrol.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblTauxCholestrol.setBounds(506, 92, 235, 30);
		panel_1.add(lblTauxCholestrol);

		vueBox = new JComboBox<String>();
		vueBox.setBackground(new Color(250, 239, 197));
		vueBox.setModel(new DefaultComboBoxModel<String>(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"}));
		vueBox.setSelectedIndex(-1);
		vueBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		vueBox.setBounds(506, 127, 200, 30);
		panel_1.add(vueBox);

		tailleSepa = new JSeparator();
		tailleSepa.setBounds(33, 150, 205, 7);
		panel_1.add(tailleSepa);

		tailleText = new JTextField();
		tailleText.setEditable(false);
		tailleText.setForeground(Color.WHITE);
		tailleText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		tailleText.setColumns(10);
		tailleText.setBorder(null);
		tailleText.setBackground(new Color(57, 113, 177));
		tailleText.setBounds(33, 121, 200, 30);
		panel_1.add(tailleText);

		groupeSangSepa = new JSeparator();
		groupeSangSepa.setBounds(269, 150, 205, 7);
		panel_1.add(groupeSangSepa);

		groupeSangText = new JTextField();
		groupeSangText.setEditable(false);
		groupeSangText.setForeground(Color.WHITE);
		groupeSangText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		groupeSangText.setColumns(10);
		groupeSangText.setBorder(null);
		groupeSangText.setBackground(new Color(57, 113, 177));
		groupeSangText.setBounds(269, 121, 200, 30);
		panel_1.add(groupeSangText);

		vueSepa = new JSeparator();
		vueSepa.setBounds(506, 150, 205, 7);
		panel_1.add(vueSepa);

		vueText = new JTextField();
		vueText.setEditable(false);
		vueText.setForeground(Color.WHITE);
		vueText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		vueText.setColumns(10);
		vueText.setBorder(null);
		vueText.setBackground(new Color(57, 113, 177));
		vueText.setBounds(505, 121, 200, 30);
		panel_1.add(vueText);

		editablePanel = new JPanel();
		editablePanel.setBackground(new Color(57, 113, 177));
		editablePanel.setLayout(null);
		editablePanel.setBorder(new TitledBorder(null, "Maladies Chroniques : ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		editablePanel.setBounds(10, 283, 746, 263);
		panelPrincipale.add(editablePanel);
		cardVascBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		cardVascBox.addActionListener(action());

		cardVascBox.setToolTipText("cardiaques et vasculaires");
		cardVascBox.setSelectedIndex(-1);
		cardVascBox.setEnabled(false);
		cardVascBox.setBounds(186, 57, 235, 30);
		editablePanel.add(cardVascBox);

		lblCardiovasculaire = new JLabel("Cardiaques et vasculaires : ");
		lblCardiovasculaire.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblCardiovasculaire.setEnabled(false);
		lblCardiovasculaire.setBounds(16, 57, 172, 36);
		editablePanel.add(lblCardiovasculaire);
		endocBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		endocBox.addActionListener(action());

		endocBox.setToolTipText("endocriniennes");
		endocBox.setEnabled(false);
		endocBox.setBounds(186, 91, 235, 30);
		editablePanel.add(endocBox);

		lblEndocriniennes = new JLabel("Endocriniennes :");
		lblEndocriniennes.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblEndocriniennes.setEnabled(false);
		lblEndocriniennes.setBounds(16, 91, 172, 36);
		editablePanel.add(lblEndocriniennes);
		orlBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		orlBox.addActionListener(action());

		orlBox.setToolTipText("respiratoires et orl");
		orlBox.setEnabled(false);
		orlBox.setBounds(186, 125, 235, 30);
		editablePanel.add(orlBox);

		lblRespiratoiresEtOrl = new JLabel("Respiratoires et ORL  :");
		lblRespiratoiresEtOrl.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblRespiratoiresEtOrl.setEnabled(false);
		lblRespiratoiresEtOrl.setBounds(16, 125, 172, 36);
		editablePanel.add(lblRespiratoiresEtOrl);
		rhumBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		rhumBox.addActionListener(action());

		rhumBox.setToolTipText("rhumatologiques");
		rhumBox.setEnabled(false);
		rhumBox.setBounds(186, 159, 235, 30);
		editablePanel.add(rhumBox);

		lblRhumatologiques = new JLabel("Rhumatologiques :");
		lblRhumatologiques.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblRhumatologiques.setEnabled(false);
		lblRhumatologiques.setBounds(16, 159, 172, 36);
		editablePanel.add(lblRhumatologiques);
		infcChroBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		infcChroBox.addActionListener(action());

		infcChroBox.setToolTipText("infectieuses chroniques");
		infcChroBox.setEnabled(false);
		infcChroBox.setBounds(186, 192, 235, 30);
		editablePanel.add(infcChroBox);

		lblInfectieusesChroniques = new JLabel("Infectieuses chroniques :");
		lblInfectieusesChroniques.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblInfectieusesChroniques.setEnabled(false);
		lblInfectieusesChroniques.setBounds(16, 193, 172, 36);
		editablePanel.add(lblInfectieusesChroniques);

		maladieChroniqueCheck = new JCheckBox("Atteint de maladie chronique");
		maladieChroniqueCheck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				active(maladieChroniqueCheck.isSelected());
			}
		});
		maladieChroniqueCheck.setFont(new Font("Century Gothic", Font.BOLD, 13));
		maladieChroniqueCheck.setBounds(16, 24, 220, 30);
		editablePanel.add(maladieChroniqueCheck);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane.setBounds(433, 43, 292, 165);
		editablePanel.add(scrollPane);

		list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(list.getSelectedIndex() != -1) {
					supprimer.setEnabled(true);
				}

			}
		});
		list.setSelectionBackground(new Color(250, 239, 197));
		list.setSelectionForeground(new Color(48, 49, 52));
		list.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		list.setBorder(null);

		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBackground(new Color(57, 113, 177));
		scrollPane.setViewportView(list);

		lblNewLabel = new JLabel("Liste des maladies chroniques du patient");
		lblNewLabel.setEnabled(false);
		lblNewLabel.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNewLabel.setBounds(440, 10, 257, 30);
		editablePanel.add(lblNewLabel);
		ajoute.setEnabled(false);

		ajoute.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(boxSelected() != null) {
					maladies.addElement(boxSelected().getSelectedItem().toString());
					boxSelected().removeItem(boxSelected().getSelectedItem().toString());
					remplirList(maladies);
					miseAjourMaladies();
				}

				ajoute.setEnabled(false);
				supprimer.setEnabled(false);
			}

		});
		ajoute.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajoute.setBounds(459, 215, 35, 28);
		ajoute.setBackground(new Color(250, 239, 197));
		editablePanel.add(ajoute);

		supprimer = new JButton();
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(list.getSelectedIndex() != -1) {
					if(list.getSelectedValue().equals(Endocrin.Diabete.getNom())) {
						glycemieText.setEditable(false);
					}
					maladies.removeElement(list.getSelectedValue());
					remplirList(maladies);
					miseAjourMaladies();
				}

				supprimer.setEnabled(false);
			}
		});
		supprimer.setEnabled(false);
		supprimer.setIcon(new ImageIcon("Images/Outils/supprimer.png"));
		supprimer.setBackground(new Color(250, 239, 197));
		supprimer.setBounds(500, 215, 35, 28);
		editablePanel.add(supprimer);

		JPanel panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(32, 33, 35)));
		panel_4.setBackground(new Color(57, 113, 177));
		panel_4.setBounds(764, 422, 413, 166);
		panelPrincipale.add(panel_4);

		JLabel lblAnteced = new JLabel("Ant\u00E9c\u00E9dent m\u00E9dical ");
		lblAnteced.setForeground(new Color(32, 33, 35));
		lblAnteced.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblAnteced.setBounds(15, 5, 134, 30);
		panel_4.add(lblAnteced);

		antMediText = new JTextArea();
		antMediText.setLineWrap(true);
		antMediText.setForeground(Color.WHITE);
		antMediText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		antMediText.setBorder(null);
		antMediText.setDocument(new JTextFieldLimit(100));
		antMediText.setBackground(new Color(57, 113, 177));
		antMediText.setBounds(30, 35, 365, 114);
		panel_4.add(antMediText);
		miseAjourMaladies();
		remplirList(maladies);
		ajoute.setEnabled(false);

		sauvegarder = new JButton("Sauvegarder");
		sauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(vide()) {
					MessageDialog.showMessageDialog(DossierPatient.this, "Veuillez remplir les champs obligatoires", "Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				if(!maladieRempli()) {
					MessageDialog.showMessageDialog(DossierPatient.this, "Veuillez specifier les maladies du patient ou bien decouchez la case correspondante", "Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				if(!diabeteRempli()) {
					MessageDialog.showMessageDialog(DossierPatient.this, "Veuillez indiquer le taux de glycemie du patient", "Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				PatientDetaille patientDetailleNV = new PatientDetaille(patientDetaille,tailleBox.getSelectedItem().toString(), poidsText.getText(),
						groupeSangBox.getSelectedItem().toString(), tensionText.getText(),
						maladieChroniqueCheck.isSelected(),vecMal(CardioVasculaire.values()),
						vecMal(Endocrin.values()), vecMal(Orl.values()), vecMal(Rhumatolog.values()),
						vecMal(InfectieusesChroniques.values()), autresMaladies,
						glycemieText.getText(),vueBox.getSelectedItem().toString(),antMediText.getText());

				if(patientDetaille.isDossierAjoute()) {
					MessageDialog.showMessageDialog(DossierPatient.this, "Modification effectue","Succes",MessageDialog.MESSAGE_SUCCES);
				}
				else {	
					MessageDialog.showMessageDialog(DossierPatient.this, "Ajout effectue","Succes",MessageDialog.MESSAGE_SUCCES);
				}

				((Docteur)doc).mettreAJour(patientDetaille, patientDetailleNV);
				chargerPatient(patientDetaille);
				editable(false);
				if(!patientDetaille.isMaladeChronique()) {
					listaffichage.setListData(new Vector<String>());
					majListeAutre(autresMaladies);
				}
			}
		});
		sauvegarder.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		sauvegarder.setBackground(new Color(250, 239, 197));
		sauvegarder.setBounds(203, 555, 144, 31);
		panelPrincipale.add(sauvegarder);

		JPanel autrPan = new JPanel();
		autrPan.setLayout(null);
		autrPan.setBorder(new TitledBorder(null, "Autre maladie chronique :", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(32, 33, 35)));
		autrPan.setBackground(new Color(57, 113, 177));
		autrPan.setBounds(764, 113, 413, 307);
		panelPrincipale.add(autrPan);

		nomAutre = new JLabel("Nom de la maladie");
		nomAutre.setForeground(new Color(32, 33, 35));
		nomAutre.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomAutre.setBounds(20, 22, 130, 30);
		autrPan.add(nomAutre);

		nomAutreText = new JTextField();

		nomAutreText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				ajouterAutre.setEnabled(!nomAutreText.getText().trim().equals(""));
				maladieDejaExiste.setVisible(false);

			}
		});
		nomAutreText.setForeground(Color.WHITE);
		nomAutreText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomAutreText.setColumns(10);
		nomAutreText.setBorder(null);
		nomAutreText.setBackground(new Color(57, 113, 177));
		nomAutreText.setBounds(25, 50, 360, 30);
		autrPan.add(nomAutreText);

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(25, 79, 360, 7);
		autrPan.add(separator_5);

		comAutre = new JLabel("Commentaire");
		comAutre.setForeground(new Color(32, 33, 35));
		comAutre.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		comAutre.setBounds(20, 86, 109, 30);
		autrPan.add(comAutre);

		comAutreText = new JTextField();
		comAutreText.setForeground(Color.WHITE);
		comAutreText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		comAutreText.setColumns(10);
		comAutreText.setBorder(null);
		comAutreText.setBackground(new Color(57, 113, 177));
		comAutreText.setBounds(25, 114, 360, 30);
		autrPan.add(comAutreText);

		JSeparator separator_6 = new JSeparator();
		separator_6.setBounds(25, 143, 360, 7);
		autrPan.add(separator_6);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setBounds(21, 187, 370, 108);
		autrPan.add(scrollPane_1);

		list_autre = new JList<String>();
		list_autre.addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(list_autre.getSelectedIndex() != -1) {
					supprimerAutre.setEnabled(true);
					nomAutreText.setText(list_autre.getSelectedValue());
					for(AutreMaladieChronique autre : autresMaladies) {
						if(autre.getNom().equals(list_autre.getSelectedValue())) {
							comAutreText.setText(autre.getCommentaire());
							break;
						}
					}
				}
			}

			
		});
		list_autre.setSelectionBackground(new Color(250, 239, 197));
		list_autre.setSelectionForeground(new Color(48, 49, 52));
		list_autre.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		list_autre.setBorder(null);
		list_autre.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list_autre.setBackground(new Color(57, 113, 177));
		scrollPane_1.setViewportView(list_autre);

		ajouterAutre = new JButton();
		ajouterAutre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!nomAutreText.getText().trim().equals("")) {
					boolean existe = false;
					int i = 0; 
					while(i < autresMaladies.size() && !existe) {
						existe = autresMaladies.get(i).getNom().equals(nomAutreText.getText().trim()) ;
						i++;
					}
					if(!existe) {
						autresMaladies.addElement(new AutreMaladieChronique(nomAutreText.getText().trim(),
								comAutreText.getText().trim(), patientDetaille.getCode()));
						majListeAutre(autresMaladies);
					}
					else {
						maladieDejaExiste.setVisible(true);
					}
				}
				nomAutreText.setText("");
				comAutreText.setText("");
				ajouterAutre.setEnabled(false);
				supprimerAutre.setEnabled(false);
			}
		});
		ajouterAutre.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajouterAutre.setEnabled(false);
		ajouterAutre.setBackground(new Color(250, 239, 197));
		ajouterAutre.setBounds(309, 155, 35, 28);
		autrPan.add(ajouterAutre);

		supprimerAutre = new JButton();
		supprimerAutre.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(list_autre.getSelectedIndex() != -1) {
					for(AutreMaladieChronique autre : autresMaladies) {
						if(autre.getNom().equals(list_autre.getSelectedValue())) {
							autresMaladies.removeElement(autre);
							break;
						}
					}
					majListeAutre(autresMaladies);
				}
				nomAutreText.setText("");
				comAutreText.setText("");
				supprimerAutre.setEnabled(false);
				ajouterAutre.setEnabled(false);
			}
		});
		supprimerAutre.setIcon(new ImageIcon("Images/Outils/supprimer.png"));
		supprimerAutre.setEnabled(false);
		supprimerAutre.setBackground(new Color(250, 239, 197));
		supprimerAutre.setBounds(350, 155, 35, 28);
		autrPan.add(supprimerAutre);

		maladieDejaExiste = new JLabel("Maladie d\u00E9ja saisie");
		maladieDejaExiste.setVisible(false);
		maladieDejaExiste.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		maladieDejaExiste.setBounds(30, 155, 129, 30);
		autrPan.add(maladieDejaExiste);


		codeText.setText(String.valueOf(patientDetaille.getCode()));
		nomText.setText(patientDetaille.getNom());
		prenomText.setText(patientDetaille.getPrenom());

		modifier = new JButton("Modifier");
		modifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editable(true);
			}
		});
		modifier.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		modifier.setBackground(new Color(250, 239, 197));
		modifier.setBounds(39, 555, 144, 31);
		panelPrincipale.add(modifier);

		nonEditablePanel = new JPanel();
		panelPrincipale.add(nonEditablePanel);
		nonEditablePanel.setBackground(new Color(57, 113, 177));
		nonEditablePanel.setLayout(null);
		nonEditablePanel.setBorder(new TitledBorder(null, "Maladies Chroniques : ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		nonEditablePanel.setBounds(10, 283, 746, 263);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setBounds(144, 45, 458, 173);
		nonEditablePanel.add(scrollPane1);

		listaffichage = new JList<String>();
		listaffichage.setSelectionBackground(new Color(250, 239, 197));
		listaffichage.setSelectionForeground(new Color(48, 49, 52));
		listaffichage.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		listaffichage.setBorder(null);

		listaffichage.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listaffichage.setBackground(new Color(57, 113, 177));
		scrollPane1.setViewportView(listaffichage);

		annuler = new JButton("Annuler");
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				editable(false);

				chargerPatient(patientDetaille);
			}
		});
		annuler.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		annuler.setBackground(new Color(250, 239, 197));
		annuler.setBounds(39, 555, 144, 31);
		panelPrincipale.add(annuler);

		if(patientDetaille.isDossierAjoute()) {
			editable(false);
			chargerPatient(patientDetaille);
		}
		else {
			editable(true);
		}
		if(!maladies.contains(Endocrin.Diabete.getNom())) {
			glycemieText.setEditable(false);
		}
	}
	private void majListeAutre(Vector<AutreMaladieChronique> autresMaladies) {
		list_autre.removeAll();  
		Vector<String> noms = new Vector<String>();
		for(AutreMaladieChronique autre : autresMaladies) {
			noms.addElement(autre.getNom());	
		}
		list_autre.setListData(noms);
	}
	private boolean vide() {
		return tailleBox.getSelectedIndex() == -1 || poidsText.getText().trim().equals("") || tensionText.getText().trim().equals("")
				|| groupeSangBox.getSelectedIndex() == -1 || vueBox.getSelectedIndex() == -1;
	}

	private boolean maladieRempli() {
		if(maladieChroniqueCheck.isSelected() && maladies.isEmpty() && autresMaladies.isEmpty()) {
			return false;
		}
		return true;
	}

	private boolean diabeteRempli() {
		if(maladies.contains(Endocrin.Diabete.getNom()) && glycemieText.getText().trim().equals("")) {
			return false;
		}
		return true;
	}

	private void remplirList(Vector<String> vect) {
		list.removeAll();
		list.setListData(vect);
		glycemie();
	}

	private void active(boolean selected) {
		cardVascBox.setEnabled(selected);
		endocBox.setEnabled(selected);
		orlBox.setEnabled(selected);
		rhumBox.setEnabled(selected);
		infcChroBox.setEnabled(selected);
		list.setEnabled(selected);
		lblCardiovasculaire.setEnabled(selected);
		lblEndocriniennes.setEnabled(selected);
		lblRespiratoiresEtOrl.setEnabled(selected);
		lblRhumatologiques.setEnabled(selected);
		lblInfectieusesChroniques.setEnabled(selected);
		maladies = new Vector<String>();
		miseAjourMaladies();
		remplirList(maladies);
		ajoute.setEnabled(false);
		supprimer.setEnabled(false);
		nomAutreText.setText("");
		nomAutreText.setEditable(selected);
		comAutreText.setText("");
		comAutreText.setEditable(selected);
		ajouterAutre.setEnabled(false);
		supprimerAutre.setEnabled(false);
		nomAutre.setEnabled(selected);
		comAutre.setEnabled(selected);
		autresMaladies = new Vector<AutreMaladieChronique>();
		majListeAutre(autresMaladies);
		lblNewLabel.setEnabled(selected);
	}
	public void miseAjourMaladies() {
		miseAJourBox(cardVascBox,CardioVasculaire.values());
		miseAJourBox(endocBox,Endocrin.values());
		miseAJourBox(orlBox,Orl.values());
		miseAJourBox(rhumBox,Rhumatolog.values());
		miseAJourBox(infcChroBox,InfectieusesChroniques.values());
	}
	private void miseAJourBox(JComboBox<String> box,MaladiesInterface[] listeMaladie) {
		box.removeAllItems();
		for(MaladiesInterface mal : listeMaladie) {
			if(!maladies.contains(mal.getNom())) {
				box.addItem(mal.getNom());
			}
		}
		box.setSelectedIndex(-1);
		box.setBackground(new Color(250, 239, 197));
		ajoute.setEnabled(false);
	}
	private JComboBox<String> boxSelected() {
		if(cardVascBox.getSelectedIndex() != -1) 
			return cardVascBox;
		if(endocBox.getSelectedIndex() != -1)
			return endocBox;
		if(orlBox.getSelectedIndex() != -1)
			return orlBox;
		if(rhumBox.getSelectedIndex() != -1)
			return rhumBox;
		if(infcChroBox.getSelectedIndex() != -1)
			return infcChroBox;	
		return null;
	}
	private ActionListener action() {
		return new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				@SuppressWarnings("unchecked")
				JComboBox<String> box = ((JComboBox<String>) e.getSource());
				if(box.getSelectedIndex() != -1) {
					String a = box.getSelectedItem().toString();
					cardVascBox.setSelectedIndex(-1);
					endocBox.setSelectedIndex(-1);
					orlBox.setSelectedIndex(-1);
					rhumBox.setSelectedIndex(-1);
					infcChroBox.setSelectedIndex(-1);
					box.setSelectedItem(a);
					ajoute.setEnabled(true);
				}
			}
		};
	}
	public void editable(boolean etat) {
		tensionText.setEditable(etat);
		poidsText.setEditable(etat);
		glycemieText.setEditable(etat);
		tailleBox.setVisible(etat);
		groupeSangBox.setVisible(etat);
		vueBox.setVisible(etat);
		tailleText.setVisible(!etat);
		groupeSangText.setVisible(!etat);
		vueText.setVisible(!etat);
		tailleSepa.setVisible(!etat);
		groupeSangSepa.setVisible(!etat);
		vueSepa.setVisible(!etat);
		editablePanel.setVisible(etat);
		nonEditablePanel.setVisible(!etat);
		modifier.setVisible(!etat);
		sauvegarder.setVisible(etat);
		antMediText.setEditable(etat);
		annuler.setVisible(etat);
		ajouterAutre.setVisible(etat);
		supprimerAutre.setVisible(etat);
		nomAutreText.setText("");
		comAutreText.setText("");
		nomAutreText.setEditable(maladieChroniqueCheck.isSelected() && etat);
		comAutreText.setEditable(maladieChroniqueCheck.isSelected() && etat);
		nomAutre.setEnabled(maladieChroniqueCheck.isSelected() && etat);
		comAutre.setEnabled(maladieChroniqueCheck.isSelected() && etat);
		list_autre.clearSelection();
	}

	private void chargerPatient(PatientDetaille patientDetaille) {
		tensionText.setText(patientDetaille.getTaille());
		poidsText.setText(patientDetaille.getPoids());
		tailleBox.setSelectedItem(patientDetaille.getTaille());
		tailleText.setText(patientDetaille.getTaille());
		groupeSangBox.setSelectedItem(patientDetaille.getGroupeSang());
		groupeSangText.setText(patientDetaille.getGroupeSang());
		vueBox.setSelectedItem(patientDetaille.getVue());
		vueText.setText(patientDetaille.getVue());
		antMediText.setText(patientDetaille.getAntMedi());

		maladies.removeAllElements();
		autresMaladies = new Vector<AutreMaladieChronique>();

		if(patientDetaille.isMaladeChronique()) {
			if(patientDetaille.getMaladies().getCardio() != null) {
				maladies.addAll(patientDetaille.getMaladies().getCardio());
			}
			if(patientDetaille.getMaladies().getEndocrin() != null) {
				maladies.addAll(patientDetaille.getMaladies().getEndocrin());
			}
			if(patientDetaille.getMaladies().getOrl() != null) {
				maladies.addAll(patientDetaille.getMaladies().getOrl());
			}
			if(patientDetaille.getMaladies().getRhumato() != null) {
				maladies.addAll(patientDetaille.getMaladies().getRhumato());
			}
			if(patientDetaille.getMaladies().getInfectChro() != null) {
				maladies.addAll(patientDetaille.getMaladies().getInfectChro());
			}
			if(patientDetaille.getMaladies().getAutreMaladie() != null) {
				autresMaladies.addAll(patientDetaille.getMaladies().getAutreMaladie());
			}
		}
		maladieChroniqueCheck.setSelected(patientDetaille.isMaladeChronique());
		cardVascBox.setEnabled(patientDetaille.isMaladeChronique());
		endocBox.setEnabled(patientDetaille.isMaladeChronique());
		orlBox.setEnabled(patientDetaille.isMaladeChronique());
		rhumBox.setEnabled(patientDetaille.isMaladeChronique());
		infcChroBox.setEnabled(patientDetaille.isMaladeChronique());
		list.setEnabled(patientDetaille.isMaladeChronique());
		lblCardiovasculaire.setEnabled(patientDetaille.isMaladeChronique());
		lblEndocriniennes.setEnabled(patientDetaille.isMaladeChronique());
		lblRespiratoiresEtOrl.setEnabled(patientDetaille.isMaladeChronique());
		lblRhumatologiques.setEnabled(patientDetaille.isMaladeChronique());
		lblInfectieusesChroniques.setEnabled(patientDetaille.isMaladeChronique());
		lblNewLabel.setEnabled(patientDetaille.isMaladeChronique());
		nomAutreText.setEditable(patientDetaille.isMaladeChronique() && !modifier.isVisible());
		comAutreText.setEditable(patientDetaille.isMaladeChronique() && !modifier.isVisible());
		nomAutre.setEnabled(patientDetaille.isMaladeChronique());
		comAutre.setEnabled(patientDetaille.isMaladeChronique());
		list_autre.removeAll();  
		Vector<String> noms = new Vector<String>();
		for(AutreMaladieChronique autre : autresMaladies) {
			noms.addElement(autre.getNom());	
		}
		list_autre.setListData(noms);
		if(patientDetaille.isMaladeChronique()) {
			miseAjourMaladies();
			if(maladies.contains(Endocrin.Diabete.getNom())) {
				lblTauxGlycemie.setEnabled(true);
			}
			list.removeAll();
			list.setListData(maladies);
			listaffichage.removeAll();
			glycemieText.setText(patientDetaille.getTauxGlsm());
			listaffichage.setListData(maladies);
		}
	}

	private Vector<String> vecMal(MaladiesInterface[] m) {
		Vector<String> mala = new Vector<String>();
		for(MaladiesInterface mal : m) {
			if(maladies.contains(mal.getNom())) {
				mala.add(mal.getNom());
			}
		}
		return mala;
	}
	private void glycemie() {
		if(maladies.contains(Endocrin.Diabete.getNom())) {
			lblTauxGlycemie.setEnabled(true);
			glycemieText.setEditable(true);
			lblTauxGlycemie.setText("Taux glycemie * ");
		}
		else {
			lblTauxGlycemie.setEnabled(false);
			lblTauxGlycemie.setText("Taux glycemie");
			glycemieText.setEditable(false);
			glycemieText.setText("");
		}
	}
}