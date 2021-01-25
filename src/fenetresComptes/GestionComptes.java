package fenetresComptes;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.DefaultTableCellRenderer;

import authentification.Authentification;
import authentification.Inscription;
import fenetresPrincipales.FenetreDocteur;
import outils.ConfirmDialog;
import utilisateur.Compte;
import utilisateur.Docteur;
import utilisateur.Utilisateur;

public class GestionComptes extends JFrame {

	private static final long serialVersionUID = -6125164971501501611L;
	private JPanel contentPane;
	private JButton infoCompte;
	private JTable table;
	private JScrollPane scrollPane;
	private JButton ajouter;
	private JButton supprimer;
	private JButton infoPers;

	public static void main(String[] args) throws UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(new NimbusLookAndFeel());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Docteur doc = new Docteur("amayas", "amayas");
					GestionComptes frame = new GestionComptes(doc);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GestionComptes(Utilisateur doc) {
		setTitle("Medic : Gestion Comptes");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				dispose();
				FenetreDocteur fendoc = new FenetreDocteur(doc);
				fendoc.setVisible(true);
			}
		});
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, 660, 459);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(48, 49, 52));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(57, 113, 177));
		panel.setBounds(0, 77, 654, 354);
		contentPane.add(panel);
		panel.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(null);
		scrollPane.setBackground(new Color(57, 113, 177));
		scrollPane.setBounds(27, 83, 600, 250);
		panel.add(scrollPane);

		infoCompte = new JButton("Informations du compte");
		infoCompte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String type = table.getModel().getValueAt(ligne, 0).toString();
					String ut = table.getModel().getValueAt(ligne, 4).toString();
					String mdp = table.getModel().getValueAt(ligne, 3).toString();
					Utilisateur utila = Utilisateur.getUtilisateur(type, ut, mdp);
					@SuppressWarnings("unused")
					ChangerInfoCompte chgInfoCom = new ChangerInfoCompte(GestionComptes.this, utila);
					miseAJourTable(Compte.getComptes());
				}
			}
		});
		infoCompte.setBounds(437, 41, 178, 30);
		panel.add(infoCompte);

		infoCompte.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		infoCompte.setBackground(new Color(250, 239, 197));

		ajouter = new JButton("Ajouter un compte");
		ajouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Inscription ins = new Inscription(GestionComptes.this);
				ins.setVisible(true);
				miseAJourTable(Compte.getComptes());
			}
		});
		ajouter.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		ajouter.setBackground(new Color(250, 239, 197));
		ajouter.setBounds(39, 23, 160, 30);
		panel.add(ajouter);

		supprimer = new JButton("Supprimer un compte");
		supprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String type = table.getModel().getValueAt(ligne, 0).toString();
					String ut = table.getModel().getValueAt(ligne, 4).toString();
					String mdp = table.getModel().getValueAt(ligne, 3).toString();
					Utilisateur util = Utilisateur.getUtilisateur(type, ut, mdp);
					int  choix= ConfirmDialog.showConfirmDialog(GestionComptes.this, "etes-vous sur de vouloir supprimer le compte ?", "Attention", ConfirmDialog.OPTION_OUI_NON,ConfirmDialog.ATTENTION);
					if(choix == ConfirmDialog.OPTION_OUI) {
						Compte.getCompte(util).supprimer();
						miseAJourTable(Compte.getComptes());
					}
					if(doc.equals(util)) {
						dispose();
						Authentification authen = new Authentification();
						authen.setVisible(true);
					}
				}

			}
		});
		supprimer.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		supprimer.setBackground(new Color(250, 239, 197));
		supprimer.setBounds(238, 23, 160, 30);
		panel.add(supprimer);

		infoPers = new JButton("Informations personnelles");
		infoPers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow() != -1) {
					int ligne = table.getSelectedRow();
					String type = table.getModel().getValueAt(ligne, 0).toString();
					String ut = table.getModel().getValueAt(ligne, 4).toString();
					String mdp = table.getModel().getValueAt(ligne, 3).toString();
					Utilisateur util = Utilisateur.getUtilisateur(type, ut, mdp);
					@SuppressWarnings("unused")
					ChangerInfoPerso chnginfPer = new ChangerInfoPerso(GestionComptes.this, util);
					miseAJourTable(Compte.getComptes());
				}
			}
		});
		infoPers.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		infoPers.setBackground(new Color(250, 239, 197));
		infoPers.setVisible(false);
		infoPers.setBounds(437, 6, 178, 30);
		panel.add(infoPers);

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
		miseAJourTable(Compte.getComptes());

	}
	public void miseAJourTable(Vector<Compte> donnes) {
		Vector<String> nomColonne = new Vector<String>(); 
		nomColonne.add("Type");
		nomColonne.add("Nom");
		nomColonne.add("Prenom");
		nomColonne.add("mot de passe");
		nomColonne.add("utilisateur");
		Vector<Vector<String>> v = new Vector<Vector<String>>();
		if(donnes != null) {
			for(Compte p : donnes) {
				Vector<String> d = new Vector<String>();
				d.addElement(p.getUtilisateur().getType().getType());
				d.addElement(p.getNom());
				d.addElement(p.getPrenom());
				d.addElement(p.getUtilisateur().getMotDePasse());
				d.addElement(p.getUtilisateur().getUtilisateur());
				v.addElement(d);
			}
		}
		infoCompte.setVisible(false);
		supprimer.setVisible(false);
		infoPers.setVisible(false);
		table = new JTable(v,nomColonne) {
			private static final long serialVersionUID = -6481828476820530197L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table.setLocation(10, 0);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				if(table.getSelectedRow() != -1) {
					infoCompte.setVisible(true);
					infoPers.setVisible(true);
					supprimer.setVisible(true);
				}
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


		int taille = 160 ;
		for(int i = 0 ; i < table.getColumnCount() ; i++) {
			table.getColumnModel().getColumn(i).setMinWidth(taille);
			table.getColumnModel().getColumn(i).setMaxWidth(taille);
			table.getColumnModel().getColumn(i).setPreferredWidth(taille);
			if(i == 3) {
				taille = 0;
			}
		}

		table.getTableHeader().setReorderingAllowed(false);
		scrollPane.setViewportView(table);
	}
}