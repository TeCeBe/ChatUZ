<%@ page import="static jakarta.mail.internet.UniqueValue.id" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edycja Profilu</title>
</head>
<body>
<h2>Edycja Profilu</h2>

<form action="EdycjaProfiluServlet" method="post">
    <input type="hidden" id="userId" required><br>

    <label for="email">Edytuj E-mail:</label>
    <input type="text" id="email" name="email" required><br>

    <label for="haslo">Zmień Hasło:</label>
    <input type="password" id="haslo" name="haslo" required><br>

    <label for="trybImprezy">Edytuj Tryb Imprezy:</label>
    <input type="checkbox" id="trybImprezy" name="trybImprezy"><br>

    <input type="submit" value="Potwierdź">
</form>
</body>
</html>
