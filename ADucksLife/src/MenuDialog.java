import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import javax.swing.SwingConstants;

public class MenuDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public static void main(String[] args) {
		try {
			MenuDialog dialog = new MenuDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	/**
	 * 
	 */
	public MenuDialog() {
		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblHolidayDash = new JLabel("Holiday");
		lblHolidayDash.setForeground(Color.RED);
		lblHolidayDash.setBackground(Color.RED);
		lblHolidayDash.setFont(new Font("Tw Cen MT", Font.PLAIN, 38));
		lblHolidayDash.setBounds(93, 48, 127, 44);
		contentPanel.add(lblHolidayDash);
		
		//currentMax = Integer.max(currentMax, nextLength);
		
		JLabel lblDash = new JLabel("Dash");
		lblDash.setForeground(Color.GREEN);
		lblDash.setFont(new Font("Tw Cen MT", Font.PLAIN, 38));
		lblDash.setBackground(Color.RED);
		lblDash.setBounds(230, 48, 127, 44);
		contentPanel.add(lblDash);
		
		JLabel lblPressPTo = new JLabel("Press Space to Play", SwingConstants.CENTER);
		lblPressPTo.setBounds(85, 191, 272, 14);
		contentPanel.add(lblPressPTo);
	}
}
