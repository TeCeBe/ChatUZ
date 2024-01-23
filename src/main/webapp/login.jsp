<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
        .login-container {
            text-align: center;
            padding: 50px;
            background-color: rgba(0, 0, 0, 0.5);
            border: 2px solid #808080;
            border-radius: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
        }
        .login-container h2 {
            font-size: 32px;
            margin-bottom: 20px;
            color: white;
        }
        .login-container label {
            font-size: 20px;
            display: block;
            margin-bottom: 10px;
            color: white;
        }
        .login-container input {
            font-size: 20px;
            width: 80%;
            padding: 10px;
            margin-bottom: 15px;
            color: black;
        }
        .login-container input[type="submit"] {
            font-size: 20px;
            margin-top: 10px;
            color: black;
        }
        .powrot_do_logowania a {
            font-size: 16px;
            text-decoration: none;
            color: white;
        }
        .error-message {
            color: red;
            margin-bottom: 10px;
            font-size: 20px;
            font-weight: bold;
        }
        <!--.odzyskanie-hasla-link {
            text-align: center;
            margin-top: 10px;
            color: white;
        }-->
        .odzyskanie-hasla-link a {
            font-size: 16px;
            text-decoration: none;
            color: #f2f2f2;
        }
        .stwórz-konto-link a {
            font-size: 16px;
            text-decoration: none;
            color: white;
        }
        .message-success {
            color: green;
            margin-bottom: 10px;
            font-size: 20px;
            font-weight: bold;
            background-color: #ccffcc;
            border: 1px solid #b2d8b2;
            border-radius: 5px;
            padding: 10px;
            width: 80%;
            margin-left: auto;
            margin-right: auto;
        }
    </style>
</head>
<body>
<div class="login-container">

    <!-- Pobieranie parametrów błędu i wiadomości z żądania -->
    <%
        String error = request.getParameter("error");
        String message = request.getParameter("message");
    %>

    <!-- Wyświetlanie komunikatu o błędzie, jeśli logowanie było nieudane -->
    <% if ("invalid_login".equals(error)) { %>
    <div class="error-message">Zly login</div>
    <% } %>


    <% if ("reset_link_sent".equals(message)) { %>
    <div class="message-success">Link do resetowania hasla na mailu</div>
    <% } %>

    <%
        String successMessage = (String) request.getSession().getAttribute("message-success");
        if (successMessage != null && !successMessage.isEmpty()) {
            out.println("<div class='message-success'>" + successMessage + "</div>");
            request.getSession().removeAttribute("message-success");
        }
    %>


    <!-- Formularz logowania -->
    <form action="LoginServlet" method="post">
        <h2>Login</h2>
        <label for="login-username">Username:</label>
        <input type="text" id="login-username" name="username" required><br>

        <label for="login-password">Password:</label>
        <input type="password" id="login-password" name="password" required><br>

        <input type="submit" value="Login">
    </form>

    <!-- Link do strony rejestracji -->
    <div class="stwórz-konto-link">
        <a href="register.jsp">Stworz konto</a>
    </div>


    <div class="odzyskanie-hasla-link">
         <a href="odzyskiwanie_hasla.jsp">Nie pamietasz hasla? Odzyskaj je</a>
    </div>


</div>
</body>
</html>