import com.sun.mail.imap.IMAPSSLStore;
import java.util.Properties;
import javax.mail.*;

public class RPAMailHandler {
	private final String COMMAND_PASSWORD = "opensesame"; //TODO: move this

	private String portNumber;

	private String hostName;

	private String user;

	private String password;

	// ----------------------------------------------------------
	/**
	 * Create a new RPAMailHandler object.
	 * 
	 * @param username
	 * @param password
	 * @param host
	 * @param portNumber
	 */
	public RPAMailHandler(String username, char[] password, String host,
			String portNumber) {
		this.user = username;
		this.password = convertPassword(password);
		this.hostName = host;
		this.portNumber = portNumber;
	}

	private String convertPassword(char[] pw) {
		String str = "";
		for (int i = 0; i < pw.length; i++) {
			str += pw[i];
		}
		return str;
	}

	// ----------------------------------------------------------
	/**
	 * Checks the email account for new messages, executes commands if needed.
	 * 
	 * @return int the number of new messages received
	 */
	public int checkEmail() {
		try {
			String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

			Properties imapProps = new Properties();

			imapProps.setProperty("mail.imap.socketFactory.class", SSL_FACTORY);
			imapProps.setProperty("mail.imap.socketFactory.fallback", "false");
			imapProps.setProperty("mail.imap.port", portNumber);
			imapProps.setProperty("mail.imap.socketFactory.port", portNumber);

			URLName url = new URLName("imap", hostName,
					Integer.parseInt(portNumber), "", user, password);

			Session emailSession = Session.getDefaultInstance(imapProps);

			IMAPSSLStore emailStore = new IMAPSSLStore(emailSession, url);
			emailStore.connect(user, password);

			Folder emailFolder = emailStore.getFolder("INBOX");
			emailFolder.open(Folder.READ_WRITE);

			Message[] messages = emailFolder.getMessages();
			parseMessages(messages);
			emailFolder.close(false);
			emailStore.close();
			return messages.length;
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return -1;
	}

	private void parseMessages(Message[] messages) {
		try {
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				// command,password
				if (message.getSubject().split(",").length == 2)
				{
					// if we have both a command and a password, do this case
					String commandReceived = message.getSubject().split(",")[0];
					String passReceived = message.getSubject().split(",")[1];
					if (!passReceived.equals(COMMAND_PASSWORD)) {
						message.setFlag(Flags.Flag.DELETED, true);
					} else {
						if (commandReceived.contains("shutdown")
								|| commandReceived.contains("shut down")) {
							runCommand("shutdown.exe -s -t 0", message);
							System.exit(0);
						} else if (commandReceived.contains("logoff")
								|| commandReceived.contains("log off")) {
							runCommand("shutdown -l -t 01", message);
							System.exit(0);
						} else if (commandReceived.contains("lock")) {
							runCommand(
									"rundll32.exe user32.dll, LockWorkStation",
									message);
						} else {
							message.setFlag(Flags.Flag.DELETED, true);
						}
					}
				} else {
					message.setFlag(Flags.Flag.DELETED, true);
				}
			}
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Executes a given command.
	 * 
	 * @param params
	 *            the command to execute
	 * @param message
	 *            the message that was received
	 */
	private static void runCommand(String params, Message message) {
		try {
			Runtime runtime = Runtime.getRuntime();
			message.setFlag(Flags.Flag.DELETED, true);
			runtime.exec(params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
