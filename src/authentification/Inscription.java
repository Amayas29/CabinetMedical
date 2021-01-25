package authentification;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import outils.CleAdmin;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import utilisateur.Compte;
import utilisateur.Types;
import utilisateur.Utilisateur;

public class Inscription extends JDialog {

	private static final long serialVersionUID = -6125164971501501611L;
	private JPanel contentPane;
	private JTextField utilisaText;
	private JPasswordField mdpText;
	private JTextField nomText;
	private JTextField prenomText;
	private JTextField cleText;
	private JPasswordField confirmerMdpText;
	private JLabel confirme = new JLabel();
	private JComboBox<String> typeBox;
	private JButton inscrire;
	private JPanel panel_1;
	private static final String CLE_ADMIN = CleAdmin.CLE.getCle();

	public Inscription(JFrame mere) {
		setTitle("M\u00E9dic : Inscription");
		setModal(true);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 500, 650);
		setLocationRelativeTo(mere);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(48, 49, 52));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(57, 113, 177));
		panel.setBounds(57, 77, 380, 490);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label_2 = new JLabel("Type ");
		label_2.setForeground(new Color(32, 33, 35));
		label_2.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_2.setBounds(42, 204, 111, 30);
		panel.add(label_2);

		typeBox = new JComboBox<String>();
		typeBox.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		typeBox.setBackground(new Color(250, 239, 197));
		String[] tab = new String[Types.values().length];
		int i = 0;
		for(Types type : Types.values()) {
			tab[ i ] = type.getType();
			i++;
		}
		typeBox.setModel(new DefaultComboBoxModel<String>(tab));
		typeBox.setSelectedIndex(-1);
		typeBox.setBounds(42, 234, 295, 30);
		panel.add(typeBox);

		JLabel label_3 = new JLabel("Utilisateur ");
		label_3.setForeground(new Color(32, 33, 35));
		label_3.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_3.setBounds(42, 271, 111, 30);
		panel.add(label_3);

		utilisaText = new JTextField();
		utilisaText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		utilisaText.setDocument(new JTextFieldLimit(25));
		utilisaText.setForeground(Color.WHITE);
		utilisaText.setColumns(10);
		utilisaText.setBorder(null);
		utilisaText.setBackground(new Color(57, 113, 177));
		utilisaText.setBounds(42, 294, 300, 30);
		panel.add(utilisaText);

		JLabel label_4 = new JLabel("Mot de passe ");
		label_4.setForeground(new Color(32, 33, 35));
		label_4.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_4.setBounds(42, 336, 111, 30);
		panel.add(label_4);

		mdpText = new JPasswordField();
		mdpText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		mdpText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String mdp = String.valueOf(mdpText.getPassword());
				String cmdp = String.valueOf(confirmerMdpText.getPassword());
				if(cmdp.equals(mdp)) {
					confirme.setIcon(new ImageIcon("Images/Outils/correct.png"));
				}
				else {
					confirme.setIcon(new ImageIcon("Images/Outils/invalide.png"));
				}
				mdp = String.valueOf(mdpText.getPassword()).trim();
				if(mdp.equals("")) {
					confirme.setIcon(null);
					confirmerMdpText.setText("");
					panel_1.setVisible(false);
				}
				else {
					panel_1.setVisible(true);
				}
			}
		});
		mdpText.setDocument(new JTextFieldLimit(25));
		mdpText.setForeground(Color.WHITE);
		mdpText.setBorder(null);
		mdpText.setBackground(new Color(57, 113, 177));
		mdpText.setBounds(42, 362, 295, 30);
		panel.add(mdpText);

		JSeparator separator = new JSeparator();
		separator.setBounds(42, 325, 290, 7);
		panel.add(separator);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(42, 393, 290, 7);
		panel.add(separator_1);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setForeground(new Color(32, 33, 35));
		lblNom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNom.setBounds(42, 78, 111, 30);
		panel.add(lblNom);

		nomText = new JTextField();
		nomText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		nomText.setDocument(new JTextFieldLimit(25));
		nomText.setForeground(Color.WHITE);
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setBackground(new Color(57, 113, 177));
		nomText.setBounds(42, 96, 300, 30);
		panel.add(nomText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(42, 127, 290, 7);
		panel.add(separator_2);

		JLabel lblPrenom = new JLabel("Pr\u00E9nom");
		lblPrenom.setForeground(new Color(32, 33, 35));
		lblPrenom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPrenom.setBounds(42, 138, 111, 30);
		panel.add(lblPrenom);

		prenomText = new JTextField();
		prenomText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		prenomText.setDocument(new JTextFieldLimit(25));
		prenomText.setForeground(Color.WHITE);
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(57, 113, 177));
		prenomText.setBounds(42, 161, 300, 30);
		panel.add(prenomText);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(42, 192, 290, 7);
		panel.add(separator_3);

		JLabel lblClAdmin = new JLabel("Cl\u00E9 administrateur ");
		lblClAdmin.setForeground(new Color(32, 33, 35));
		lblClAdmin.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblClAdmin.setBounds(42, 6, 142, 30);
		panel.add(lblClAdmin);

		cleText = new JTextField();
		cleText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		cleText.setDocument(new JTextFieldLimit(25));
		cleText.setForeground(Color.WHITE);
		cleText.setColumns(10);
		cleText.setBorder(null);
		cleText.setBackground(new Color(57, 113, 177));
		cleText.setBounds(42, 29, 300, 30);
		panel.add(cleText);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(42, 60, 290, 7);
		panel.add(separator_4);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(57, 113, 177));
		panel_1.setBounds(42, 404, 295, 64);
		panel.add(panel_1);
		panel_1.setLayout(null);
		panel_1.setVisible(false);

		JLabel lblConfirmerLeMot = new JLabel("Confirmer le mot de passe");
		lblConfirmerLeMot.setBounds(0, 0, 174, 30);
		panel_1.add(lblConfirmerLeMot);
		lblConfirmerLeMot.setForeground(new Color(32, 33, 35));
		lblConfirmerLeMot.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		confirmerMdpText = new JPasswordField();
		confirmerMdpText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		confirmerMdpText.setBounds(0, 26, 295, 30);
		panel_1.add(confirmerMdpText);
		confirmerMdpText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				String mdp = String.valueOf(mdpText.getPassword());
				String cmdp = String.valueOf(confirmerMdpText.getPassword());
				if(cmdp.equals(mdp)) {
					confirme.setIcon(new ImageIcon("Images/Outils/correct.png"));
				}
				else {
					confirme.setIcon(new ImageIcon("Images/Outils/invalide.png"));
				}
			}
		});
		confirmerMdpText.setDocument(new JTextFieldLimit(25));
		confirmerMdpText.setForeground(Color.WHITE);
		confirmerMdpText.setBorder(null);
		confirmerMdpText.setBackground(new Color(57, 113, 177));

		JSeparator separator_5 = new JSeparator();
		separator_5.setBounds(0, 57, 290, 7);
		panel_1.add(separator_5);
		confirme.setHorizontalAlignment(SwingConstants.CENTER);

		confirme.setBounds(340, 437, 23, 30);
		panel.add(confirme);

		JLabel label = new JLabel("Un logiciel pour vous");
		label.setForeground(new Color(62, 122, 189));
		label.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label.setBounds(90, 48, 147, 20);
		contentPane.add(label);

		JLabel label_1 = new JLabel("M\u00E9dic");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 50));
		label_1.setBounds(57, -35, 228, 122);
		contentPane.add(label_1);

		inscrire = new JButton("S'inscrire");
		inscrire.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(!champsRempli()) {
					MessageDialog.showMessageDialog(Inscription.this, "Veuillez remplir tous formulaires", "Erreur", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				if(!cleValide()) {
					MessageDialog.showMessageDialog(Inscription.this, "La cle administrateur neest pas valide", "Erreur", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				if(!supp()) {
					MessageDialog.showMessageDialog(Inscription.this, "Le nom d'utilisateur et le mot de passe doivent contenir plus de 5 caracteres", "Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				if(!memeMdp()) {
					MessageDialog.showMessageDialog(Inscription.this, "Erreur lors de la confirmation du mot de passe", "Erreur", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				
				Utilisateur utilisateur = Utilisateur.getUtilisateur(typeBox.getSelectedItem().toString(), utilisaText.getText().trim(), 
						String.valueOf(mdpText.getPassword()).trim());
				Compte compte = new Compte(utilisateur, nomText.getText().trim(), prenomText.getText().trim());
				if(compte.existe()) {
					MessageDialog.showMessageDialog(Inscription.this, "Nom d'utilisateur deja existant", "Erreur", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				compte.creeCompte();
				MessageDialog.showMessageDialog(Inscription.this, "Compte cree avec succes", "Succes",MessageDialog.MESSAGE_SUCCES);
				dispose();
			}
		});
		inscrire.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		inscrire.setBackground(new Color(250, 239, 197));
		inscrire.setBounds(237, 584, 200, 30);
		contentPane.add(inscrire);
	}

	private boolean champsRempli() {
		return !(cleText.getText().trim().equals("") || nomText.getText().trim().equals("") || prenomText.getText().trim().equals("")
				|| typeBox.getSelectedIndex() == -1 || utilisaText.getText().trim().equals("") || String.valueOf(mdpText.getPassword()).trim().equals("")
				|| String.valueOf(confirmerMdpText.getPassword()).trim().equals(""));
	}

	private boolean memeMdp() {
		return String.valueOf(mdpText.getPassword()).trim().equals(String.valueOf(confirmerMdpText.getPassword()).trim());
	}

	private boolean supp() {
		return utilisaText.getText().trim().length() > 5 && String.valueOf(mdpText.getPassword()).trim().length() > 5;
	}
	private boolean cleValide() {
		return cleText.getText().trim().equals(CLE_ADMIN);
	}
}