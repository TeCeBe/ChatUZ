<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Odzyskiwanie hasła</title>
  <style>
    body {
      display: flex;
      align-items: center;
      justify-content: center;
      height: 100vh;
      margin: 0;
      background: url('zdjecia/tlo_login_register.jpg') no-repeat center center fixed;
      background-size: cover;
      font-family: Arial, sans-serif;
    }

    .password-reset-container {
      text-align: center;
      padding: 50px;
      background-color: rgba(0, 0, 0, 0.5);
      border: 2px solid #808080;
      border-radius: 20px;
      box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
      margin: 20px;
      color: white;
    }

    .password-reset-container h2 {
      font-size: 32px;
      margin-bottom: 20px;
      color: white;
    }

    .password-reset-container label {
      font-size: 20px;
      display: block;
      margin-bottom: 10px;
      color: white;
    }

    .password-reset-container input {
      font-size: 20px;
      width: 80%;
      padding: 10px;
      margin-bottom: 15px;
      color: black;
    }

    .password-reset-container input[type="submit"] {
      font-size: 20px;
      margin-top: 10px;
      color: black;
    }

    .error-message {
      color: red;
      margin-bottom: 10px;
      font-size: 20px;
      font-weight: bold;
    }

    .powrot_do_logowania a {
      font-size: 16px;
      text-decoration: none;
      color: white;
    }
  </style>
</head>
<body>

<div class="password-reset-container">
  <h2>Zresetuj hasło</h2>

  <% String error = (String) request.getAttribute("error"); %>
  <% if (error != null && !error.isEmpty()) { %>
  <p class="error-message"><%= error %></p>
  <% } %>

  <form action="ProcesResetowaniaHaslaServlet" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>

    <label for="newPassword">Nowe hasło:</label>
    <input type="password" id="newPassword" name="newPassword" required>

    <label for="confirmPassword">Potwierdź hasło:</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required>

    <input type="hidden" name="token" value="<%= request.getParameter("token") %>">

    <input type="submit" value="Zresetuj hasło">
  </form>
</div>

</body>
</html>
