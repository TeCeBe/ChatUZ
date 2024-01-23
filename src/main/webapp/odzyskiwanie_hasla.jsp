<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Odzyskiwanie hasla</title>
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
        .password-reset-container {
            text-align: center;
            padding: 50px;
            background-color: rgba(0, 0, 0, 0.5);
            border: 2px solid #808080;
            border-radius: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            margin: 20px;
            color: white;
        }
        .password-reset-container h2 {
            font-size: 32px;
            margin-bottom: 20px;
            color: white;
        }
        .password-reset-container label {
            font-size: 20px;
            display: block;
            margin-bottom: 10px;
            color: white;
        }
        .password-reset-container input {
            font-size: 20px;
            width: 80%;
            padding: 10px;
            margin-bottom: 15px;
            color: black;
        }
        .password-reset-container input[type="submit"] {
            font-size: 20px;
            margin-top: 10px;
            color: black;
        }
        .error-message {
            color: red;
            margin-bottom: 10px;
            font-size: 20px;
            font-weight: bold;
        }
        .powrot_do_logowania a {
            font-size: 16px;
            text-decoration: none;
            color: white;
        }

    </style>
</head>
<body>
<div class="password-reset-container">
    <form action="OdzyskanieHasla" method="post">
        <h2>Odzyskiwanie hasla</h2>
        <label for="reset-email">Email:</label>
        <input type="email" id="reset-email" name="email" required><br>
        <input type="submit" value="Resetuj haslo">
    </form>
    <div class="powrot_do_logowania">
        <a href="login.jsp">Powrot do logowania</a>
    </div>
</div>
</body>
</html>