package com.auction.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class MailServiceImpl {
    
    @Autowired
    private JavaMailSender mailSender;
    
    private static final Logger LOGGER = Logger.getLogger(MailServiceImpl.class.getName());

    public String sendEmail(String to, String subject, String body) {
        try {
            // Creating mail message
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            message.setFrom("sabarikarthiks08@gmail.com"); // Ensure this email is verified in Brevo/SMTP provider

            // Sending email
            mailSender.send(message);
            LOGGER.info("✅ Email sent successfully to " + to);
            return "✅ Email sent successfully to " + to;

        } catch (MailSendException e) {
            LOGGER.log(Level.SEVERE, "❌ MailSendException: Failed to send email - SMTP settings might be incorrect.", e);
            return "❌ Error: Failed to send email. Check SMTP configuration.";

        } catch (MailException e) {
            LOGGER.log(Level.SEVERE, "❌ MailException: Email sending failed - Invalid email format or SMTP issue.", e);
            return "❌ Error: Unable to send email. Check recipient address and SMTP settings.";

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "❌ General Exception: An unexpected error occurred while sending email.", e);
            return "❌ Unexpected error occurred while sending email.";
        }
    }
    public String sendWelcomeEmail(String recipientEmail, String recipientName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(recipientEmail);
            helper.setSubject("Welcome to Auction Bazaar!");
            helper.setFrom("sabarikarthiks08@gmail.com");

            // Email Body (Use HTML for better formatting)
            String emailContent = "<h2>Welcome to Auction Bazaar, " + recipientName + "!</h2>"
                    + "<p>Thank you for joining our platform. We are excited to have you onboard.</p>"
                    + "<p>Explore and start bidding today!</p>"
                    + "<br><p>Best Regards,</p><p>Auction Bazaar Team</p>";

            helper.setText(emailContent, true); // true enables HTML

            mailSender.send(message);
            return "Email sent successfully to " + recipientEmail;
        } catch (MessagingException e) {
            return "Error sending email: " + e.getMessage();
        }
    }
}
