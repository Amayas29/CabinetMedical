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

public class ChangerInfoPerso extends JDialog {

	private static final long serialVersionUID = -3363270261063376519L;
	private JTextField nomText;
	private JTextField prenomText;

	public ChangerInfoPerso( JFrame mere , Utilisateur utila) {
		setTitle("Modifier les informations personnelles");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setIconImage(img);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 260);
		setLocationRelativeTo(mere);
		getContentPane().setBackground(new Color(48, 49, 52));
		getContentPane().setLayout(null);

		JLabel label = new JLabel("Nom");
		label.setForeground(new Color(250, 239, 197));
		label.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		label.setBounds(60, 40, 111, 30);
		getContentPane().add(label);

		nomText = new JTextField();
		nomText.setCaretColor(new Color(250, 239, 197));
		nomText.setForeground(Color.WHITE);
		nomText.setDocument(new JTextFieldLimit(25));
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setBackground(new Color(48, 49, 52));
		nomText.setBounds(72, 61, 300, 30);
		nomText.setForeground(new Color(250, 239, 197));
		nomText.setText(Compte.getCompte(utila).getNom());
		getContentPane().add(nomText);

		JSeparator separator = new JSeparator();
		separator.setBounds(72, 91, 290, 7);
		getContentPane().add(separator);

		JLabel label_1 = new JLabel("Pr\u00E9nom");
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_1.setBounds(60, 100, 111, 30);
		getContentPane().add(label_1);

		prenomText = new JTextField();
		prenomText.setCaretColor(new Color(250, 239, 197));
		prenomText.setForeground(Color.WHITE);
		prenomText.setDocument(new JTextFieldLimit(25));
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(48, 49, 52));
		prenomText.setBounds(72, 121, 300, 30);
		prenomText.setForeground(new Color(250, 239, 197));
		prenomText.setText(Compte.getCompte(utila).getPrenom());
		getContentPane().add(prenomText);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(72, 151, 290, 7);
		getContentPane().add(separator_1);

		JButton btnSauvgarder = new JButton("Sauvgarder");
		btnSauvgarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(nomText.getText().trim().equals("") || prenomText.getText().trim().equals("")) {
					MessageDialog.showMessageDialog(ChangerInfoPerso.this, "Veuillez remplir toutes les informations",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				Compte compte = Compte.getCompte(utila);
				compte.setNom(nomText.getText().trim());
				compte.setPrenom(prenomText.getText().trim());
				compte.modifer(compte);
				dispose();

			}
		});
		btnSauvgarder.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnSauvgarder.setBackground(new Color(250, 239, 197));
		btnSauvgarder.setBounds(234, 190, 200, 30);
		getContentPane().add(btnSauvgarder);

		JButton btnAnnuler = new JButton("Annuler");
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		btnAnnuler.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		btnAnnuler.setBackground(new Color(250, 239, 197));
		btnAnnuler.setBounds(10, 190, 200, 30);
		getContentPane().add(btnAnnuler);
		setVisible(true);

	}
}