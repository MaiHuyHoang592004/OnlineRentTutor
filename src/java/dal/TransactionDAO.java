package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Transaction;
import model.Withdraw;

public class TransactionDAO extends DBContext {

    // Constructor gọi constructor của DBContext để thiết lập kết nối
    public TransactionDAO() {
        super();
    }
    public static List<Transaction> getAllTransactions() {
        return getTransactionsWithPaging(null, null, null, 0, Integer.MAX_VALUE);
    }
    public static ArrayList<Withdraw> getWithdraw() throws Exception {
        ArrayList<Withdraw> arr = new ArrayList<>();
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection(); // Lấy kết nối từ DBContext
            PreparedStatement ps = dbo.prepareStatement("SELECT t.ID , u.email ,  u.fullname , b.BankType , b.BankName ,  b.BankNo , t.Balance , t.Status "
                    + "FROM [Transaction] t JOIN [User] U On t.UserID = u.UserID JOIN UserBank b ON b.UserID = u.UserID "
                    + "WHERE [Content] = N'Rút tiền' ORDER BY t.[Status] ASC");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(new Withdraw(rs.getInt("ID"), rs.getString("email"), rs.getString("fullname"), rs.getString("BankType"),
                        rs.getString("BankName"), rs.getString("BankNo"), rs.getInt("Balance"), rs.getString("Status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbo != null) {
                dbo.close();  // Đảm bảo kết nối được đóng
            }
        }
        return arr;
    }

    public static void acceptWithdraw(int id) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Lấy kết nối từ DBContext
            PreparedStatement ps = dbo.prepareStatement("UPDATE [Transaction] SET [Status] = N'Success' WHERE [ID] = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbo != null) {
                dbo.close();  // Đảm bảo kết nối được đóng
            }
        }
    }

    public static void withdraw(int uid, int money) throws Exception {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Lấy kết nối từ DBContext
            PreparedStatement ps = dbo.prepareStatement("INSERT INTO [Transaction] ([UserID], [Balance], [Type], [Content], [Status]) VALUES (?, ?, N'-', N'Rút tiền', N'Pending')");
            ps.setInt(1, uid);
            ps.setInt(2, money);
            ps.executeUpdate();

            ps = dbo.prepareStatement("UPDATE [User] SET [wallet] = [wallet] - ? WHERE [UserID] = ?");
            ps.setInt(1, money);
            ps.setInt(2, uid);
            ps.executeUpdate();

            dbo.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbo != null) {
                dbo.close();  // Đảm bảo kết nối được đóng
            }
        }
    }

    public static ArrayList<Transaction> getTransactionById(int id) throws Exception {
        ArrayList<Transaction> arr = new ArrayList<>();
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();  // Lấy kết nối từ DBContext
            PreparedStatement ps = dbo.prepareStatement("SELECT * FROM [Transaction] WHERE [UserID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(new Transaction(rs.getInt("ID"), rs.getInt("UserID"), rs.getInt("Balance"), rs.getString("Type"),
                        rs.getString("Content"), rs.getDate("Time"), rs.getString("Status")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (dbo != null) {
                dbo.close();  // Đảm bảo kết nối được đóng
            }
        }
        return arr;
    }

