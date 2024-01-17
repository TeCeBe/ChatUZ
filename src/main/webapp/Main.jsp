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
      color: black;
    }
    .text-container {
      border: 4px solid white;
      padding: 40px;
      border-radius: 20px;
      background-color: rgba(255, 255, 255, 0.6);
      text-align: center;
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
    userName = "Anonim";
  }
%>
<div class="text-container">
  <h1>Witaj, <%= userName %></h1>

  <form action="chat.jsp" method="get" style="margin-bottom: 20px;">
    <input type="submit" value="Przejdź do Chatu" class="button chat-button">
  </form>
  <div>
    <h2>Lista Użytkowników</h2>
    <div id="usersList"></div>

    <script>
      // Pobierz dane JSON z serwera i zaktualizuj listę użytkowników
      fetch('/FriendsServlet')
              .then(response => response.json())
              .then(data => {
                const usersListDiv = document.getElementById('usersList');
                data.forEach(user => {
                  const userDiv = document.createElement('div');
                  userDiv.innerHTML = `<p>${user.id_to}</p>`;
                  usersListDiv.appendChild(userDiv);
                });
              })
              .catch(error => console.error('Błąd podczas pobierania danych:', error));
    </script>
  </div>

  <form action="logout.jsp" method="post">
    <input type="submit" value="Wyloguj" class="button logout-button">
  </form>
</div>

</body>
</html>