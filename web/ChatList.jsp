<%@ page import="java.util.List" %>
<%@ page import="dal.UserDAO" %>
<%@ page import="dal.ConversationDAO" %>
<%@ page import="model.Conversation" %>
<%@ page import="model.User" %>

<%
    // L?y thông tin user t? session
    User u = (User) session.getAttribute("u");
    if (u == null) {
        response.sendRedirect("Login.jsp");
        return;
    }

    // Kh?i t?o DAO ?? l?y d? li?u t? database
    ConversationDAO conversationDAO = new ConversationDAO();
    UserDAO userDAO = new UserDAO();

    // L?y danh sách t?t c? cu?c trò chuy?n c?a user hi?n t?i
    List<Conversation> conversationList = conversationDAO.getConversationsByUser(u.getUserID());
%>

<!DOCTYPE html>
<html>
<head>
    <% if (u != null && u.getRole().equalsIgnoreCase("Mentor")) { %>
        <%@include file="MentorHeader.jsp" %>
    <% } else { %>
        <%@include file="MenteePanner.jsp" %>
    <% } %>
    <meta charset="UTF-8">
    <title>List Message</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 30px; }
        .chat-container { width: 50%; margin: auto; border: 1px solid #ddd; padding: 20px; border-radius: 10px; box-shadow: 2px 2px 10px #aaa; background-color: #f9f9f9; }
        ul { list-style-type: none; padding: 0; }
        .chat-item { padding: 10px; border-bottom: 1px solid #ddd; display: flex; align-items: center; }
        .chat-item img { width: 40px; height: 40px; border-radius: 50%; margin-right: 10px; }
        .chat-item a { text-decoration: none; color: black; font-size: 16px; }
    </style>
</head>
<body>
    <div class="chat-container">
        <h2>List Message</h2>
        <ul>
            <% 
                if (conversationList != null && !conversationList.isEmpty()) {
                    for (Conversation conversation : conversationList) {
                        // Xác ??nh chat partner (Mentor ho?c Mentee)
                        User chatPartner = null;
                        
                        if (u.getUserID() == conversation.getMentorID()) {
                            chatPartner = userDAO.getUserByID(conversation.getMenteeID());
                        } else {
                            chatPartner = userDAO.getUserByID(conversation.getMentorID());
                        }

                        if (chatPartner == null) continue; // N?u không tìm th?y user, b? qua
            %>
            <li class="chat-item">
                <a href="ChatServlet?conversationID=<%= conversation.getConversationID() %>">
                    <img src="<%= (chatPartner.getAvatar() != null) ? chatPartner.getAvatar() : "default-avatar.png" %>" alt="Avatar">
                    <span><%= (chatPartner.getFullname() != null) ? chatPartner.getFullname() : "Ng??i dùng không t?n t?i" %></span>
                </a>
            </li>
            <% 
                    }
                } else { 
            %>
            <p>Không có cu?c trò chuy?n nào.</p>
            <% } %>
        </ul>
    </div>
         <%@include file="Footer.jsp" %>
</body>
</html>
