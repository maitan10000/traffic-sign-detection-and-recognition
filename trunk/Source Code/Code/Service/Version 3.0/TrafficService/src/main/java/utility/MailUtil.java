package utility;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailUtil {

	/**
	 * Send email using Gmail SMTP
	 * 
	 * @param email
	 * @param subject
	 * @param message
	 * @return
	 */
	public static boolean sendEmail(String email, String subject, String message) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "false");
		props.put("mail.smtp.port", 25);
		props.put("mail.smtp.socketFactory.port", 25);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.transport.protocol", "smtp");
		Session mailSession = null;

		mailSession = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(GlobalValue
								.getGmailUsername(), GlobalValue
								.getGmailPassword());
					}
				});

		try {

			Transport transport = mailSession.getTransport();
			MimeMessage mineMessage = new MimeMessage(mailSession);
			mineMessage.setSubject(subject);
			mineMessage.setFrom(new InternetAddress(GlobalValue.getGmailUsername(),"Biển báo giao thông"));
			String[] to = new String[] { email };
			mineMessage.addRecipient(Message.RecipientType.TO,
					new InternetAddress(to[0]));
			String body = message;
			mineMessage.setContent(body, "text/html; charset=UTF-8");
			transport.connect();

			transport.sendMessage(mineMessage,
					mineMessage.getRecipients(Message.RecipientType.TO));
			transport.close();
			return true;
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return false;
	}
}
