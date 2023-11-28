<%--
  Created by IntelliJ IDEA.
  User: forge
  Date: 28.11.2023
  Time: 19:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Stworz konto</title>
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
        .register-container {
            text-align: center;
            padding: 50px;
            background-color: rgba(0, 0, 0, 0.5);
            border: 2px solid #808080;
            border-radius: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
            color: white;
        }
        .register-container h2 {
            font-size: 32px;
            margin-bottom: 20px;
            color: white;
        }
        .register-container label {
            font-size: 20px;
            display: block;
            margin-bottom: 10px;
            color: white;
        }
        .register-container input {
            font-size: 20px;
            width: 80%;
            padding: 10px;
            margin-bottom: 15px;
            color: black;
        }
        .register-container input[type="submit"] {
            font-size: 20px;
            margin-top: 10px;
            color: black;
        }
        .login-link {
            text-align: center;
            margin-top: 10px;
            color: white;
        }
        .login-link a {
            font-size: 16px;
            text-decoration: none;
            color: white;
        }
    </style>
</head>
<body>
<div class="register-container">


    <form action="RegisterServlet" method="post">
        <h2>Stworz konto</h2>
        <label for="register-username">Username:</label>
        <input type="text" id="register-username" name="username" required><br>

        <label for="register-password">Password:</label>
        <input type="password" id="register-password" name="password" required><br>

        <input type="submit" value="Register">
    </form>


    <div class="login-link">
        <a href="login.jsp">Masz juz konto? Zaloguj sie</a>
    </div>
</div>
</body>
</html>
