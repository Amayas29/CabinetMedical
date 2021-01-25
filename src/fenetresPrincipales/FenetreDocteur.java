package fenetresPrincipales;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import authentification.Authentification;
import fenetresComptes.AdminDialog;
import fenetresComptes.ChangerInfoCompte;
import fenetresComptes.ChangerInfoPerso;
import fenetresComptes.GestionComptes;
import fenetresPatient.AjouterPatient;
import fenetresPatient.RechercherPatient;
import fenetresStatistiques.Statistiques;
import fenetresTemps.GestionRdv;
import outils.CleAdmin;
import outils.ConfirmDialog;
import outils.MessageDialog;
import utilisateur.Utilisateur;

public class FenetreDocteur extends JFrame {

	private static final long serialVersionUID = 6108692526272079160L;
	private static final String CLE = CleAdmin.CLE.getCle();
	private JPanel contentPane;
	private JLabel nomPre;

	public FenetreDocteur(Utilisateur doc) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				int  choix= ConfirmDialog.showConfirmDialog(FenetreDocteur.this, "etes-vous sur de vouloir quitter ?", "Attention", ConfirmDialog.OPTION_OUI_NON,ConfirmDialog.ATTENTION);
				if(choix == ConfirmDialog.OPTION_OUI) {
					dispose();
				}
			}

			@Override
			public void windowActivated(WindowEvent arg0) {
				if(doc.getNomPrenom() != null) {
					nomPre.setText("Bienvenu  ".concat(doc.getNomPrenom()));
				}
			}

		});

		setTitle("Medic : Authentification");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 781, 565);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setLayout(null);
		panel.setBackground(new Color(48, 49, 52));
		panel.setBounds(0, 0, 775, 83);
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
		nomPre = new JLabel();
		if(doc.getNomPrenom() != null) {
			nomPre.setText("Bienvenu  ".concat(doc.getNomPrenom()));
		}
		nomPre.setForeground(new Color(250, 239, 197));
		nomPre.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nomPre.setHorizontalAlignment(SwingConstants.CENTER);
		nomPre.setBounds(600, 11, 176, 20);
		panel.add(nomPre);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(600, 0, 10, 83);
		panel.add(separator);

		JLabel label_2 = new JLabel();
		label_2.setIcon(new ImageIcon("Images/Outils/gmail.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(379, 3, 44, 28);
		panel.add(label_2);

		JLabel label_3 = new JLabel("Medic.contact@gmail.com");
		label_3.setForeground(new Color(250, 239, 197));
		label_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_3.setBounds(429, 1, 168, 30);
		panel.add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("Images/Outils/facebook.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(379, 24, 44, 28);
		panel.add(label_4);

		JLabel label_5 = new JLabel("M\u00E9dic");
		label_5.setForeground(new Color(250, 239, 197));
		label_5.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_5.setBounds(429, 22, 139, 30);
		panel.add(label_5);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon("Images/Outils/site.png"));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(379, 46, 44, 28);
		panel.add(label_6);

		JLabel label_7 = new JLabel("www.Medic.com");
		label_7.setForeground(new Color(250, 239, 197));
		label_7.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_7.setBounds(429, 44, 139, 30);
		panel.add(label_7);

		JSeparator separator_1 = new JSeparator();
		separator_1.setOrientation(SwingConstants.VERTICAL);
		separator_1.setBounds(370, 0, 10, 83);
		panel.add(separator_1);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		menuBar.setBackground(new Color(48, 49, 52));
		menuBar.setBounds(660, 43, 55, 23);

		JMenu outils = new JMenu("Outils");
		outils.setHorizontalAlignment(SwingConstants.CENTER);
		outils.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		outils.setBackground(new Color(48, 49, 52));
		outils.setBorder(null);
		JMenu mnuParam = new JMenu("Parametres");
		mnuParam.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		mnuParam.setIcon(new ImageIcon( "Images/Outils/param.png"));

		JMenuItem mnuParamInfo = new JMenuItem( "Modifier les informations personnelles" );
		mnuParamInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				ChangerInfoPerso infoPe = new ChangerInfoPerso(FenetreDocteur.this, doc);
			}
		});
		mnuParamInfo.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		mnuParamInfo.setIcon( new ImageIcon( "Images/Outils/infoPerso.png" ) );
		mnuParam.add(mnuParamInfo);

		mnuParam.addSeparator();

		JMenuItem mnuParamCompte = new JMenuItem( "Modifier les informations du compte" );
		mnuParamCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				@SuppressWarnings("unused")
				ChangerInfoCompte infoCo = new ChangerInfoCompte(FenetreDocteur.this, doc);

			}
		});
		mnuParamCompte.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		mnuParamCompte.setIcon( new ImageIcon( "Images/Outils/paramCompte.png" ) );
		mnuParam.add(mnuParamCompte);

		JMenuItem mnuDeco = new JMenuItem( "Deconnexion" );
		mnuDeco.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int  choix = ConfirmDialog.showConfirmDialog(FenetreDocteur.this, "etes-vous sur de vouloir quitter ?", "Attention", ConfirmDialog.OPTION_OUI_NON,ConfirmDialog.ATTENTION);
				if(choix == ConfirmDialog.OPTION_OUI) {
					dispose();
				}
			}
		});
		mnuDeco.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		mnuDeco.setIcon( new ImageIcon( "Images/Outils/logout.png" ) );

		outils.add(mnuParam);
		outils.addSeparator();
		outils.add(mnuDeco);
		menuBar.add(outils);

		panel.add(menuBar);

		Panel panel_1 = new Panel();
		panel_1.setLayout(null);
		panel_1.setForeground(Color.BLACK);
		panel_1.setBackground(new Color(57, 113, 177));
		panel_1.setBounds(0, 80, 775, 457);
		contentPane.add(panel_1);

		JLabel lblajout = new JLabel("");
		lblajout.setIcon(new ImageIcon("Images/Fonctions/ajouter.png"));
		lblajout.setHorizontalAlignment(SwingConstants.CENTER);
		lblajout.setBackground(Color.WHITE);
		lblajout.setForeground(Color.WHITE);
		lblajout.setBounds(538, 26, 170, 133);
		panel_1.add(lblajout);

		JLabel lblcomptes = new JLabel("");
		lblcomptes.setIcon(new ImageIcon("Images/Fonctions/Gcompte.png"));
		lblcomptes.setHorizontalAlignment(SwingConstants.CENTER);
		lblcomptes.setBackground(Color.WHITE);
		lblcomptes.setForeground(Color.WHITE);
		lblcomptes.setBounds(538, 241, 170, 133);
		panel_1.add(lblcomptes);

		JLabel lblagenda = new JLabel("");
		lblagenda.setIcon(new ImageIcon("Images/Fonctions/agenda.png"));
		lblagenda.setHorizontalAlignment(SwingConstants.CENTER);
		lblagenda.setBackground(Color.WHITE);
		lblagenda.setForeground(Color.WHITE);
		lblagenda.setBounds(66, 241, 170, 133);
		panel_1.add(lblagenda);

		JLabel lblconsu = new JLabel("");
		lblconsu.setIcon(new ImageIcon("Images/Fonctions/consu.png"));
		lblconsu.setHorizontalAlignment(SwingConstants.CENTER);
		lblconsu.setBackground(Color.WHITE);
		lblconsu.setForeground(Color.WHITE);
		lblconsu.setBounds(66, 26, 170, 133);
		panel_1.add(lblconsu);

		JLabel lblstats = new JLabel("");
		lblstats.setIcon(new ImageIcon("Images/Fonctions/statistique.png"));
		lblstats.setHorizontalAlignment(SwingConstants.CENTER);
		lblstats.setBackground(Color.WHITE);
		lblstats.setForeground(Color.WHITE);
		lblstats.setBounds(302, 241, 170, 133);
		panel_1.add(lblstats);

		JLabel lblrecherche = new JLabel("");
		lblrecherche.setIcon(new ImageIcon("Images/Fonctions/recherche.png"));
		lblrecherche.setHorizontalAlignment(SwingConstants.CENTER);
		lblrecherche.setBackground(Color.WHITE);
		lblrecherche.setForeground(Color.WHITE);
		lblrecherche.setBounds(302, 26, 170, 133);
		panel_1.add(lblrecherche);

		JButton gererCompte = new JButton("G\u00E9rer les comptes ");
		gererCompte.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				int reponse = AdminDialog.showAdminDialog(FenetreDocteur.this, CLE);
				if(reponse == AdminDialog.CORRECT) {
					dispose();
					GestionComptes gCom = new GestionComptes(doc);
					gCom.setVisible(true);
				}
				if(reponse == AdminDialog.SURCHARGE) {
					MessageDialog.showMessageDialog(FenetreDocteur.this, "Vous-avez depassez le nombre d'essais autorises ! Veuillez-vous reconnectez.",
							"Erreur", MessageDialog.MESSAGE_ERREUR);
					dispose();
					Authentification authen = new Authentification();
					authen.setVisible(true);
				}
			}

		});

		gererCompte.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		gererCompte.setBackground(new Color(250, 239, 197));
		gererCompte.setBounds(534, 400, 180, 30);
		panel_1.add(gererCompte);

		JButton consulterStat = new JButton("Consulter les statistiques");
		consulterStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Statistiques stats = new Statistiques(doc);
				stats.setVisible(true);
			}
		});

		consulterStat.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		consulterStat.setBackground(new Color(250, 239, 197));
		consulterStat.setBounds(296, 400, 180, 30);
		panel_1.add(consulterStat);

		JButton consulterAgenda = new JButton("Consulter l'agenda");
		consulterAgenda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GestionRdv agenda = new GestionRdv("Consulter l'agenda", doc, FenetreDocteur.this, true);
				agenda.setVisible(true);
			}
		});

		consulterAgenda.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		consulterAgenda.setBackground(new Color(250, 239, 197));
		consulterAgenda.setBounds(58, 400, 180, 30);
		panel_1.add(consulterAgenda);

		JButton consulterPatient = new JButton("Consulter un patient");
		consulterPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				GestionRdv gstRdv = new GestionRdv("Consulter Patient", doc, FenetreDocteur.this, false);
				gstRdv.setVisible(true);
			}
		});
		consulterPatient.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		consulterPatient.setBackground(new Color(250, 239, 197));
		consulterPatient.setBounds(58, 185, 180, 30);
		panel_1.add(consulterPatient);

		JButton rechercherPatient = new JButton("Rechercher un patient");
		rechercherPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				RechercherPatient recherche = new RechercherPatient(doc,false, null);
				recherche.setVisible(true);
			}
		});

		rechercherPatient.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		rechercherPatient.setBackground(new Color(250, 239, 197));
		rechercherPatient.setBounds(296, 185, 180, 30);
		panel_1.add(rechercherPatient);

		JButton ajouterPatient = new JButton("Ajouter un patient");
		ajouterPatient.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				AjouterPatient ajout = new AjouterPatient(doc);
				ajout.setVisible(true);
			}
		});

		ajouterPatient.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		ajouterPatient.setBackground(new Color(250, 239, 197));
		ajouterPatient.setBounds(534, 185, 180, 30);
		panel_1.add(ajouterPatient);
	}
}