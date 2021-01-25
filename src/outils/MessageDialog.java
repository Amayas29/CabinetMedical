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
/***
 * Une fenetre de message propre e nous
 * 
 */
public class MessageDialog extends JDialog {

	private static final long serialVersionUID = -8602733239989967205L;
	private final JPanel contentPanel = new JPanel();
	public static final int MESSAGE_ERREUR = 1;
	public static final int MESSAGE_ATTENTION = 2;
	public static final int MESSAGE_SUCCES = 3;
	public static final int MESSAGE_INFORMATION = 4;

	public static void showMessageDialog(Component comp, Object message, String titre,int type) {
		MessageDialog dialog = new MessageDialog(comp,message.toString(),titre,type);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		dialog.setVisible(true);
	}

	private MessageDialog(Component comp,String message,String titre,int type) {
		setResizable(false);
		setModal(true);
		setTitle(titre);
		setType(Type.POPUP);
		setBounds(100, 100, 387, 193);
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

		JLabel label_1 = new JLabel("M\u00E9dic");
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
		String path = "";
		if(type == MESSAGE_ERREUR) {
			path = "Images/Outils/erreur.png";
		}
		if(type == MESSAGE_ATTENTION) {
			path = "Images/Outils/attention.png";
		}
		if(type == MESSAGE_SUCCES) {
			path = "Images/Outils/succes.png";
		}
		if(type == MESSAGE_INFORMATION) {
			path = "Images/Outils/info.png";
		}
		image.setIcon(new ImageIcon(path));

		String t = "<html><span> "+ message +"</span></html>";

		JButton ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		getRootPane().setDefaultButton(ok);
		ok.setFont(new Font("Century Gothic", Font.PLAIN, 12));
		ok.setBackground(new Color(250, 239, 197));
		ok.setBounds(260, 87, 60, 30);
		panel_1.add(ok);

		JLabel messageText = new JLabel();
		messageText.setBackground(new Color(57, 113, 177));
		messageText.setForeground(Color.BLACK);
		messageText.setText(t);
		messageText.setFont(new Font("Century Gothic", Font.PLAIN, 14));
		messageText.setBounds(106, 18, 250, 67);
		panel_1.add(messageText);
	}
}
