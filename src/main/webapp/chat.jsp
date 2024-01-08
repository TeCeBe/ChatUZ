<%--
  Created by IntelliJ IDEA.
  User: Lenovo
  Date: 23.11.2023
  Time: 19:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="Java" %>
<%
    String userName = (String) session.getAttribute("username");
    if (userName == null || userName.isEmpty()) {
        userName = "Anonim";
    }
%>
<html>
<head>
    <title>Chat</title>
<style>
    body {
        font-family: Arial, sans-serif;
        margin: 0;
        padding: 0;
        background-color: #F3F3F3;
    }
    #chatbox {
        width: 90%;
        height: 70%;
        padding: 20px;
        border: 1px solid #cccccc;
        margin: 0 auto;
        margin-top: 50px;
        overflow-y: auto;
        background-color: #ffffff;
        border-radius: 10px;
    }
    #userInput {
        width: 80%;
        margin: 0 auto;
        margin-top: 20px;
    }
    #textInput {
        width: 70%;
        height: 50px;
        padding: 10px;
        border: 1px solid #cccccc;
        border-radius: 10px;
    }
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
    #title {
        width: 100%;
        height: 60px;
        background-color: blue;
        color: white;
        border: none;
        font-size: 18px;
        padding-left: 100px;
        padding-top: 5px;
    }
    #submitBtn:hover {
        background-color: #45a049;
    }
</style>

</head>
<body>
<div id="title">
    <p><b>${nick}</b>   ostatnio online: teraz</p>
</div>
<div id="chatbox">
</div>
<div id="userInput">
    <input id="textInput" type="text" name="msg" placeholder="Wpisz swoją wiadomość">
    <input id="submitBtn" type="submit" value="Wyślij" onclick="sendMessage();">
</div>
<script>
    function sendMessage()
    {
        var socket = new WebSocket('ws://localhost:8080/ChatUZ-1.0-SNAPSHOT/chat');
        var message = {
            id_from: "<%= userName %>",
            id_to: "${nick}",
            timestamp: new Date(),
            content: document.getElementById("textInput").value,
            target: ""
        };
        date = new Date(message.timestamp);
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds();
        if (hours < 10) hours = "0" + hours;
        if (minutes < 10) minutes = "0" + minutes;
        if (seconds < 10) seconds = "0" + seconds;
        var formattedDate = day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds;
        var serializedMessage = JSON.stringify(message);
        var chatbox = document.getElementById("chatbox");
        var newMessage = document.createElement("message");
        newMessage.innerHTML = "<p style='text-align:right; border-radius: 10px; background-color: blue; color: white; padding: 5px;'>(" + formattedDate + ") <strong>Ja: </strong> " + message.content + "</p>";
        chatbox.appendChild(newMessage);
        socket.onopen = function(event) {
            socket.send(serializedMessage);
            console.log("Wiadomość wysłana");
            var messageInput = document.getElementById("textInput");
            messageInput.value = "";
        }
    }

    var socket = new WebSocket('ws://localhost:8080/ChatUZ-1.0-SNAPSHOT/chat');
    socket.onmessage = function(event) {
        var deserializedMessage = JSON.parse(event.data);
        console.log('Otrzymano wiadomość: ' + deserializedMessage.content);
        var newMessage = document.createElement("message");
        var chatbox = document.getElementById("chatbox");
        date = new Date(deserializedMessage.timestamp);
        var day = date.getDate();
        var month = date.getMonth() + 1;
        var year = date.getFullYear();
        var hours = date.getHours();
        var minutes = date.getMinutes();
        var seconds = date.getSeconds();
        if (hours < 10) hours = "0" + hours;
        if (minutes < 10) minutes = "0" + minutes;
        if (seconds < 10) seconds = "0" + seconds;
        var formattedDate = day + "/" + month + "/" + year + " " + hours + ":" + minutes + ":" + seconds;
        if (deserializedMessage.id_from !== deserializedMessage.id_to)
        {
            if (deserializedMessage.id_to === "<%= userName %>")
            {
                newMessage.innerHTML = "<p>(" + formattedDate + ") <strong>" + deserializedMessage.id_from + ":</strong> " + deserializedMessage.content + "</p>";
                chatbox.appendChild(newMessage);
                chatbox.scrollTop = chatbox.scrollHeight;
            }

        }
    };

    document.getElementById("textInput").addEventListener("keydown", function(e) {
        if (e.key === "Enter") {
            sendMessage();
        }
    });

</script>
</body>
</html>
