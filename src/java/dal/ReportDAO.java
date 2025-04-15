/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Report;
import model.User;

/**
 *
 * @author kttan
 */
public class ReportDAO extends DBContext {

    public ArrayList<Report> getAllReport() {
        ArrayList<Report> reports = new ArrayList<>();

        try {
            Connection connection = new DBContext().getConnection();
            String sql = "select R.ReportContent, R.ReportID, R.reportTime, R.Status, U.UserID, U.fullname \n"
                    + "from Report R join [User] U on R.UserID = U.UserID";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Report r = new Report();
                User u = new User();
                r.setId(rs.getInt("ReportID"));
                r.setContent(rs.getString("ReportContent"));
                r.setSendTime(rs.getTimestamp("reportTime"));
                u.setUserID(rs.getInt("UserID"));
                u.setFullname(rs.getString("fullname"));
                r.setUser(u);
                r.setStatus(rs.getString("Status"));
                reports.add(r);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reports;
    }
}
