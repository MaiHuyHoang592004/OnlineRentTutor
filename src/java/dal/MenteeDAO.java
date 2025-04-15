/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.MenteeStatistic;
import model.User;

/**
 *
 * @author zzz
 */
public class MenteeDAO extends DBContext {

    public MenteeStatistic getMenteeStatistic(int uid) {
        MenteeStatistic ms = null;
        try {
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT \n"
                    + "    [UserID],\n"
                    + "    [sex],\n"
                    + "    [activeStatus],\n"
                    + "    [username],\n"
                    + "    [password],\n"
                    + "    [dob],\n"
                    + "    [email],\n"
                    + "    [phoneNumber],\n"
                    + "    [wallet],\n"
                    + "    [address],\n"
                    + "    [roleID],\n"
                    + "    [isValidate],\n"
                    + "    [Avatar],\n"
                    + "    [fullname], \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [SenderID] = [User].[UserID]) AS TotalRequest, \n"
                    + "    (SELECT COUNT([SkillID]) \n"
                    + "     FROM [Slot] \n"
                    + "     WHERE [MenteeID] = [User].[UserID]) AS TotalSkill, \n"
                    + "    (SELECT SUM(CAST([Time] AS FLOAT)) \n"
                    + "     FROM [Slot] \n"
                    + "     WHERE [MenteeID] = [User].[UserID]) AS TotalHour, \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [RejectRequest] \n"
                    + "     WHERE [RequestID] IN \n"
                    + "         (SELECT [RequestID] \n"
                    + "          FROM [Request] \n"
                    + "          WHERE [SenderID] = [User].[UserID])) AS RejectedRequest, \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [SenderID] = [User].[UserID] \n"
                    + "     AND [RequestStatus] != N'Open' \n"
                    + "     AND [RequestStatus] != N'Close' \n"
                    + "     AND [RequestStatus] != N'Reject') AS AcceptedRequest, \n"
                    + "    (SELECT COUNT([UserID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [SenderID] = [User].[UserID]) AS TotalMentor \n"
                    + "FROM \n"
                    + "    [User] \n"
                    + "WHERE \n"
                    + "    [UserID] = ?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ms = new MenteeStatistic();
                ms.setId(rs.getInt("UserID"));
                ms.setName(rs.getString("username"));
                ms.setFullname(rs.getString("fullname"));
                ms.setTotalHours(rs.getFloat("TotalHour"));
                ms.setTotalRequest(rs.getInt("TotalRequest"));
                ms.setTotalSkill(rs.getInt("TotalSkill"));
                ms.setAcceptedRequest(rs.getInt("AcceptedRequest"));
                ms.setRejectedRequest(rs.getInt("RejectedRequest"));
                ms.setTotalMentor(rs.getInt("TotalMentor"));
            }
            connection.close();
        } catch (Exception e) {
        }
        return ms;
    }

    public ArrayList<MenteeStatistic> getMenteeStatistic() {
        ArrayList<MenteeStatistic> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT \n"
                    + "    [UserID],\n"
                    + "    [sex],\n"
                    + "    [activeStatus],\n"
                    + "    [username],\n"
                    + "    [password],\n"
                    + "    [dob],\n"
                    + "    [email],\n"
                    + "    [phoneNumber],\n"
                    + "    [wallet],\n"
                    + "    [address],\n"
                    + "    [roleID],\n"
                    + "    [isValidate],\n"
                    + "    [Avatar],\n"
                    + "    [fullname], \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [SenderID] = [User].[UserID]) AS TotalRequest, \n"
                    + "    (SELECT COUNT([SkillID]) \n"
                    + "     FROM [Slot] \n"
                    + "     WHERE [MenteeID] = [User].[UserID]) AS TotalSkill, \n"
                    + "    (SELECT SUM([Time]) \n"
                    + "     FROM [Slot] \n"
                    + "     WHERE [MenteeID] = [User].[UserID]) AS TotalHour, \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [RejectRequest] \n"
                    + "     WHERE [RequestID] IN \n"
                    + "         (SELECT [RequestID] \n"
                    + "          FROM [Request] \n"
                    + "          WHERE [SenderID] = [User].[UserID])) AS RejectedRequest, \n"
                    + "    (SELECT COUNT([RequestID]) \n"
                    + "     FROM [Request] \n"
                    + "     WHERE [SenderID] = [User].[UserID] \n"
                    + "     AND [RequestStatus] != N'Open' \n"
                    + "     AND [RequestStatus] != N'Close' \n"
                    + "     AND [RequestStatus] != N'Reject') AS AcceptedRequest \n"
                    + "FROM \n"
                    + "    [User] \n"
                    + "WHERE \n"
                    + "    [UserID] IN (SELECT [UserID] FROM [Mentee])");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                MenteeStatistic ms = new MenteeStatistic();
                ms.setId(rs.getInt("UserID"));
                ms.setName(rs.getString("username"));
                ms.setFullname(rs.getString("fullname"));
                ms.setTotalHours(rs.getFloat("TotalHour"));
                ms.setTotalRequest(rs.getInt("TotalRequest"));
                ms.setTotalSkill(rs.getInt("TotalSkill"));
                ms.setAcceptedRequest(rs.getInt("AcceptedRequest"));
                ms.setRejectedRequest(rs.getInt("RejectedRequest"));
                arr.add(ms);
            }
            connection.close();
        } catch (Exception e) {
        }
        return arr;
    }

    public User getMenteeById(int id) {
        User mentee = new User();
        try {
            Connection connection = new DBContext().getConnection();
            String sql = "select * from [User] where [User].UserID = ? and [User].RoleID = 4";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                mentee.setFullname(rs.getString("fullname"));
                mentee.setAvatar(rs.getString("Avatar"));
                mentee.setPhoneNumber(rs.getString("phoneNumber"));
                mentee.setAddress(rs.getString("address"));
                mentee.setEmail(rs.getString("email"));
                mentee.setSex(rs.getBoolean("sex"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return mentee;
    }
}
