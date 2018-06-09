package com.bbloggsbott.billingsystem.service.billingservice;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class BillMailer {
    String username = "mail@mail.com";
    String password = "password";
    Properties props;
    Session session;

    public BillMailer(){
        props = new Properties();
        session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    public boolean mailBill(String bill, String customerEmail, String billNo){
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        session.getProperties().put("mail.smtp.ssl.trust", "smtp.gmail.com");

        try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("johndoe@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(customerEmail));
            message.setSubject("Bill No: "+billNo);
            MimeBodyPart messageBodyPart = new MimeBodyPart();

            Multipart multipart = new MimeMultipart();

            messageBodyPart = new MimeBodyPart();
            String file = billNo+".txt";
            String fileName = "Bill";
            DataSource source = new FileDataSource(file);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName(fileName);
            multipart.addBodyPart(messageBodyPart);

            message.setContent(multipart);
            Transport.send(message);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        
    return false;
    }

}
