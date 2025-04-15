/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import model.Mentor;
import model.MentorDetail;
import model.MentorStatistic;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Rate;
import model.User;

/**
 *
 * @author zzz
 */
public class MentorDAO extends DBContext {

    public boolean acceptedCv(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Mentor] WHERE [MentorStatus] = N'Accepted' AND [UserID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                connection.close();
                return true;
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void insertMentor(int userId) throws SQLException {
        String sql = "INSERT INTO [Mentor] ([UserID], [MentorStatus]) VALUES (?, 'Draft')";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public HashMap<Mentor, MentorDetail> getAllWithDetail() throws SQLException {
        HashMap<Mentor, MentorDetail> arr = new HashMap<>();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Mentor]");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement("SELECT AVG([noStar]) AS Rate FROM [Rating] WHERE [MentorID] = ?");
                ps2.setInt(1, rs.getInt("UserID"));
                ResultSet rs2 = ps2.executeQuery();
                int rate = 0;
                if (rs2.next()) {
                    rate = rs2.getInt("Rate");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT Count([RequestID]) as [accept]  FROM [Request] WHERE [UserID] = ? AND ([RequestStatus] = 'Confirmed' OR [RequestStatus] = 'Done' OR [RequestStatus] = 'Processing')");
                ps2.setInt(1, rs.getInt("UserID"));
                rs2 = ps2.executeQuery();
                int accept = 0;
                if (rs2.next()) {
                    accept = rs2.getInt("accept");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT Count([RequestID]) as [done]  FROM [Request] WHERE [UserID] = ? AND ([RequestStatus] = 'Confirmed' OR [RequestStatus] = 'Done')");
                ps2.setInt(1, rs.getInt("UserID"));
                rs2 = ps2.executeQuery();
                int done = 0;
                if (rs2.next()) {
                    done = rs2.getInt("done");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] = ?");
                ps2.setInt(1, rs.getInt("UserID"));
                rs2 = ps2.executeQuery();
                boolean active = false;
                String account = "";
                String fullname = "";
                String avatar = "";
                if (rs2.next()) {
                    account = rs2.getString("email");
                    active = rs2.getInt("activeStatus") == 1;
                    fullname = rs2.getString("fullname");
                    avatar = rs2.getString("Avatar");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT [ProfessionIntroduction]FROM [CV] WHERE [CvID] = ?");
                ps2.setInt(1, rs.getInt("CvID"));
                rs2 = ps2.executeQuery();
                String profession = "";
                if (rs2.next()) {
                    profession = rs2.getString("ProfessionIntroduction");
                }
                arr.put(new Mentor(rs.getString("MentorStatus"), rs.getString("Achivement"), rs.getString("Description"), rs.getInt("UserID"), rs.getInt("CvID"), fullname, avatar), new MentorDetail(rs.getInt("UserID"), rate, accept, (accept > 0 ? (int) ((float) done / ((float) accept / 100)) : 0), account, profession, active));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

    public ArrayList<Mentor> searchMentor(String name, String city, String skill, String gender, String ready) throws Exception {
        ArrayList<Mentor> arr = new ArrayList();
        String sql = "SELECT s.[UserID] ,m.[Description] ,m.[CvID] ,m.[Achivement] ,m.[MentorStatus], s.[Avatar], s.[fullname] FROM Mentor m INNER JOIN [User] s ON s.[UserID] = m.[UserID] WHERE ";
        int filter = 0;
        if (name != null) {
            sql += "(s.[username] LIKE N'%" + name + "%' OR s.[fullname] LIKE N'%" + name + "%')";
            filter++;
        }
        if (city != null) {
            if (filter > 0) {
                sql += " AND ";
            }
            sql += "(s.[address] LIKE N'%" + city + "%')";
            filter++;
        }
        if (gender != null) {
            if (filter > 0) {
                sql += " AND ";
            }
            sql += "(s.[sex] = " + (gender.equalsIgnoreCase("female") ? 1 : 0) + ")";
            filter++;
        }
        if (ready != null) {
            if (filter > 0) {
                sql += " AND ";
            }
            sql += "(m.[MentorStatus] " + (ready.equalsIgnoreCase("true") ? "= N'Accepted'" : "!= N'Accepted'") + ")";
            filter++;
        }
        if (skill != null) {
            if (filter > 0) {
                sql += " AND ";
            }
            sql += skill + " IN ( SELECT [SkillID]  FROM  [MentorSkills] WHERE [MentorID] = m.[UserID])";
        }
        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            arr.add(new Mentor(rs.getString("MentorStatus"), rs.getString("Achivement"), rs.getString("Description"), rs.getInt("UserID"), rs.getInt("CvID"), rs.getString("fullname"), rs.getString("Avatar")));
        }
        connection.close();
        return arr;
    }

    public HashMap<Mentor, MentorDetail> getAllBySkillId(int id) throws Exception {
        HashMap<Mentor, MentorDetail> arr = new HashMap();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Mentor] WHERE [UserID] in (SELECT [MentorID] FROM [MentorSkills] WHERE [SkillID] = ?) AND (SELECT [activeStatus] FROM [User] WHERE [UserID] = [Mentor].[UserID]) = 1 AND (SELECT Count(*) as FreeSlot FROM [Slot] WHERE (Select MentorID FROM Schedule WHERE [Slot].ScheduleID = Schedule.ScheduleID) = [Mentor].[UserID] AND SkillID IS NULL) > 0 AND [MentorStatus] = N'Accepted'");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                PreparedStatement ps2 = connection.prepareStatement("SELECT AVG([noStar]) AS Rate FROM [Rating] WHERE [MentorID] = ?");
                ps2.setInt(1, rs.getInt("UserID"));
                ResultSet rs2 = ps2.executeQuery();
                int rate = 0;
                if (rs2.next()) {
                    rate = rs2.getInt("Rate");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT Count([RequestID]) as [accept]  FROM [Request] WHERE [UserID] = ? AND ([RequestStatus] = 'Confirmed' OR [RequestStatus] = 'Done' OR [RequestStatus] = 'Processing')");
                ps2.setInt(1, rs.getInt("UserID"));
                rs2 = ps2.executeQuery();
                int accept = 0;
                if (rs2.next()) {
                    accept = rs2.getInt("accept");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT Count([RequestID]) as [done]  FROM [Request] WHERE [UserID] = ? AND ([RequestStatus] = 'Confirmed' OR [RequestStatus] = 'Done')");
                ps2.setInt(1, rs.getInt("UserID"));
                rs2 = ps2.executeQuery();
                int done = 0;
                if (rs2.next()) {
                    done = rs2.getInt("done");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] = ?");
                ps2.setInt(1, rs.getInt("UserID"));
                rs2 = ps2.executeQuery();
                boolean active = false;
                String account = "";
                String fullname = "";
                String avatar = "";
                if (rs2.next()) {
                    account = rs2.getString("username");
                    active = rs2.getInt("activeStatus") == 1;
                    fullname = rs2.getString("fullname");
                    avatar = rs2.getString("Avatar");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT [ProfessionIntroduction] FROM [CV] WHERE [CvID] = ?");
                ps2.setInt(1, rs.getInt("CvID"));
                rs2 = ps2.executeQuery();
                String profession = "";
                if (rs2.next()) {
                    profession = rs2.getString("ProfessionIntroduction");
                }
                rs2.close();
                ps2.close();
                ps2 = connection.prepareStatement("SELECT Count([RequestID]) as [requests]  FROM [Request] WHERE [UserID] = ?");
                ps2.setInt(1, rs.getInt("UserID"));
                rs2 = ps2.executeQuery();
                int request = 0;
                if (rs2.next()) {
                    request = rs2.getInt("requests");
                }
                rs2.close();
                ps2.close();
                MentorDetail md = new MentorDetail(rs.getInt("UserID"), rate, accept, (accept > 0 ? ((accept) == 0 ? 0 : (int) ((float) done / (float) (accept / 100))) : 0), account, profession, active);
                md.setRequests(request);
                arr.put(new Mentor(rs.getString("MentorStatus"), rs.getString("Achivement"), rs.getString("Description"), rs.getInt("UserID"), rs.getInt("CvID"), fullname, avatar), md);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

    public void updateAchivement(int id, String achivement) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Mentor] WHERE [UserID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                ps.close();
                ps = connection.prepareStatement("UPDATE [Mentor] SET [Achivement] = ? WHERE [UserID] = ?");
                ps.setInt(2, id);
                ps.setString(1, achivement);
                ps.executeUpdate();
            } else {
                rs.close();
                ps.close();
                ps = connection.prepareStatement("INSERT INTO [Mentor] ([Achivement], [UserID]) VALUES (?, ?)");
                ps.setInt(2, id);
                ps.setString(1, achivement);
                ps.executeUpdate();
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void updateDescription(int id, String description) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Mentor] WHERE [UserID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                rs.close();
                ps.close();
                ps = connection.prepareStatement("UPDATE [Mentor] SET [Description] = ? WHERE [UserID] = ?");
                ps.setInt(2, id);
                ps.setString(1, description);
                ps.executeUpdate();
            } else {
                rs.close();
                ps.close();
                ps = connection.prepareStatement("INSERT INTO [Mentor] ([Description], [UserID]) VALUES (?, ?)");
                ps.setInt(2, id);
                ps.setString(1, description);
                ps.executeUpdate();
            }
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public boolean toggle(boolean type, int id) throws Exception {
        PreparedStatement ps = connection.prepareStatement("UPDATE [User] SET [activeStatus] = " + (type ? 1 : 0) + " WHERE [UserID] = ?");
        ps.setInt(1, id);
        int k = ps.executeUpdate();
        connection.commit();
        if (k > 0) {
            connection.close();
            return true;
        }
        connection.close();
        return false;
    }

    public int getTotalMentor() throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("  Select count(*) from Mentor");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return 0;
    }

    public MentorStatistic getMentorStatistic(int id) {
        MentorStatistic ms = null;
        try {
            PreparedStatement ps = connection.prepareStatement("  SELECT \n"
                    + "    COUNT([RequestID]) AS TotalRequest, \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [UserID] = ? \n"
                    + "       AND ([RequestStatus] = N'Accept' \n"
                    + "        OR [RequestStatus] = N'Processing' \n"
                    + "        OR [RequestStatus] = N'Done')) AS TotalAccepted, \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [UserID] = ? \n"
                    + "       AND [RequestStatus] = N'Reject') AS TotalRejected, \n"
                    + "    (SELECT AVG(CAST([noStar] AS FLOAT)) \n"
                    + "     FROM [Rating] \n"
                    + "     WHERE [MentorID] = ?) AS Rating, \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [UserID] = ? \n"
                    + "       AND [RequestStatus] = N'Done') AS TotalDone \n"
                    + "FROM \n"
                    + "    [Request] \n"
                    + "WHERE \n"
                    + "    [UserID] = ?");
            ps.setInt(1, id);
            ps.setInt(2, id);
            ps.setInt(3, id);
            ps.setInt(4, id);
            ps.setInt(5, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ms = new MentorStatistic();
                ms.setId(id);
                ms.setInvitedRequest(rs.getInt("TotalRequest"));
                ms.setAccepedRequest(rs.getInt("TotalAccepted"));
                ms.setRejectedRequest(rs.getInt("TotalRejected"));
                ms.setRating(rs.getFloat("Rating"));
                if (ms.getInvitedRequest() > 0) {
                    ms.setRejectPercent((float) ms.getRejectedRequest() / (float) ms.getInvitedRequest());
                } else {
                    ms.setRejectPercent(0);
                }
                if (ms.getAccepedRequest() > 0) {
                    ms.setCompletePercent((float) rs.getInt("TotalDone") / (float) ms.getAccepedRequest());
                } else {
                    ms.setCompletePercent(0);
                }
                connection.close();
            }
        } catch (Exception e) {
        }
        return ms;
    }

    public Mentor getMentor(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Mentor] WHERE [UserID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] = ?");
                ps.setInt(1, id);
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                Mentor m = new Mentor(rs.getString("MentorStatus"), rs.getString("Achivement"), rs.getString("Description"), rs.getInt("UserID"), rs.getInt("CvID"), rs2.getString("fullname"), rs2.getString("Avatar"));
                ps = connection.prepareStatement("SELECT Count([FollowID]) as Follow FROM [Follow] WHERE [MentorID] = ?");
                ps.setInt(1, id);
                rs2 = ps.executeQuery();
                rs2.next();
                m.setFollow(rs2.getInt("Follow"));
                ps = connection.prepareStatement("SELECT Count(*) as ratingTime, AVG(Cast([noStar] as Float)) as Rate FROM [Rating] WHERE MentorID = ?");
                ps.setInt(1, id);
                rs2 = ps.executeQuery();
                rs2.next();
                m.setRatingTime(rs2.getInt("ratingTime"));
                m.setRate(rs2.getFloat("Rate"));
                connection.close();
                return m;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public Mentor getMentorByCVID(int cvId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Mentor] WHERE [CvID] = ?");
            ps.setInt(1, cvId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int userId = rs.getInt("UserID");
                ps = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] = ?");
                ps.setInt(1, userId);
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                Mentor m = new Mentor(
                        rs.getString("MentorStatus"),
                        rs.getString("Achivement"),
                        rs.getString("Description"),
                        userId,
                        cvId,
                        rs2.getString("fullname"),
                        rs2.getString("Avatar")
                );

                ps = connection.prepareStatement("SELECT Count([FollowID]) as Follow FROM [Follow] WHERE [MentorID] = ?");
                ps.setInt(1, userId);
                rs2 = ps.executeQuery();
                rs2.next();
                m.setFollow(rs2.getInt("Follow"));

                ps = connection.prepareStatement("SELECT Count(*) as ratingTime, AVG(Cast([noStar] as Float)) as Rate FROM [Rating] WHERE MentorID = ?");
                ps.setInt(1, userId);
                rs2 = ps.executeQuery();
                rs2.next();
                m.setRatingTime(rs2.getInt("ratingTime"));
                m.setRate(rs2.getFloat("Rate"));

                connection.close();
                return m;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getSlotCash(int id) {
        int cash = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [CashPerSlot] FROM [CV] WHERE [CvID] = (SELECT [CvID] FROM [Mentor] WHERE [UserID] = ?)");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            cash = rs.getInt("CashPerSlot");
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
        return cash;
    }

    public HashMap<Mentor, MentorDetail> getAllWithDetail(int page, int pageSize) throws SQLException {
        HashMap<Mentor, MentorDetail> arr = new HashMap<>();
        try {
            // Tính toán giới hạn và offset cho phân trang
            int offset = (page - 1) * pageSize;

            // Chỉ lấy mentor có RoleID = 3 và activeStatus = 1, với phân trang
            String sql = "SELECT * FROM [Mentor] WHERE [UserID] IN (SELECT [UserID] FROM [User] WHERE RoleID = 3 AND activeStatus = 1) "
                    + "ORDER BY [UserID] "
                    + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, offset);  // offset
            ps.setInt(2, pageSize); // pageSize
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int mentorId = rs.getInt("UserID");

                // Lấy rating
                PreparedStatement ps2 = connection.prepareStatement("SELECT AVG([noStar]) AS Rate FROM [Rating] WHERE [MentorID] = ?");
                ps2.setInt(1, mentorId);
                ResultSet rs2 = ps2.executeQuery();
                int rate = (rs2.next()) ? rs2.getInt("Rate") : 0;
                rs2.close();
                ps2.close();

                // Lấy số lượng request
                ps2 = connection.prepareStatement("SELECT COUNT([RequestID]) as [accept] FROM [Request] WHERE [UserID] = ?");
                ps2.setInt(1, mentorId);
                rs2 = ps2.executeQuery();
                int accept = (rs2.next()) ? rs2.getInt("accept") : 0;
                rs2.close();
                ps2.close();

                // Lấy số lượng request hoàn thành
                ps2 = connection.prepareStatement("SELECT COUNT([RequestID]) as [done] FROM [Request] WHERE [UserID] = ? AND [RequestStatus] = 'Done'");
                ps2.setInt(1, mentorId);
                rs2 = ps2.executeQuery();
                int done = (rs2.next()) ? rs2.getInt("done") : 0;
                rs2.close();
                ps2.close();

                // Lấy thông tin user
                ps2 = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] = ?");
                ps2.setInt(1, mentorId);
                rs2 = ps2.executeQuery();
                boolean active = false;
                String account = "";
                String fullname = "";
                String avatar = "";
                if (rs2.next()) {
                    account = rs2.getString("email");
                    active = rs2.getInt("activeStatus") == 1;
                    fullname = rs2.getString("fullname");
                    avatar = rs2.getString("Avatar");
                }
                rs2.close();
                ps2.close();

                // Lấy thông tin CV
                String profession = "Không có thông tin";
                if (rs.getInt("CvID") > 0) {
                    ps2 = connection.prepareStatement("SELECT [ProfessionIntroduction] FROM [CV] WHERE [CvID] = ?");
                    ps2.setInt(1, rs.getInt("CvID"));
                    rs2 = ps2.executeQuery();
                    if (rs2.next()) {
                        profession = rs2.getString("ProfessionIntroduction");
                    }
                    rs2.close();
                    ps2.close();
                }

                Mentor mentor = new Mentor(rs.getString("MentorStatus"), rs.getString("Achivement"), rs.getString("Description"), mentorId, rs.getInt("CvID"), fullname, avatar);
                System.out.println(mentor);
                MentorDetail detail = new MentorDetail(mentorId, rate, accept, (accept > 0 ? (int) ((float) done / ((float) accept / 100)) : 0), account, profession, active);
                arr.put(mentor, detail);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public User getMentorById(int id) {
        User mentor = new User();
        try {
            Connection connection = new DBContext().getConnection();
            String sql = "select * from [User] where [User].UserID = ? and [User].RoleID = 3";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                mentor.setFullname(rs.getString("fullname"));
                mentor.setAvatar(rs.getString("Avatar"));
                mentor.setPhoneNumber(rs.getString("phoneNumber"));
                mentor.setAddress(rs.getString("address"));
                mentor.setEmail(rs.getString("email"));
                mentor.setSex(rs.getBoolean("sex"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mentor;
    }

    public ArrayList<Rate> getRateByMentorId(int id) {
        ArrayList<Rate> rates = new ArrayList<>();
        try {
            Connection connection = new DBContext().getConnection();
            String sql = "SELECT TOP 5 Rating.noStar, Rating.ratingComment\n"
                    + "FROM     Rating left JOIN[User] ON Rating.MenteeID = [User].UserID \n"
                    + "AND Rating.MentorID = [User].UserID AND Rating.MenteeID = [User].UserID \n"
                    + "AND Rating.MentorID = [User].UserID\n"
                    + "where MentorID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Rate rate = new Rate();
                rate.setNoStar(rs.getInt("noStar"));
                rate.setContent(rs.getString("ratingComment"));
                rates.add(rate);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return rates;
    }

    public int getPriceByMentorId(int mentorId) {
        int price = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("select * from [Mentor] as m\n"
                    + "join [CV] as c on m.CvID = c.CvID\n"
                    + "where m.UserID = ?");
            ps.setInt(1, mentorId);
            ResultSet rs = ps.executeQuery();
            rs.next();
            price = rs.getInt("CashPerSlot");
        } catch (Exception e) {
            e.printStackTrace();

        }
        return price;
    }

    public HashMap<Mentor, MentorDetail> getMentorsBySkillIdSimple(int skillId) throws Exception {
        HashMap<Mentor, MentorDetail> result = new HashMap<>();

        try {
            String sql = "SELECT * FROM [Mentor] WHERE [UserID] IN "
                    + "(SELECT [MentorID] FROM [MentorSkills] WHERE [SkillID] = ?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, skillId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int mentorId = rs.getInt("UserID");

                // Lấy thông tin user
                PreparedStatement psUser = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] = ?");
                psUser.setInt(1, mentorId);
                ResultSet rsUser = psUser.executeQuery();
                String fullname = "", avatar = "", account = "";
                boolean active = false;
                if (rsUser.next()) {
                    account = rsUser.getString("username");
                    fullname = rsUser.getString("fullname");
                    avatar = rsUser.getString("Avatar");
                    active = rsUser.getInt("activeStatus") == 1;
                }
                rsUser.close();
                psUser.close();

                // Lấy rate
                int rate = 0;
                PreparedStatement psRate = connection.prepareStatement(
                        "SELECT AVG([noStar]) AS Rate FROM [Rating] WHERE [MentorID] = ?");
                psRate.setInt(1, mentorId);
                ResultSet rsRate = psRate.executeQuery();
                if (rsRate.next()) {
                    rate = rsRate.getInt("Rate");
                }
                rsRate.close();
                psRate.close();

                // Lấy profession từ CV
                String profession = "";
                if (rs.getInt("CvID") > 0) {
                    PreparedStatement psCV = connection.prepareStatement(
                            "SELECT ProfessionIntroduction FROM [CV] WHERE [CvID] = ?");
                    psCV.setInt(1, rs.getInt("CvID"));
                    ResultSet rsCV = psCV.executeQuery();
                    if (rsCV.next()) {
                        profession = rsCV.getString("ProfessionIntroduction");
                    }
                    rsCV.close();
                    psCV.close();
                }

                Mentor mentor = new Mentor(
                        rs.getString("MentorStatus"),
                        rs.getString("Achivement"),
                        rs.getString("Description"),
                        mentorId,
                        rs.getInt("CvID"),
                        fullname,
                        avatar
                );

                MentorDetail detail = new MentorDetail(
                        mentorId,
                        rate,
                        0,
                        0,
                        account,
                        profession,
                        active
                );

                result.put(mentor, detail);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }

        return result;
    }
}