public static ArrayList<Transaction> getFilteredTransactions(int userId, String type, String status, String amountOrder, String time, String fromDate, String toDate) throws Exception {
    ArrayList<Transaction> arr = new ArrayList<>();
    Connection dbo = null;
    try {
        dbo = new DBContext().getConnection();
        StringBuilder sql = new StringBuilder("SELECT * FROM [Transaction] WHERE UserID = ?");

        // Add filters for date range
        if (fromDate != null && !fromDate.isEmpty()) {
            sql.append(" AND Time >= ?");
        }
        if (toDate != null && !toDate.isEmpty()) {
            sql.append(" AND Time <= ?");
        }

        // Build dynamic query based on filters
        if (type != null && !type.isEmpty()) {
            sql.append(" AND Type = ?");
        }
        if (status != null && !status.isEmpty()) {
            sql.append(" AND Status = ?");
        }
        if (amountOrder != null && !amountOrder.isEmpty()) {
            sql.append(" ORDER BY Balance ");
            sql.append(amountOrder.equals("asc") ? "ASC" : "DESC");
        }
        if (time != null && !time.isEmpty()) {
            sql.append(" AND Time LIKE ?");
        }

        PreparedStatement ps = dbo.prepareStatement(sql.toString());
        int index = 1;
        ps.setInt(index++, userId);

        // Set values for filters
        if (fromDate != null && !fromDate.isEmpty()) {
            ps.setString(index++, fromDate);
        }
        if (toDate != null && !toDate.isEmpty()) {
            ps.setString(index++, toDate);
        }
        if (type != null && !type.isEmpty()) {
            ps.setString(index++, type);
        }
        if (status != null && !status.isEmpty()) {
            ps.setString(index++, status);
        }
        if (time != null && !time.isEmpty()) {
            ps.setString(index++, "%" + time + "%");
        }

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            arr.add(new Transaction(
                    rs.getInt("ID"),
                    rs.getInt("UserID"),
                    rs.getInt("Balance"),
                    rs.getString("Type"),
                    rs.getString("Content"),
                    rs.getDate("Time"),
                    rs.getString("Status")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (dbo != null) {
            dbo.close();
        }
    }
    return arr;
}

    public int getAllEarning() throws SQLException {
        int sum = 0;
        ArrayList<Transaction> transactions = new ArrayList<>();
        Connection connection = new DBContext().getConnection();
        String sql = "select * from [Transaction] where Status = 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Transaction t = new Transaction();
            t.setBalance(rs.getInt("Balance"));
            t.setFee(rs.getFloat("fee"));
            
            transactions.add(t);
        }
        for (Transaction t : transactions) {
            sum += t.getBalance() * t.getFee();
        }
        System.out.println("SUM ALL = " + sum);
        return sum;
    }

    public int getEarningOfMonth() throws java.sql.SQLException {
        int sum = 0;
        ArrayList<Transaction> transactions = new ArrayList<>();
        Connection connection = new DBContext().getConnection();
        String sql = "SELECT TOP (1000)\n"
                + "    [ID],\n"
                + "    [UserID],\n"
                + "    [Balance],\n"
                + "    [Type],\n"
                + "    [Content],\n"
                + "    [fee],\n"
                + "    [Time],\n"
                + "    [Status],\n"
                + "    [TransCode],\n"
                + "    [Balance] * [fee] AS BalanceTimesFee\n"
                + "FROM [DB_SWP391].[dbo].[Transaction]\n"
                + "WHERE [Time] >= DATEADD(day, -30, GETDATE())";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Transaction t = new Transaction();
            t.setBalance(rs.getInt("Balance"));
            t.setFee(rs.getFloat("fee"));
            
            transactions.add(t);
        }
        for (Transaction t : transactions) {
            sum += t.getBalance() * t.getFee();
        }

        System.out.println("SUM MONTH = " + sum);

        return sum;
    }

    public ArrayList<Transaction> getAllTransaction() throws java.sql.SQLException {
        ArrayList<Transaction> transactions = new ArrayList<>();

        Connection connection = new DBContext().getConnection();
        String sql = "SELECT * \n"
                + "FROM [Transaction]\n"
                + "WHERE YEAR(time) = YEAR(GETDATE())";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet rs = statement.executeQuery();
        while (rs.next()) {
            Transaction t = new Transaction();
            t.setBalance(rs.getInt("Balance"));
            t.setFee(rs.getFloat("fee"));
            t.setTime(rs.getDate("Time"));
            transactions.add(t);
        }
        return transactions;
    }
        public static int countTransactions(String search, String status) {
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();
            
            StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM [Transaction] WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            // Add search condition if provided
            if (search != null && !search.trim().isEmpty()) {
                // Try to parse as integer for UserID search
                try {
                    int userId = Integer.parseInt(search.trim());
                    sql.append(" AND (UserID = ? OR Content LIKE ?)");
                    params.add(userId);
                    params.add("%" + search + "%");
                } catch (NumberFormatException e) {
                    // If not a number, search only in Content
                    sql.append(" AND Content LIKE ?");
                    params.add("%" + search + "%");
                }
            }
            
            // Add status filter if provided
            if (status != null && !status.trim().isEmpty()) {
                sql.append(" AND Status = ?");
                params.add(status);
            }
            
            PreparedStatement ps = dbo.prepareStatement(sql.toString());
            
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbo != null) dbo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
            public static List<Transaction> getTransactionsWithPaging(String search, String status, String sortBy, int page, int pageSize) {
        List<Transaction> transactions = new ArrayList<>();
        Connection dbo = null;
        try {
            dbo = new DBContext().getConnection();
            
            StringBuilder sql = new StringBuilder("SELECT * FROM [Transaction] WHERE 1=1");
            List<Object> params = new ArrayList<>();
            
            // Add search condition if provided
            if (search != null && !search.trim().isEmpty()) {
                // Try to parse as integer for UserID search
                try {
                    int userId = Integer.parseInt(search.trim());
                    sql.append(" AND (UserID = ? OR Content LIKE ?)");
                    params.add(userId);
                    params.add("%" + search + "%");
                } catch (NumberFormatException e) {
                    // If not a number, search only in Content
                    sql.append(" AND Content LIKE ?");
                    params.add("%" + search + "%");
                }
            }
            
            // Add status filter if provided
            if (status != null && !status.trim().isEmpty()) {
                sql.append(" AND Status = ?");
                params.add(status);
            }
            
            // Add sorting
            if (sortBy != null && !sortBy.trim().isEmpty()) {
                switch (sortBy.toLowerCase()) {
                    case "balance":
                        sql.append(" ORDER BY Balance DESC");
                        break;
                    case "status":
                        sql.append(" ORDER BY Status ASC");
                        break;
                    default:
                        sql.append(" ORDER BY [Time] DESC"); // Default sort by time
                        break;
                }
            } else {
                sql.append(" ORDER BY [Time] DESC"); // Default sort by time
            }
            
            // Add pagination
            if (pageSize > 0) {
                sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
                params.add(page * pageSize); // Offset
                params.add(pageSize);        // Fetch count
            }
            
            PreparedStatement ps = dbo.prepareStatement(sql.toString());
            
            // Set parameters
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                Transaction transaction = new Transaction(
                    rs.getInt("ID"),
                    rs.getInt("UserID"),
                    rs.getInt("Balance"),
                    rs.getString("Type"),
                    rs.getString("Content"),
                    rs.getDate("Time"),
                    rs.getString("Status")
                );
                
                // Set fee if it exists in the result set
                try {
                    transaction.setFee(rs.getFloat("fee"));
                } catch (SQLException e) {
                    // Fee column might not exist or be null, ignore
                }
                
                transactions.add(transaction);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (dbo != null) dbo.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return transactions;
    }
            public static void rejectWithdraw(int id) throws Exception {
    Connection dbo = null;
    try {
        dbo = new DBContext().getConnection();
        
        // First, get the transaction details to know how much to refund
        PreparedStatement ps = dbo.prepareStatement("SELECT UserID, Balance FROM [Transaction] WHERE [ID] = ?");
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery();
        
        if (rs.next()) {
            int userId = rs.getInt("UserID");
            int amount = rs.getInt("Balance");
            
            // Update transaction status to Failed
            ps = dbo.prepareStatement("UPDATE [Transaction] SET [Status] = N'Failed' WHERE [ID] = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
            
            // Refund the money to user's wallet
            ps = dbo.prepareStatement("UPDATE [User] SET [wallet] = [wallet] + ? WHERE [UserID] = ?");
            ps.setInt(1, amount);
            ps.setInt(2, userId);
            ps.executeUpdate();
            
            dbo.commit();
        }
    } catch (Exception e) {
        if (dbo != null) {
            try {
                dbo.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        throw e;
    } finally {
        if (dbo != null) {
            dbo.close();
        }
    }
}
}
