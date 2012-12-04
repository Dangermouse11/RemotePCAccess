import java.awt.Dimension;
import java.awt.event.*;
import javax.swing.*;

public class RPAPanel extends JPanel {
	/**
	 * Appeasing the compiler. This class won't be serialized anyway.
	 */
	private static final long serialVersionUID = 8965799340400758573L;

	private JButton startButton;

	private JButton aboutButton;

	private JTextField emailField;

	private JTextField hostField;

	private JPasswordField passwordField;

	private JTextField portField;

	private RPALog rpaLogger;

	public RPAPanel() {
		startButton = new JButton("Start Listening");
		startButton.addActionListener(new ButtonListener());
		startButton.setName("start");

		rpaLogger = new RPALog();
		rpaLogger.setName("log");

		aboutButton = new JButton("Help/About");
		aboutButton.addActionListener(new ButtonListener());
		aboutButton.setName("about");

		emailField = new JTextField("engetester@gmail.com");
		passwordField = new JPasswordField(10);
		hostField = new JTextField("imap.gmail.com");
		portField = new JTextField("993");
		this.add(emailField);
		this.add(passwordField);
		this.add(hostField);
		this.add(portField);
		this.add(startButton);
		this.add(rpaLogger);
		this.add(aboutButton);
		this.setPreferredSize(new Dimension(500, 200));
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			JButton sourceButton = (JButton) (event.getSource());
			if (sourceButton.getName().equals("about")) {
				JOptionPane
						.showMessageDialog(
								sourceButton.getParent(),
								"Remote PC Access Tool\n"
										+ "Written by: Ben Katz (bakatz@vt.edu)");
			} else if (startButton.getText().startsWith("Start")) {
				startEmailChecker();
			} else {
				stopEmailChecker();
			}
		}
	}

	public void startEmailChecker() {
		new EmailCheckerThread(emailField.getText(),
				passwordField.getPassword(), hostField.getText(),
				portField.getText(), startButton, this, rpaLogger);
		startButton.setText("Stop Listening");
		rpaLogger.addToLog("Email checker started.");

	}

	public void stopEmailChecker() {
		startButton.setText("Start Listening");
		rpaLogger.addToLog("Email checker stopped.");
	}
}
