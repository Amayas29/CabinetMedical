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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import consultation.Consultation;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import utilisateur.Utilisateur;

public class FournirBon extends JDialog {

	private static final long serialVersionUID = -6125164971501501611L;
	private JPanel contentPane;
	private JTextField nomText;
	private JTextField prenomText;
	private JButton imprimer;
	private JTextField numText;
	private JTextField totalText;
	private JTextField payeText;

	public FournirBon(Utilisateur doc, Consultation consu, JFrame mere) {
		Patient p = Patient.getPatient(consu.getCodePatient());
		setTitle("M\u00E9dic : Bon");
		setModal(true);

		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);

		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 500, 500);
		setLocationRelativeTo(mere);
		contentPane = new JPanel(); 
		contentPane.setBackground(new Color(48, 49, 52));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(57, 113, 177));
		panel.setBounds(57, 77, 380, 345);
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

		JLabel lblNomMdicamanet = new JLabel("Num\u00E9ro du bon");
		lblNomMdicamanet.setForeground(new Color(32, 33, 35));
		lblNomMdicamanet.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNomMdicamanet.setBounds(35, 139, 183, 30);
		panel.add(lblNomMdicamanet);

		numText = new JTextField();
		numText.setEditable(false);
		numText.setDocument(new JTextFieldLimit(30));
		numText.setForeground(Color.WHITE);
		numText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		numText.setColumns(10);
		numText.setBorder(null);
		numText.setBackground(new Color(57, 113, 177));
		numText.setBounds(35, 157, 300, 30);
		String code = String.valueOf(consu.getCodeConsu());
		if(code.length() != 11) {
			code = "0".concat(code);
		}
		numText.setText(code);
		panel.add(numText);

		JSeparator separator = new JSeparator();
		separator.setBounds(35, 188, 290, 7);
		panel.add(separator);

		JLabel lblMontantTotal = new JLabel("Montant Total");
		lblMontantTotal.setForeground(new Color(32, 33, 35));
		lblMontantTotal.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblMontantTotal.setBounds(35, 207, 183, 30);
		panel.add(lblMontantTotal);

		totalText = new JTextField();
		totalText.setForeground(Color.WHITE);
		totalText.setDocument(new JTextFieldLimit(10));
		totalText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		totalText.setColumns(10);
		totalText.setBorder(null);
		totalText.setBackground(new Color(57, 113, 177));
		totalText.setBounds(35, 225, 300, 30);
		panel.add(totalText);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(35, 256, 290, 7);
		panel.add(separator_1);

		JLabel lblMontantPay = new JLabel("Montant Pay\u00E9");
		lblMontantPay.setForeground(new Color(32, 33, 35));
		lblMontantPay.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblMontantPay.setBounds(35, 269, 183, 30);
		panel.add(lblMontantPay);

		payeText = new JTextField();
		payeText.setDocument(new JTextFieldLimit(10));
		payeText.setText("0");
		payeText.setForeground(Color.WHITE);
		payeText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		payeText.setColumns(10);
		payeText.setBorder(null);
		payeText.setBackground(new Color(57, 113, 177));
		payeText.setBounds(35, 287, 300, 30);
		panel.add(payeText);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(35, 318, 290, 7);
		panel.add(separator_4);

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

		imprimer = new JButton("Imprimer");
		imprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(totalText.getText().trim().equals("") || payeText.getText().trim().equals("")) {
					MessageDialog.showMessageDialog(FournirBon.this, "Veuillez indiquer les montants", "Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				if(!valideMontant(totalText.getText().trim()) || !valideMontant(payeText.getText().trim())) {
					MessageDialog.showMessageDialog(FournirBon.this, "Montant incorrect", "Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				consu.etablirBon(Double.valueOf(totalText.getText().trim()), Double.valueOf(payeText.getText().trim()));
				dispose();
			}
		});

		imprimer.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		imprimer.setBackground(new Color(250, 239, 197));
		imprimer.setBounds(257, 430, 180, 30);
		contentPane.add(imprimer);
	}

	private boolean valideMontant(String montant) {
		for(int i = 0 ; i < montant.length() ; i++) {
			int car = (char) montant.charAt(i);
			if( car < 48 || car > 57) {
				return false;
			}
		}
		return true;
	}
}