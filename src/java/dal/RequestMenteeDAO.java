/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Request;
import model.User;

/**
 *
 * @author zzz
 */
public class RequestMenteeDAO extends DBContext {

    public List<Request> getListRequestOfMentee(int menteeId) {
        List<Request> list = new ArrayList<>();
        String sql = "select r.*, u.fullname, sk.SkillName from Request as r\n"
                + "join Mentor as mo on r.UserID = mo.UserID\n"
                + "join [User] as u on u.UserID = mo.UserID\n"
                + "join Skills as sk on sk.SkillID = r.SkillID\n"
                + "Where r.SenderID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, menteeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request r = new Request();
                r.setId(rs.getInt("RequestID"));
                r.setSendID(menteeId);
                r.setUserID(rs.getInt("UserID"));
                r.setReason(rs.getString("RequestReason"));
                r.setStatus(rs.getString("RequestStatus"));
                r.setRequestTime(rs.getTimestamp("RequestTime"));
                r.setDeadlineTime(rs.getTimestamp("DeadlineTime"));
                r.setSubject(rs.getString("RequestSubject"));
                r.setMentorName(rs.getString("fullname"));
                r.setSkillName(rs.getString("SkillName"));
                r.setRejectReason(rs.getString("rejectReason"));
                list.add(r);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Request> getListRequestProcessing(int mentorId) {
        List<Request> list = new ArrayList<>();
        String sql = "select r.*, u.fullname, sk.SkillName from Request as r\n"
                + "join [User] as u on u.UserID = r.SenderID\n"
                + "join Skills as sk on sk.SkillID = r.SkillID\n"
                + "Where r.UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mentorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request r = new Request();
                r.setId(rs.getInt("RequestID"));
                r.setSendID(rs.getInt("SenderID"));
                r.setUserID(mentorId);
                r.setReason(rs.getString("RequestReason"));
                r.setStatus(rs.getString("RequestStatus"));
                r.setRequestTime(rs.getTimestamp("RequestTime"));
                r.setDeadlineTime(rs.getTimestamp("DeadlineTime"));
                r.setSubject(rs.getString("RequestSubject"));
                r.setMenteeName(rs.getString("fullname"));
                r.setSkillName(rs.getString("SkillName"));
                r.setRejectReason(rs.getString("rejectReason"));
                list.add(r);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStatusRequest(int requestId, String status, String reasonReject) {
        String sql = "";
        if (status.equalsIgnoreCase("accept")) {
            sql = "update [Request]\n"
                    + "set [RequestStatus] = 'accept'\n"
                    + "where RequestID = ?";

            // Get request by ID
            Request r = getRequestById(requestId);
            MentorDAO mod = new MentorDAO();
            int price = mod.getPriceByMentorId(r.getUserID());  // Get the mentor's price

            MenteeDAO med = new MenteeDAO();
            UserDAO ud = new UserDAO();
            BankDAO bd = new BankDAO();

            // Get the mentee
            User mentee = ud.getUserById(r.getSendID());
            int menteeWallet = mentee.getWallet();  // Mentee wallet balance

            // Check if the mentee has enough balance
            if (menteeWallet >= price) {
                int newMenteeWallet = menteeWallet - price;  // Deduct price from mentee's wallet

                // Update the mentee's wallet
                try {
                    ud.updateMoney(mentee.getUserID(), newMenteeWallet);  // Update mentee's wallet
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                // Create a transaction for mentee
                String menteeTransCode = "MenteeTrans" + System.currentTimeMillis();  // Unique transaction code
                try {
                    bd.createNewTrans(menteeTransCode, "Payment for mentoring session", mentee.getUserID(), price, false);  // Transaction for mentee
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                // Get the mentor
                User mentor = ud.getUserById(r.getUserID());
                int mentorWallet = mentor.getWallet();  // Mentor wallet balance
                int newMentorWallet = mentorWallet + price;  // Add price to mentor's wallet

                // Update the mentor's wallet
                try {
                    ud.updateMoney(mentor.getUserID(), newMentorWallet);  // Update mentor's wallet
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }

                // Create a transaction for mentor
                String mentorTransCode = "MentorTrans" + System.currentTimeMillis();  // Unique transaction code
                try {
                    bd.createNewTrans(mentorTransCode, "Received payment for mentoring session", mentor.getUserID(), price, true);  // Transaction for mentor
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else {
                // If mentee wallet is less than the price, return false or handle error
                System.out.println("Mentee does not have enough balance to complete the transaction.");
                return false;
            }

        } else if (status.equalsIgnoreCase("reject")) {
            sql = "update [Request]\n" + "set [RequestStatus] = 'reject', rejectReason = ?\n"
                    + "where RequestID = ?";
        }
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            if (status.equalsIgnoreCase("accept")) {
                ps.setInt(1, requestId);
            } else if (status.equalsIgnoreCase("reject")) {
                ps.setString(1, reasonReject);
                ps.setInt(2, requestId);
            }
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean createRequest(int menteeId, int mentorId, String requestReason, Timestamp requestTime, Timestamp deadlineTime, int skillId) {
        int requestId = 0;
        int slotId = 0;
        String sqlCreateRequest = "INSERT INTO [Request] (SenderID, UserID, RequestReason, DeadlineTime ,RequestStatus, RequestTime, SkillID)\n"
                + "VALUES (?, ?, ?, ?,'Processing', ?, ?);";
        String sqlCreateSlot = "INSERT INTO [Slot] ([Time], startAt, SkillID, MenteeID, [Status])\n"
                + "VALUES (1.5 , ?, ?, ?, 'Processing');";
        String sqlCreateRequestSlot = "INSERT INTO [RequestSlot] ([RequestID], [SlotID])\n"
                + "VALUES (?, ?);";
        PreparedStatement smt1 = null, smt2 = null, smt3 = null;
        ResultSet rs1 = null, rs2 = null;
        try {
            // Step 1: Insert into Request table
            smt1 = connection.prepareStatement(sqlCreateRequest, Statement.RETURN_GENERATED_KEYS);
            smt1.setInt(1, menteeId);  // SenderID
            smt1.setInt(2, mentorId);  // UserID
            smt1.setString(3, requestReason);
            smt1.setTimestamp(4, deadlineTime);  // DeadlineTime
            smt1.setTimestamp(5, requestTime);   // RequestTime
            smt1.setInt(6, skillId);  // SkillID
            smt1.executeUpdate();

            rs1 = smt1.getGeneratedKeys();
            if (rs1.next()) {
                requestId = rs1.getInt(1);
            }

            // Step 2: Insert into Slot table
            smt2 = connection.prepareStatement(sqlCreateSlot, Statement.RETURN_GENERATED_KEYS);
            smt2.setTimestamp(1, requestTime);  // Assuming startAt is the same as requestTime
            smt2.setInt(2, skillId);  // SkillID
            smt2.setInt(3, menteeId);  // MenteeID
            smt2.executeUpdate();

            rs2 = smt2.getGeneratedKeys();
            if (rs2.next()) {
                slotId = rs2.getInt(1);  // Get generated SlotID
            }

            // Step 3: Insert into RequestSlot table
            smt3 = connection.prepareStatement(sqlCreateRequestSlot);
            smt3.setInt(1, requestId);  // RequestID
            smt3.setInt(2, slotId);     // SlotID
            smt3.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println(e);
        }
        return false;
    }

    public Request getRequestById(int requestId) {
        String sql = "select r.*, u.fullname, sk.SkillName from Request as r\n"
                + "join [User] as u on u.UserID = r.SenderID\n"
                + "join Skills as sk on sk.SkillID = r.SkillID\n"
                + "Where r.RequestID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, requestId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request r = new Request();
                r.setId(rs.getInt("RequestID"));
                r.setSendID(rs.getInt("SenderID"));
                r.setUserID(rs.getInt("UserID"));
                r.setReason(rs.getString("RequestReason"));
                r.setStatus(rs.getString("RequestStatus"));
                r.setRequestTime(rs.getTimestamp("RequestTime"));
                r.setDeadlineTime(rs.getTimestamp("DeadlineTime"));
                r.setSubject(rs.getString("RequestSubject"));
                r.setMenteeName(rs.getString("fullname"));
                r.setSkillName(rs.getString("SkillName"));
                r.setRejectReason(rs.getString("rejectReason"));
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RequestMenteeDAO rd = new RequestMenteeDAO();
        List<Request> l = rd.getListRequestOfMentee(7);

    }
}
