<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background: url('zdjecia/tlo_index.jpg') no-repeat center center fixed;
            background-size: cover;
            margin: 0;
            padding: 0;
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
        }
        .container {
            text-align: center;
            border: 2px solid #808080;
            padding: 40px;
            border-radius: 20px;
            background-color: rgba(0, 0, 0, 0.8);
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            color: white;
        }
        h2, a {
            color: #fff;
            font-size: 36px;
        }
        a {
            display: inline-block;
            margin: 15px;
            padding: 15px 30px;
            text-decoration: none;
            background-color: rgba(0, 0, 0, 0.5);
            border-radius: 10px;
            font-size: 18px;
            transition: background-color 0.3s;
        }
        a:hover {
            background-color: #333;
        }
    </style>
</head>
<body>
<div class="container">
    <h2>MESSENGER</h2>
    <p>
        <a href="login.jsp">Login</a>
    </p>
    <p>
        <a href="register.jsp">Register</a>
    </p>
</div>
</body>
</html>
