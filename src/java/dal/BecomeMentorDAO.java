package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.BecomeMentor;
import model.MentorSkill;

public class BecomeMentorDAO extends DBContext {

    // Lớp này sẽ tự động kế thừa kết nối từ DBContext thông qua phương thức getConnection()
    public BecomeMentorDAO() {
    }

    // Method to insert a new mentor into the BecomeMentor table
    public boolean insertBecomeMentor(int mentorID, String fullName, String phoneNumber, String email, String address, boolean sex, Date dob, String skills, String professionIntroduction, String serviceDescription, int cashPerSlot) {
        String sql = "INSERT INTO [dbo].[BecomeMentor] (MentorID, FullName, PhoneNumber, Email, Address, sex, dob, Skills, ProfessionIntroduction, ServiceDescription, CashPerSlot) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {  // Using inherited method getConnection()
            stmt.setInt(1, mentorID);
            stmt.setString(2, fullName);
            stmt.setString(3, phoneNumber);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setBoolean(6, sex);
            stmt.setDate(7, dob);
            stmt.setString(8, skills);
            stmt.setString(9, professionIntroduction);
            stmt.setString(10, serviceDescription);
            stmt.setInt(11, cashPerSlot);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve a list of mentors
    public List<BecomeMentor> getAllMentors() {
        List<BecomeMentor> mentors = new ArrayList<>();
        String sql = "SELECT * FROM [dbo].[BecomeMentor]";
        try (Statement stmt = getConnection().createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                BecomeMentor mentor = new BecomeMentor(
                        rs.getInt("MentorID"),
                        rs.getString("FullName"),
                        rs.getString("PhoneNumber"),
                        rs.getString("Email"),
                        rs.getString("Address"),
                        rs.getBoolean("sex"),
                        rs.getDate("dob"),
                        rs.getString("Skills"),
                        rs.getString("ProfessionIntroduction"),
                        rs.getString("ServiceDescription"),
                        rs.getInt("CashPerSlot")
                );
                mentors.add(mentor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentors;
    }

    public BecomeMentorDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean updateBecomeMentor(int mentorID, String fullName, String phoneNumber, String email,
            String address, boolean sex, Date dob, String skills,
            String professionIntroduction, String serviceDescription,
            int cashPerSlot) {
        String sql = "UPDATE [dbo].[BecomeMentor] SET "
                + "FullName = ?, PhoneNumber = ?, Email = ?, Address = ?, sex = ?, dob = ?, "
                + "Skills = ?, ProfessionIntroduction = ?, ServiceDescription = ?, CashPerSlot = ? "
                + "WHERE MentorID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, fullName);
            stmt.setString(2, phoneNumber);
            stmt.setString(3, email);
            stmt.setString(4, address);
            stmt.setBoolean(5, sex);
            stmt.setDate(6, dob);
            stmt.setString(7, skills);
            stmt.setString(8, professionIntroduction);
            stmt.setString(9, serviceDescription);
            stmt.setInt(10, cashPerSlot);
            stmt.setInt(11, mentorID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBecomeMentor(int mentorID) {
        String sql = "DELETE FROM [dbo].[BecomeMentor] WHERE MentorID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mentorID);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public BecomeMentor getMentorByID(int mentorID) {
        String sql = "SELECT * FROM [dbo].[BecomeMentor] WHERE MentorID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, mentorID);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new BecomeMentor(
                            rs.getInt("MentorID"),
                            rs.getString("FullName"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Email"),
                            rs.getString("Address"),
                            rs.getBoolean("sex"),
                            rs.getDate("dob"),
                            rs.getString("Skills"),
                            rs.getString("ProfessionIntroduction"),
                            rs.getString("ServiceDescription"),
                            rs.getInt("CashPerSlot")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if mentor not found
    }

 public boolean acceptMentor(int mentorID, List<Integer> skillIDs) {
    String sqlUpdateUser = "UPDATE [dbo].[User] SET RoleID = 3 WHERE UserID = ?";
    String sqlInsertMentor = "INSERT INTO [dbo].[Mentor] (UserID, MentorStatus) VALUES (?, 'Active')";
    String sqlInsertMentorSkill = "INSERT INTO [dbo].[MentorSkills] (SkillID, MentorID) VALUES (?, ?)";
    try (Connection connection = getConnection()) {
        connection.setAutoCommit(false);
        try (PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdateUser)) {
            stmtUpdate.setInt(1, mentorID);
            int rowsAffected = stmtUpdate.executeUpdate();
            if (rowsAffected > 0) {
                try (PreparedStatement stmtInsertMentor = connection.prepareStatement(sqlInsertMentor)) {
                    stmtInsertMentor.setInt(1, mentorID);
                    stmtInsertMentor.executeUpdate();
                }
                if (!skillIDs.isEmpty()) {
                    try (PreparedStatement stmtInsertSkill = connection.prepareStatement(sqlInsertMentorSkill)) {
                        // Sử dụng MentorSkill để chèn kỹ năng vào cơ sở dữ liệu
                        for (Integer skillID : skillIDs) {
                            MentorSkill mentorSkill = new MentorSkill(skillID, mentorID); // Sử dụng mentorId thay vì đối tượng Mentor
                            stmtInsertSkill.setInt(1, mentorSkill.getSkillId());
                            stmtInsertSkill.setInt(2, mentorSkill.getMentorId());
                            stmtInsertSkill.addBatch(); // Thêm vào batch
                        }
                        // Thực thi batch insert
                        stmtInsertSkill.executeBatch();
                    }
                }
                connection.commit(); // Commit transaction
                return true;
            }
        } catch (SQLException e) {
            connection.rollback(); // Rollback if error occurs
            e.printStackTrace();
        } finally {
            connection.setAutoCommit(true);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return false;
}


    public boolean rejectMentor(int mentorID) {
        String sql = "DELETE FROM [dbo].[BecomeMentor] WHERE MentorID = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, mentorID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // You can add other CRUD methods (update, delete) as needed
}
