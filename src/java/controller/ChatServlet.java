package controller;

import dal.MessageDAO;
import dal.ConversationDAO;
import model.Message;
import model.User;
import model.Conversation;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChatServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("u"); // Lấy thông tin người dùng từ session
        if (u == null) {
            response.sendRedirect("login.jsp"); // Nếu chưa đăng nhập, chuyển hướng về login
            return;
        }

        int conversationID = 0;
        try {
            conversationID = Integer.parseInt(request.getParameter("conversationID"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conversationID == 0) {
            response.sendRedirect("chatList.jsp"); // Nếu không có cuộc hội thoại, chuyển về danh sách
            return;
        }

        // Lấy tin nhắn từ database
        MessageDAO messageDAO = new MessageDAO();
        List<Message> messages = messageDAO.getMessagesByConversation(conversationID);

        request.setAttribute("messages", messages);
        request.setAttribute("conversationID", conversationID);
        request.getRequestDispatcher("Chat.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("u");

        if (u == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int conversationID = Integer.parseInt(request.getParameter("conversationID"));
        String content = request.getParameter("message");

        if (conversationID > 0 && content != null && !content.trim().isEmpty()) {
            MessageDAO messageDAO = new MessageDAO();
            Message message = new Message(0, conversationID, u.getUserID(), new Timestamp(System.currentTimeMillis()), content);
            messageDAO.saveMessage(message);
        }

        response.sendRedirect("ChatServlet?conversationID=" + conversationID); // Reload chat
    }
}
