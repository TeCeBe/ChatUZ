<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Odzyskiwanie hasła</title>
  <style>
    /* Tutaj możesz dodać swój CSS */
    body {
      font-family: Arial, sans-serif;
    }
    .container {
      max-width: 400px;
      margin: auto;
      padding: 20px;
      border: 1px solid #ccc;
      border-radius: 5px;
      background: #f9f9f9;
    }
    h2 {
      text-align: center;
    }
    label, input {
      display: block;
      width: 100%;
      margin-bottom: 10px;
    }
    input[type="submit"] {
      cursor: pointer;
    }
  </style>
</head>
<body>

<div class="container">
  <h2>Zresetuj hasło</h2>

  <% String error = (String) request.getAttribute("error"); %>
  <% if (error != null && !error.isEmpty()) { %>
  <p style="color: red;"><%= error %></p>
  <% } %>

  <form action="ProcesResetowaniaHaslaServlet" method="post">
    <label for="email">Email:</label>
    <input type="email" id="email" name="email" required>

    <label for="newPassword">Nowe hasło:</label>
    <input type="password" id="newPassword" name="newPassword" required>

    <label for="confirmPassword">Potwierdź hasło:</label>
    <input type="password" id="confirmPassword" name="confirmPassword" required>

    <!-- Zakładam, że token jest przekazywany jako parametr GET lub w inny sposób -->
    <!-- Ten input hidden powinien przechwycić wartość tokenu -->
    <input type="hidden" name="token" value="<%= request.getParameter("token") %>">

    <input type="submit" value="Zresetuj hasło">
  </form>
</div>

</body>
</html>
