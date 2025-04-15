package dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBContext {
    public Connection connection;

    public DBContext() {
        try {
            String user = "sa"; // change this to your MySQL username
            String pass = "sa"; // change this to your MySQL password
            String url = "jdbc:sqlserver://localhost:1433;databaseName=DB_SWP391"; // change mydb to your database name
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            connection = DriverManager.getConnection(url, user, pass);
            System.out.println(connection);

        } catch (Exception ex) {
            ex.printStackTrace();
            Logger.getLogger(DBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Phương thức getter để lấy kết nối
    public Connection getConnection() {
        return connection;
    }

    public static void main(String[] args) {
        DBContext dbContext = new DBContext();
        if (dbContext.connection != null) {
            System.out.println("Kết nối thành công với cơ sở dữ liệu!");
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}
