<%@ page import="java.util.List" %>
<%@ page import="model.Message" %>
<%@ page import="model.User" %>
<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
    <% User u = (User) session.getAttribute("u"); %>
    <% if (u != null && u.getRole().equalsIgnoreCase("Mentor")) { %>
        <%@include file="MentorHeader.jsp" %>
    <% } else { %>
        <%@include file="MenteePanner.jsp" %>
    <% } %>
        <meta charset="utf-8">
    <title>Chat</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .chat-container { width: 50%; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px; box-shadow: 2px 2px 10px #aaa; background-color: #f9f9f9; }
        .message-box { height: 300px; overflow-y: auto; border: 1px solid #ddd; padding: 10px; background-color: white; display: flex; flex-direction: column; }
        .message { margin: 10px 0; padding: 8px; border-radius: 5px; max-width: 60%; clear: both; }
        .sent { background-color: #007bff; color: white; align-self: flex-end; text-align: right; }
        .received { background-color: #ddd; align-self: flex-start; text-align: left; }
        .message p { margin: 0; white-space: pre-line; }
        .message small { display: block; margin-top: 5px; font-size: 0.8em; color: #555; }
        .message-form { margin-top: 20px; display: flex; gap: 10px; }
        .message-form textarea { width: 80%; height: 40px; padding: 5px; border-radius: 5px; }
        .message-form button { padding: 10px 15px; border: none; background-color: #007bff; color: white; cursor: pointer; }
        .message-form button:hover { background-color: #0056b3; }
    </style>
</head>
<body>
    <div class="chat-container">
        <h2>Chat</h2>
        <div class="message-box">
            <% 
                List<Message> messages = (List<Message>) request.getAttribute("messages");
                if (messages != null) {
                    for (Message msg : messages) {
                        boolean isSender = (u != null && u.getUserID() == msg.getSenderID());
            %>
                <div class="message <%= isSender ? "sent" : "received" %>">
                    <p><%= msg.getContent() %></p>
                    <small><%= msg.getSentAt() %></small>
                </div>
            <% 
                    }
                } else {
            %>
                <p>Không có tin nh?n nào.</p>
            <% } %>
        </div>

        <form action="ChatServlet" method="POST" class="message-form">
            <input type="hidden" name="conversationID" value="<%= request.getAttribute("conversationID") %>">
            <textarea name="message" required></textarea>
            <button type="submit">Send</button>
        </form>
    </div>
    <%@include file="Footer.jsp" %>
</body>
</html>