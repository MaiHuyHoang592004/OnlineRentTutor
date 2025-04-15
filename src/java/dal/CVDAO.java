/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import model.CV;
import model.Skill;

/**
 *
 * @author ADMIN
 */
public class CVDAO extends DBContext{

    public  String getMentorByCvID(int id) {
        String idName = "";
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [UserID], (SELECT [fullname] FROM [User] WHERE [UserID] = [Mentor].[UserID]) as [fullname] FROM [Mentor] WHERE [CvID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                idName = rs.getInt("UserID") + ";" + rs.getString("fullname");
            }
        } catch (Exception e) {
        }
        return idName;
    }

    public  void confirmCv(int id) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE [Mentor] SET [MentorStatus] = N'Accepted' WHERE CvID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rejectCv(int id, String rejectReason) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE [Mentor] SET MentorStatus='Reject' WHERE CvID = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            ps = connection.prepareStatement("Update [Cv] set rejectReason=? WHERE CvID = ?");
            ps.setString(1, rejectReason);
            ps.setInt(2, id);
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CV> getAllNotConfirmedCV() throws Exception {
        ArrayList<CV> arr = new ArrayList();
        
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [CV]");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ps = connection.prepareStatement("SELECT [UserID] FROM [Mentor] WHERE [CvID] = ?");
                ps.setInt(1, rs.getInt("CvID"));
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                ps = connection.prepareStatement("SELECT * FROM [MentorSkills] WHERE [MentorID] = ?");
                ps.setInt(1, rs2.getInt("UserID"));
                rs2 = ps.executeQuery();
                CV cv = new CV(rs.getInt("CvID"), rs.getString("ProfessionIntroduction"), rs.getString("ServiceDescription"));
                cv.setCashPerSlot(rs.getInt("CashPerSlot"));
                cv.setRejectReason(rs.getString("rejectReason"));
                while (rs2.next()) {
                    ps = connection.prepareStatement("SELECT * FROM [Skills] WHERE [SkillID] = ?");
                    ps.setInt(1, rs2.getInt("SkillID"));
                    ResultSet rs3 = ps.executeQuery();
                    rs3.next();
                    cv.getSkills().add(new Skill(rs2.getInt("SkillID"), rs3.getString("SkillName"), rs3.getInt("enable") == 1, rs3.getString("image"), rs3.getString("Description")));
                }
                arr.add(cv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return arr;
    }

     public boolean createCV(int userID, String ProfessionIntro, String Description, String[] skills, int money) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO [CV] ([ProfessionIntroduction], [ServiceDescription], [CashPerSlot]) VALUES (?, ?, ?)");
            ps.setString(1, ProfessionIntro);
            ps.setString(2, ProfessionIntro);
            ps.setInt(3, money);
            ps.executeUpdate();
            connection.commit();
            ps = connection.prepareStatement("SELECT TOP (1) [CvID] FROM [CV] ORDER BY [CvID] DESC");
            ResultSet rs = ps.executeQuery();
            rs.next();
            ps = connection.prepareStatement("UPDATE [Mentor] SET [CvID] = ?,[MentorStatus]='Draft' WHERE [UserID] = ?");
            ps.setInt(1, rs.getInt("CvID"));
            ps.setInt(2, userID);
            ps.executeUpdate();
            connection.commit();
            for (int i = 0; i < skills.length; i++) {
                int id = Integer.parseInt(skills[i]);
                ps = connection.prepareStatement("INSERT INTO [MentorSkills] ([SkillID], [MentorID]) VALUES (?, ?)");
                ps.setInt(1, id);
                ps.setInt(2, userID);
                ps.executeUpdate();
            }
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }


    public boolean sendToAdmim(String cvId) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE [Mentor] SET [MentorStatus]='Pending' WHERE [CvID] = ?");
            ps.setString(1, cvId);
            ps.executeUpdate();
            ps = connection.prepareStatement("UPDATE [CV] SET [rejectReason]=? WHERE [CvID] = ?");
            ps.setString(1, null);
            ps.setString(2, cvId);
            ps.executeUpdate();
            connection.commit();
