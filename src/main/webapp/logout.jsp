<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Wylogowanie</title>
</head>
<body>
<%
  session.invalidate();
  response.sendRedirect("login.jsp");
%>
</body>
</html>