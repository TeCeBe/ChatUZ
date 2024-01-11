package com.chatuz.chatuz;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.UUID; // Do generowania unikalnego tokenu

public class OdzyskiwanieHasłaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        String email = request.getParameter("email");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://sql.freedb.tech:3306/freedb_chatuz?useSSL=false", "freedb_tecebe", "%Dn@fSFRz&ph7%3");

            // Sprawdzenie, czy istnieje użytkownik z tym adresem e-mail
            ps = con.prepareStatement("SELECT id FROM users WHERE email = ?");
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Generowanie tokenu resetowania hasła
                String token = UUID.randomUUID().toString();
                // Zapisanie tokenu resetowania hasła w bazie danych
                PreparedStatement psUpdate = con.prepareStatement("UPDATE users SET reset_token = ?, token_expiry = ? WHERE email = ?");
                psUpdate.setString(1, token);
                psUpdate.setTimestamp(2, new Timestamp(System.currentTimeMillis() + 3600000)); // 1 godzina ważności
                psUpdate.setString(3, email);
                psUpdate.executeUpdate();

                // Wysyłanie e-maila z linkiem do resetowania hasła
                String resetLink = "http://localhost:8080/ChatUZ-1.0-SNAPSHOT/odzyskiwanie_hasla.jsp?token=" + token;
                sendEmail(email, "Resetowanie Hasła", "Kliknij w link, aby zresetować hasło: " + resetLink);

                response.sendRedirect("login.jsp?message=reset_link_sent");
            } else {
                response.sendRedirect("password-reset.jsp?error=invalid_email");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Database access error.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendEmail(String recipient, String subject, String body) {
        final String sender = "javalovers01@gmail.com";
        final String password = "JavaProjekt01!";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
