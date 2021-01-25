package fenetresPatient;

import java.awt.Color;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;

import consultation.Consultation;
import patient.Patient;
import utilisateur.Utilisateur;

public class RechercherConsultation extends JFrame {

	private static final long serialVersionUID = 7639975931114391948L;
	private JPanel contentPane;
	private JTextField prenomText;
	private JTextField nomText;
	private JTable table;
	private JScrollPane scrollPane;
	private JLabel aucun; 
	private JButton histoConsu;

	public RechercherConsultation(Utilisateur doc, Patient patient, JFrame mere) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mere.setVisible(true);
			}
		});

		setTitle("Medic : Selectionner une Consultation");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 534);
		setLocationRelativeTo(null);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Panel medic = new Panel();
		medic.setLayout(null);
		medic.setBackground(new Color(48, 49, 52));
		medic.setBounds(0, 0, 894, 83);
		contentPane.add(medic);

		JLabel label = new JLabel("Un logiciel pour vous");
		label.setForeground(new Color(62, 122, 189));
		label.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label.setBounds(66, 54, 147, 20);
		medic.add(label);

		JLabel label_1 = new JLabel("Medic");
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 50));
		label_1.setBounds(24, -33, 228, 122);
		medic.add(label_1);

		JLabel label_2 = new JLabel();
		label_2.setIcon(new ImageIcon("Images/Outils/gmail.png"));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(670, 3, 44, 28);
		medic.add(label_2);

		JLabel label_3 = new JLabel("Medic.contact@gmail.com");
		label_3.setForeground(new Color(250, 239, 197));
		label_3.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_3.setBounds(720, 1, 168, 30);
		medic.add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon("Images/Outils/facebook.png"));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(670, 24, 44, 28);
		medic.add(label_4);

		JLabel label_5 = new JLabel("Medic");
		label_5.setForeground(new Color(250, 239, 197));
		label_5.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_5.setBounds(720, 22, 139, 30);
		medic.add(label_5);

		JLabel label_6 = new JLabel("");
		label_6.setIcon(new ImageIcon("Images/Outils/site.png"));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(670, 46, 44, 28);
		medic.add(label_6);

		JLabel label_7 = new JLabel("www.Medic.com");
		label_7.setForeground(new Color(250, 239, 197));
		label_7.setFont(new Font("Century Gothic", Font.BOLD | Font.ITALIC, 11));
		label_7.setBounds(720, 44, 139, 30);
		medic.add(label_7);

		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(663, 0, 10, 83);
		medic.add(separator);

		Panel panelPrincipale = new Panel();
		panelPrincipale.setLayout(null);
		panelPrincipale.setForeground(Color.BLACK);
		panelPrincipale.setBackground(new Color(57, 113, 177));
		panelPrincipale.setBounds(0, 80, 894, 426);
		contentPane.add(panelPrincipale);

		aucun = new JLabel("Aucune consultation n'a ete trouvee");
		aucun.setVisible(false);
		aucun.setHorizontalAlignment(SwingConstants.CENTER);
		aucun.setFont(new Font("Century Gothic", Font.PLAIN, 16));
		aucun.setBounds(188, 220, 518, 30);
		panelPrincipale.add(aucun);

		JLabel lblPrenom = new JLabel("Prenom");
		lblPrenom.setForeground(new Color(32, 33, 35));
		lblPrenom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblPrenom.setBounds(361, 33, 111, 30);
		panelPrincipale.add(lblPrenom);

		prenomText = new JTextField();
		prenomText.setText(patient.getPrenom());
		prenomText.setEditable(false);
		prenomText.setForeground(Color.WHITE);
		prenomText.setBorder(null);
		prenomText.setBackground(new Color(57, 113, 177));
		prenomText.setBounds(361, 56, 300, 30);
		panelPrincipale.add(prenomText);

		JSeparator separator_3 = new JSeparator();
		separator_3.setBounds(361, 86, 290, 7);
		panelPrincipale.add(separator_3);

		JLabel lblNom = new JLabel("Nom");
		lblNom.setForeground(new Color(32, 33, 35));
		lblNom.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblNom.setBounds(23, 33, 111, 30);
		panelPrincipale.add(lblNom);

		nomText = new JTextField();
		nomText.setText(patient.getNom());
		nomText.setEditable(false);
		nomText.setForeground(Color.WHITE);
		nomText.setBorder(null);
		nomText.setBackground(new Color(57, 113, 177));
		nomText.setBounds(23, 56, 300, 30);
		panelPrincipale.add(nomText);

		JSeparator separator_4 = new JSeparator();
		separator_4.setBounds(23, 86, 290, 7);
		panelPrincipale.add(separator_4);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(20, 125, 853, 288);
		scrollPane.setBackground(new Color(57, 113, 177));
		scrollPane.setBorder(null);
		panelPrincipale.add(scrollPane);

		histoConsu = new JButton("Details de la consultation");
		histoConsu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String codeConsu = table.getModel().getValueAt(ligne, 0).toString();
					HistoriqueConsultation histo = new HistoriqueConsultation(doc, patient, Long.valueOf(codeConsu), mere);
					histo.setVisible(true);
					dispose();
				}
			}
		});
		histoConsu.setEnabled(false);
		histoConsu.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		histoConsu.setBackground(new Color(250, 239, 197));
		histoConsu.setBounds(690, 72, 183, 45);
		panelPrincipale.add(histoConsu);

		miseAJourTable(Consultation.getConsultation(patient.getCode()));
	}

	public void miseAJourTable(Vector<Consultation> donnes) {

		Vector<String> nomColonne = new Vector<String>(); 
		nomColonne.add("Code Consultation");
		nomColonne.add("Date de Consultation");
		nomColonne.add("Nom medecin");

		Vector<Vector<String>> v = new Vector<Vector<String>>();
		if(donnes != null) {
			for(Consultation consu : donnes) {
				Vector<String> d = new Vector<String>();
				d.addElement(String.valueOf(consu.getCodeConsu()));
				d.addElement(consu.getDateConsu());
				d.addElement(consu.getNomMedcin());
				v.addElement(d);
			}
		}

		aucun.setVisible(donnes == null);

		table = new JTable(v,nomColonne) {
			private static final long serialVersionUID = -6481828476820530197L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				histoConsu.setEnabled(table.getSelectedRow() != -1);
			}
		});
		table.getTableHeader().setFont(new Font("Century Gothic", Font.PLAIN, 12));
		table.getTableHeader().setOpaque(false);
		DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
		headerRenderer.setBackground(new Color(48, 49, 52));
		headerRenderer.setForeground(new Color(250, 239, 197));

		for (int i = 0; i < table.getModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
		}
		table.setRowHeight(25);
		table.setFillsViewportHeight(true);
		table.setFont(new Font("Century Gothic", Font.PLAIN, 13));
		table.setBorder(null);
		table.setBackground(new Color(57, 113, 177));
		table.setSelectionBackground(new Color(250, 239, 197));
		table.setSelectionForeground(new Color(48, 49, 52));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				histoConsu.setEnabled(table.getSelectedRow() != -1);
			}
		});

		int taille = 300;
		for(int i = 0 ; i < table.getColumnCount() ; i++) {

			table.getColumnModel().getColumn(i).setMinWidth(taille);
			table.getColumnModel().getColumn(i).setMaxWidth(taille);
			table.getColumnModel().getColumn(i).setPreferredWidth(taille);
		}

		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
	}
}