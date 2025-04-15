package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Bank;

public class BankDAO {

    // Cập nhật thông tin ngân hàng cho người dùng
    public static void updateBank(Bank bank) throws Exception {
        boolean insert = (getBank(bank.getId()) == null);
        String sql;
        if (insert) {
            sql = "INSERT INTO [UserBank] ([UserID], [BankName], [BankNo], [BankType]) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE [UserBank] SET BankName = ?, BankNo = ?, BankType = ? WHERE UserID = ?";
        }

        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement(sql);

            if (insert) {
                ps.setInt(1, bank.getId());
                ps.setString(2, bank.getBankName());
                ps.setString(3, bank.getBankNo());
                ps.setString(4, bank.getBankType());
            } else {
                ps.setString(1, bank.getBankName());
                ps.setString(2, bank.getBankNo());
                ps.setString(3, bank.getBankType());
                ps.setInt(4, bank.getId());
            }
            ps.executeUpdate();
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    // Lấy thông tin ngân hàng của người dùng
    public static Bank getBank(int id) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [UserBank] WHERE [UserID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bank(id, rs.getString("BankName"), rs.getString("BankNo"), rs.getString("BankType"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbo != null) dbo.close();  // Đảm bảo kết nối được đóng
        }
        return null;
    }

    // Kiểm tra mã giao dịch
    public static boolean checkingTransCode(String code) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [Transaction] WHERE [TransCode] = ?");
            ps.setString(1, code);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }

    // Tạo giao dịch mới
    public static void createNewTrans(String transCode, String content, int userId, int balance, boolean type) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement("INSERT INTO [Transaction] ([UserID], [Balance], [Type], [Content], [Status], [TransCode]) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, userId);
            ps.setInt(2, balance);
            ps.setString(3, type ? "+" : "-");
            ps.setString(4, content);
            ps.setString(5, "Pending");
            ps.setString(6, transCode);
            ps.executeUpdate();
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public static int updateTrans(String Code, String status) throws Exception {
        Connection dbo = null;
        int uid = -1;
        try {
            dbo = new DBContext().getConnection();  // Sử dụng getConnection() từ DBContext
            PreparedStatement ps = dbo.prepareStatement("SELECT [UserID] FROM [Transaction] WHERE [TransCode] = ? AND [Status] = N'Pending'");
            ps.setString(1, Code);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                uid = rs.getInt("UserID");
            }
            ps = dbo.prepareStatement("UPDATE [Transaction] SET [Status] = ? WHERE [TransCode] = ? AND [Status] = N'Pending'");
            ps.setString(1, status);
            ps.setString(2, Code);
            ps.executeUpdate();
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return uid;
    }

}
