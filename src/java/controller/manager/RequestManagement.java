package controller.manager;

import dal.RequestDAO;
import jakarta.servlet.ServletException;
// Removing the WebServlet annotation to avoid conflict with web.xml mapping
// import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import model.Request;

// Removed @WebServlet annotation as it conflicts with web.xml mapping
public class RequestManagement extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RequestDAO requestDAO;

    @Override
    public void init() throws ServletException {
        requestDAO = new RequestDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sort");
        String sortOrder = request.getParameter("order");
        
        // Get current page from request parameter
        int currentPage = 1;
        if (request.getParameter("page") != null) {
            try {
                currentPage = Integer.parseInt(request.getParameter("page"));
                if (currentPage < 1) currentPage = 1;
            } catch (NumberFormatException e) {
                // If page parameter is not a valid number, default to page 1
                currentPage = 1;
            }
        }
        
        List<Request> requestList = new ArrayList<>();

        try {
            if (search != null && !search.trim().isEmpty()) {
                requestList = requestDAO.searchRequests(search);
            } else if (status != null && !status.isEmpty()) {
                requestList = requestDAO.getRequestsByStatus(status);
            } else {
                requestList = requestDAO.getRequests();
            }
            
            // Apply sorting if specified
            if (sortBy != null && !sortBy.isEmpty()) {
                sortRequests(requestList, sortBy, sortOrder);
            }
            
        } catch (Exception e) {  
            e.printStackTrace();
        }
        
        // Pagination logic
        int recordsPerPage = 10;
        int totalRecords = requestList.size();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);
        
        // Set attributes for pagination
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("requestList", requestList);
        request.getRequestDispatcher("/manager/RequestManagement.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        int requestId = Integer.parseInt(request.getParameter("requestId"));

        try {
            if ("approve".equals(action)) {
                requestDAO.updateRequestStatus(requestId, "Approved");
            } else if ("reject".equals(action)) {
                requestDAO.updateRequestStatus(requestId, "Rejected");
            } else if ("assignSlot".equals(action)) {
                int slotId = Integer.parseInt(request.getParameter("slotId"));
                requestDAO.assignSlotToRequest(requestId, slotId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("/RequestManagement");
    }

    private void sortRequests(List<Request> requestList, String sortBy, String sortOrder) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
