package outils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
/***
 * Une fenetre de confirmation propre e nous
 * 
 */
public class ConfirmDialog extends JDialog {

	private static final long serialVersionUID = -8602733239989967205L;
	private final JPanel contentPanel = new JPanel();
	private JButton non;
	private JButton oui;
	
	public static final int OPTION_OUI_NON = 4;
	public static final int OPTION_OUI = 1;
	public static final int OPTION_NON = 0;
	
	
	public static final int ATTENTION = 1;
	public static final int DEMANDE = 0;
	
	
	private static int choix = -1;

	public static int showConfirmDialog(Component comp, Object msg, String titre,int btn,int type) {
		ConfirmDialog dialog = new ConfirmDialog(comp,msg.toString(),titre,btn,type);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
		while(choix == -1) {}
		return choix;
	}

	private ConfirmDialog(Component comp,String message,String titre,int btn,int type) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				choix = 2;
			}
		});
		setResizable(false);
		setModal(true);
		setTitle(titre);
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

		JLabel image = new JLabel();
		image.setBounds(10, 15, 70, 70);
		panel_1.add(image);
		String path = null;

		if(type == ATTENTION) {
			path = "Images/Outils/attention.png";
		}
		if(type == DEMANDE) {
			path = "Images/Outils/question.png";
		}
		image.setIcon(new ImageIcon(path));

		oui = new JButton("Oui");
		oui.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choix = 1;
				dispose();
			}
		});
		getRootPane().setDefaultButton(oui);
		oui.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		oui.setBackground(new Color(250, 239, 197));
		oui.setBounds(230, 87, 60, 30);
		panel_1.add(oui);

		JLabel messageText = new JLabel();
		messageText.setBackground(new Color(57, 113, 177));
		messageText.setForeground(Color.BLACK);
		messageText.setText("<html><span> "+ message +"</span></html>");
		messageText.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		messageText.setBounds(106, 18, 250, 67);
		panel_1.add(messageText);

		non = new JButton("Non");
		non.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choix = 0;
				dispose();
			}
		});
		non.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		non.setBackground(new Color(250, 239, 197));
		non.setBounds(300, 87, 60, 30);
		panel_1.add(non);
	}
}
