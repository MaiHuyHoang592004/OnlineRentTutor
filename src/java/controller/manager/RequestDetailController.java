package controller.manager;

import dal.RequestDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.Request;

public class RequestDetailController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private RequestDAO requestDAO;

    @Override
    public void init() throws ServletException {
        requestDAO = new RequestDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String requestIdParam = request.getParameter("requestId");
        
        if (requestIdParam == null || requestIdParam.isEmpty()) {
            response.sendRedirect("RequestManagement");
            return;
        }
        
        try {
            int requestId = Integer.parseInt(requestIdParam);
            Request requestObj = RequestDAO.getRequest(requestId);
            
            if (requestObj == null) {
                response.sendRedirect("RequestManagement");
                return;
            }
            
            request.setAttribute("request", requestObj);
            request.getRequestDispatcher("/manager/RequestDetail.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            response.sendRedirect("RequestManagement");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("RequestManagement");
        }
    }
}