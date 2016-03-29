package nbad.utility;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import nbad.constants.Strings;

public class MailUtils {

	public static boolean sendEmail_generic(EmailDetail emailDetail, String title,String msgbody,String link) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Strings.USERNAME, Strings.PASSWORD);
					}
				  });
		
		String msgBody = "From:" + emailDetail.getEmailFrom() + "\nTo: "+ emailDetail.getEmailTo() +"\n Message: "+emailDetail.getMsg()+"\n"
        		 + "\n\n"
        				+ msgbody
        				+ link + "\n"
        				+ "Regards,\n Admin";
		
		 try {
	            Message msg = new MimeMessage(session);
	            msg.setFrom(new InternetAddress(emailDetail.getEmailFrom(), emailDetail.getName()));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(emailDetail.getEmailTo(),emailDetail.getEmailTo().split("@")[0]));
	            msg.setSubject("Researchers Exchange Participation : " + title);
	            msg.setText(msgBody);
	            Transport.send(msg);
	            
	            return true;

	        } catch (AddressException e) {
	           System.out.println("Error with Address : AddressException occured" + e);
	        } catch (MessagingException e) {
	        	System.out.println("Messaging Exception occured " + e);
	        } catch (UnsupportedEncodingException e) {
				System.out.println("Unsupported Encoding error " + e);
				e.printStackTrace();
			}
		 
		return false;
	}
	
public static boolean sendEmail(EmailDetail emailDetail,String bonusLink,String msgbody, String title) {
		
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(Strings.USERNAME, Strings.PASSWORD);
					}
				  });

        String msgBody = "From:" + emailDetail.getEmailFrom() + "\nTo: "+ emailDetail.getEmailTo() +"\n Message: "+emailDetail.getMsg()+"\n"
        		+ "Regards,\n"+emailDetail.getName() + "\n\n"
        				+ msgbody
        				+ bonusLink;

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailDetail.getEmailFrom(), emailDetail.getName()));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(emailDetail.getEmailTo(),emailDetail.getEmailTo().split("@")[0]));
            msg.setSubject("Researchers Exchange Participation : " + title);
            msg.setText(msgBody);
            Transport.send(msg);
            
            return true;

        } catch (AddressException e) {
           System.out.println("Error with Address : AddressException occured" + e);
        } catch (MessagingException e) {
        	System.out.println("Messaging Exception occured " + e);
        } catch (UnsupportedEncodingException e) {
			System.out.println("Unsupported Encoding error " + e);
			e.printStackTrace();
		}
		
		return false;
	}

public static boolean sendContactEmail(Contact reco, String FromEmail, String FromName) {
	Properties props = new Properties();
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.smtp.host", "smtp.gmail.com");
	props.put("mail.smtp.port", "587");

	Session session = Session.getInstance(props,
			  new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(Strings.USERNAME, Strings.PASSWORD);
				}
			  });


    String msgBody = "\nTo: "+ reco.getToEmail() +"\n Message: "+reco.getMessage()+"\n"
    		+ "Regards,\n"+FromName;

    try {
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(FromEmail, FromName));
        msg.addRecipient(Message.RecipientType.TO,
                         new InternetAddress(reco.getToEmail(),reco.getToUsername()));
        msg.setSubject("Researchers Exchange Participation : Contact");
        msg.setText(msgBody);
        Transport.send(msg);
        
        return true;

    } catch (AddressException e) {
       System.out.println("Error with Address : AddressException occured" + e);
    } catch (MessagingException e) {
    	System.out.println("Messaging Exception occured " + e);
    } catch (UnsupportedEncodingException e) {
		System.out.println("Unsupported Encoding error " + e);
		e.printStackTrace();
	}
	
	return false;
}

public static String genkey() {
	MessageDigest md = null;
	try {
		md = MessageDigest.getInstance("SHA-1");
	} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
	}
    byte[] b = new byte[1024];
    new Random().nextBytes(b);
    
    md.update(b,0,b.length);
    byte[] mdbytes = md.digest();
 
    //convert the byte to hex format method 1
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < mdbytes.length; i++) {
      sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
    }

    System.out.println("Hex format : " + sb.toString());
    
	return sb.toString();
}

}
