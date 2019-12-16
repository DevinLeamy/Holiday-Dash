import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameOverPage extends JDialog {
	
	private final JPanel contentPanel = new JPanel();
	private boolean ok = false;

	public boolean isOk() {
		return ok;
	}

	/**
	 * Create the dialog.
	 */
	public GameOverPage() {
		setBackground(Color.WHITE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel backgroundImageLbl = new JLabel(new ImageIcon("res/Images/GameOver.jpg"));
			backgroundImageLbl.setBackground(Color.white);
			backgroundImageLbl.setBounds(0, 11, 434, 261);
			backgroundImageLbl.setBackground(new Color(255, 255, 255));
			contentPanel.add(backgroundImageLbl);
		}
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setBounds(242, 17, 0, 0);
		contentPanel.add(lblNewLabel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Retry");
				okButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						okButton_mouseClicked(arg0);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Quit");
				cancelButton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						cancelButton_mouseClicked(e);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	protected void okButton_mouseClicked(MouseEvent arg0) {
		this.ok = true;
		dispose();
		
	}
	protected void cancelButton_mouseClicked(MouseEvent e) {
		dispose();
	}
}
