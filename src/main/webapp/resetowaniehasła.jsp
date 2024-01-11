<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
    }
    .password-reset-container {
      /* style kontenera */
    }
    /* style pozostałych elementów */
  </style>
</head>
<body>
<div class="password-reset-container">

  <% String error = request.getParameter("error"); %>
  <% if ("invalid_email".equals(error)) { %>
  <div class="error-message">Nieznany adres email</div>
  <% } %>

  <form action="OdzyskiwanieHasłaServlet" method="post">
    <h2>Odzyskiwanie hasła</h2>
    <label for="reset-email">Email:</label>
    <input type="email" id="reset-email" name="email" required><br>

    <input type="submit" value="Resetuj hasło">
  </form>

  <div class="powrot_do_logowania">
    <a href="login.jsp">Powrót do logowania</a>
  </div>
</div>
</body>
</html>