package authentification;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import fenetresPrincipales.FenetreDocteur;
import fenetresPrincipales.FenetreSecretaire;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import utilisateur.Types;
import utilisateur.Utilisateur;

public class Authentification extends JFrame {

	private static final long serialVersionUID = 2521768826099126442L;
	private JPanel contentPane;
	private JTextField utilisaText;
	private JPasswordField mdpText;
	private static int cacher = 1; 
	private JComboBox<String> typeBox;

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try { 
					Authentification frame = new Authentification();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Authentification() {
		setTitle("Medic : Authentification");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 701, 386);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel panel = new Panel();
		panel.setBackground(new Color(48, 49, 52));
		panel.setBounds(0, 0, 305, 358);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon("Images/Outils/facebook.png"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(38, 253, 44, 28);
		panel.add(label);

		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon("Images/Outils/site.png"));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(38, 275, 44, 28);
		panel.add(label_1);

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setIcon(new ImageIcon("Images/Outils/gmail.png"));
		lblNewLabel_3.setBounds(38, 232, 44, 28);
		panel.add(lblNewLabel_3);

		JLabel lblNewLabel = new JLabel("Un logiciel pour vous");
		lblNewLabel.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		lblNewLabel.setForeground(new Color(62,122,189));
		lblNewLabel.setBounds(98, 133, 147, 20);
		panel.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Medic");
		lblNewLabel_1.setForeground(new Color(250,239,197));
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_1.setFont(new Font("Century", Font.BOLD, 65));
		lblNewLabel_1.setBounds(28, 38, 228, 122);
		panel.add(lblNewLabel_1);

		JLabel lblContacteznous = new JLabel("Contactez-nous");
		lblContacteznous.setBounds(28, 206, 168, 30);
		panel.add(lblContacteznous);
		lblContacteznous.setForeground(new Color(250, 239, 197));
		lblContacteznous.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));

		JLabel lblMediccontactgmailcom = new JLabel("Medic.contact@gmail.com");
		lblMediccontactgmailcom.setForeground(new Color(250, 239, 197));
		lblMediccontactgmailcom.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		lblMediccontactgmailcom.setBounds(88, 230, 168, 30);
		panel.add(lblMediccontactgmailcom);

		JLabel lblMdic = new JLabel("M\u00E9dic");
		lblMdic.setForeground(new Color(250, 239, 197));
		lblMdic.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		lblMdic.setBounds(88, 251, 139, 30);
		panel.add(lblMdic);

		JLabel lblWwwmediccom = new JLabel("www.Medic.com");
		lblWwwmediccom.setForeground(new Color(250, 239, 197));
		lblWwwmediccom.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		lblWwwmediccom.setBounds(88, 273, 139, 30);
		panel.add(lblWwwmediccom);

		JSeparator separator = new JSeparator();
		separator.setBounds(17, 201, 260, 8);
		panel.add(separator);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(17, 303, 260, 8);
		panel.add(separator_2);

		JLabel lblNewLabel_2 = new JLabel("\u00A9 2020   |   Version 1.0");
		lblNewLabel_2.setFont(new Font("Century", Font.PLAIN, 12));
		lblNewLabel_2.setForeground(new Color(62,122,189));
		lblNewLabel_2.setBounds(27, 315, 250, 16);
		panel.add(lblNewLabel_2);

		JLabel lblToutDroit = new JLabel("Tous droits r\u00E9serv\u00E9s ");
		lblToutDroit.setFont(new Font("Century", Font.PLAIN, 12));
		lblToutDroit.setForeground(new Color(62, 122, 189));
		lblToutDroit.setBounds(27, 332, 125, 16);
		panel.add(lblToutDroit);

		Panel panel_1 = new Panel();
		panel_1.setForeground(Color.BLACK);
		panel_1.setBackground(new Color(57, 113, 177));
		panel_1.setBounds(301, 0, 394, 358);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel affiche = new JLabel();
		affiche.setIcon(new ImageIcon("Images/Outils/afficher.png"));
		affiche.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {

				if(cacher == 1) {
					mdpText.setEchoChar((char)0);
					affiche.setIcon(new ImageIcon("Images/Outils/masquer.png"));
					cacher = 0;
				}
				else {
					mdpText.setEchoChar('*');
					affiche.setIcon(new ImageIcon("Images/Outils/afficher.png"));
					cacher = 1;
				}
			}
		});
		affiche.setBounds(350, 205, 38, 20);
		panel_1.add(affiche);

		JLabel lblUtilisateur = new JLabel("Utilisateur ");
		lblUtilisateur.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblUtilisateur.setForeground(new Color(32, 33, 35));
		lblUtilisateur.setBounds(56, 99, 111, 30);
		panel_1.add(lblUtilisateur);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(56, 153, 290, 7);
		panel_1.add(separator_1);

		utilisaText = new JTextField();
		utilisaText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		utilisaText.setForeground(Color.WHITE);
		utilisaText.setBackground(new Color(57, 113, 177));
		utilisaText.setBorder(null);
		utilisaText.setBounds(56, 122, 300, 30);
		utilisaText.setDocument(new JTextFieldLimit(25));
		panel_1.add(utilisaText);
		utilisaText.setColumns(10);

		JLabel lblMotDePasse = new JLabel("Mot de passe ");
		lblMotDePasse.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblMotDePasse.setForeground(new Color(32, 33, 35));
		lblMotDePasse.setBounds(56, 172, 92, 30);
		panel_1.add(lblMotDePasse);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(56, 225, 290, 7);
		panel_1.add(separator_3);

		JLabel lblType = new JLabel("Type ");
		lblType.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblType.setForeground(new Color(32, 33, 35));
		lblType.setBounds(56, 27, 111, 30);
		panel_1.add(lblType);

		typeBox = new JComboBox<String>();
		typeBox.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		typeBox.setBackground(new Color(250, 239, 197));
		String[] tab = new String[Types.values().length];
		int i = 0;
		for(Types type : Types.values()) {
			tab[ i ] = type.getType();
			i++;
		}
		typeBox.setModel(new DefaultComboBoxModel<String>(tab));
		typeBox.setSelectedIndex(-1);
		typeBox.setBounds(56, 57, 290, 30);
		panel_1.add(typeBox);

		mdpText = new JPasswordField();
		mdpText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		mdpText.setForeground(Color.WHITE);
		mdpText.setBackground(new Color(57, 113, 177));
		mdpText.setBorder(null);
		mdpText.setDocument(new JTextFieldLimit(25));
		mdpText.setBounds(56, 194, 295, 30);
		panel_1.add(mdpText);

		JButton connexionBtn = new JButton("Connexion");
		connexionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(vide()) {
					MessageDialog.showMessageDialog(Authentification.this, "Veuillez remplir tout les formulaires ","Attention",MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				Utilisateur util = Utilisateur.getUtilisateur(typeBox.getSelectedItem().toString(),
						utilisaText.getText().trim(), String.valueOf(mdpText.getPassword()));

				if(!util.connecter()) {
					MessageDialog.showMessageDialog(Authentification.this, "Compte invalide","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				MessageDialog.showMessageDialog(Authentification.this, "Connexion effectuee","Succes",MessageDialog.MESSAGE_SUCCES);
				dispose();
				if(util.getType() == Types.Docteur) {
					FenetreDocteur fendoc = new FenetreDocteur(util);
					fendoc.setVisible(true);
				}
				if(util.getType() == Types.Secretaire) {
					FenetreSecretaire fensec = new FenetreSecretaire(util);
					fensec.setVisible(true);
				}

			}
		});
		connexionBtn.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		connexionBtn.setBackground(new Color(250,239,197));
		connexionBtn.setBounds(56, 276, 274, 30);
		panel_1.add(connexionBtn);
		getRootPane().setDefaultButton(connexionBtn);

		JLabel mdpOublie = new JLabel("Mot de passe oublie ? ");
		mdpOublie.setForeground(new Color(32, 33, 35));
		mdpOublie.setFont(new Font("Century Gothic", Font.ITALIC, 11));
		mdpOublie.setBounds(56, 234, 139, 30);
		mdpOublie.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				mdpOublie.setText("<html><u>Mot de passe oublie ?</u></html>");
				mdpOublie.setFont(new Font("Century Gothic", Font.ITALIC, 12));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				mdpOublie.setText("Mot de passe oublie ?");
				mdpOublie.setFont(new Font("Century Gothic", Font.ITALIC, 11));
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				RecuperationMotPasse recup = new RecuperationMotPasse();
				recup.setVisible(true);
			}
		});
		panel_1.add(mdpOublie);

		JLabel lblVousNavezPas = new JLabel("Vous n'avez pas encore de compte ?");
		lblVousNavezPas.setForeground(new Color(32, 33, 35));
		lblVousNavezPas.setFont(new Font("Century Gothic", Font.ITALIC, 11));
		lblVousNavezPas.setBounds(60, 323, 206, 20);
		panel_1.add(lblVousNavezPas);

		JLabel inscrire = new JLabel("S'inscrire");
		inscrire.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseEntered(MouseEvent e) {
				inscrire.setText("<html><u>S'inscrire</u></html>");
				inscrire.setFont(new Font("Century Gothic", Font.ITALIC, 13));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				inscrire.setText("S'inscrire");
				inscrire.setFont(new Font("Century Gothic", Font.ITALIC, 11));
			}
			@Override
			public void mousePressed(MouseEvent arg0) {
				Inscription inscri = new Inscription(Authentification.this);
				inscri.setVisible(true);
			}
		});
		inscrire.setForeground(new Color(250,239,197));
		inscrire.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		inscrire.setBounds(273, 318, 73, 30);
		panel_1.add(inscrire);
	}

	private boolean vide() {
		String mdp = String.valueOf(mdpText.getPassword());
		return typeBox.getSelectedIndex() == -1 || utilisaText.getText().trim().equals("") || mdp.trim().equals("");
	}
}
