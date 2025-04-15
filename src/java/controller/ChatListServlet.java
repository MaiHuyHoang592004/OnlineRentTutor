package controller;

import dal.ConversationDAO;
import model.Conversation;
import model.User;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ChatListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User u = (User) session.getAttribute("u");

        if (u == null) {
            response.sendRedirect("Login.jsp");
            return;
        }

        ConversationDAO conversationDAO = new ConversationDAO();
        List<Conversation> conversationList = conversationDAO.getConversationsByUser(u.getUserID());

        request.setAttribute("conversationList", conversationList);
        request.getRequestDispatcher("chatList.jsp").forward(request, response);
    }
}
