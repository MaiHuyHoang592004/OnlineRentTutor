/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package API;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import dal.ScheduleDAO;


import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.Slot;

/**
 *
 * @author TGDD
 */
public class ApiScheduleController extends HttpServlet {

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
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter r = response.getWriter();
        String sid = request.getParameter("id");
        ScheduleDAO sd = new ScheduleDAO();
        if (sid == null) {
            sid = request.getParameter("sid");
            if(sid == null) {
                response.setContentType("text/plain;charset=UTF-8");
                r.println("You need to provide id");
                response.setStatus(502);
            } else {
                try {
                    response.setStatus(200);
                    int id = Integer.parseInt(sid);
                    Slot s = sd.getSlotById(id);
                    Gson gson = new Gson();
                    String json = gson.toJson(s);
                    r.println(json);
                } catch(Exception e) {
                    response.setContentType("text/plain;charset=UTF-8");
                    r.println(e.getMessage());
                    response.setStatus(502);
                }
            }
        } else {
            try {
                if (request.getParameter("free") != null) {
                    int id = Integer.parseInt(sid);
                    String smid = request.getParameter("mid");
                    int mid = Integer.parseInt(smid);
                    ArrayList<Slot> slots = sd.getFreeSlots(new java.util.Date(), mid);
                    ArrayList<Integer> requestSlot = sd.getSlotsIDByRequest(id);
                    String json = "[";
                    for (int i = 0; i < slots.size(); i++) {
                        String tempJ = "{";
                        tempJ += "\"id\":" + slots.get(i).getId();
                        tempJ += ",\"SlotTime\":\"" + slots.get(i).getSlotTime().toString() + "\"";
                        tempJ += ",\"hour\":" + slots.get(i).getHour();
                        tempJ += ",\"link\":\"" + slots.get(i).getLink() + "\"";
                        tempJ += ",\"mentor\":\"" + slots.get(i).getMentor() + "\"";
                        tempJ += ",\"Status\":\"" + slots.get(i).getStatus() + "\"";
                        tempJ += ",\"menteeId\":" + slots.get(i).getMenteeId();
                        tempJ += ",\"mentorId\":" + slots.get(i).getMentorId();
                        tempJ += ",\"mentee\":\"" + slots.get(i).getMentee() + "\"";
                        if(requestSlot.contains(slots.get(i).getId())) {
                            tempJ += ",\"checked\": true";
                        } else {
                            tempJ += ",\"checked\": false";
                        }
                        tempJ += "}";
                        json += tempJ + ", ";
                    }
                    json = json.substring(0, json.length() - 2);
                    json += "]";
                    int weekCount = ScheduleDAO.weekCount(slots);
                    json = "{\"slots\":" + json + ",\"weekCount\":" + weekCount + "}";
                    r.println(json);
                } else {
                    int id = Integer.parseInt(sid);
                    ArrayList<Slot> slots = sd.getSlotsByRequest(id);
                    String json = "[";
                    for (int i = 0; i < slots.size(); i++) {
                        String tempJ = "{";
                        tempJ += "\"id\":" + slots.get(i).getId();
                        tempJ += ",\"SlotTime\":\"" + slots.get(i).getSlotTime().toString() + "\"";
                        tempJ += ",\"hour\":" + slots.get(i).getHour();
                        tempJ += ",\"link\":\"" + slots.get(i).getLink() + "\"";
                        tempJ += ",\"mentor\":\"" + slots.get(i).getMentor() + "\"";
                        tempJ += ",\"Status\":\"" + slots.get(i).getStatus() + "\"";
                        tempJ += ",\"menteeId\":" + slots.get(i).getMenteeId();
                        tempJ += ",\"mentorId\":" + slots.get(i).getMentorId();
                        tempJ += ",\"mentee\":\"" + slots.get(i).getMentee() + "\"";
                        tempJ += "}";
                        json += tempJ + ", ";
                    }
                    json = json.substring(0, json.length() - 2);
                    json += "]";
                    int weekCount = ScheduleDAO.weekCount(slots);
                    json = "{\"slots\":" + json + ",\"weekCount\":" + weekCount + "}";
                    r.println(json);
                }
            } catch (Exception e) {
                response.setContentType("text/plain;charset=UTF-8");
                r.println(e.getMessage());
                response.setStatus(502);
            }
        }
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
     * @param request servlet request
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
