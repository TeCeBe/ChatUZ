package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.sql.*;
import java.util.Properties;
import java.util.UUID;

public class OdzyskanieHasla extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String email = request.getParameter("email");
        String token = UUID.randomUUID().toString();

        // Database connection setup
        String dbUrl = "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false";
        String dbUser = "freedb_tecebe";
        String dbPassword = "%Dn@fSFRz&ph7%3";

        // Email setup
        String emailSender = "loversjava01@outlook.com"; // Change to your email
        String emailPassword = "JavaProjekt01!"; // Change to your password

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
                 PreparedStatement ps = con.prepareStatement("SELECT id FROM users WHERE email = ?")) {

                ps.setString(1, email);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        try (PreparedStatement psUpdate = con.prepareStatement(
                                "UPDATE users SET reset_token = ?, token_expiry = ? WHERE email = ?")) {
                            psUpdate.setString(1, token);
                            psUpdate.setTimestamp(2, new Timestamp(System.currentTimeMillis() + 3600000)); // Godzina na wygaszenie tokenu
                            psUpdate.setString(3, email);
                            psUpdate.executeUpdate();
                        }

                        // Stworzenie linku na reset
                        String resetLink = "http://localhost:8080/ChatUZ-1.0-SNAPSHOT/resetowaniehasla.jsp?token=" + token;

                        // Wyslanie
                        sendEmail(email, "Resetowanie Hasła", "Aby zresetować hasło, kliknij w poniższy link:\n" + resetLink, emailSender, emailPassword);
                        response.sendRedirect("login.jsp?message=reset_link_sent");
                    } else {
                        response.sendRedirect("password-reset.jsp?error=invalid_email");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Error przetwarzania zadania.", e);
        }
    }

    private void sendEmail(String recipient, String subject, String body, String sender, String password) throws MessagingException {
        Properties properties = new Properties();
        properties.put("mail.smtp.ssl.trust", "smtp.office365.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.office365.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sender));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        message.setSubject(subject);
        message.setText(body);

        Transport.send(message);
    }
}
