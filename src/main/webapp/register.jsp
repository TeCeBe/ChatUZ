
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stworz konto</title>
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
            background: url('zdjecia/tlo_login_register.jpg') no-repeat center center fixed;
            background-size: cover;
        }
        .register-container {
            text-align: center;
            padding: 50px;
            background-color: rgba(0, 0, 0, 0.5);
            border: 2px solid #808080;
            border-radius: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
            color: white;
        }
        .register-container h2 {
            font-size: 32px;
            margin-bottom: 20px;
            color: white;
        }
        .register-container label {
            font-size: 20px;
            display: block;
            margin-bottom: 10px;
            color: white;
        }
        .register-container input {
            font-size: 20px;
            width: 80%;
            padding: 10px;
            margin-bottom: 15px;
            color: black;
        }
        .register-container input[type="submit"] {
            font-size: 20px;
            margin-top: 10px;
            color: black;
        }
        .login-link {
            text-align: center;
            margin-top: 10px;
            color: white;
        }
        .login-link a {
            font-size: 16px;
            text-decoration: none;
            color: white;
        }
    </style>
</head>
<body>
<div class="register-container">
    <!-- Blok kodu JSP do obsługi błędów rejestracji -->
    <%
        String error = request.getParameter("error");
        // Wyświetlanie komunikatu o zajętej nazwie użytkownika
        if ("username_taken".equals(error)) {
            out.print("<div class='error-message'>Username zajęty</div>");
        }
        // Wyświetlanie komunikatu o zbyt krótkim haśle
        else if ("short_password".equals(error)) {
            out.print("<div class='error-message'>Hasło musi mieć przynajmniej 8 znaków</div>");
        }
        // Wyświetlanie komunikatu o zajętym adresie email
        else if ("email_taken".equals(error)) {
            out.print("<div class='error-message'>Adres email jest już zajęty</div>");
        }
    %>

    <!-- Formularz rejestracji -->
    <form action="RegisterServlet" method="post">
        <h2>Stworz konto</h2>
        <label for="register-username">Username:</label>
        <input type="text" id="register-username" name="username" required><br>

        <label for="register-password">Password:</label>
        <input type="password" id="register-password" name="password" required><br>

        <label for="register-email">Email:</label>
        <input type="email" id="register-email" name="email" required><br>

        <input type="submit" value="Register">
    </form>

    <!-- Link do strony logowania dla użytkowników, którzy mają już konto -->
    <div class="login-link">
        <a href="login.jsp">Masz juz konto? Zaloguj sie</a>
    </div>
</div>
</body>
</html>