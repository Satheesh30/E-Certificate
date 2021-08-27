package sendMail;


import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;




public class SendMail {
	
	 public static void sendme(String tomail,String name, Multipart multipart) throws Exception {
	       
		 Properties properties = new Properties();
		 
		 properties.put("mail.smtp.auth", "true");
		 properties.put("mail.smtp.starttls.enable", "true");
		 properties.put("mail.smtp.host", "smtp.gmail.com");
		 properties.put("mail.smtp.port", "587");
		 
		 System.out.println("Prepare to Send Email :"+tomail);
		 
		 	
		
			
	       
	        final String myAccount = "abcd123@gmail.com";// set from mail id 
	        final String password="*******";//set password of mail id
	     
	        


	       
	        
	        Session session = Session.getInstance(properties,new Authenticator() {
	        	@Override
	        	protected PasswordAuthentication getPasswordAuthentication() {
	        		
	        		return new PasswordAuthentication(myAccount,password);
	        	}
	        }); 
	     
	        
	        Message message =prepareMessage(session,myAccount,tomail,multipart,name);
	        Transport.send(message);
	       System.out.println(name+" send succussfully");
	           
	 }
	 public static Message prepareMessage(Session session,String myAccount,String tomail, Multipart multipart,String name) throws Exception {
			
		 Message message=new MimeMessage(session);
			try {
				
				message.setFrom(new InternetAddress("myAccount"));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(tomail));
				
				//mail of Subject useing setsubject() method
				
				message.setSubject("E-Certificate");
				
				
				message.setContent(multipart) ;
	       
				
				return message;
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
			return message;
		
	 }
	       

	            
	    


}
