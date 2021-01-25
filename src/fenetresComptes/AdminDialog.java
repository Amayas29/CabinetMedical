package fenetresComptes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class AdminDialog extends JDialog {

	private static final long serialVersionUID = -8602733239989967205L;
	private final JPanel contentPanel = new JPanel();
	private JTextField cleText;

	public static final int CORRECT = 1;
	public static final int SURCHARGE = 0;
	public static int nbFois = 0;

	private static int reponse = -1;

	public static int showAdminDialog(Component arg0,String cle) {
		AdminDialog dialog = new AdminDialog(arg0,cle);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		while(reponse == -1) {}
		return reponse;
	}

	private AdminDialog(Component comp,String cle) {
		nbFois = 0;
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				reponse = 2;
				dispose();
			}
		});

		setResizable(false);
		setModal(true);
		setTitle("La cle admin");
		setType(Type.POPUP);
		setBounds(100, 100, 387, 193);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		setLocationRelativeTo(comp);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		Panel panel = new Panel();
		panel.setBackground(new Color(48, 49, 52));
		panel.setBounds(0, 0, 381, 48);
		contentPanel.add(panel);
		panel.setLayout(null);

		JLabel label = new JLabel("Un logiciel pour vous");
		label.setBounds(6, 26, 147, 20);
		label.setForeground(new Color(62, 122, 189));
		label.setFont(new Font("Century Gothic", Font.ITALIC, 10));
		panel.add(label);

		JLabel label_1 = new JLabel("Medic");
		label_1.setBounds(6, -46, 228, 122);
		label_1.setHorizontalAlignment(SwingConstants.LEFT);
		label_1.setForeground(new Color(250, 239, 197));
		label_1.setFont(new Font("Century", Font.BOLD, 30));
		panel.add(label_1);

		Panel panel_1 = new Panel();
		panel_1.setForeground(Color.BLACK);
		panel_1.setBackground(new Color(57, 113, 177));
		panel_1.setBounds(0, 43, 381, 122);
		contentPanel.add(panel_1);
		panel_1.setLayout(null);

		JSeparator separator = new JSeparator();
		separator.setBounds(33, 65, 290, 7);
		panel_1.add(separator);
		JButton valider = new JButton("Valider");
		getRootPane().setDefaultButton(valider);
		cleText = new JTextField();
		cleText.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				valider.setEnabled(!cleText.getText().trim().equals(""));
			}
		});
		cleText.setForeground(Color.WHITE);
		cleText.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		cleText.setColumns(10);
		cleText.setBorder(null);
		cleText.setBackground(new Color(57, 113, 177));
		cleText.setBounds(33, 34, 300, 30);
		panel_1.add(cleText);

		valider.setEnabled(false);
		valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(cleText.getText().trim().equals(cle)) {
					reponse = AdminDialog.CORRECT;
					dispose();
				}
				else {
					cleText.setText("");
				}
				nbFois += 1;
				if(nbFois == 3) {
					reponse = AdminDialog.SURCHARGE;
					dispose();
				}

			}
		});
		valider.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		valider.setBackground(new Color(250, 239, 197));
		valider.setBounds(261, 83, 80, 30);
		panel_1.add(valider);

		JLabel lblVeuillezIntroduireLacl = new JLabel("Veuillez introduire la cl\u00E9 administrateur ");
		lblVeuillezIntroduireLacl.setForeground(new Color(32, 33, 35));
		lblVeuillezIntroduireLacl.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		lblVeuillezIntroduireLacl.setBounds(20, 11, 252, 30);
		panel_1.add(lblVeuillezIntroduireLacl);

	}
}