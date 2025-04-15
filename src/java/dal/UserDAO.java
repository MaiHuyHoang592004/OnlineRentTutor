/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Mentee;
import model.Mentor;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author zzz
 */
public class UserDAO extends DBContext{
    
     // Thêm một User vào database
    public void insertUser(User user) {
        String sql = "INSERT INTO [User] (sex, activeStatus, username, password, dob, email, phoneNumber, wallet, " +
                     "address, RoleID, isValidate, Avatar, fullname) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, user.getSex());
            stmt.setBoolean(2, user.getActiveStatus());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());
            stmt.setDate(5, new java.sql.Date(user.getDob().getTime()));
            stmt.setString(6, user.getEmail());
            stmt.setString(7, user.getPhoneNumber());
            stmt.setInt(8, user.getWallet());
            stmt.setString(9, user.getAddress());
            stmt.setInt(10, user.getRoleID());
            stmt.setBoolean(11, user.getIsValidate());
            stmt.setString(12, user.getAvatar());
            stmt.setString(13, user.getFullname());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Lấy danh sách tất cả Users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM [User]";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getBoolean("sex"),
                        rs.getBoolean("activeStatus"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getInt("wallet"),
                        rs.getString("address"),
                        rs.getInt("RoleID"),
                        rs.getBoolean("isValidate"),
                        rs.getString("Avatar"),
                        rs.getString("fullname")
                );
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
//
//    // Tìm User theo ID
public User getUserById(int userID) {
        String sql = "SELECT * FROM [User] WHERE UserID = ?";
        User user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {            
            stmt.setInt(1, userID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("UserID"),
                        rs.getBoolean("sex"),
                        rs.getBoolean("activeStatus"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getInt("wallet"),
                        rs.getString("address"),
                        rs.getInt("RoleID"),
                        rs.getBoolean("isValidate"),
                        rs.getString("Avatar"),
                        rs.getString("fullname")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
//get user by email
    public User getUserByEmail(String email) {
        String sql = "SELECT * FROM [User] WHERE email = ?";
        User user = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) { 
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                user = new User(
                        rs.getInt("UserID"),
                        rs.getBoolean("sex"),
                        rs.getBoolean("activeStatus"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getInt("wallet"),
                        rs.getString("address"),
                        rs.getInt("RoleID"),
                        rs.getBoolean("isValidate"),
                        rs.getString("Avatar"),
                        rs.getString("fullname")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
       // Cập nhật thông tin User
    public void updateUser(User user) {
        String sql = "UPDATE [User] SET sex=?, dob=?, email=?, " +
                     "phoneNumber=?, address=?,fullname=? WHERE UserID=?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) { 

            stmt.setBoolean(1, user.getSex());
            stmt.setDate(2, new java.sql.Date(user.getDob().getTime()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPhoneNumber());
            stmt.setString(5, user.getAddress());
            stmt.setString(6, user.getFullname());
            stmt.setInt(7, user.getUserID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//
//    // Xóa User theo ID
    public void deleteUser(int userID) {
        String sql = "DELETE FROM [User] WHERE UserID = ?";

       try (PreparedStatement stmt = connection.prepareStatement(sql)) { 

            stmt.setInt(1, userID);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Lấy thông tin User theo username và password

    public UserDAO() {
        super();
    }



public User getOne(String username, String password) {
    if (connection == null) {
        throw new IllegalStateException("Database connection is not initialized.");
    }

    ResultSet rs = null;
    String sql = "SELECT * FROM [User] WHERE [username] = ?";

    try (PreparedStatement stm = connection.prepareStatement(sql)) {
        stm.setString(1, username);
        rs = stm.executeQuery();

        if (rs.next()) {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            String hashedPassword = rs.getString("password"); // Mật khẩu đã mã hóa từ cơ sở dữ liệu

            // Kiểm tra xem mật khẩu nhập vào có khớp với mật khẩu đã mã hóa trong cơ sở dữ liệu không
            if (BCrypt.checkpw(password, hashedPassword)) {
                User u = new User();
                u.setUserID(rs.getInt("userID"));
                u.setUsername(rs.getString("username"));
                u.setPassword(rs.getString("password"));
                u.setFullname(rs.getString("fullname"));
                u.setAddress(rs.getString("address"));
                u.setEmail(rs.getString("email"));
                u.setPhoneNumber(rs.getString("phoneNumber"));
                u.setRoleID(rs.getInt("roleID"));
                u.setActiveStatus(rs.getBoolean("activeStatus"));
                u.setWallet(rs.getInt("wallet"));
                u.setIsValidate(rs.getBoolean("isValidate"));
                u.setAvatar(rs.getString("avatar"));
                u.setSex(rs.getBoolean("sex"));
                u.setDob(rs.getDate("dob"));
                System.out.println("User found: " + u.getUsername());
                return u; // Trả về người dùng nếu mật khẩu đúng
            } else {
                System.out.println("Mật khẩu không đúng cho username: " + username);
            }
        } else {
            System.out.println("Không tìm thấy user với username: " + username);
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    return null; // Trả về null nếu không tìm thấy người dùng hoặc mật khẩu không khớp
}

    public void updatePassword(String email, String password) {
        String sql = "UPDATE [dbo].[User]\n"
                + "   SET [password] = ?\n"
                + " WHERE [email] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setString(2, email);
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
 public static boolean isRegistered(String email, String username) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [User] WHERE [email] = ? AND [username] = ?");
            ps.setString(1, email);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu người dùng tồn tại
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    // Kiểm tra người dùng là Admin
    public static boolean isSignInAdmin(String email, String username) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [User] WHERE [email] = ? AND [username] = ? AND [roleID] IN (1, 2); ");
            ps.setString(1, email);
            ps.setString(2, username);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Trả về true nếu là Admin
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    // Lấy thông tin vai trò của người dùng
    public static Object getRole(int id, String role) throws SQLException {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            if (role.equalsIgnoreCase("Mentee")) {
                try (PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [Mentee] WHERE [UserID] = ?")) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            return new Mentee(rs.getString("MenteeStatus"), id);
                        }
                    }
                }
            } else if (role.equalsIgnoreCase("Mentor")) {
                try (PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [Mentor] WHERE [UserID] = ?")) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            try (PreparedStatement ps2 = dbo.prepareStatement("SELECT * FROM [User] WHERE [UserID] = ?")) {
                                ps2.setInt(1, id);
                                try (ResultSet rs2 = ps2.executeQuery()) {
                                    if (rs2.next()) {
                                        return new Mentor(
                                                rs.getString("MentorStatus"),
                                                rs.getString("Achivement"),
                                                rs.getString("Description"),
                                                id,
                                                rs.getInt("CvID"),
                                                rs2.getString("fullname"),
                                                rs2.getString("Avatar")
                                        );
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return null; // Trả về null nếu không tìm thấy vai trò
    }

    // Cập nhật tiền trong ví người dùng
    public static void updateMoney(int uid, int price) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement("UPDATE [User] SET [wallet] = ? WHERE [UserID] = ?");
            ps.setInt(1, price);
            ps.setInt(2, uid);
            ps.executeUpdate();
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    // Kiểm tra tài khoản trùng lặp (email hoặc username)
    public boolean isDuplicateAccount(String userName, String email) throws SQLException {
        Connection dbo = null;
        String query = "SELECT * FROM [User] WHERE username = ? OR email = ?";
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            try (PreparedStatement preparedStatement = dbo.prepareStatement(query)) {
                preparedStatement.setString(1, userName);
                preparedStatement.setString(2, email);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // Returns true if there is at least one result
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
        }
        return false;
    }

    public static List<User> getTopUsersWithRoleID3() throws Exception {
    List<User> users = new ArrayList<>();
    Connection dbo = null;
    try {
        dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
        String sql = "SELECT TOP (4) u.[Avatar], u.[fullname], r.[Star] " +
                     "FROM [User] u JOIN Rating r ON u.UserID = r.MentorID " +
                     "WHERE u.roleID = 3 ORDER BY r.[Star] DESC";  // Giả sử roleID = 3 là mentor
        PreparedStatement ps = dbo.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            User mentor = new User();
            mentor.setAvatar(rs.getString("Avatar"));
            mentor.setFullname(rs.getString("fullname"));
            mentor.setWallet(rs.getInt("Star"));
            users.add(mentor);
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    }
    return users;
}

public int getRequestMentor(int id) throws Exception {
    Connection dbo = null;
    try {
        dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
        String sql = "SELECT COUNT(DISTINCT SendID) " +
                     "FROM Request " +
                     "WHERE UserID = ?";
        PreparedStatement ps = dbo.prepareStatement(sql);
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1);  // Trả về số lượng request
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    }
    return 0;  // Trả về 0 nếu không có kết quả
}

    public static int getWallet(int uid) throws Exception {
    DBContext dbContext = new DBContext();  // Khởi tạo đối tượng DBContext để lấy kết nối
    Connection dbo = dbContext.connection;  // Lấy kết nối từ DBContext
    int wallet = 0;
    try {
        PreparedStatement ps = dbo.prepareStatement("SELECT [wallet] FROM [User] WHERE [UserID] = ?");
        ps.setInt(1, uid);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {  // Sử dụng if để đảm bảo có kết quả trả về
            wallet = rs.getInt("wallet");
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
    }
    return wallet;
}


    public void updateWalletForRole2(int newWalletValue) throws Exception {
        Connection dbo = getConnection();
        PreparedStatement psCheckRole = null;
        ResultSet rsRole = null;
        PreparedStatement psUpdate = null;

        try {
            psCheckRole = dbo.prepareStatement("SELECT [UserID] FROM [User] WHERE [roleID] = 2");
            rsRole = psCheckRole.executeQuery();
            while (rsRole.next()) {
                int userId = rsRole.getInt("UserID");
                psUpdate = dbo.prepareStatement("UPDATE [User] SET [wallet] = [wallet]+ ? WHERE [UserID] = ?");
                psUpdate.setInt(1, newWalletValue);
                psUpdate.setInt(2, userId);
                psUpdate.executeUpdate();
                System.out.println("Ok rồi");
            }
            System.out.println("Wallet updated successfully for all users with role 2.");
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isMentee(User u) {
        if (u.getRole().equalsIgnoreCase("Mentee")) {
            return true;
        }
        return false;
    }
    public User getUserByID(int userID) {
        String sql = "SELECT * FROM [User] WHERE UserID = ?";
        try (Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return new User(
                    rs.getInt("UserID"),
                    rs.getBoolean("sex"),
                    rs.getBoolean("activeStatus"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getDate("dob"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getInt("wallet"),
                    rs.getString("address"),
                    rs.getInt("RoleID"),
                    rs.getBoolean("isValidate"),
                    rs.getString("Avatar"),
                    rs.getString("fullname")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }
        public void updateStatusUserById(int id, boolean status) {
        try {
            Connection connection = new DBContext().getConnection();

            String sql = "UPDATE [dbo].[User]\n"
                    + "   SET [isValidate] = ?\n"
                    + "     \n"
                    + " WHERE [User].UserID = ?";
            if (connection != null) {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setBoolean(1, status);
                preparedStatement.setInt(2, id);

                preparedStatement.executeUpdate();

            }

        } catch (SQLException ignored) {
            System.out.println(ignored.getMessage());
        }
    }
        public ArrayList<User> getAllMentee() {
        ArrayList<User> mentees = new ArrayList<>();
        String sql = "SELECT * FROM [User] where RoleID  = 4";

        try {
            Connection connection = new DBContext().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getBoolean("sex"),
                        rs.getBoolean("activeStatus"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getInt("wallet"),
                        rs.getString("address"),
                        rs.getInt("RoleID"),
                        rs.getBoolean("isValidate"),
                        rs.getString("Avatar"),
                        rs.getString("fullname")
                );
                mentees.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return mentees;
    }
            public ArrayList<User> getAllMentor() {
        ArrayList<User> mentors = new ArrayList<>();
        String sql = "SELECT * FROM [User] where RoleID  = 3";

        try {
            Connection connection = new DBContext().getConnection();
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                User user = new User(
                        rs.getInt("UserID"),
                        rs.getBoolean("sex"),
                        rs.getBoolean("activeStatus"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getDate("dob"),
                        rs.getString("email"),
                        rs.getString("phoneNumber"),
                        rs.getInt("wallet"),
                        rs.getString("address"),
                        rs.getInt("RoleID"),
                        rs.getBoolean("isValidate"),
                        rs.getString("Avatar"),
                        rs.getString("fullname")
                );
                mentors.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return mentors;
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
       public int countUsersByRole(int roleId) {
        String sql = "SELECT COUNT(*) FROM [DB_SWP391].[dbo].[User] WHERE RoleID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
public int getTotalAccount() {
        try {
            Connection con = new DBContext().getConnection();
            String sql = "SELECT COUNT(*) FROM [User]";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {  // Moves to the first row of the result set
                return rs.getInt(1);  // Retrieve the count from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions
        }
        return 0;  // Return 0 if there's an exception or no result
    }
    
    public List<User> getAllUserByPage(int page, int itemsPerPage) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM [User] ORDER BY UserID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int offset = (page - 1) * itemsPerPage;  // Calculate the starting point

            stmt.setInt(1, offset);
            stmt.setInt(2, itemsPerPage);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    User user = new User(
                            rs.getInt("UserID"),
                            rs.getBoolean("sex"),
                            rs.getBoolean("activeStatus"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getDate("dob"),
                            rs.getString("email"),
                            rs.getString("phoneNumber"),
                            rs.getInt("wallet"),
                            rs.getString("address"),
                            rs.getInt("RoleID"),
                            rs.getBoolean("isValidate"),
                            rs.getString("Avatar"),
                            rs.getString("fullname")
                    );
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
public int getTotalMentee() {
        try {
            Connection con = new DBContext().getConnection();
            String sql = "SELECT COUNT(*) FROM [User] where [User].RoleID = 4";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {  // Moves to the first row of the result set
                return rs.getInt(1);  // Retrieve the count from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions
        }
        return 0;
    }
    
    public int getTotalMentor() {
        try {
            Connection con = new DBContext().getConnection();
            String sql = "SELECT COUNT(*) FROM [User] where [User].RoleID = 3";
            PreparedStatement statement = con.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {  // Moves to the first row of the result set
                return rs.getInt(1);  // Retrieve the count from the first column
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any SQL exceptions
        }
        return 0;
    }
public ArrayList<User> getAllMenteeByPage(int page, int itemsPerPage) {
    ArrayList<User> mentees = new ArrayList<>();
    String sql = "SELECT * FROM [User] WHERE RoleID = 4 ORDER BY UserID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try {
        Connection connection = new DBContext().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        int offset = (page - 1) * itemsPerPage;  // Calculate the offset

        statement.setInt(1, offset);
        statement.setInt(2, itemsPerPage);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            User user = new User(
                    rs.getInt("UserID"),
                    rs.getBoolean("sex"),
                    rs.getBoolean("activeStatus"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getDate("dob"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getInt("wallet"),
                    rs.getString("address"),
                    rs.getInt("RoleID"),
                    rs.getBoolean("isValidate"),
                    rs.getString("Avatar"),
                    rs.getString("fullname")
            );
            mentees.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return mentees;
}
public ArrayList<User> getAllMentorByPage(int page, int itemsPerPage) {
    ArrayList<User> mentors = new ArrayList<>();
    String sql = "SELECT * FROM [User] WHERE RoleID = 3 ORDER BY UserID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try {
        Connection connection = new DBContext().getConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        
        int offset = (page - 1) * itemsPerPage;  // Calculate offset for pagination
        
        statement.setInt(1, offset);
        statement.setInt(2, itemsPerPage);

        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            User user = new User(
                    rs.getInt("UserID"),
                    rs.getBoolean("sex"),
                    rs.getBoolean("activeStatus"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getDate("dob"),
                    rs.getString("email"),
                    rs.getString("phoneNumber"),
                    rs.getInt("wallet"),
                    rs.getString("address"),
                    rs.getInt("RoleID"),
                    rs.getBoolean("isValidate"),
                    rs.getString("Avatar"),
                    rs.getString("fullname")
            );
            mentors.add(user);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return mentors;
}
} 
