/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package API;

import dal.BankDAO;
import dal.UserDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;


@WebServlet(name = "CallbackController", urlPatterns = {"/callback"})
public class CallbackController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String sAmount = request.getParameter("vnp_Amount");
        String vpn_TxnRef = request.getParameter("vnp_TxnRef");
        String status = request.getParameter("vnp_ResponseCode");
        String vnp_TransactionStatus = request.getParameter("vnp_TransactionStatus");
        int returnVal = 0;

        // Kiểm tra giao dịch có thành công hay không
        if ("00".equals(status) && "00".equals(vnp_TransactionStatus)) {
            returnVal = 1;
            try {
                BankDAO bd = new BankDAO();
                // Cập nhật trạng thái giao dịch thành công trong DB
                int uid = bd.updateTrans(vpn_TxnRef, "Success");
                if (uid != -1) {
                    // Lấy thông tin user từ session
                    User u = (User) request.getSession().getAttribute("u");
                    if (u != null && u.getUserID() == uid) {
                        // Cập nhật ví của user
                        int newBalance = u.getWallet() + (Integer.parseInt(sAmount) / 100);
                        UserDAO.updateMoney(uid, newBalance);
                        u.setWallet(newBalance); // Cập nhật thông tin trong session
                    } else {
                        // Cập nhật tiền nếu user không có trong session
                        int newBalance = UserDAO.getWallet(uid) + (Integer.parseInt(sAmount) / 100);
                        UserDAO.updateMoney(uid, newBalance);
                    }
                } else {
                    returnVal = 0; // Không tìm thấy người dùng, đánh dấu thất bại
                }
            } catch (Exception e) {
                // Xử lý lỗi cập nhật DB và đánh dấu giao dịch thất bại
                try {
                    BankDAO.updateTrans(vpn_TxnRef, "Fail");
                } catch (Exception ex) {
                    e.printStackTrace(); // Ghi log lỗi
                }
            }
        } else {
            // Xử lý giao dịch thất bại hoặc bị hủy
            try {
                BankDAO.updateTrans(vpn_TxnRef, "Fail");
                if ("24".equals(status)) {
                    // Trường hợp người dùng hủy giao dịch
                    System.out.println("Giao dịch " + vpn_TxnRef + " đã bị hủy.");
                } else {
                    // Các trường hợp khác của giao dịch thất bại
                    System.out.println("Giao dịch " + vpn_TxnRef + " thất bại với mã phản hồi: " + status);
                }
            } catch (Exception e) {
                e.printStackTrace(); // Ghi log lỗi khi cập nhật DB thất bại
            }
            returnVal = 0; // Đánh dấu giao dịch không thành công
        }

        // Điều hướng về trang ví với tham số callback để thông báo kết quả
        response.sendRedirect("wallet?callback=" + returnVal);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet requests
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
