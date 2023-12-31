<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Messenger Home</title>
  <style>
    body {
      text-align: center;
      background: url('zdjecia/tlomain.jpg') no-repeat center center fixed;
      background-size: cover;
      font-family: Arial, sans-serif;
      margin: 0;
      height: 100vh;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-direction: column;
    }
    h1 {
      color: white;
    }
    .button {
      margin: 10px;
      padding: 10px 20px;
      font-size: 16px;
      border: none;
      border-radius: 5px;
      cursor: pointer;
    }
    .logout-button {
      background-color: #f44336;
      color: white;
    }
    .chat-button {
      background-color: #4CAF50;
      color: white;
    }
  </style>
</head>
<body>
<%
  String userName = (String) session.getAttribute("username");
  if (userName == null || userName.isEmpty()) {
    userName = "Nieznany Użytkownik";
  }
%>
<div>
  <h1>Witaj, <%= userName %></h1>

  <form action="chat.jsp" method="get" style="margin-bottom: 20px;">
    <input type="submit" value="Przejdź do Chatu" class="button chat-button">
  </form>

  <form action="logout.jsp" method="post">
    <input type="submit" value="Wyloguj" class="button logout-button">
  </form>
</div>

</body>
</html>
