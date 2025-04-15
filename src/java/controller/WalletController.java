/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dal.BankDAO;
import dal.TransactionDAO;
import Service.BankingService;
import dal.BankDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import model.Bank;
import model.User;

@WebServlet(name = "WalletController", urlPatterns = {"/wallet"})
public class WalletController extends HttpServlet {
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (request.getParameter("callback") != null) {
            if (request.getParameter("callback").equals("1")) {
                request.setAttribute("alert", "Nạp tiền thành công!");
            } else {
                request.setAttribute("alert", "Có lỗi xảy ra khi nạp tiền!");
            }
        }
        User u = (User) request.getSession().getAttribute("u");
        try {
            request.setAttribute("Bank", BankDAO.getBank(u.getUserID()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("Wallet.jsp").forward(request, response);
    }

@Override
protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    User u = (User) request.getSession().getAttribute("u");

    if (u == null) {
        response.sendRedirect("Login.jsp");
        return;
    }
    // Kiểm tra nếu người dùng muốn rút tiền
    String type = request.getParameter("type");

    if ("withdraw".equalsIgnoreCase(type)) {
        try {
            Bank userBank = BankDAO.getBank(u.getUserID());
            request.setAttribute("Bank", userBank);
        } catch (Exception e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("Withdraw.jsp").forward(request, response);
        return;  // Dừng lại sau khi điều hướng sang trang rút tiền
    }

    // Nếu không có type=withdraw, hiển thị trang Wallet bình thường
    try {
        request.setAttribute("Bank", BankDAO.getBank(u.getUserID()));
    } catch (Exception e) {
        e.printStackTrace();
    }
    request.getRequestDispatcher("Wallet.jsp").forward(request, response);
}




    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        User u = (User) request.getSession().getAttribute("u");
        if (u != null) {
            try {
                Bank userBank = BankDAO.getBank(u.getUserID());
                request.setAttribute("Bank", userBank);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
         String type = request.getParameter("type");

    if ("withdraw".equalsIgnoreCase(type)) {
        try {
            int amount = Integer.parseInt(request.getParameter("amount"));

            if (amount > u.getWallet()) {
                request.getSession().setAttribute("alert", "Số tiền rút vượt quá số dư!");
            } else {
                TransactionDAO.withdraw(u.getUserID(), amount);
                u.setWallet(u.getWallet() - amount);
                request.getSession().setAttribute("u", u);
                request.getSession().setAttribute("alert", "Gửi yêu cầu rút tiền thành công!");
            }
        } catch (Exception e) {
            request.getSession().setAttribute("alert", "Lỗi trong quá trình rút tiền!");
            e.printStackTrace();
        }
        response.sendRedirect("wallet");
    }    else {
            try {
                if (request.getParameter("type") != null) {
                    String sCash = request.getParameter("amount");
                   //Loại bỏ dấu phẩy, khoảng trắng, và dấu chấm để đảm bảo chuỗi chỉ chứa số.
                    sCash = sCash.replaceAll(",", "").replaceAll(" ", "").replaceAll("\\.", "");
                    float Cash = Float.parseFloat(sCash);
                    String vnp_Version = "2.1.0";
                    String vnp_Command = "pay";
                    String orderType = "other";
                    //Chuyển số tiền sang đơn vị nhỏ nhất (VNPAY yêu cầu nhân 100 để bỏ phần thập phân).
                    long amount = (int) (Cash * 100);
                    String bankCode = request.getParameter("bankCode");
                    //Mã giao dịch ngẫu nhiên (8 ký tự) được tạo bởi BankingService.getRandomNumber(8)
                    String vnp_TxnRef = BankingService.getRandomNumber(8);
                    while (BankDAO.checkingTransCode(vnp_TxnRef)) {
                        vnp_TxnRef = BankingService.getRandomNumber(8);
                    }
                    String vnp_IpAddr = BankingService.getIpAddress(request);

                    String vnp_TmnCode = BankingService.vnp_TmnCode;

                    Map<String, String> vnp_Params = new HashMap<>();
                    vnp_Params.put("vnp_Version", vnp_Version);
                    vnp_Params.put("vnp_Command", vnp_Command);
                    vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
                    vnp_Params.put("vnp_Amount", String.valueOf(amount));
                    vnp_Params.put("vnp_CurrCode", "VND");

                    if (bankCode != null && !bankCode.isEmpty()) {
                        vnp_Params.put("vnp_BankCode", bankCode);
                    }
                    vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
                    vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
                    vnp_Params.put("vnp_OrderType", orderType);

                    String locate = "vn";
                    if (locate != null && !locate.isEmpty()) {
                        vnp_Params.put("vnp_Locale", locate);
                    } else {
                        vnp_Params.put("vnp_Locale", "vn");
                    }
                    vnp_Params.put("vnp_ReturnUrl", BankingService.vnp_ReturnUrl);
                    vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

                    Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
                    String vnp_CreateDate = formatter.format(cld.getTime());
                    vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

                    cld.add(Calendar.MINUTE, 15);
                    String vnp_ExpireDate = formatter.format(cld.getTime());
                    vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

                    List fieldNames = new ArrayList(vnp_Params.keySet());
                    Collections.sort(fieldNames);
                    StringBuilder hashData = new StringBuilder();
                    StringBuilder query = new StringBuilder();
                    Iterator itr = fieldNames.iterator();
                    while (itr.hasNext()) {
                        String fieldName = (String) itr.next();
                        String fieldValue = (String) vnp_Params.get(fieldName);
                        if ((fieldValue != null) && (fieldValue.length() > 0)) {
                            //Build hash data
                            hashData.append(fieldName);
                            hashData.append('=');
                            hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                            //Build query
                            query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                            query.append('=');
                            query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                            if (itr.hasNext()) {
                                query.append('&');
                                hashData.append('&');
                            }
                        }
                    }
                    String queryUrl = query.toString();
                    String vnp_SecureHash = BankingService.hmacSHA512(BankingService.vnp_HashSecret, hashData.toString());
                    queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
                    String paymentUrl = BankingService.vnp_Url + "?" + queryUrl;
                    BankDAO.createNewTrans(vnp_TxnRef, "Thanh toan don hang:" + vnp_TxnRef, u.getUserID(), (int) Cash, true);
                    response.sendRedirect(paymentUrl);
                    return;
                } else {
                    processRequest(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
