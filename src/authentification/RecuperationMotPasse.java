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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import outils.MessageDialog;
import utilisateur.Compte;
import utilisateur.Types;

public class RecuperationMotPasse extends JDialog {

	private static final long serialVersionUID = -725614484009154635L;
	private JPanel contentPane;
	private JTextField utilsaText;
	private JTextField afficheText;
	private JComboBox<String> typeBox;

	public RecuperationMotPasse() {
		setModal(true);
		setTitle("M\u00E9dic : R\u00E9cup\u00E9ration mot de passe");
	
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.getImage("Images/stetho.png");
		setIconImage(img);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 530, 310);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(57, 113, 177));
		panel.setBounds(0, 0, 524, 152);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("Utilisateur ");
		label.setForeground(new Color(32, 33, 35));
		label.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label.setBounds(25, 78, 111, 30);
		panel.add(label);

		utilsaText = new JTextField();
		utilsaText.setForeground(Color.WHITE);
		utilsaText.setColumns(10);
		utilsaText.setBorder(null);
		utilsaText.setBackground(new Color(57, 113, 177));
		utilsaText.setBounds(25, 101, 300, 30);
		panel.add(utilsaText);

		JSeparator separator = new JSeparator();
		separator.setBounds(25, 132, 290, 7);
		panel.add(separator);

		JButton recup = new JButton("R\u00E9cup\u00E9rer");
		recup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				if(typeBox.getSelectedIndex() == -1 || utilsaText.getText().trim().equals("")) {
					MessageDialog.showMessageDialog(RecuperationMotPasse.this,"Veuillez remplir tous les formulaires","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				String mdp = Compte.getMdp(utilsaText.getText().trim(),typeBox.getSelectedItem().toString());
				if(mdp == null) {
					MessageDialog.showMessageDialog(RecuperationMotPasse.this,"Il n'existe aucun compte avec ces informations","Erreur",MessageDialog.MESSAGE_ERREUR);
					return;
				}
				afficheText.setText(mdp.substring(0,3));
			}
		});
		recup.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		recup.setBackground(new Color(250, 239, 197));
		recup.setBounds(374, 102, 118, 30);
		panel.add(recup);

		JLabel label_3 = new JLabel("Type ");
		label_3.setForeground(new Color(32, 33, 35));
		label_3.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		label_3.setBounds(25, 6, 111, 30);
		panel.add(label_3);

		typeBox = new JComboBox<String>();
		typeBox.setBackground(new Color(250, 239, 197));
		String[] tab = new String[Types.values().length];
		int i = 0;
		for(Types type : Types.values()) {
			tab[ i ] = type.getType();
			i++;
		}
		typeBox.setModel(new DefaultComboBoxModel<String>(tab));
		typeBox.setSelectedIndex(-1);
		typeBox.setBounds(25, 36, 295, 30);
		panel.add(typeBox);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(48, 49, 52));
		panel_1.setBounds(0, 148, 524, 124);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel label_1 = new JLabel("M\u00E9dic");
		label_1.setBounds(383, 20, 228, 122);
		panel_1.add(label_1);
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 40));

		JLabel lblLesTroisPremieres = new JLabel("Les trois premi\u00E8res lettres de votre mot de passe");
		lblLesTroisPremieres.setForeground(new Color(250, 239, 197));
		lblLesTroisPremieres.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblLesTroisPremieres.setBounds(24, 18, 290, 30);
		panel_1.add(lblLesTroisPremieres);

		afficheText = new JTextField();
		afficheText.setEditable(false);
		afficheText.setForeground(Color.WHITE);
		afficheText.setColumns(10);
		afficheText.setBorder(null);
		afficheText.setBackground(new Color(48, 49, 52));
		afficheText.setBounds(70, 42, 300, 30);
		panel_1.add(afficheText);
		
		typeBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
					afficheText.setText("");
			}
		});
		utilsaText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				afficheText.setText("");
			}
			
		});
		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(70, 73, 290, 7);
		panel_1.add(separator_1);

		JLabel label_2 = new JLabel("Un logiciel pour vous");
		label_2.setForeground(new Color(62, 122, 189));
		label_2.setFont(new Font("Century Gothic", Font.ITALIC, 13));
		label_2.setBounds(370, 100, 147, 20);
		panel_1.add(label_2);
	}
}
