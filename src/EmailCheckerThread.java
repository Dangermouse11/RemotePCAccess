import javax.swing.JButton;

/**
 * EmailCheckerThread
 * 
 * @author Ben Katz
 * @version 2012.12.04
 */
public class EmailCheckerThread implements Runnable {

	private RPAMailHandler mailHandler;
	private String email;
	private char[] password;
	private String host;
	private String port;
	private JButton startButton;
	private RPAPanel pan;
	private RPALog log;
	Thread myThread;

	public EmailCheckerThread(String email, char[] password, String host,
			String port, JButton startButton, RPAPanel pan, RPALog log) {
		this.pan = pan;
		this.email = email;
		this.password = password;
		this.host = host;
		this.port = port;
		this.startButton = startButton;
		this.log = log;
		myThread = new Thread(this, "EmailCheckerThread");
		myThread.start();
	}

	public void run() {

		try {
			mailHandler = new RPAMailHandler(email.split("@")[0], password,
					host, port);
			while (startButton.getText().startsWith("Stop")) {
				int numNewEmails = mailHandler.checkEmail();
				if (numNewEmails >= 0) {
					log.addToLog("Checked email ... got " + numNewEmails
							+ " new message(s).");
				} else {
					log.addToLog("The email handler encountered a"
							+ " connection error while checking the mail. "
							+ "Stopping...");
					pan.stopEmailChecker();
				}
				Thread.sleep(3000);
			}
		} catch (InterruptedException e) {
			log.addToLog("Error: main thread interrupted.");
		}
	}
}