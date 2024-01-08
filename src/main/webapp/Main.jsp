<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Messenger Home</title>
</head>
<body>
<%
  String userName = (String) session.getAttribute("username");
  if (userName == null || userName.isEmpty()) {
    userName = "Nieznany Użytkownik";
  }
%>
<h1>Witaj, <%= userName %></h1>

<form action="logout.jsp" method="post">
  <input type="submit" value="Wyloguj">
</form>

</body>
</html>
