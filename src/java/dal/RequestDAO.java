/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import model.Mentor;
import model.Request;
import model.Skill;

/**
 *
 * @author ADMIN
 */
public class RequestDAO extends DBContext {

    public static int getSlots(int id) {
        int cash = 0;
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext
        try {
            PreparedStatement ps = dbo.prepareStatement("SELECT Count([SlotID]) as Count FROM [RequestSlot] WHERE [RequestID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            cash = rs.getInt("Count");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbo.close();  // Đảm bảo kết nối được đóng
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cash;
    }

    public static boolean deleteAll(int uid) throws Exception {
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        try {
            PreparedStatement ps = dbo.prepareStatement("UPDATE [Request] SET [RequestStatus] = N'Close' WHERE [SenderID] = ? AND ([RequestStatus] = N'Open' OR [RequestStatus] = N'Reopen' OR [RequestStatus] = N'Reject')");
            ps.setInt(1, uid);
            int k = ps.executeUpdate();
            dbo.commit();
            if (k > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();  // Đảm bảo kết nối được đóng
        }
        return false;
    }

    public static void rejectRequest(int rid, int uid, String reason) throws Exception {
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        try {
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [RejectRequest] WHERE [RequestID] = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = dbo.prepareStatement("UPDATE [RejectRequest] SET [Reason] = ? WHERE [RequestID] = ?");
                ps.setString(1, reason);
                ps.setInt(2, rid);
                ps.executeUpdate();
            } else {
                ps = dbo.prepareStatement("INSERT INTO [RejectRequest] ([RequestID], [Reason]) VALUES (?, ?)");
                ps.setInt(1, rid);
                ps.setString(2, reason);
                ps.executeUpdate();
            }

            ps = dbo.prepareStatement("UPDATE [Request] SET [RequestStatus] = 'Reject' WHERE [RequestID] = ?");
            ps.setInt(1, rid);
            ps.executeUpdate();
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();  // Đảm bảo kết nối được đóng
        }
    }

    public static boolean deleteRequest(int rid, int uid) throws Exception {
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        try {
            PreparedStatement ps = dbo.prepareStatement("UPDATE [Request] SET [RequestStatus] = N'Close' WHERE [RequestID] = ? AND [SenderID] = ? AND ([RequestStatus] = N'Open' OR [RequestStatus] = N'Reopen' OR [RequestStatus] = N'Reject')");
            ps.setInt(1, rid);
            ps.setInt(2, uid);
            int k = ps.executeUpdate();
            dbo.commit();
            if (k > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();  // Đảm bảo kết nối được đóng
        }
        return false;
    }

    public static void payment(int rid, int oid, int uid) throws Exception {
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        try {
            PreparedStatement ps = dbo.prepareStatement("UPDATE [User] SET [wallet] = [wallet] - ((SELECT Count([SlotID]) FROM [RequestSlot] WHERE [RequestID] = ?) * (SELECT [CashPerSlot] FROM [CV] WHERE [CvID] = (SELECT [CvID] FROM [Mentor] WHERE [UserID] = ?))) WHERE [UserID] = ?");
            ps.setInt(1, rid);
            ps.setInt(2, oid);
            ps.setInt(3, uid);
            ps.executeUpdate();

            ps = dbo.prepareStatement("INSERT INTO [Transaction] ([UserID], [balance], [Type], [Content], [Status]) VALUES (?, ((SELECT Count([SlotID]) FROM [RequestSlot] WHERE [RequestID] = ?) * (SELECT [CashPerSlot] FROM [CV] WHERE [CvID] = (SELECT [CvID] FROM [Mentor] WHERE [UserID] = ?))), N'-', N'Nộp tiền request id " + rid + "', N'Success')");
            ps.setInt(1, uid);
            ps.setInt(2, rid);
            ps.setInt(3, oid);
            ps.executeUpdate();

            ps = dbo.prepareStatement("UPDATE [Request] SET [RequestStatus] = N'Processing' WHERE [RequestID] = ?");
            ps.setInt(1, rid);
            ps.executeUpdate();

            ps = dbo.prepareStatement("UPDATE [Slot] SET [Status] = N'Not Confirm' WHERE [SlotID] in (SELECT [SlotID] FROM [RequestSlot] WHERE [RequestID] = ?)");
            ps.setInt(1, rid);
            ps.executeUpdate();

            ps = dbo.prepareStatement("INSERT INTO [Payment] ([Status], [balance], [UserID], [ReceiverID], [RequestID]) VALUES (N'Sent', ((SELECT Count([SlotID]) FROM [RequestSlot] WHERE [RequestID] = ?) * (SELECT [CashPerSlot] FROM [CV] WHERE [CvID] = (SELECT [CvID] FROM [Mentor] WHERE [UserID] = ?))), ?, ?, ?)");
            ps.setInt(1, rid);
            ps.setInt(2, oid);
            ps.setInt(3, uid);
            ps.setInt(4, oid);
            ps.setInt(5, rid);

            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();  // Đảm bảo kết nối được đóng
        }
    }

    public static int completeRequest(int rid, int oid) throws Exception {
        int cash = 0;
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        try {
            PreparedStatement ps = dbo.prepareStatement("UPDATE [Request] SET [RequestStatus] = N'Done' WHERE [RequestID] = ?");
            ps.setInt(1, rid);
            ps.executeUpdate();

            ps = dbo.prepareStatement("SELECT [price] FROM [Payment] WHERE [RequestID] = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                cash = rs.getInt("price");
            }
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();  // Đảm bảo kết nối được đóng
        }
        return cash;
    }

    public static void acceptRequest(int rid, int oid) throws Exception {
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        try {
            PreparedStatement ps = dbo.prepareStatement("DELETE FROM [RejectRequest] WHERE [RequestID] = ?");
            ps.setInt(1, rid);
            ps.executeUpdate();

            ps = dbo.prepareStatement("UPDATE [Request] SET [RequestStatus] = 'Accept' WHERE [RequestID] = ? AND [UserID] = ?");
            ps.setInt(1, rid);
            ps.setInt(2, oid);
            ps.executeUpdate();

            ps = dbo.prepareStatement("UPDATE [Slot] SET [Status] = N'Not Paid', [SkillID] = (SELECT [SkillID] FROM [Request] WHERE [RequestID] = ? AND [UserID] = ?), MenteeID = (SELECT [SenderID] FROM [Request] WHERE [RequestID] = ? AND [UserID] = ?) WHERE [SlotID] in (SELECT [SlotID] FROM [RequestSlot] WHERE [RequestID] = ?)");
            ps.setInt(1, rid);
            ps.setInt(2, oid);
            ps.setInt(3, rid);
            ps.setInt(4, oid);
            ps.setInt(5, rid);
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();  // Đảm bảo kết nối được đóng
        }
    }

    public static Request getRequest(int rid) throws Exception {
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        try {
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [Request] WHERE [RequestID] = ?");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Request r = new Request(rs.getInt("RequestID"), rs.getInt("SenderID"), rs.getInt("UserID"), rs.getString("RequestReason"), rs.getString("RequestStatus"), rs.getString("RequestSubject"), rs.getTimestamp("RequestTime"), rs.getTimestamp("DeadlineTime"));
                ps = dbo.prepareStatement("SELECT [fullname] FROM [User] WHERE [UserID] = ?");
                ps.setInt(1, r.getUserID());
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    r.setMentor(rs2.getString("fullname"));
                }

                if (r.getStatus().equalsIgnoreCase("reject")) {
                    ps = dbo.prepareStatement("SELECT * FROM [RejectRequest] WHERE [RequestID] = ?");
                    ps.setInt(1, rs.getInt("RequestID"));
                    rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        r.setRejectReason(rs2.getString("Reason"));
                    }
                }

                ps = dbo.prepareStatement("SELECT * FROM [Skills] WHERE [SkillID] in (SELECT [SkillID] FROM [Request] WHERE [RequestID] = ?)");
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    r.getSkills().add(new Skill(rs2.getInt("SkillID"), rs2.getString("SkillName"), rs2.getInt("enable") == 1, rs2.getString("image"), rs2.getString("Description")));
                }
                return r;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();  // Đảm bảo kết nối được đóng
        }
        return null;
    }

