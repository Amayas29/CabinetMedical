package fenetresTemps;

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

import gestionTemps.Rdv;
import patient.Patient;
import utilisateur.Utilisateur;

public class AnnulerRdv extends JDialog {

	private static final long serialVersionUID = -3363270261063376519L;
	private JTextField nomText;
	private JTextField prenomText;
	private JTextField dateText;
	private JTextField heureText;

	public AnnulerRdv( JFrame mere , Utilisateur utila, Rdv rdv) {

		setTitle("Fiche Rdv");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		setIconImage(img);
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 434);
		setLocationRelativeTo(mere);
		getContentPane().setBackground(new Color(48, 49, 52));
		getContentPane().setLayout(null);

		JLabel label = new JLabel("Nom");
		label.setForeground(new Color(250, 239, 197));
		label.setFont(new Font("Century Gothic", Font.PLAIN, 12));

		label.setBounds(60, 40, 111, 30);
		getContentPane().add(label);

		nomText = new JTextField();
		nomText.setEditable(false);
		nomText.setCaretColor(new Color(250, 239, 197));
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setBackground(new Color(48, 49, 52));
		nomText.setBounds(72, 61, 300, 30);
		nomText.setForeground(new Color(250, 239, 197));
		nomText.setText(Patient.getPatient(rdv.getCodePatient()).getNom());
		getContentPane().add(nomText);

		JSeparator separator = new JSeparator();
		separator.setBounds(72, 91, 290, 7);
		getContentPane().add(separator);

		JLabel label_1 = new JLabel("Pr\u00E9nom");
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_1.setBounds(60, 102, 111, 30);
		getContentPane().add(label_1);

		prenomText = new JTextField();
		prenomText.setEditable(false);
		prenomText.setCaretColor(new Color(250, 239, 197));
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(48, 49, 52));
		prenomText.setBounds(72, 123, 300, 30);
		prenomText.setForeground(new Color(250, 239, 197));
		prenomText.setText(Patient.getPatient(rdv.getCodePatient()).getPrenom());
		getContentPane().add(prenomText);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(72, 153, 290, 7);
		getContentPane().add(separator_1);

		JButton annulerRdv = new JButton("Annuler le Rdv");
		annulerRdv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdv.annuler();
				dispose();

			}
		});
		annulerRdv.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		annulerRdv.setBackground(new Color(250, 239, 197));
		annulerRdv.setBounds(14, 365, 200, 30);
		getContentPane().add(annulerRdv);

		JButton quitter = new JButton("Quitter");
		quitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		quitter.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		quitter.setBackground(new Color(250, 239, 197));
		quitter.setBounds(228, 365, 200, 30);
		getContentPane().add(quitter);

		JLabel lblDateRdv = new JLabel("Date Rdv");
		lblDateRdv.setForeground(new Color(250, 239, 197));
		lblDateRdv.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblDateRdv.setBounds(60, 169, 111, 30);
		getContentPane().add(lblDateRdv);

		dateText = new JTextField();
		dateText.setForeground(new Color(250, 239, 197));
		dateText.setText(rdv.getDate());
		dateText.setEditable(false);
		dateText.setColumns(10);
		dateText.setCaretColor(new Color(250, 239, 197));
		dateText.setBorder(null);
		dateText.setBackground(new Color(48, 49, 52));
		dateText.setBounds(72, 190, 300, 30);
		getContentPane().add(dateText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(72, 220, 290, 7);
		getContentPane().add(separator_2);

		JLabel lblHeureRdv = new JLabel("Heure Rdv");
		lblHeureRdv.setForeground(new Color(250, 239, 197));
		lblHeureRdv.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblHeureRdv.setBounds(60, 236, 111, 30);
		getContentPane().add(lblHeureRdv);

		heureText = new JTextField();
		heureText.setForeground(new Color(250, 239, 197));
		int numero = rdv.getNumero();
		String heure = "";
		int div = numero / 2;
		if(numero % 2 == 1) {

			heure = heure.concat(String.valueOf(div + 7)).concat(":00");
		}
		else {
			heure = heure.concat(String.valueOf(div + 6)).concat(":30");
		}
		if(heure.length() == 4) {
			heure = "0".concat(heure);
		}

		heureText.setText(heure);
		heureText.setEditable(false);
		heureText.setColumns(10);
		heureText.setCaretColor(new Color(250, 239, 197));
		heureText.setBorder(null);
		heureText.setBackground(new Color(48, 49, 52));
		heureText.setBounds(72, 257, 300, 30);
		getContentPane().add(heureText);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(72, 287, 290, 7);
		getContentPane().add(separator_3);

	}
}