/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Rate;

/**
 *
 * @author TGDD
 */
public class RateDAO extends DBContext {

    public void Rating(int menteeId, int mentorId, int noStar, String ratingComment) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Rating ([MenteeID],[MentorID],[noStar],[rateTime],[ratingComment])\n"
                    + "VALUES (?, ?, ?, GETDATE(), ?)");
            ps.setInt(1, menteeId);
            ps.setInt(2, mentorId);
            ps.setInt(3, noStar);
            ps.setString(4, ratingComment);
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public ArrayList<Rate> getRates(int mentorId) throws Exception {
        ArrayList<Rate> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("select r.*, me.fullname as MenteeName, me.Avatar, mo.fullname as MentorName from Rating as r\n"
                    + "join [User] as me on r.MenteeID = me.UserID\n"
                    + "join [User] as mo on r.MentorID = mo.UserID\n"
                    + "where r.MentorID = ?\n"
                    + "ORDER BY rateTime DESC"
            );
            ps.setInt(1, mentorId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Rate r = new Rate();
                r.setSenderID(rs.getInt("MenteeID"));
                r.setNoStar(rs.getInt("noStar"));
                r.setSenderName(rs.getString("MenteeName"));
                r.setSenderAvatar(rs.getString("Avatar"));
                r.setContent(rs.getString("ratingComment"));
                r.setRateTime(rs.getTimestamp("rateTime"));
                arr.add(r);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

    public static void main(String[] args) {
        RateDAO r = new RateDAO();
        ArrayList<Rate> arr = new ArrayList<>();
        try {
            arr = r.getRates(5);
        } catch (Exception ex) {
            Logger.getLogger(RateDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i).toString());
        }

    }
}
