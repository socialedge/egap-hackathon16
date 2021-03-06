package com.receipts.on.util;

import com.receipts.on.model.Prescription;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.Properties;

public class Notifications {

    private static final String USERNAME = "prescriptions.egap@mail.ru";
    private static final String PASSWORD = "asdfcerhw3rwrx24j342342";
    private static final String RECIPIENT = "glasierr@inbox.ru";

    private static final String SUBJECT = "Рецепт від лікаря";

    public static void sendEmail(Prescription prescription, String url) {
        Properties props = new Properties();
        props.setProperty("mail.debug", "true");
        props.setProperty("javax.net.ssl.debug", "all");
        props.setProperty("mail.smtp.host", "smtp.mail.ru");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.connectiontimeout", "60000");
        props.setProperty("mail.smtp.timeout", "60000");
        props.setProperty("mail.transport.protocol", "smtps");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");
        props.setProperty("mail.smtp.ssl.enable", "true");
        props.setProperty("mail.mime.charset", "UTF-8");
        props.setProperty("java.security.debug", "all");
        props.setProperty("logging.properties", "all");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            String cid = prescription.getPrescriptionId();

            MimeMultipart content = new MimeMultipart("related");

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(HtmlUtils.prescriptionTable(prescription, url, cid), "text/html; charset=utf-8");
            content.addBodyPart(htmlPart);

            MimeBodyPart imagePart = new MimeBodyPart();
            File qrCodeFile = QrCodes.generateCodeToFile(prescription.getPrescriptionId());
            imagePart.attachFile(qrCodeFile);
            imagePart.setContentID("<" + cid + ">");
            imagePart.setDisposition(MimeBodyPart.INLINE);
            content.addBodyPart(imagePart);


            MimeMessage message = new MimeMessage(session);
            message.setSubject(SUBJECT);
            message.setContent(content);
            message.setRecipients(Message.RecipientType.TO, RECIPIENT);
            message.setSender(new InternetAddress(USERNAME));
            Transport.send(message);
        } catch (Throwable e) {
            e.printStackTrace(System.err);
        }
    }
}
