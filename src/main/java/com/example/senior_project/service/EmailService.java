package com.example.senior_project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String to, String verificationLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        
        helper.setFrom("noreply@yourapp.com");
        helper.setTo(to);
        helper.setSubject("E-posta Adresinizi Doğrulayın");
        
        String emailContent = String.format("""
            <html>
                <body>
                    <h2>E-posta Doğrulama</h2>
                    <p>Hesabınızı doğrulamak için aşağıdaki linke tıklayın:</p>
                    <a href="%s">E-posta Adresimi Doğrula</a>
                    <p>Bu link 24 saat geçerlidir.</p>
                </body>
            </html>
            """, verificationLink);
        
        helper.setText(emailContent, true);
        mailSender.send(message);
    }
} 