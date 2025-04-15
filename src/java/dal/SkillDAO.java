package dal;

import model.Skill;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillDAO extends DBContext {

    public SkillDAO() {
        super(); // Gọi constructor của DBContext để thiết lập kết nối
    }

    public boolean createSkill(String name, boolean active, String description) {
        String query = "INSERT INTO Skills (SkillName, enable, Description) VALUES (?, ?, ?)";
        try (Connection conn = connection; // Sử dụng kết nối từ DBContext
                 PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setBoolean(2, active);
            ps.setString(3, description);

            int result = ps.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateSkill(int id, String name, String description, boolean active) {
        String query = "UPDATE Skills SET SkillName = ?, enable = ?, Description = ? WHERE SkillID = ?";
        try (Connection conn = connection; // Sử dụng kết nối từ DBContext
                 PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setBoolean(2, active);
            ps.setString(3, description);
            ps.setInt(4, id);

            int result = ps.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public ArrayList<Skill> getAll() throws Exception {
        ArrayList<Skill> arr = new ArrayList();
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM Skills");
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            arr.add(new Skill(rs.getInt("SkillID"), rs.getString("SkillName"), rs.getInt("enable") == 1, rs.getString("image"), rs.getString("Description")));
        }

        return arr;
    }

    public List<Skill> getAll(boolean enable) {
        String query = "SELECT * FROM Skills WHERE enable = ?";
        List<Skill> skills = new ArrayList<>();

        try (Connection conn = connection; // Sử dụng kết nối từ DBContext
                 PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setBoolean(1, enable);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                skills.add(new Skill(
                        rs.getInt("SkillID"),
                        rs.getString("SkillName"),
                        rs.getBoolean("enable"),
                        rs.getString("image"),
                        rs.getString("Description")
                ));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return skills;
    }

    public Skill getSkillById(int id) {
        String query = "SELECT * FROM Skills WHERE SkillID = ?";
        Skill skill = null;

        try (Connection conn = connection; // Sử dụng kết nối từ DBContext
                 PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String avatar = rs.getString("image");
                avatar = (avatar == null || avatar.isEmpty()) ? "path-to-default-image.jpg" : avatar;

                skill = new Skill(
                        rs.getInt("SkillID"),
                        rs.getString("SkillName"),
                        rs.getBoolean("enable"),
                        avatar,
                        rs.getString("Description")
                );
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return skill;
    }

    public boolean toggleSkillStatus(boolean enable, int id) {
        String query = "UPDATE Skills SET enable = ? WHERE SkillID = ?";
        try (Connection conn = connection; // Sử dụng kết nối từ DBContext
                 PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setBoolean(1, enable);
            ps.setInt(2, id);

            int result = ps.executeUpdate();
            conn.commit();
            return result > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    public int skillCount() {
        String query = "SELECT COUNT(SkillID) as count FROM Skills";
        try (Connection conn = connection; // Sử dụng kết nối từ DBContext
                 PreparedStatement ps = conn.prepareStatement(query); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("count");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Skill> getSkillsByMentorId(int mentorId) throws Exception {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT s.* FROM Skills s "
                + "INNER JOIN MentorSkills ms ON s.SkillID = ms.SkillID "
                + "WHERE ms.MentorID = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, mentorId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            skills.add(new Skill(
                    rs.getInt("SkillID"),
                    rs.getString("SkillName"),
                    rs.getInt("enable") == 1,
                    rs.getString("image"),
                    rs.getString("Description")
            ));
        }

        return skills;
    }

    public void insertNewSkill(String name, boolean enable, String des) {
        try {
            Connection connection = new DBContext().getConnection();

            String sql = "INSERT INTO [dbo].[Skills]\n"
                    + "           ([SkillName]\n"
                    + "           ,[enable]\n"
                    + "           ,[Description])\n"
                    + "     VALUES\n"
                    + "           (?, ?, ?)";
            if (connection != null) {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setBoolean(2, enable);
                preparedStatement.setString(3, des);

                preparedStatement.execute();

            }

        } catch (SQLException ignored) {
            System.out.println(ignored.getMessage());
        }
    }

    public Map<String, Integer> getSkillCounts() {
        Map<String, Integer> skillCounts = new HashMap<>();

        try {
            Connection connection = new DBContext().getConnection();
            String sql = "SELECT \n"
                    + "    Sk.SkillName, \n"
                    + "    COUNT(*) AS TotalCount\n"
                    + "FROM Slot Sl \n"
                    + "JOIN Skills Sk ON Sl.SkillID = Sk.SkillID \n"
                    + "WHERE Sl.Status = 'Done'\n"
                    + "GROUP BY Sk.SkillName";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                String skillName = rs.getString("SkillName");
                int totalCount = rs.getInt("TotalCount");
                skillCounts.put(skillName, totalCount);
            }

        } catch (SQLException ignored) {
            System.out.println(ignored.getMessage());
        }
        return skillCounts;
    }

    public ArrayList<Skill> getAllSkill() {
        ArrayList<Skill> skills = new ArrayList<>();

        try {
            Connection connection = new DBContext().getConnection();
            String sql = "select * from Skills";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Skill s = new Skill();
                s.setId(rs.getInt("SkillID"));
                s.setName(rs.getString("SkillName"));
                s.setDescription(rs.getString("Description"));
                s.setAvatar(rs.getString("image"));
                s.setEnable(rs.getBoolean("enable"));
                skills.add(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return skills;
    }

    public void updateSkillById(int id, boolean enable) {
        try {
            Connection connection = new DBContext().getConnection();

            String sql = "UPDATE [dbo].[Skills]\n"
                    + "   SET [enable] = ?\n"
                    + " WHERE [dbo].[Skills].SkillID = ?";
            if (connection != null) {

                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, enable);
                preparedStatement.setInt(2, id);

                preparedStatement.executeUpdate();

            }

        } catch (SQLException ignored) {
            System.out.println(ignored.getMessage());
        }
    }

    public List<Skill> getSkillsByMentor(int mentorId) {
        List<Skill> list = new ArrayList<>();
        String sql = """
        SELECT s.SkillID, s.SkillName, s.Description, s.enable, s.image
        FROM MentorSkills ms
        JOIN Skills s ON ms.SkillID = s.SkillID
        WHERE ms.MentorID = ?
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mentorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getInt("SkillID"));
                skill.setName(rs.getString("SkillName"));
                skill.setDescription(rs.getString("Description"));
                skill.setEnable(rs.getBoolean("enable"));
                skill.setAvatar(rs.getString("image"));
                list.add(skill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public ArrayList<Skill> getAllSkillByPage(int page, int itemsPerPage) {
        ArrayList<Skill> skills = new ArrayList<>();

        try {
            Connection connection = new DBContext().getConnection();
            String sql = "SELECT * FROM Skills ORDER BY SkillID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            PreparedStatement statement = connection.prepareStatement(sql);

            int offset = (page - 1) * itemsPerPage;  // Calculate offset for pagination

            statement.setInt(1, offset);
            statement.setInt(2, itemsPerPage);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Skill s = new Skill();
                s.setId(rs.getInt("SkillID"));
                s.setName(rs.getString("SkillName"));
                s.setDescription(rs.getString("Description"));
                s.setAvatar(rs.getString("image"));
                s.setEnable(rs.getBoolean("enable"));
                skills.add(s);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return skills;
    }
}