return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    public CV getCV(int id) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [CV] WHERE [CvID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ps = connection.prepareStatement("SELECT [UserID] FROM [Mentor] WHERE [CvID] = ?");
                ps.setInt(1, id);
                ResultSet rs2 = ps.executeQuery();
                rs2.next();
                ps = connection.prepareStatement("SELECT * FROM [MentorSkills] WHERE [MentorID] = ?");
                ps.setInt(1, rs2.getInt("UserID"));
                rs2 = ps.executeQuery();
                CV cv = new CV(rs.getInt("CvID"), rs.getString("ProfessionIntroduction"), rs.getString("ServiceDescription") , rs.getString("RejectReason"));
                cv.setCashPerSlot(rs.getInt("CashPerSlot"));
                while (rs2.next()) {
                    ps = connection.prepareStatement("SELECT * FROM [Skills] WHERE [SkillID] = ?");
                    ps.setInt(1, rs2.getInt("SkillID"));
                    rs = ps.executeQuery();
                    rs.next();
                    cv.getSkills().add(new Skill(rs2.getInt("SkillID"), rs.getString("SkillName"), rs.getInt("enable") == 1, rs.getString("image"), rs.getString("Description")));
                }
                return cv;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null;
    }

    public boolean updateCV(int CvID, String ProfessionIntroduction, String ServiceDescription, int CashPerSlot) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE [CV] SET [ProfessionIntroduction] = ?, [ServiceDescription] = ?, [CashPerSlot] = ?, rejectReason = ? WHERE [CvID] = ?");
            ps.setString(1, ProfessionIntroduction);
            ps.setString(2, ServiceDescription);
            ps.setInt(3, CashPerSlot);
            ps.setString(4, null);
            ps.setInt(5, CvID);
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("UPDATE [Mentor] SET [MentorStatus] = 'Draft' WHERE [CvID] = ?");
            ps2.setInt(1, CvID);
            ps2.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    public boolean updatePriceOfSlot(int CvID, int CashPerSlot) throws Exception {
        try {
PreparedStatement ps = connection.prepareStatement("UPDATE [CV] SET [CashPerSlot] = ? WHERE [CvID] = ?");
            ps.setInt(1, CashPerSlot);
            ps.setInt(2, CvID);
            ps.executeUpdate();
            connection.commit();
            connection.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    public boolean removeSkill(int userID, int skillID) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM [MentorSkills] WHERE [MentorID] = ? AND SkillID = ?");
            ps.setInt(1, userID);
            ps.setInt(2, skillID);
            int k = ps.executeUpdate();
            connection.commit();
            if (k > 0) {
                connection.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    public boolean addSkill(int userID, int skillID) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [MentorSkills] WHERE [MentorID] = ? AND [SkillID] = ?");
            ps.setInt(1, userID);
            ps.setInt(2, skillID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return false;
            }
            ps = connection.prepareStatement("INSERT INTO [MentorSkills] ([MentorID], [SkillID]) VALUES (?, ?)");
            ps.setInt(1, userID);
            ps.setInt(2, skillID);
            int k = ps.executeUpdate();
            connection.commit();
            if (k > 0) {
                connection.close();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

//     public void main(String[] args) {
//        try {
//            // Gọi phương thức getAllNotConfirmedCV
//            ArrayList<CV> notConfirmedCVs = getAllNotConfirmedCV();
//
//            // In thông tin của tất cả các CV chưa được xác nhận
//            for (CV cv : notConfirmedCVs) {
//             
//                System.out.println("Profession Introduction: " + cv.getProfessionIntroduction());
//                System.out.println("Service Description: " + cv.getServiceDescription());
//                System.out.println("Cash Per Slot: " + cv.getCashPerSlot());
//                System.out.println("Reject Reason: " + cv.getRejectReason());
//
//                System.out.println("Skills: ");
//                for (Skill skill : cv.getSkills()) {
//                 
//                    System.out.println("\tDescription: " + skill.getDescription());
//                }
//                System.out.println("----------------------------------");
//            }
//        } catch (Exception e) {
//            System.err.println("Error occurred: " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}