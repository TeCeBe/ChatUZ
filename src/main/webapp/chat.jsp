<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 23.11.2023
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="Java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    var socket = new WebSocket('ws://localhost:8080/ChatUZ-1.0-SNAPSHOT/chat');
    socket.onopen = function(event) {
        setTimeout(function () {
            socket.send('Twoja wiadomość');
        }, 3000);
    }
    var socket = new WebSocket('ws://localhost:8080/ChatUZ-1.0-SNAPSHOT/chat');
    socket.onmessage = function(event) {
        console.log('Otrzymano wiadomość: ' + event.data);
    };

</script>


</body>
</html>
