package fenetresPatient;

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
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import consultation.Consultation;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import utilisateur.Utilisateur;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

public class FournirCertificatMedical extends JDialog {

	private static final long serialVersionUID = -6125164971501501611L;
	private JPanel contentPane;
	private JTextField nomText;
	private JTextField prenomText;
	private JButton sauvegarder;
	private JTextField typeText;
	private JTextArea contenuText;

	public FournirCertificatMedical(Utilisateur doc, Consultation consu, JFrame mere) {
		Patient p = Patient.getPatient(consu.getCodePatient());
		setTitle("M\u00E9dic : Certificat Medical");
		setModal(true); 

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(0, 0, 500, 550);
		setLocationRelativeTo(mere);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(48, 49, 52));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(57, 113, 177));
		panel.setBounds(57, 77, 380, 385);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setForeground(new Color(32, 33, 35));
		lblNom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNom.setBounds(35, 6, 111, 30);
		panel.add(lblNom);

		nomText = new JTextField();
		nomText.setText(p.getNom());
		nomText.setEditable(false);
		nomText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		nomText.setForeground(Color.WHITE);
		nomText.setColumns(10);
		nomText.setBorder(null);
		nomText.setBackground(new Color(57, 113, 177));
		nomText.setBounds(35, 24, 300, 30);
		panel.add(nomText);

		JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(35, 55, 290, 7);
		panel.add(separator_2);

		JLabel lblPrenom = new JLabel("Pr\u00E9nom");
		lblPrenom.setForeground(new Color(32, 33, 35));
		lblPrenom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPrenom.setBounds(35, 66, 111, 30);
		panel.add(lblPrenom);

		prenomText = new JTextField();
		prenomText.setText(p.getPrenom());
		prenomText.setEditable(false);
		prenomText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		prenomText.setForeground(Color.WHITE);
		prenomText.setColumns(10);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(57, 113, 177));
		prenomText.setBounds(35, 89, 300, 30);
		panel.add(prenomText);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(35, 120, 290, 7);
		panel.add(separator_3);

		JLabel lblNomMdicamanet = new JLabel("Type de certificat m\u00E9dical");
		lblNomMdicamanet.setForeground(new Color(32, 33, 35));
		lblNomMdicamanet.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNomMdicamanet.setBounds(35, 134, 183, 30);
		panel.add(lblNomMdicamanet);

		typeText = new JTextField();
		typeText.setDocument(new JTextFieldLimit(30));
		typeText.setForeground(Color.WHITE);
		typeText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		typeText.setColumns(10);
		typeText.setBorder(null);
		typeText.setBackground(new Color(57, 113, 177));
		typeText.setBounds(35, 152, 300, 30);
		panel.add(typeText);

		JSeparator separator = new JSeparator();
		separator.setBounds(35, 183, 290, 7);
		panel.add(separator);
		
		JLabel lblContenu = new JLabel("Contenu");
		lblContenu.setForeground(new Color(32, 33, 35));
		lblContenu.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblContenu.setBounds(35, 196, 183, 30);
		panel.add(lblContenu);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 230, 360, 145);
		scrollPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(scrollPane);
		
		contenuText = new JTextArea();
		scrollPane.setViewportView(contenuText);
		contenuText.setTabSize(4);
		contenuText.setLineWrap(true);
		contenuText.setDocument(new JTextFieldLimit(300));
		contenuText.setForeground(new Color(250, 239, 197));
		contenuText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		contenuText.setColumns(10);
		contenuText.setBackground(new Color(57, 113, 177));

		JLabel label = new JLabel("Un logiciel pour vous");
		label.setForeground(new Color(62, 122, 189));
		label.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label.setBounds(90, 48, 147, 20);
		contentPane.add(label);

		JLabel label_1 = new JLabel("Medic");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 50));
		label_1.setBounds(57, -35, 228, 122);
		contentPane.add(label_1);

		sauvegarder = new JButton("Sauvegarder");
		sauvegarder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(typeText.getText().trim().equals("")) {
					MessageDialog.showMessageDialog(FournirCertificatMedical.this, "Veuillez indiquer le type du certificat",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				if(contenuText.getText().trim().equals("")) {
					MessageDialog.showMessageDialog(FournirCertificatMedical.this, "Veuillez remplir le contenu du certificat",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				
				consu.fournirCertificatMedical(contenuText.getText().trim(), typeText.getText().trim());
				dispose();
			}
		});
		sauvegarder.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		sauvegarder.setBackground(new Color(250, 239, 197));
		sauvegarder.setBounds(257, 475, 180, 30);
		contentPane.add(sauvegarder);

		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		annuler.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		annuler.setBackground(new Color(250, 239, 197));
		annuler.setBounds(57, 475, 180, 30);
		contentPane.add(annuler);

	}
}