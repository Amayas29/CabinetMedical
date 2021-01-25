package fenetresPatient;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
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
import documents.Medicament;
import outils.JTextFieldLimit;
import outils.MessageDialog;
import patient.Patient;
import utilisateur.Utilisateur;

public class EtablirOrdonnance extends JDialog {

	private static final long serialVersionUID = -6125164971501501611L;
	private JPanel contentPane;
	private JTextField nomText;
	private JTextField prenomText;
	private JButton sauvegarder;
	private JList<String> listMedicament;
	private JTextField nomMedicText;
	private JTextField quantiteText;
	private JTextField nombreFoisText;
	private JButton ajouterMedic;
	private JButton supprimerMedic;
	private Vector<Medicament> medicaments = new Vector<Medicament>();
	private int[] n = new int[3];

	public EtablirOrdonnance(Utilisateur doc, Consultation consu, JFrame mere) {
		Patient p = Patient.getPatient(consu.getCodePatient());
		setTitle("M\u00E9dic : Ordannance");
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
		panel.setBounds(57, 77, 380, 496);
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

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 295, 340, 190);
		scrollPane.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(scrollPane);

		listMedicament = new JList<String>();
		listMedicament.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if(listMedicament.getSelectedIndex() != -1) {
					supprimerMedic.setEnabled(true);
					nomMedicText.setText(listMedicament.getSelectedValue());
					n[1] = 1;
					n[2] = 1;
					for(Medicament medic : medicaments) {
						if(medic.getNom().equals(listMedicament.getSelectedValue())) {
							quantiteText.setText(medic.getQuantite());
							nombreFoisText.setText(medic.getNbPriseParTemps());
							break;
						}
					}
				}
			}
		});
		listMedicament.setLocation(0, 170);
		listMedicament.setSize(370, 145);
		listMedicament.setSelectionBackground(new Color(250, 239, 197));
		listMedicament.setSelectionForeground(new Color(48, 49, 52));
		listMedicament.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		listMedicament.setBorder(null);
		listMedicament.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMedicament.setBackground(new Color(57, 113, 177));
		scrollPane.setViewportView(listMedicament);

		JLabel lblNomMdicamanet = new JLabel("Nom medicament");
		lblNomMdicamanet.setForeground(new Color(32, 33, 35));
		lblNomMdicamanet.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNomMdicamanet.setBounds(35, 134, 144, 30);
		panel.add(lblNomMdicamanet);

		nomMedicText = new JTextField();
		nomMedicText.setDocument(new JTextFieldLimit(30));
		nomMedicText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(nomMedicText.getText().trim().equals("")) {
					n[ 0 ] = 0;
				}
				else {
					n[ 0 ] = 1;
				}
				ajouterMedic.setEnabled(n[ 0 ]*n[ 1 ]*n[ 2 ] != 0);
			}
		});
		nomMedicText.setForeground(Color.WHITE);
		nomMedicText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		nomMedicText.setColumns(10);
		nomMedicText.setBorder(null);
		nomMedicText.setBackground(new Color(57, 113, 177));
		nomMedicText.setBounds(35, 152, 300, 30);
		panel.add(nomMedicText);

		JSeparator separator = new JSeparator();
		separator.setBounds(35, 183, 290, 7);
		panel.add(separator);

		JLabel lblQuantit = new JLabel("Quantit\u00E9");
		lblQuantit.setForeground(new Color(32, 33, 35));
		lblQuantit.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblQuantit.setBounds(35, 194, 147, 30);
		panel.add(lblQuantit);

		quantiteText = new JTextField();
		quantiteText.setDocument(new JTextFieldLimit(20));
		quantiteText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(quantiteText.getText().trim().equals("")) {
					n[ 1 ] = 0;
				}
				else {
					n[ 1 ] = 1;
				}
				ajouterMedic.setEnabled(n[ 0 ]*n[ 1 ]*n[ 2 ] != 0);
			}
		});
		quantiteText.setForeground(Color.WHITE);
		quantiteText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		quantiteText.setColumns(10);
		quantiteText.setBorder(null);
		quantiteText.setBackground(new Color(57, 113, 177));
		quantiteText.setBounds(35, 212, 147, 30);
		panel.add(quantiteText);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(35, 243, 147, 7);
		panel.add(separator_1);

		JLabel lblNombreDeFois = new JLabel("Nombre de prises ");
		lblNombreDeFois.setForeground(new Color(32, 33, 35));
		lblNombreDeFois.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNombreDeFois.setBounds(202, 194, 157, 30);
		panel.add(lblNombreDeFois);

		nombreFoisText = new JTextField();
		nombreFoisText.setDocument(new JTextFieldLimit(20));
		nombreFoisText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(nombreFoisText.getText().trim().equals("")) {
					n[ 2 ] = 0;
				}
				else {
					n[ 2 ] = 1;
				}
				ajouterMedic.setEnabled(n[ 0 ]*n[ 1 ]*n[ 2 ] != 0);
			}
		});
		nombreFoisText.setForeground(Color.WHITE);
		nombreFoisText.setFont(new Font("Century Gothic", Font.PLAIN, 11));
		nombreFoisText.setColumns(10);
		nombreFoisText.setBorder(null);
		nombreFoisText.setBackground(new Color(57, 113, 177));
		nombreFoisText.setBounds(202, 212, 147, 30);
		panel.add(nombreFoisText);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(202, 243, 147, 7);
		panel.add(separator_4);

		supprimerMedic = new JButton();
		supprimerMedic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(listMedicament.getSelectedIndex() != -1) {
					for(Medicament medic : medicaments) {
						if(medic.getNom().equals(listMedicament.getSelectedValue())) {
							medicaments.removeElement(medic);
							break;
						}
					}
					majListeMedic();
				}
				nomMedicText.setText("");
				quantiteText.setText("");
				nombreFoisText.setText("");
				n = new int[3];
				supprimerMedic.setEnabled(false);
				ajouterMedic.setEnabled(false);
			}
		});
		supprimerMedic.setIcon(new ImageIcon("Images/Outils/supprimer.png"));
		supprimerMedic.setEnabled(false);
		supprimerMedic.setBackground(new Color(250, 239, 197));
		supprimerMedic.setBounds(325, 262, 35, 28);
		panel.add(supprimerMedic);

		ajouterMedic = new JButton();
		ajouterMedic.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(!nomMedicText.getText().trim().equals("") && !quantiteText.getText().trim().equals("") 
						&& !nombreFoisText.getText().trim().equals("")) {
					boolean existe = false;
					int i = 0; 
					while(i < medicaments.size() && !existe) {
						existe = medicaments.get(i).getNom().equals(nomMedicText.getText().trim()) ;
						i++;
					}
					if(!existe) {
						medicaments.addElement(new Medicament(consu.getCodeConsu(), nomMedicText.getText().trim(),
								quantiteText.getText().trim(), nombreFoisText.getText().trim()));
						majListeMedic();
					}
				}
				nomMedicText.setText("");
				quantiteText.setText("");
				nombreFoisText.setText("");
				n = new int[3];
				supprimerMedic.setEnabled(false);
				ajouterMedic.setEnabled(false);
			}
		});
		ajouterMedic.setIcon(new ImageIcon("Images/Outils/ajouter.png"));
		ajouterMedic.setEnabled(false);
		ajouterMedic.setBackground(new Color(250, 239, 197));
		ajouterMedic.setBounds(280, 262, 35, 28);
		panel.add(ajouterMedic);

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
				if(medicaments.isEmpty()) {
					MessageDialog.showMessageDialog(EtablirOrdonnance.this, "Veuillez renseigner les medicaments",
							"Attention", MessageDialog.MESSAGE_ATTENTION);
					return;
				}
				else {
					consu.fournirOrdannace(medicaments);
					dispose();
				}
			}
		});

		sauvegarder.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		sauvegarder.setBackground(new Color(250, 239, 197));
		sauvegarder.setBounds(257, 584, 180, 30);
		contentPane.add(sauvegarder);

		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		annuler.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		annuler.setBackground(new Color(250, 239, 197));
		annuler.setBounds(57, 585, 180, 30);
		contentPane.add(annuler);

	}

	private void majListeMedic() {
		listMedicament.removeAll();  
		Vector<String> noms = new Vector<String>();
		for(Medicament medic : medicaments) {
			noms.addElement(medic.getNom());	
		}
		listMedicament.setListData(noms);
	}
}