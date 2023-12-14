<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
        .login-container {
            text-align: center;
            padding: 50px;
            background-color: rgba(0, 0, 0, 0.5);
            border: 2px solid #808080;
            border-radius: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
        }
        .login-container h2 {
            font-size: 32px;
            margin-bottom: 20px;
            color: white;
        }
        .login-container label {
            font-size: 20px;
            display: block;
            margin-bottom: 10px;
            color: white;
        }
        .login-container input {
            font-size: 20px;
            width: 80%;
            padding: 10px;
            margin-bottom: 15px;
            color: black;
        }
        .login-container input[type="submit"] {
            font-size: 20px;
            margin-top: 10px;
            color: black;
        }
        .create-account-link {
            text-align: center;
            margin-top: 10px;
            color: white;
        }
        .create-account-link a {
            font-size: 16px;
            text-decoration: none;
            color: white;
        }
        .error-message {
            color: red;
            margin-bottom: 10px;
            font-size: 20px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<div class="login-container">

    <% String error = request.getParameter("error"); %>
    <% if ("invalid_login".equals(error)) { %>
    <div class="error-message">Zły login</div>
    <% } %>

    <form action="LoginServlet" method="post">
        <h2>Login</h2>
        <label for="login-username">Username:</label>
        <input type="text" id="login-username" name="username" required><br>

        <label for="login-password">Password:</label>
        <input type="password" id="login-password" name="password" required><br>

        <input type="submit" value="Login">
    </form>

    <div class="create-account-link">
        <a href="register.jsp">Stworz konto</a>
    </div>
</div>
</body>
</html>
