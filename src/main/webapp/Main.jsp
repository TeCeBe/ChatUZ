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

  <form id="redirectForm">
    <label for="chatUrl">Podaj nazwę użytkownika czatu:</label>
    <input type="text" id="chatUrl" name="chatUrl">
    <input type="button" value="Nowy czat" onclick="redirectToChat()" class="chat-button">
  </form>
  <div>
    <h2>Lista Użytkowników</h2>
    <div id="usersList"></div>
    <script>
      window.onload = function() {
      };
    </script>
  </div>

  <div>
    <h2>Top 10 Znajomych</h2>
    <div id="topusersList"></div>
    <script>
      window.onload = function() {
        var data2 = JSON.parse('${topActiveUsers}');
        var chatbox2 = document.getElementById("topusersList");
        for (var i = 0; i < data2.length; i++) {
          //uzupełnianie DIVa wiadomościami z bazy
          var newMessage = document.createElement("userserss");
          //porównywanie nadawcy z odbiorca by wyświetlić we właściwym miejscu
          newMessage.innerHTML = "<p style='text-align:center; border-radius: 12px; background-color: green; color: white; padding: 5px;'><a href='http://localhost:8080/ChatUZ-1.0-SNAPSHOT/ChatRedirect?nickname="+ data2[i].id_to + "'>"+data2[i].id_to+"</a></p>";
          chatbox2.appendChild(newMessage);
        }
        var data = JSON.parse('${data}');
        var chatbox = document.getElementById("usersList");
        for (var i = 0; i < data.length; i++) {
          //uzupełnianie DIVa wiadomościami z bazy
          var newMessage = document.createElement("usersers");
          //porównywanie nadawcy z odbiorca by wyświetlić we właściwym miejscu
          newMessage.innerHTML = "<p style='text-align:center; border-radius: 12px; background-color: green; color: white; padding: 5px;'><a href='http://localhost:8080/ChatUZ-1.0-SNAPSHOT/ChatRedirect?nickname="+ data[i].id_to + "'>"+data[i].id_to+"</a></p>";
          chatbox.appendChild(newMessage);
        }
      };
    </script>

    <div>
    <script>
      function redirectToChat() {
        const chatUrlInput = document.getElementById('chatUrl');
        const chatUrl = chatUrlInput.value;

        if (chatUrl.trim() !== '') {
          window.location.href = "http://localhost:8080/ChatUZ-1.0-SNAPSHOT/ChatRedirect?nickname=" + chatUrl;
        } else {
          alert('Proszę podać nazwę użytkownika czatu.');
        }
      }
    </script>
    </div>
  </div>

  <form action="logout.jsp" method="post">
    <input type="submit" value="Wyloguj" class="button logout-button">
  </form>
</div>

</body>
</html>