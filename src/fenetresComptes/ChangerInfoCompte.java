package fenetresComptes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import outils.JTextFieldLimit;
import outils.MessageDialog;
import utilisateur.Compte;
import utilisateur.Utilisateur;

public class ChangerInfoCompte extends JDialog {

	private static final long serialVersionUID = -3363270261063376519L;
	private JTextField utilisateurText;
	private JTextField ancienText;
	private JTextField nouveauText;

	public ChangerInfoCompte( JFrame mere , Utilisateur utila) {
		setTitle("Modifier les informations du compte");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(img);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 307);
		setLocationRelativeTo(mere);
		getContentPane().setBackground(new Color(48, 49, 52));
		getContentPane().setLayout(null);

		JLabel lblUtilisateur = new JLabel("Utilisateur");
		lblUtilisateur.setForeground(new Color(250, 239, 197));
		lblUtilisateur.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		lblUtilisateur.setBounds(60, 40, 111, 30);
		getContentPane().add(lblUtilisateur);

		utilisateurText = new JTextField();
		utilisateurText.setEditable(false);
		utilisateurText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		utilisateurText.setCaretColor(new Color(250, 239, 197));
		utilisateurText.setDocument(new JTextFieldLimit(25));
		utilisateurText.setColumns(10);
		utilisateurText.setBorder(null);
		utilisateurText.setBackground(new Color(48, 49, 52));
		utilisateurText.setBounds(72, 61, 300, 30);
		utilisateurText.setForeground(new Color(250, 239, 197));
		utilisateurText.setText(utila.getUtilisateur());
		getContentPane().add(utilisateurText);

		JSeparator separator = new JSeparator();
		separator.setBounds(72, 91, 290, 7);
		getContentPane().add(separator);

		JLabel lblMotDePasse = new JLabel("Ancien mot de passe");
		lblMotDePasse.setForeground(new Color(250, 239, 197));
		lblMotDePasse.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblMotDePasse.setBounds(60, 100, 180, 30);
		getContentPane().add(lblMotDePasse);

		ancienText = new JTextField();
		ancienText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		ancienText.setCaretColor(new Color(250, 239, 197));
		ancienText.setDocument(new JTextFieldLimit(25));
		ancienText.setColumns(10);
		ancienText.setBorder(null);
		ancienText.setBackground(new Color(48, 49, 52));
		ancienText.setBounds(72, 121, 300, 30);
		ancienText.setForeground(new Color(250, 239, 197));
		getContentPane().add(ancienText);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(72, 151, 290, 7);
		getContentPane().add(separator_1);

		JButton btnSauvgarder = new JButton("Sauvgarder");
		btnSauvgarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(nouveauText.getText().trim().equals("") || ancienText.getText().trim().equals("")) {
					MessageDialog.showMessageDialog(ChangerInfoCompte.this, "Veuillez remplir toutes les informations",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				if(nouveauText.getText().trim().length() < 6) {
					MessageDialog.showMessageDialog(ChangerInfoCompte.this, "Le mot de passe doit contenir plus de 5 caracteres",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				if(!ancienText.getText().trim().equals(utila.getMotDePasse())) {
					MessageDialog.showMessageDialog(ChangerInfoCompte.this, "Mot de passe incorrect",
							"Erreur", MessageDialog.MESSAGE_ERREUR);
					return;
				}
				Compte compte = Compte.getCompte(utila);
				if(compte != null) {
					utila.setMotDePasse(nouveauText.getText().trim());
					compte.setUtilisateur(utila);
					compte.modifer(compte);
				}
				dispose();
			}
		});
		btnSauvgarder.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnSauvgarder.setBackground(new Color(250, 239, 197));
		btnSauvgarder.setBounds(230, 242, 200, 30);
		getContentPane().add(btnSauvgarder);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAnnuler.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnAnnuler.setBackground(new Color(250, 239, 197));
		btnAnnuler.setBounds(6, 242, 200, 30);
		getContentPane().add(btnAnnuler);

		JLabel lblNouveauMotDe = new JLabel("Nouveau mot de passe");
		lblNouveauMotDe.setForeground(new Color(250, 239, 197));
		lblNouveauMotDe.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNouveauMotDe.setBounds(60, 160, 146, 30);
		getContentPane().add(lblNouveauMotDe);

		nouveauText = new JTextField();
		nouveauText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		nouveauText.setForeground(new Color(250, 239, 197));
		nouveauText.setCaretColor(new Color(250, 239, 197));
		nouveauText.setDocument(new JTextFieldLimit(25));
		nouveauText.setColumns(10);
		nouveauText.setBorder(null);
		nouveauText.setBackground(new Color(48, 49, 52));
		nouveauText.setBounds(72, 181, 300, 30);
		getContentPane().add(nouveauText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(72, 211, 290, 7);
		getContentPane().add(separator_2);
		setVisible(true);

	}
}