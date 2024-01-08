<%@ page import="java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Messenger Home</title>
</head>
<body>
<%
  String userID = (String) session.getAttribute("userID");
  String userName = "Nieznany Użytkownik";
  Connection conn = null;
  Statement stmt = null;
  try {

    Class.forName("com.mysql.jdbc.Driver");
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/baza", "root", "");

    stmt = conn.createStatement();
    String sql = "SELECT id FROM baza WHERE id = " + userID;
    ResultSet rs = stmt.executeQuery(sql);

    if (rs.next()) {
      userName = rs.getString("id");
    }
    rs.close();
    stmt.close();
    conn.close();
  } catch (Exception e) {
    e.printStackTrace();
  } finally {
    try {
      if (stmt != null) stmt.close();
    } catch (SQLException se2) {}
    try {
      if (conn != null) conn.close();
    } catch (SQLException se) {
      se.printStackTrace();
    }
  }
%>
<h1>Witaj, <%= userName %></h1>

<form action="logout.jsp" method="post">
  <input type="submit" value="Wyloguj">
</form>

</body>
</html>