    public static ArrayList<Request> getMentorRequests(int uid) throws Exception {
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext
        ArrayList<Request> arr = new ArrayList<>();

        try {
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [Request] WHERE [UserID] = ? AND "
                    + "((( [RequestStatus] = N'Open' OR [RequestStatus] = N'Reopen' OR [RequestStatus] = N'Reject' OR [RequestStatus] = N'Close') "
                    + "AND [DeadlineTime] > GETDATE()) OR ([RequestStatus] = N'Accept' OR [RequestStatus] = N'Processing' OR [RequestStatus] = N'Done'))");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Request r = new Request(
                        rs.getInt("RequestID"),
                        rs.getInt("SenderID"),
                        rs.getInt("UserID"),
                        rs.getString("RequestReason"),
                        rs.getString("RequestStatus"),
                        rs.getString("RequestSubject"),
                        rs.getTimestamp("RequestTime"),
                        rs.getTimestamp("DeadlineTime")
                );

                ps = dbo.prepareStatement("SELECT [fullname] FROM [User] WHERE [UserID] = ?");
                ps.setInt(1, r.getSendID());
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    r.setSend(rs2.getString("fullname"));
                }

                if (r.getStatus().equalsIgnoreCase("reject")) {
                    ps = dbo.prepareStatement("SELECT [Reason] FROM [RejectRequest] WHERE [RequestID] = ?");
                    ps.setInt(1, rs.getInt("RequestID"));
                    rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        r.setRejectReason(rs2.getString("Reason"));
                    }
                }

                ps = dbo.prepareStatement("SELECT * FROM [Skills] WHERE [SkillID] IN (SELECT [SkillID] FROM [Request] WHERE [RequestID] = ?)");
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    r.getSkills().add(new Skill(
                            rs2.getInt("SkillID"),
                            rs2.getString("SkillName"),
                            rs2.getInt("enable") == 1,
                            rs2.getString("image"),
                            rs2.getString("Description")
                    ));
                }

                ps = dbo.prepareStatement("SELECT COUNT(SlotID) AS TotalSlots FROM [RequestSlot] WHERE [RequestID] = ?");
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                if (rs2.next()) {
                    r.setTotalRequest(rs2.getInt("TotalSlots"));
                }

                ps = dbo.prepareStatement("SELECT s.startAt FROM [Slot] s INNER JOIN [RequestSlot] rs ON s.SlotID = rs.SlotID WHERE rs.RequestID = ?");
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                Timestamp startDate = null;
                Timestamp endDate = null;

                while (rs2.next()) {
                    Timestamp startAt = rs2.getTimestamp("startAt");
                    if (startDate == null || startAt.before(startDate)) {
                        startDate = startAt;
                    }
                    if (endDate == null || startAt.after(endDate)) {
                        endDate = startAt;
                    }
                }

                if (startDate != null) {
                    r.setStartdate(startDate);
                }
                if (endDate != null) {
                    r.setEnddate(endDate);
                }

                arr.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            dbo.close();
        }

        return arr;
    }

    public static ArrayList<Request> getMenteeRequests(int uid) throws Exception {
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext
        ArrayList<Request> arr = new ArrayList<>();
        try {
            PreparedStatement ps = dbo.prepareStatement(
                    "SELECT * FROM [Request] WHERE [SenderID] = ? AND "
                    + "((( [RequestStatus] = N'Open' OR [RequestStatus] = N'Reopen' OR [RequestStatus] = N'Reject' OR [RequestStatus] = N'Close') "
                    + "AND [DeadlineTime] > GETDATE()) OR ([RequestStatus] = N'Accept' OR [RequestStatus] = N'Processing' OR [RequestStatus] = N'Done'))"
            );
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String rejectReason = rs.getString("rejectReason");
                Request r = new Request(
                        rs.getInt("RequestID"),
                        rs.getInt("SenderID"),
                        rs.getInt("UserID"),
                        rs.getString("RequestReason"),
                        rs.getString("RequestStatus"),
                        rs.getString("RequestSubject"),
                        rs.getTimestamp("RequestTime"),
                        rs.getTimestamp("DeadlineTime")
                );

                ps = dbo.prepareStatement("SELECT [fullname] FROM [User] WHERE [UserID] = ?");
                ps.setInt(1, r.getUserID());
                ResultSet rs2 = ps.executeQuery();
                if (rs2.next()) {
                    r.setMentor(rs2.getString("fullname"));
                }

                r.setRejectReason(rejectReason);

                if (r.getStatus().equalsIgnoreCase("reject")) {
                    ps = dbo.prepareStatement("SELECT [Reason] FROM [RejectRequest] WHERE [RequestID] = ?");
                    ps.setInt(1, rs.getInt("RequestID"));
                    rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        r.setRejectReason(rs2.getString("Reason"));
                    }
                } else if (r.getStatus().equalsIgnoreCase("done")) {
                    ps = dbo.prepareStatement("SELECT * FROM [Rating] WHERE [RequestID] = ?");
                    ps.setInt(1, rs.getInt("RequestID"));
                    rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        r.setRated(true);
                    }
                }

                ps = dbo.prepareStatement(
                        "SELECT * FROM [Skills] WHERE [SkillID] IN (SELECT [SkillID] FROM [Request] WHERE [RequestID] = ?)"
                );
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    r.getSkills().add(new Skill(
                            rs2.getInt("SkillID"),
                            rs2.getString("SkillName"),
                            rs2.getInt("enable") == 1,
                            rs2.getString("image"),
                            rs2.getString("Description")
                    ));
                }

                ps = dbo.prepareStatement("SELECT COUNT(SlotID) AS TotalSlots FROM [RequestSlot] WHERE [RequestID] = ?");
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                if (rs2.next()) {
                    r.setTotalRequest(rs2.getInt("TotalSlots"));
                }

                ps = dbo.prepareStatement(
                        "SELECT s.startAt FROM [Slot] s INNER JOIN [RequestSlot] rs ON s.SlotID = rs.SlotID WHERE rs.RequestID = ?"
                );
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                Timestamp startDate = null;
                Timestamp endDate = null;

                while (rs2.next()) {
                    Timestamp startAt = rs2.getTimestamp("startAt");
                    if (startDate == null || startAt.before(startDate)) {
                        startDate = startAt;
                    }
                    if (endDate == null || startAt.after(endDate)) {
                        endDate = startAt;
                    }
                }

                if (startDate != null) {
                    r.setStartdate(startDate);
                }
                if (endDate != null) {
                    r.setEnddate(endDate);
                }

                arr.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            dbo.close();
        }

        return arr;
    }

    public static ArrayList<Request> getRequests() throws Exception {
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext
        ArrayList<Request> arr = new ArrayList<>();

        try {
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [Request]");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Request r = new Request(rs.getInt("RequestID"), rs.getInt("SenderID"), rs.getInt("UserID"), rs.getString("RequestReason"), rs.getString("RequestStatus"), rs.getString("RequestSubject"), rs.getTimestamp("RequestTime"), rs.getTimestamp("DeadlineTime"));

                ps = dbo.prepareStatement("SELECT [fullname] FROM [User] WHERE [UserID] = ?");
                ps.setInt(1, r.getUserID());
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                r.setMentor(rs2.getString("fullname"));

                ps = dbo.prepareStatement("SELECT [username] FROM [User] WHERE [UserID] = ?");
                ps.setInt(1, r.getSendID());
                rs2 = ps.executeQuery();
                rs2.next();
                r.setSend(rs2.getString("username"));

                if (r.getStatus().equalsIgnoreCase("reject")) {
                    ps = dbo.prepareStatement("SELECT * FROM [RejectRequest] WHERE [RequestID] = ?");
                    ps.setInt(1, rs.getInt("RequestID"));
                    rs2 = ps.executeQuery();
                    if (rs2.next()) {
                        r.setRejectReason(rs2.getString("Reason"));
                    }
                }

                ps = dbo.prepareStatement("SELECT * FROM [Skills] WHERE [SkillID] in (SELECT [SkillID] FROM [Request] WHERE [RequestID] = ?)");
                ps.setInt(1, rs.getInt("RequestID"));
                rs2 = ps.executeQuery();
                while (rs2.next()) {
                    r.getSkills().add(new Skill(rs2.getInt("SkillID"), rs2.getString("SkillName"), rs2.getInt("enable") == 1, rs2.getString("image"), rs2.getString("Description")));
                }
                arr.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();
        }
        return arr;
    }

    public static boolean updateRequest(String[] skills, Timestamp deadline, String subject, String reason, int sid, String status, int rid) throws Exception {
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext

        try {
            PreparedStatement ps = dbo.prepareStatement("UPDATE [Request] SET [SenderID] = ?, [RequestSubject] = ?, [RequestReason] = ?, [DeadlineTime] = ?, [RequestStatus] = ?, [SkillID] = ?, rejectReason=? WHERE [RequestID] = ?");
            ps.setInt(1, sid);
            ps.setString(2, subject);
            ps.setString(3, reason);
            ps.setTimestamp(4, deadline);
            ps.setString(5, status);
            ps.setInt(6, Integer.parseInt(skills[0]));
            ps.setString(7, null);
            ps.setInt(8, rid);

            int k = ps.executeUpdate();
            dbo.commit();
            if (k > 0) {
                dbo.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();
        }
        return false;
    }

    public double getMustPayAmountHold(int sendId) {
        double mustPay = 0;
        String sql = "select sum(C.CashPerSlot) as mustPay from Request as R\n"
                + "join RequestSlot as RS on RS.RequestID = R.RequestID\n"
                + "join Mentor as M \n"
                + "on M.UserID = R.UserID\n"
                + "join CV as C on C.CvID = M.CvID\n"
                + "where R.SenderID = ? and R.RequestStatus != 'Close' and R.RequestStatus != 'Done' AND r.RequestStatus != 'Reject' AND r.RequestStatus != 'Processing'\n"
                + "group by R.SenderID";
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext

        try {
            PreparedStatement preparedStatement = dbo.prepareStatement(sql);
            preparedStatement.setInt(1, sendId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mustPay = resultSet.getDouble("mustPay");
            }
        } catch (Exception e) {
            System.out.println("Get total must pay: " + e);
        } finally {
            try {
                dbo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mustPay;
    }

    public static double getMustPayAmount(int sendId) {
        double mustPay = 0;
        String sql = "select sum(C.CashPerSlot) as mustPay from Request as R\n"
                + "join RequestSlot as RS on RS.RequestID = R.RequestID\n"
                + "join Mentor as M \n"
                + "on M.UserID = R.UserID\n"
                + "join CV as C on C.CvID = M.CvID\n"
                + "where R.SenderID = ? and R.RequestStatus != 'Close' and R.RequestStatus != 'Done' AND r.RequestStatus != 'Reject' AND r.RequestStatus != 'Processing'\n"
                + "group by R.SenderID";
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext

        try {
            PreparedStatement preparedStatement = dbo.prepareStatement(sql);
            preparedStatement.setInt(1, sendId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                mustPay = resultSet.getDouble("mustPay");
            }
        } catch (Exception e) {
            System.out.println("Get total must pay: " + e);
        } finally {
            try {
                dbo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mustPay;
    }

    public static boolean createRequest(String[] skills, Timestamp deadline, String subject, String reason, int sid, int uid, String[] slots) throws Exception {
        DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
        Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext

        try {
            PreparedStatement ps = dbo.prepareStatement("INSERT INTO [Request] ([SenderID], [UserID], [RequestSubject], [RequestReason], [DeadlineTime], [RequestStatus], [SkillID]) VALUES (?, ?, ?, ?, ?, 'Open', ?)");
            ps.setInt(1, sid);
            ps.setInt(2, uid);
            ps.setString(3, subject);
            ps.setString(4, reason);
            ps.setTimestamp(5, deadline);
            ps.setInt(6, Integer.parseInt(skills[0]));

            int k = ps.executeUpdate();
            for (int i = 0; i < slots.length; i++) {
                ps = dbo.prepareStatement("INSERT INTO [RequestSlot] ([RequestID], [SlotID]) VALUES ((SELECT Top (1) [RequestID] FROM [Request] ORDER BY [RequestID] DESC), ?)");
                ps.setInt(1, Integer.parseInt(slots[i]));
                ps.executeUpdate();
            }

            dbo.commit();
            if (k > 0) {
                dbo.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbo.close();
        }
        return false;
    }

    public boolean sendRequest(int menteeId, int mentorId, String subject, String reason) {
        String sql = "INSERT INTO Request (SenderID, UserID, RequestSubject, RequestReason, RequestStatus, RequestTime, DeadlineTime) "
                + "VALUES (?, ?, ?, ?, 'Pending', GETDATE(), DATEADD(DAY, 7, GETDATE()))";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, menteeId);
            ps.setInt(2, mentorId);
            ps.setString(3, subject);
            ps.setString(4, reason);
            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Request> getRequestsByMenteeId(int menteeId) {
        List<Request> requests = new ArrayList<>();
        String sql = "SELECT r.RequestID, r.RequestSubject, r.RequestReason, r.RequestStatus, "
                + "r.RequestTime, r.DeadlineTime, r.rejectReason, "
                + "u.fullname AS MentorName, "
                + "(SELECT STRING_AGG(s.SkillName, ', ') FROM RequestSlot rs "
                + "JOIN Slot sl ON rs.SlotID = sl.SlotID "
                + "JOIN Skills s ON sl.SkillID = s.SkillID WHERE rs.RequestID = r.RequestID) AS Skills "
                + "FROM Request r "
                + "JOIN [User] u ON r.UserID = u.UserID "
                + "WHERE r.SenderID = ?";

        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, menteeId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Request req = new Request(
                        rs.getInt("RequestID"),
                        menteeId,
                        rs.getInt("RequestID"), // Lưu userID của Mentor
                        rs.getString("RequestReason"),
                        rs.getString("RequestStatus"),
                        rs.getString("RequestSubject"),
                        rs.getTimestamp("RequestTime"),
                        rs.getTimestamp("DeadlineTime")
                );
                req.setMentor(rs.getString("MentorName"));
                req.setRejectReason(rs.getString("rejectReason"));

                // Lấy danh sách kỹ năng của yêu cầu
                String skillNames = rs.getString("Skills");
                if (skillNames != null) {
                    String[] skillArray = skillNames.split(", ");
                    ArrayList<Skill> skillList = new ArrayList<>();
                    for (String skillName : skillArray) {
                        Skill skill = new Skill();
                        skill.setName(skillName);
                        skillList.add(skill);
                    }
                    req.skills = skillList;
                }

                requests.add(req);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public void updateRequestStatus(int requestId, String status, String rejectReason) {
        String sql = "UPDATE Request SET RequestStatus = ?, rejectReason = ? WHERE RequestID = ?";
        try (
                PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, rejectReason);
            ps.setInt(3, requestId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



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
                r.setSendID(rs.getInt("MenteeId"));
                r.setUserID(mentorId);
                r.setReason(rs.getString("RequestReason"));
                r.setStatus(rs.getString("RequestStatus"));
                r.setRequestTime(rs.getTimestamp("RequestTime"));
                r.setDeadlineTime(rs.getTimestamp("DeadlineTime"));
                r.setSubject(rs.getString("RequestSubject"));
                r.setMenteeName(rs.getString("fullname"));
                r.setSkillName(rs.getString("SkillName"));
                list.add(r);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        RequestDAO rd = new RequestDAO();
        System.out.println(rd.getListRequestOfMentee(4).size());
    }
public boolean isRequestAccepted(int menteeId, int mentorId) {
        String sql = "SELECT COUNT(*) FROM Request WHERE SenderID = ? AND UserID = ? AND RequestStatus = 'Accepted'";
        try (
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, menteeId);
            ps.setInt(2, mentorId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
public int countPendingRequests() {
        int count = 0;
        DBContext dbContext = new DBContext();
        Connection dbo = dbContext.connection;
        
        try {
            String sql = "SELECT COUNT(*) FROM [Request] WHERE [RequestStatus] = 'Open' OR [RequestStatus] = 'Reopen'";
            PreparedStatement ps = dbo.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dbo.close();  // Đảm bảo kết nối được đóng
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        return count;
    }
public static boolean updateRequestStatus(int requestId, String status) throws Exception {
    DBContext dbContext = new DBContext();
    Connection dbo = dbContext.connection;
    try {
        String sql = "UPDATE [Request] SET [RequestStatus] = ? WHERE [RequestID] = ?";
        PreparedStatement ps = dbo.prepareStatement(sql);
        ps.setString(1, status);
        ps.setInt(2, requestId);
        int updated = ps.executeUpdate();
        dbo.commit();
        return updated > 0;
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        dbo.close();
    }
    return false;
}
public static ArrayList<Request> searchRequests(String keyword) throws Exception {
    ArrayList<Request> requestList = new ArrayList<>();
    DBContext dbContext = new DBContext();
    Connection dbo = dbContext.connection;

    try {
        String sql = "SELECT * FROM [Request] WHERE [RequestSubject] LIKE ? OR [RequestReason] LIKE ?";
        PreparedStatement ps = dbo.prepareStatement(sql);
        ps.setString(1, "%" + keyword + "%");
        ps.setString(2, "%" + keyword + "%");
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Request r = new Request(
                rs.getInt("RequestID"),
                rs.getInt("SenderID"),
                rs.getInt("UserID"),
                rs.getString("RequestReason"),
                rs.getString("RequestStatus"),
                rs.getString("RequestSubject"),
                rs.getTimestamp("RequestTime"),
                rs.getTimestamp("DeadlineTime")
            );
            requestList.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        dbo.close();
    }

    return requestList;
}
public static ArrayList<Request> getRequestsByStatus(String status) throws Exception {
    ArrayList<Request> requestList = new ArrayList<>();
    DBContext dbContext = new DBContext();
    Connection dbo = dbContext.connection;

    try {
        String sql = "SELECT * FROM [Request] WHERE [RequestStatus] = ?";
        PreparedStatement ps = dbo.prepareStatement(sql);
        ps.setString(1, status);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Request r = new Request(
                rs.getInt("RequestID"),
                rs.getInt("SenderID"),
                rs.getInt("UserID"),
                rs.getString("RequestReason"),
                rs.getString("RequestStatus"),
                rs.getString("RequestSubject"),
                rs.getTimestamp("RequestTime"),
                rs.getTimestamp("DeadlineTime")
            );
            requestList.add(r);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        dbo.close();
    }

    return requestList;
}
public static boolean assignSlotToRequest(int requestId, int slotId) throws Exception {
    DBContext dbContext = new DBContext();
    Connection dbo = dbContext.connection;
    try {
        String sql = "INSERT INTO [RequestSlot] ([RequestID], [SlotID]) VALUES (?, ?)";
        PreparedStatement ps = dbo.prepareStatement(sql);
        ps.setInt(1, requestId);
        ps.setInt(2, slotId);
        int updated = ps.executeUpdate();
        dbo.commit();
        return updated > 0;
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        dbo.close();
    }
    return false;
}
}
