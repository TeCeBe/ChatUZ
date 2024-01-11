<%--
  Strona pokazująca informację o tym, że użytkownik czatu nie został znaleziony.
  Przekierowanie z ChatRedirect.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Nie znaleziono użytkownika</title>
    <style>
        #submitBtn {
            height: 50px;
            width: 20%;
            background-color: blue;
            color: white;
            border: none;
            border-radius: 10px;
            cursor: pointer;
            font-size: 18px;
        }
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #F3F3F3;
            text-align: center;
        }
        #title1
        {
            padding-top: 80px;
        }
    </style>
</head>
<body>
<div id="content">
    <h1 id="title1">Nie znaleziono użytkownika o takiej nazwie!</h1>
    <input id="submitBtn" type="submit" value="Powrót" onclick="window.location.href='Main.jsp';">
</div>
</body>
</html>
