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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import model.Schedule;
import model.ScheduleView;
import model.Skill;
import model.Slot;
import model.User;

/**
 *
 * @author TGDD
 */
public class ScheduleDAO extends DBContext {

    public ScheduleDAO() {
    }

    public int getPercentByRequest(int id) {
        int percent = 0;
//    Connection dbo = null;

        try {
//        dbo = DatabaseUtil.getConn(); // Initialize connection

            // SQL query to count total slots and done slots
            PreparedStatement ps = connection.prepareStatement(
                    "SELECT COUNT(rs.[SlotID]) AS Total, "
                    + "COUNT(CASE WHEN s.[Status] = N'Done' THEN 1 END) AS Done "
                    + "FROM [RequestSlot] rs "
                    + "JOIN [Slot] s ON rs.[SlotID] = s.[SlotID] "
                    + "WHERE rs.[RequestID] = ?"
            );

            ps.setInt(1, id); // Setting parameter for request ID

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int totalSlots = rs.getInt("Total");
                int doneSlots = rs.getInt("Done");

                // Calculate percentage only if there are slots
                if (totalSlots > 0) {
                    percent = (int) ((double) doneSlots / totalSlots * 100);
                } else {
                    percent = 0; // If no slots, percentage is 0
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return percent; // Return the percentage
    }

    public void updateSlot(int id, String link, float hour, Timestamp start) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE [Slot] SET [Link] = ?, [Time] = ?, [startAt] = ? WHERE [SlotID] = ?");
            ps.setString(1, link);
            ps.setFloat(2, hour);
            ps.setTimestamp(3, start);
            ps.setInt(4, id);
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void updateSlotNew(int id, String link, float hour, Timestamp start, int scheId) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE [Slot] SET [Link] = ?, [Time] = ?, [startAt] = ?, scheduleID=?, status='Pending' WHERE [SlotID] = ?");
            ps.setString(1, link);
            ps.setFloat(2, hour);
            ps.setTimestamp(3, start);
            ps.setInt(4, scheId);
            ps.setInt(5, id);
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public void updateSlotSchedule(int id, String link, float hour, Timestamp start, int scheduleId) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("UPDATE [Slot] SET [Link] = ?, [Time] = ?, [startAt] = ?, Status='Pending' WHERE [SlotID] = ?");
            ps.setString(1, link);
            ps.setFloat(2, hour);
            ps.setTimestamp(3, start);
            ps.setInt(4, id);
            ps.executeUpdate();
            PreparedStatement ps2 = connection.prepareStatement("UPDATE [Schedule] SET Status = N'New' WHERE [ScheduleId] = ?");
            ps2.setInt(1, scheduleId);
            ps2.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
    }

    public boolean deleteSchedule(int scheduleID) {
        try {
            connection.setAutoCommit(false);

            PreparedStatement ps = connection.prepareStatement("DELETE FROM [RequestSlot] WHERE [SlotID] IN (SELECT [SlotID] FROM [Slot] WHERE [ScheduleID] = ?)");
            ps.setInt(1, scheduleID);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM [Slot] WHERE [ScheduleID] = ?");
            ps.setInt(1, scheduleID);
            ps.executeUpdate();

            ps = connection.prepareStatement("DELETE FROM [Schedule] WHERE [ScheduleID] = ?");
            ps.setInt(1, scheduleID);
            ps.executeUpdate();

            connection.commit();
            return true;
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    public boolean deleteSlot(int id) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Request] WHERE [RequestID] = (SELECT [RequestID] FROM [RequestSlot] WHERE [SlotID] = ?) AND [RequestStatus] = N'Accept'");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                connection.close();
                return false;
            } else {
                ps = connection.prepareStatement("DELETE FROM [RequestSlot] WHERE [SlotID] = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps = connection.prepareStatement("DELETE FROM [Slot] WHERE [SlotID] = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.close();
        return true;
    }

    public boolean deleteScheduleSlot(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Request] WHERE [RequestID] = (SELECT [RequestID] FROM [RequestSlot] WHERE [SlotID] = ?) AND [RequestStatus] = N'Accept'");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                connection.close();
                return false;
            } else {
                ps = connection.prepareStatement("DELETE FROM [RequestSlot] WHERE [SlotID] = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps = connection.prepareStatement("DELETE FROM [Slot] WHERE [SlotID] = ?");
                ps.setInt(1, id);
                ps = connection.prepareStatement("DELETE FROM [Slot] WHERE [SlotID] = ?");
                ps.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean deleteSlotPending(int id) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Request] WHERE [RequestID] = (SELECT [RequestID] FROM [RequestSlot] WHERE [SlotID] = ?) AND [RequestStatus] = N'Accept'");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                connection.close();
                return false;
            } else {
                ps = connection.prepareStatement("DELETE FROM [RequestSlot] WHERE [SlotID] = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                ps = connection.prepareStatement("DELETE FROM [Slot] WHERE [SlotID] = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
                connection.commit();
            }
        } catch (Exception e) {
            System.out.println("Delete slot: " + e);
        }
        return true;
    }

    public Slot getSlotById(int id) {
        Slot s = null;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [SlotID]\n"
                    + "      ,[Time]\n"
                    + "      ,[startAt]\n"
                    + "      ,[Link]\n"
                    + "      ,[ScheduleID]\n"
                    + "      ,[SkillID]\n"
                    + "      ,[MenteeID], [Status],\n"
                    + " (SELECT [fullname] FROM [User] WHERE [UserID] = (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID])) as Mentor, (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID]) as MentorID FROM [Slot] WHERE [SlotID] = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                s = new Slot(rs.getInt("SlotID"), rs.getTimestamp("startAt"), rs.getFloat("Time"), rs.getString("Link"), rs.getString("Mentor"), rs.getInt("MentorID"));
                s.setStatus(rs.getString("Status"));
                s.setMenteeId(rs.getInt("MenteeID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static int weekCount(ArrayList<Slot> array) {
        Collections.sort(array, new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getSlotTime().before(o2.getSlotTime()) ? -1 : 1;
            }
        });
        int r = 1;
        if (array.size() > 0) {
            if (array.get(0).getSlotTime().before(new java.util.Date())) {
                int firstWeek = weekOfYear(array.get(0).getSlotTime());
                int lastWeek = weekOfYear(new java.util.Date(array.get(array.size() - 1).getSlotTime().toInstant().toEpochMilli()));
                r = lastWeek - firstWeek + 1;
            } else {
                int firstWeek = weekOfYear(new java.util.Date());
                int lastWeek = weekOfYear(new java.util.Date(array.get(array.size() - 1).getSlotTime().toInstant().toEpochMilli()));
                r = lastWeek - firstWeek + 1;
            }
        }
        return r;
    }

    public boolean confirmSlot(int sid, int uid, String role) throws Exception {
        try {
            // Retrieve the current status of the slot
            PreparedStatement ps = connection.prepareStatement("SELECT Status FROM Slot WHERE SlotID = ?");
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String status = rs.getString("Status").toLowerCase();
            System.out.println(status);

            // If the slot is not confirmed yet
            if (status.contains("not confirm")) {
                if (role.equalsIgnoreCase("mentor")) {
                    // Mentor confirms the slot
                    ps = connection.prepareStatement("UPDATE Slot SET Status = N'Mentor Confirm' WHERE SlotID = ? AND ScheduleID in (SELECT ScheduleID From Schedule WHERE MentorID = ?)");
                    ps.setInt(1, sid);
                    ps.setInt(2, uid);
                    ps.executeUpdate();
                } else if (role.equalsIgnoreCase("mentee")) {
                    // Mentee confirms the slot
                    ps = connection.prepareStatement("UPDATE Slot SET Status = N'Mentee Confirm' WHERE SlotID = ? AND MenteeID = ?");
                    ps.setInt(1, sid);
                    ps.setInt(2, uid);
                    ps.executeUpdate();
                }
            } // If the mentor has already confirmed and now the mentee is confirming
            else if (status.contains("mentor confirm") && role.equalsIgnoreCase("mentee")) {
                // Mentee confirms after mentor has confirmed, update slot to 'Done'
                ps = connection.prepareStatement("UPDATE Slot SET Status = N'Done' WHERE SlotID = ? AND MenteeID = ?");
                ps.setInt(1, sid);
                ps.setInt(2, uid);
                ps.executeUpdate();
            } // If the mentee has confirmed and now the mentor is confirming
            else if (status.contains("mentee confirm") && role.equalsIgnoreCase("mentor")) {
                // Mentor confirms after mentee, update slot to 'Done'
                ps = connection.prepareStatement("UPDATE Slot SET Status = N'Done' WHERE SlotID = ? AND MentorID = ?");
                ps.setInt(1, sid);
                ps.setInt(2, uid);
                ps.executeUpdate();
            }

            // Retrieve the RequestID associated with the slot
            ps = connection.prepareStatement("SELECT RequestID FROM RequestSlot WHERE SlotID = ?");
            ps.setInt(1, sid);
            rs = ps.executeQuery();
            if (rs.next()) {
                int requestId = rs.getInt("RequestID");

                // Check total slots for this RequestID
                ps = connection.prepareStatement("SELECT COUNT(*) FROM RequestSlot WHERE RequestID = ?");
                ps.setInt(1, requestId);
                ResultSet rsCount = ps.executeQuery();
                rsCount.next();
                int totalSlots = rsCount.getInt(1);

                // Check how many slots are already confirmed as 'Done'
                ps = connection.prepareStatement("SELECT COUNT(*) FROM Slot WHERE SlotID IN (SELECT SlotID FROM RequestSlot WHERE RequestID = ?) AND Status = N'Done'");
                ps.setInt(1, requestId);
                ResultSet rsConfirmedCount = ps.executeQuery();
                rsConfirmedCount.next();
                int confirmedSlots = rsConfirmedCount.getInt(1);

                // If all slots for the RequestID are confirmed as 'Done', update the RequestStatus to 'Done'
                if (confirmedSlots == totalSlots) {
                    ps = connection.prepareStatement("UPDATE [Request] SET [RequestStatus] = N'Done' WHERE [RequestID] = ?");
                    ps.setInt(1, requestId);
                    ps.executeUpdate();
                } else {
                    // Otherwise, update RequestStatus to 'Processing'
                    ps = connection.prepareStatement("UPDATE [Request] SET [RequestStatus] = N'Processing' WHERE [RequestID] = ?");
                    ps.setInt(1, requestId);
                    ps.executeUpdate();
                }
            }

            connection.commit(); // Commit changes to the database
        } catch (Exception e) {
            e.printStackTrace();
            connection.rollback(); // Rollback in case of an error
            return false;
        } finally {
            connection.close();
        }
        return true;
    }

    public static int numberOfWeek(int year) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, Calendar.DECEMBER);
        cal.set(Calendar.DAY_OF_MONTH, 31);

        int ordinalDay = cal.get(Calendar.DAY_OF_YEAR);
        int weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1; // Sunday = 0
        int numberOfWeeks = (ordinalDay - weekDay + 10) / 7;
        return numberOfWeeks;
    }

    public static int weekOfYear(java.util.Date day) {
        Calendar c = Calendar.getInstance();
        c.setTime(day);
        return c.get(Calendar.DAY_OF_WEEK) >= 2 ? c.get(Calendar.WEEK_OF_YEAR) : c.get(Calendar.WEEK_OF_YEAR) - 1;
    }

    public static Calendar FirstDateOfWeek(int year, int weekOfYear) throws ParseException {
        SimpleDateFormat format1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date jan1 = format1.parse("01/01/" + year);
        Calendar c = Calendar.getInstance();
        c.setTime(jan1);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        int dayOffset = 2 - dayOfWeek;
        if (dayOffset == 1) {
            dayOffset = -6;
        }
        c.add(Calendar.DATE, dayOffset);
        c.add(Calendar.DATE, 7 * (weekOfYear - 1));
        return c;
    }

    public static ArrayList<Slot> sortByWeek(Calendar first, Calendar last, ArrayList<Slot> array) {
        ArrayList<Slot> copy = (ArrayList) array.clone();
        first.set(Calendar.HOUR_OF_DAY, 0);
        first.set(Calendar.MINUTE, 0);
        first.set(Calendar.SECOND, 0);
        last.set(Calendar.HOUR_OF_DAY, 23);
        last.set(Calendar.MINUTE, 59);
        last.set(Calendar.SECOND, 59);
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i).getSlotTime().after(last.getTime()) || copy.get(i).getSlotTime().before(first.getTime())) {
                copy.remove(i);
                i--;
            }
        }
        Collections.sort(copy, new Comparator<Slot>() {
            @Override
            public int compare(Slot o1, Slot o2) {
                return o1.getSlotTime().before(o2.getSlotTime()) ? -1 : 1;
            }
        });
        return copy;
    }

    public ArrayList<Slot> getFreeSlots(java.util.Date date, int uid) throws Exception {
        ArrayList<Slot> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT \n"
                    + "    [SlotID],\n"
                    + "    [Time],\n"
                    + "    [startAt],\n"
                    + "    [Link],\n"
                    + "    [ScheduleID],\n"
                    + "    [SkillID],\n"
                    + "    [MenteeID], \n"
                    + "    [Status], \n"
                    + "    (SELECT [fullname] FROM [User] WHERE [UserID] = ?) as [Mentor], \n"
                    + "    (SELECT [fullname] FROM [User] WHERE [UserID] = [Slot].[MenteeID]) as [Mentee], \n"
                    + "    (SELECT [SkillName] FROM [Skills] WHERE [SkillID] = [Slot].[SkillID]) as [Skill] \n"
                    + "FROM \n"
                    + "    [Slot] \n"
                    + "WHERE \n"
                    + "    [SkillID] IS NULL \n"
                    + "    AND [startAt] > ? \n"
                    + "    AND [ScheduleID] IN (SELECT [ScheduleID] FROM [Schedule] WHERE [MentorID] = ?) \n"
                    + "    AND [Status] = 'Not Confirm'  \n"
                    + "ORDER BY \n"
                    + "    [startAt];");
            ps.setInt(1, uid);
            ps.setTimestamp(2, Timestamp.from(date.toInstant()));
            ps.setInt(3, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Slot slot = new Slot(rs.getInt("SlotID"), rs.getTimestamp("startAt"), rs.getFloat("Time"), rs.getString("Link"), rs.getString("Mentor"), uid);
                slot.setSkill(rs.getString("Skill"));
                slot.setMentee(rs.getString("Mentee"));
                slot.setMenteeId(rs.getInt("MenteeID"));
                slot.setStatus(rs.getString("Status"));
                arr.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

    public ArrayList<Integer> getSlotsIDByRequest(int rid) throws Exception {
        ArrayList<Integer> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [SlotID]\n"
                    + "      ,[Time]\n"
                    + "      ,[startAt]\n"
                    + "      ,[Link]\n"
                    + "      ,[ScheduleID]\n"
                    + "      ,[SkillID]\n"
                    + "      ,[MenteeID], [Status], (SELECT [fullname] FROM [User] WHERE [User].[UserID] = (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID])) as [Mentor], (SELECT [fullname] FROM [User] WHERE [UserID] = [Slot].[MenteeID]) as [Mentee], (SELECT [SkillName] FROM [Skills] WHERE [SkillID] = [Slot].[SkillID]) as [Skill], (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID]) as [MentorID] FROM [Slot] WHERE [SlotID] in (SELECT [SlotID] FROM [RequestSlot] WHERE [RequestID] = ?)");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(rs.getInt("SlotID"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

    public ArrayList<Slot> getSlotsByRequest(int rid) throws Exception {
        ArrayList<Slot> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [SlotID]\n"
                    + "      ,[Time]\n"
                    + "      ,[startAt]\n"
                    + "      ,[Link]\n"
                    + "      ,[ScheduleID]\n"
                    + "      ,[SkillID]\n"
                    + "      ,[MenteeID], [Status], (SELECT [fullname] FROM [User] WHERE [User].[UserID] = (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID])) as [Mentor], (SELECT [fullname] FROM [User] WHERE [UserID] = [Slot].[MenteeID]) as [Mentee], (SELECT [SkillName] FROM [Skills] WHERE [SkillID] = [Slot].[SkillID]) as [Skill], (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID]) as [MentorID] FROM [Slot] WHERE [SlotID] in (SELECT [SlotID] FROM [RequestSlot] WHERE [RequestID] = ?) ORDER BY [StartAt]");
            ps.setInt(1, rid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Slot slot = new Slot(rs.getInt("SlotID"), rs.getTimestamp("startAt"), rs.getFloat("Time"), rs.getString("Link"), rs.getString("Mentor"), rs.getInt("MentorID"));
                slot.setSkill(rs.getString("Skill"));
                slot.setMentee(rs.getString("Mentee"));
                slot.setMenteeId(rs.getInt("MenteeID"));
                slot.setStatus(rs.getString("Status"));
                arr.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

    public ArrayList<Slot> getSlots(int year, int week, int uid) throws Exception {
        ArrayList<Slot> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [SlotID]\n"
                    + "      ,[Time]\n"
                    + "      ,[startAt]\n"
                    + "      ,[Link]\n"
                    + "      ,[ScheduleID]\n"
                    + "      ,[SkillID]\n"
                    + "      ,[MenteeID], [Status], (SELECT [fullname] FROM [User] WHERE [UserID] = ?) as [Mentor], (SELECT [fullname] FROM [User] WHERE [UserID] = [Slot].[MenteeID]) as [Mentee], (SELECT [SkillName] FROM [Skills] WHERE [SkillID] = [Slot].[SkillID]) as [Skill] FROM [Slot] WHERE ScheduleID = (SELECT ScheduleID FROM Schedule WHERE [Year] = ? AND [Week] = ? AND [MentorID] = ?) ORDER BY [StartAt]");
            ps.setInt(1, uid);
            ps.setInt(2, year);
            ps.setInt(3, week);
            ps.setInt(4, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Slot slot = new Slot(rs.getInt("SlotID"), rs.getTimestamp("startAt"), rs.getFloat("Time"), rs.getString("Link"), rs.getString("Mentor"), uid);
                slot.setSkill(rs.getString("Skill"));
                slot.setMentee(rs.getString("Mentee"));
                slot.setMenteeId(rs.getInt("MenteeID"));
                slot.setStatus(rs.getString("Status"));
                arr.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

    public ArrayList<Slot> menteeGetSlots(int year, int week, int uid) throws Exception {
        ArrayList<Slot> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [SlotID]\n"
                    + "      ,[Time]\n"
                    + "      ,[startAt]\n"
                    + "      ,[Link]\n"
                    + "      ,[ScheduleID]\n"
                    + "      ,[SkillID]\n"
                    + "      ,[MenteeID], [Status], (SELECT [fullname] FROM [User] WHERE [UserID] = ?) as [Mentee], (SELECT [fullname] FROM [User] WHERE [UserID] = (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID])) as [Mentor], (SELECT [SkillName] FROM [Skills] WHERE [SkillID] = [Slot].[SkillID]) as [Skill], (SELECT [MentorID] FROM [Schedule] WHERE [ScheduleID] = [Slot].[ScheduleID]) as [MentorID] FROM [Slot] WHERE ScheduleID in (SELECT ScheduleID FROM Schedule WHERE [Year] = ? AND [Week] = ?) AND [MenteeID] = ?");
            ps.setInt(1, uid);
            ps.setInt(2, year);
            ps.setInt(3, week);
            ps.setInt(4, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Slot slot = new Slot(rs.getInt("SlotID"), rs.getTimestamp("startAt"), rs.getFloat("Time"), rs.getString("Link"), rs.getString("Mentor"), rs.getInt("MentorID"));
                slot.setSkill(rs.getString("Skill"));
                slot.setMentee(rs.getString("Mentee"));
                slot.setMenteeId(rs.getInt("MenteeID"));
                slot.setStatus(rs.getString("Status"));
                arr.add(slot);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
        return arr;
    }

//    public void addSlot(String link, float hour, java.util.Date start, int week, int year, int uid) throws Exception {
//        Timestamp date = Timestamp.from(start.toInstant());
//        try {
//            PreparedStatement ps = connection.prepareStatement("SELECT ScheduleID FROM Schedule WHERE [Year] = ? AND [Week] = ? AND [MentorID] = ?");
//            ps.setInt(1, year);
//            ps.setInt(2, week);
//            ps.setInt(3, uid);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                ps = connection.prepareStatement("INSERT INTO [Slot] ([Link], [Time], [startAt], [ScheduleID] , [Status]) VALUES (?, ?, ?, (SELECT ScheduleID FROM Schedule WHERE [Year] = ? AND [Week] = ? AND [MentorID] = ?) , N'Pending')");
//                ps.setString(1, link);
//                ps.setFloat(2, hour);
//                ps.setTimestamp(3, date);
//                ps.setInt(4, year);
//                ps.setInt(5, week);
//                ps.setInt(6, uid);
//                ps.executeUpdate();
//                connection.commit();
//            } else {
//                ps = connection.prepareStatement("INSERT INTO Schedule ([Year], [Week], [MentorID] , [Status]) VALUES (?, ?, ? , N'New')");
//                ps.setInt(1, year);
//                ps.setInt(2, week);
//                ps.setInt(3, uid);
//                ps.executeUpdate();
//                ps = connection.prepareStatement("INSERT INTO [Slot] ([Link], [Time], [startAt], [ScheduleID] , [Status]) VALUES (?, ?, ?, (SELECT ScheduleID FROM Schedule WHERE [Year] = ? AND [Week] = ? AND [MentorID] = ? AND [Status] = N'New'), N'Pending')");
//                ps.setString(1, link);
//                ps.setFloat(2, hour);
//                ps.setTimestamp(3, date);
//                ps.setInt(4, year);
//                ps.setInt(5, week);
//                ps.setInt(6, uid);
//                ps.executeUpdate();
//                connection.commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//    }

    public void addSlotbyDay(String link, float hour, java.util.Date startDate, int weekOfYear, int year, int userID) throws SQLException {
        // Tạo kết nối cơ sở dữ liệu
        String sql = "INSERT INTO [dbo].[Slot] ([Link], [Time], [startAt], [ScheduleID], [MenteeID], [Status]) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Chuyển đổi startDate từ java.util.Date sang java.sql.Timestamp
            Timestamp startTimestamp = new Timestamp(startDate.getTime()); // Chuyển Date thành Timestamp

            // Thêm dữ liệu vào Slot
            statement.setString(1, link);
            statement.setFloat(2, hour);
            statement.setTimestamp(3, startTimestamp);  // Sử dụng Timestamp ở đây

            // Lấy ScheduleID từ bảng Schedule
            String scheduleSQL = "INSERT INTO [dbo].[Schedule] ([MentorID], [Year], [Week], [Status]) VALUES (?, ?, ?, ?)";
            try (PreparedStatement scheduleStatement = connection.prepareStatement(scheduleSQL, Statement.RETURN_GENERATED_KEYS)) {
                scheduleStatement.setInt(1, userID);
                scheduleStatement.setInt(2, year);
                scheduleStatement.setInt(3, weekOfYear);
                scheduleStatement.setString(4, "active");  // Ví dụ: trạng thái là active

                // Lưu Schedule
                scheduleStatement.executeUpdate();

                // Lấy ScheduleID sau khi insert
                ResultSet generatedKeys = scheduleStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int scheduleID = generatedKeys.getInt(1);
                    statement.setInt(4, scheduleID);
                }
            }

            // MenteeID và Status, tùy theo nhu cầu của bạn (chỉ ví dụ, có thể thay đổi)
            statement.setInt(5, 0);  // MenteeID: có thể không có ở thời điểm này
            statement.setString(6, "active");  // Trạng thái slot

            // Thực thi truy vấn
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting slot.", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void addSlotbyWeek(String link, float hour, String[] weekdays, int userID, int slot) throws SQLException {
        // Tạo kết nối cơ sở dữ liệu

        String sql = "INSERT INTO [dbo].[Slot] ([Link], [Time], [startAt], [ScheduleID], [MenteeID], [Status]) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Vòng lặp để thêm nhiều slot cho mỗi ngày trong tuần (weekdays)
            for (String day : weekdays) {
                // Xử lý ngày trong tuần và tạo thời gian startAt cho mỗi ngày
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_WEEK, getDayOfWeek(day)); // Chuyển từ chuỗi ngày sang ngày trong tuần

                // Thêm dữ liệu vào Slot
                statement.setString(1, link);
                statement.setFloat(2, hour);
                statement.setTimestamp(3, new Timestamp(calendar.getTimeInMillis()));

                // Lấy ScheduleID từ bảng Schedule
                String scheduleSQL = "INSERT INTO [dbo].[Schedule] ([MentorID], [Year], [Week], [Status]) VALUES (?, ?, ?, ?)";
                try (PreparedStatement scheduleStatement = connection.prepareStatement(scheduleSQL, Statement.RETURN_GENERATED_KEYS)) {
                    scheduleStatement.setInt(1, userID);
                    scheduleStatement.setInt(2, calendar.get(Calendar.YEAR));
                    scheduleStatement.setInt(3, calendar.get(Calendar.WEEK_OF_YEAR));
                    scheduleStatement.setString(4, "active"); // Ví dụ: trạng thái là active

                    // Lưu Schedule
                    scheduleStatement.executeUpdate();

                    // Lấy ScheduleID
                    ResultSet generatedKeys = scheduleStatement.getGeneratedKeys();
                    if (generatedKeys.next()) {
                        int scheduleID = generatedKeys.getInt(1);
                        statement.setInt(4, scheduleID);
                    }
                }

                // MenteeID và Status, tùy theo nhu cầu của bạn (chỉ ví dụ, có thể thay đổi)
                statement.setInt(5, 0);  // MenteeID: có thể không có ở thời điểm này
                statement.setString(6, "active");  // Trạng thái slot

                // Thực thi truy vấn
                statement.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new SQLException("Error while inserting slots.", e);
        } finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

// Phương thức giúp chuyển đổi tên ngày thành số ngày trong tuần (1 = Sunday, 2 = Monday, ..., 7 = Saturday)
    private int getDayOfWeek(String day) {
        switch (day.toLowerCase()) {
            case "sun":
                return Calendar.SUNDAY;
            case "mon":
                return Calendar.MONDAY;
            case "tue":
                return Calendar.TUESDAY;
            case "wen":
                return Calendar.WEDNESDAY;
            case "thu":
                return Calendar.THURSDAY;
            case "fri":
                return Calendar.FRIDAY;
            case "sat":
                return Calendar.SATURDAY;
            default:
                throw new IllegalArgumentException("Invalid day: " + day);
        }
    }

//    public static void confirmSchedule(int id) throws Exception {
//        Connection dbo = DatabaseUtil.getConn();
//        PreparedStatement ps = dbo.prepareStatement("UPDATE slots\n"
//                + "SET Status = 'Not Confirm'\n"
//                + "FROM slots a\n"
//                + "INNER JOIN schedule s ON a.ScheduleID = s.ScheduleID\n"
//                + "WHERE s.MentorID = ?");
//        ps.setInt(1, id);
//        ps.setInt(2, id);
//        ps.executeUpdate();
//        ps = dbo.prepareStatement("UPDATE [Slots] SET [Status] = N'Not Confirm' WHERE [SlotID] = ?");
//        ps.setInt(1, id);
//        ps.executeUpdate();
//        dbo.commit();
//        dbo.close();
//    }
    public void rejectSchedule(int id, String rejectReason) {
//        Connection dbo = null;
        PreparedStatement psSlotsUpdate = null;
        PreparedStatement psScheduleDelete = null;

        try {
//            dbo = DatabaseUtil.getConn();
            connection.setAutoCommit(false); // Start transaction

            // Step 1: Update status to 'Reject' in Slots table
            psSlotsUpdate = connection.prepareStatement("UPDATE SLOT\n"
                    + "SET [status] = N'Reject'\n"
                    + "WHERE ScheduleID IN (SELECT ScheduleID FROM Schedule WHERE MentorID = ? and Status = 'Pending')");
            psSlotsUpdate.setInt(1, id);
            psSlotsUpdate.executeUpdate();

            psScheduleDelete = connection.prepareStatement("Update Schedule set [status] = 'Reject', rejectReason=? where MentorID = ? and Status = 'Pending'");
            psScheduleDelete.setString(1, rejectReason);
            psScheduleDelete.setInt(2, id);

            psScheduleDelete.executeUpdate();

            // Commit the transaction
            connection.commit();
        } catch (SQLException e) {
            // Rollback in case of any SQL exception
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Close resources in finally block to ensure they are always closed
            try {
                if (psSlotsUpdate != null) {
                    psSlotsUpdate.close();
                }
                if (psScheduleDelete != null) {
                    psScheduleDelete.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    public void confirmSchedule(int id) {
//        Connection dbo = null;
        PreparedStatement psSchedule = null;
        PreparedStatement psSlots = null;

        try {
//            dbo = DatabaseUtil.getConn();

            // Step 1: Update Schedule table
            psSchedule = connection.prepareStatement("UPDATE S\n"
                    + "SET S.[status] = N'Active'\n"
                    + "FROM [Schedule] S\n"
                    + "JOIN [slot] sl ON S.[ScheduleID] = sl.[ScheduleID]\n"
                    + "WHERE S.[MentorID] = ? and S.Status = 'Pending' and sl.Status = 'Pending';");
            psSchedule.setInt(1, id);
            psSchedule.executeUpdate();

            // Step 2: Update Slots table
            psSlots = connection.prepareStatement("UPDATE S\n"
                    + "SET S.[status] = N'Not Confirm'\n"
                    + "FROM [SLOT] S\n"
                    + "JOIN [Schedule] sl ON S.[ScheduleID] = sl.[ScheduleID]\n"
                    + "WHERE sl.[MentorID] = ? and s.Status = 'Pending' and sl.Status = 'Active'");
            psSlots.setInt(1, id);
            psSlots.executeUpdate();

            // Commit the transaction
            connection.commit();
        } catch (Exception e) {
            // Rollback in case of any exception
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Close resources in finally block to ensure they are always closed
            try {
                if (psSchedule != null) {
                    psSchedule.close();
                }
                if (psSlots != null) {
                    psSlots.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    public void SendToAdminSchedule(int id) {
//        Connection dbo = null;
        PreparedStatement psSchedule = null;
        PreparedStatement psSlots = null;

        try {
            psSchedule = connection.prepareStatement("UPDATE S\n"
                    + "SET S.[status] = N'Pending'\n"
                    + "FROM [Schedule] S\n"
                    + "JOIN [slot] sl ON S.[ScheduleID] = sl.[ScheduleID]\n"
                    + "WHERE S.[ScheduleID] = ?;");
            psSchedule.setInt(1, id);
            psSchedule.executeUpdate();
            psSlots = connection.prepareStatement("UPDATE S\n"
                    + "SET S.[status] = N'Pending'\n"
                    + "FROM [SLOT] S\n"
                    + "JOIN [Schedule] sl ON S.[ScheduleID] = sl.[ScheduleID]\n"
                    + "WHERE sl.[ScheduleID] = ?;");
            psSlots.setInt(1, id);
            psSlots.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            // Rollback in case of any exception
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            // Close resources in finally block to ensure they are always closed
            try {
                if (psSchedule != null) {
                    psSchedule.close();
                }
                if (psSlots != null) {
                    psSlots.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    public void sendSlotToAdmin(int id) {
//        Connection dbo = null;
        PreparedStatement psSchedule = null;
        PreparedStatement psSlots = null;

        try {
//            dbo = DatabaseUtil.getConn();
            psSlots = connection.prepareStatement("UPDATE Slot\n"
                    + "SET [status] = N'Pending'\n"
                    + "WHERE [SlotID] = ?;");
            psSlots.setInt(1, id);
            psSlots.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
        } finally {
            try {
                if (psSchedule != null) {
                    psSchedule.close();
                }
                if (psSlots != null) {
                    psSlots.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException closeEx) {
                closeEx.printStackTrace();
            }
        }
    }

    public ArrayList<Schedule> getAllSchedulesWithPendingSlots() throws SQLException {
        ArrayList<Schedule> schedules = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT DISTINCT sch.ScheduleID, sch.MentorID, sch.Year, sch.Week, sch.Status "
                    + "FROM SCHEDULE sch "
                    + "JOIN SLOT s ON sch.ScheduleId = s.ScheduleId "
                    + "WHERE s.Status = 'Pending';";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                int scheduleID = rs.getInt("ScheduleID");
                int mentorID = rs.getInt("MentorID");
                int year = rs.getInt("Year");
                int week = rs.getInt("Week");
                String status = rs.getString("Status");
                Schedule schedule = new Schedule(scheduleID, mentorID, year, week, status);
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return schedules;
    }

    public Schedule getScheduleById(int mentorID) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT top 1 * "
                    + "FROM SCHEDULE sch "
                    + "WHERE sch.MentorID = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, mentorID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int scheduleID = rs.getInt("ScheduleID");
                int year = rs.getInt("Year");
                int week = rs.getInt("Week");
                String status = rs.getString("Status");
                String rejectReason = rs.getString("rejectReason");
                Schedule schedule = new Schedule(scheduleID, mentorID, year, week, status);
                schedule.setRejectReason(rejectReason);
                return schedule;
            }
        } catch (SQLException e) {
            System.out.println("Get schedule by id: " + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public Schedule getScheduleByIdAndSlot(int mentorID, int scheduleId) {
        ArrayList<Schedule> schedules = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "select Sch.* from Slot as S \n"
                    + "join Schedule as Sch on S.ScheduleID = Sch.ScheduleID\n"
                    + "where S.ScheduleID = ? and Sch.MentorId = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, scheduleId);
            ps.setInt(2, mentorID);
            rs = ps.executeQuery();
            if (rs.next()) {
                int scheduleID = rs.getInt("ScheduleID");
                int year = rs.getInt("Year");
                int week = rs.getInt("Week");
                String status = rs.getString("Status");
                String rejectReason = rs.getString("rejectReason");
                Schedule schedule = new Schedule(scheduleID, mentorID, year, week, status);
                schedule.setRejectReason(rejectReason);
                return schedule;
            }
        } catch (SQLException e) {
            System.out.println("Get schedule by id: " + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public int createSchedule(Schedule schedule) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = -1;

        try {
            String query = "INSERT INTO Schedule (MentorId, Year, Week, Status, rejectReason) VALUES (?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, schedule.getMentorID());
            ps.setInt(2, schedule.getYear());
            ps.setInt(3, schedule.getWeek());
            ps.setString(4, schedule.getStatus());
            ps.setString(5, schedule.getRejectReason());
            ps.executeUpdate();

            rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
            connection.commit();
        } catch (Exception e) {
            System.out.println("Create schedule: " + e);
        }
        return generatedId;
    }

    public ArrayList<Schedule> getAllSchedulesWithNewSlots(int mentorId) throws SQLException {
        ArrayList<Schedule> schedules = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT DISTINCT sch.rejectReason, sch.ScheduleID, sch.MentorID, sch.Year, sch.Week, sch.Status "
                    + "FROM SCHEDULE sch "
                    + "JOIN SLOT s ON sch.ScheduleId = s.ScheduleId "
                    + "WHERE (sch.Status = 'New' or sch.Status = 'Pending' Or sch.Status = 'Reject') and sch.MentorID = ?;";
            ps = connection.prepareStatement(query);
            ps.setInt(1, mentorId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int scheduleID = rs.getInt("ScheduleID");
                int mentorID = rs.getInt("MentorID");
                int year = rs.getInt("Year");
                int week = rs.getInt("Week");
                String status = rs.getString("Status");
                String rejectReason = rs.getString("rejectReason");
                Schedule schedule = new Schedule(scheduleID, mentorID, year, week, status);
                schedule.setRejectReason(rejectReason);
                schedules.add(schedule);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return schedules;
    }

    public ArrayList<User> getAllUserWithPendingSlots() throws SQLException {
        ArrayList<User> mentors = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            String query = "SELECT DISTINCT sch.MentorID, u.*"
                    + "FROM SCHEDULE sch "
                    + "JOIN SLOT s ON sch.ScheduleId = s.ScheduleId "
                    + "join [User] as U on U.UserID = sch.MentorID ";
            ps = connection.prepareStatement(query);
            resultSet = ps.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.userID = resultSet.getInt("UserID");
                user.username = resultSet.getString("username");
                user.password = resultSet.getString("password");
                user.email = resultSet.getString("email");
                user.phoneNumber = resultSet.getString("phoneNumber");
                user.address = resultSet.getString("address");
                user.roleID = resultSet.getInt("roleID");
                user.avatar = resultSet.getString("Avatar");
                user.fullname = resultSet.getString("fullname");
                user.dob = resultSet.getDate("dob");
                user.wallet = resultSet.getInt("wallet");
                user.activeStatus = resultSet.getBoolean("activeStatus");
                user.sex = resultSet.getBoolean("sex");
                user.isValidate = resultSet.getBoolean("isValidate");
                mentors.add(user);
            }
        } catch (SQLException e) {
            System.out.println("Get by mentor: " + e);
        }
        return mentors;
    }

    public ArrayList<Slot> getSlotsForScheduleById(int scheduleId) {
        ArrayList<Slot> slots = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT s.slotID, s.ScheduleID, s.Time, s.startAt, s.Link, s.Status, SC.* "
                    + "FROM SLOT s "
                    + "join Schedule as SC on Sc.ScheduleID = S.ScheduleID "
                    + "join Mentor as M on M.UserID = Sc.MentorID "
                    + "WHERE SC.ScheduleID = ?";

            ps = connection.prepareStatement(query);
            ps.setInt(1, scheduleId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int schedID = rs.getInt("ScheduleID");
                int slotId = rs.getInt("slotID");
                float time = rs.getFloat("Time");
                Timestamp startAt = rs.getTimestamp("startAt");
                String link = rs.getString("Link");
                String status = rs.getString("Status");
                String fullName = "";
                Slot slot = new Slot(schedID, startAt, time, link, fullName, status);
                slot.setId(slotId);
                slots.add(slot);
            }
        } catch (SQLException e) {
            System.out.println("Get by schedule: " + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return slots;
    }

    public ArrayList<Slot> getSlotsForSchedule(int mentorId) {
        ArrayList<Slot> slots = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT S.slotID, s.ScheduleID, s.Time, s.startAt, s.Link, s.Status, SC.* "
                    + "FROM SLOT s "
                    + "join Schedule as SC on Sc.ScheduleID = S.ScheduleID "
                    + "join Mentor as M on M.UserID = Sc.MentorID "
                    + "WHERE SC.MentorID = ? AND (SC.status = 'Pending' and s.status = 'Pending')";

            ps = connection.prepareStatement(query);
            ps.setInt(1, mentorId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int schedID = rs.getInt("ScheduleID");
                float time = rs.getFloat("Time");
                Timestamp startAt = rs.getTimestamp("startAt");
                String link = rs.getString("Link");
                String status = rs.getString("Status");
                int slotId = rs.getInt("slotID");
                String fullName = "";

                Slot slot = new Slot(schedID, startAt, time, link, fullName, status);
                slot.setId(slotId);
                slots.add(slot);
            }
        } catch (SQLException e) {
            System.out.println("Get by schedule: " + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return slots;
    }

    public ArrayList<Slot> getSlotsForCheck(int mentorId) {
        ArrayList<Slot> slots = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String query = "SELECT S.slotID, s.ScheduleID, s.Time, s.startAt, s.Link, s.Status, SC.* "
                    + "FROM SLOT s "
                    + "join Schedule as SC on Sc.ScheduleID = S.ScheduleID "
                    + "join Mentor as M on M.UserID = Sc.MentorID "
                    + "WHERE SC.MentorID = ? AND (SC.status = 'Pending' and s.status = 'Pending')";

            ps = connection.prepareStatement(query);
            ps.setInt(1, mentorId);
            rs = ps.executeQuery();

            while (rs.next()) {
                int schedID = rs.getInt("ScheduleID");
                float time = rs.getFloat("Time");
                Timestamp startAt = rs.getTimestamp("startAt");
                String link = rs.getString("Link");
                String status = rs.getString("Status");
                int slotId = rs.getInt("slotID");
                String fullName = "";

                Slot slot = new Slot(schedID, startAt, time, link, fullName, status);
                slot.setId(slotId);
                slots.add(slot);
            }
        } catch (SQLException e) {
            System.out.println("Get by schedule: " + e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return slots;
    }

// public static void main(String[] args) {
//        try {
////            // Test case 1: Mentor xác nhận slot (Status là "not confirm")
////            System.out.println("Test 1: Mentor xác nhận slot (Status là 'not confirm')");
////            boolean result1 = confirmSlot(1, 101, "mentor");
////            System.out.println("Result: " + (result1 ? "Success" : "Failure"));
//
//            // Test case 2: Mentee xác nhận slot (Status là "mentor confirm")
//            System.out.println("Test 2: Mentee xác nhận slot (Status là 'mentor confirm')");
//            boolean result2 = confirmSlot(3, 56, "mentee");
//            System.out.println("Result: " + (result2 ? "Success" : "Failure"));
//
//            
//           
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        
//
//}
//      public static void main(String[] args) {
//        int requestId = 1011;  // Sample RequestID to test
//        int percent = getPercentByRequest(requestId);
//        
//        System.out.println("Percentage of completed slots for RequestID " + requestId + ": " + percent + "%");
//    }
    public List<ScheduleView> getWeeklySchedule(int mentorID, int year, int week) {
        List<ScheduleView> list = new ArrayList<>();
        String sql = "SELECT s.SlotID, s.Time, s.startAt, s.Link, s.ScheduleID, s.SkillID, s.MenteeID, "
                + "s.Status as SlotStatus, sch.MentorID, sch.Year, sch.Week, sch.Status as ScheduleStatus, sch.rejectReason "
                + "FROM Slot s JOIN Schedule sch ON s.ScheduleID = sch.ScheduleID "
                + "WHERE sch.MentorID = ? AND sch.Year = ? AND sch.Week = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mentorID);
            ps.setInt(2, year);
            ps.setInt(3, week);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ScheduleView sv = new ScheduleView();
                sv.setSlotID(rs.getInt("SlotID"));
                sv.setTime(rs.getString("Time"));
                sv.setStartAt(rs.getDate("startAt").toLocalDate());
                sv.setLink(rs.getString("Link"));
                sv.setSkillID(rs.getInt("SkillID"));
                sv.setMenteeID(rs.getInt("MenteeID"));
                sv.setStatus(rs.getString("SlotStatus"));
                sv.setMentorID(rs.getInt("MentorID"));
                sv.setYear(rs.getInt("Year"));
                sv.setWeek(rs.getInt("Week"));
                sv.setScheduleStatus(rs.getString("ScheduleStatus"));
                sv.setRejectReason(rs.getString("rejectReason"));

                // Gán tên kỹ năng từ SkillDAO
                SkillDAO skillDAO = new SkillDAO();
                String skillName = skillDAO.getSkillById(rs.getInt("SkillID")).getName();
                sv.setSkillName(skillName);  // Thêm thuộc tính này vào ScheduleView

                // ✅ BỔ SUNG TẠI ĐÂY
                // 1. Gán date (dưới dạng yyyy-MM-dd)
                sv.setDate(rs.getDate("startAt").toLocalDate().toString());

                // 2. Gán slot (0–6) dựa theo Time
                String time = rs.getString("Time");
                int slot = switch (time) {
                    case "05:00" ->
                        0;
                    case "07:30" ->
                        1;
                    case "10:00" ->
                        2;
                    case "12:50" ->
                        3;
                    case "15:20" ->
                        4;
                    case "17:50" ->
                        5;
                    case "20:30" ->
                        6;
                    default ->
                        -1;
                };
                sv.setSlot(slot);

                list.add(sv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println(list.get(0));
        return list;
    }

    private int getSlotFromHour(Time time) {
        String hour = new SimpleDateFormat("HH:mm").format(time);
        return switch (hour) {
            case "05:00" ->
                0;
            case "07:30" ->
                1;
            case "10:00" ->
                2;
            case "12:50" ->
                3;
            case "15:20" ->
                4;
            case "17:50" ->
                5;
            case "20:30" ->
                6;
            default ->
                -1;
        };
    }

    // Cập nhật slot
    public void updateSlot(int slotID, String status, String link, String rejectReason) {
        String sql = """
            UPDATE Slot SET Status = ?, Link = ?, 
            ScheduleID = ScheduleID -- giữ nguyên
            WHERE SlotID = ?
        """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, link);
            ps.setInt(3, slotID);
            ps.executeUpdate();

            // Update rejectReason trong bảng Schedule nếu cần
            if (rejectReason != null && !rejectReason.isEmpty()) {
                String updateSchedule = """
                    UPDATE Schedule SET rejectReason = ?
                    WHERE ScheduleID = (SELECT ScheduleID FROM Slot WHERE SlotID = ?)
                """;
                PreparedStatement ps2 = connection.prepareStatement(updateSchedule);
                ps2.setString(1, rejectReason);
                ps2.setInt(2, slotID);
                ps2.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Xóa slot
    public void deleteSlotNew(int slotID) {
        String sql = "DELETE FROM Slot WHERE SlotID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, slotID);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Helper: Tạo schedule nếu chưa có
    private int getOrCreateSchedule(int mentorId, int year, int week) throws SQLException {
        String selectSQL = "SELECT ScheduleID FROM Schedule WHERE MentorID = ? AND Year = ? AND Week = ?";
        PreparedStatement selectStmt = connection.prepareStatement(selectSQL);
        selectStmt.setInt(1, mentorId);
        selectStmt.setInt(2, year);
        selectStmt.setInt(3, week);
        ResultSet rs = selectStmt.executeQuery();

        if (rs.next()) {
            return rs.getInt("ScheduleID");
        }

        // Nếu chưa có thì tạo mới
        String insertSQL = "INSERT INTO Schedule (MentorID, Year, Week, Status) VALUES (?, ?, ?, 'Pending')";
        PreparedStatement insertStmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
        insertStmt.setInt(1, mentorId);
        insertStmt.setInt(2, year);
        insertStmt.setInt(3, week);
        insertStmt.executeUpdate();

        ResultSet generatedKeys = insertStmt.getGeneratedKeys();
        if (generatedKeys.next()) {
            return generatedKeys.getInt(1);
        }

        throw new SQLException("Tạo Schedule thất bại.");
    }

    public List<Skill> getSkillsForMentor(int mentorID) {
        List<Skill> skills = new ArrayList<>();
        String sql = "SELECT s.SkillID, s.SkillName, s.enable, s.Description, s.image "
                + "FROM Mentor m "
                + "JOIN MentorSkills ms ON m.UserID = ms.MentorID "
                + "JOIN Skills s ON ms.SkillID = s.SkillID "
                + "WHERE m.UserID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mentorID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Skill skill = new Skill();
                skill.setId(rs.getInt("SkillID"));
                skill.setName(rs.getString("SkillName"));
                skill.setEnable(rs.getBoolean("enable"));
                skill.setDescription(rs.getString("Description"));
                skill.setAvatar(rs.getString("image"));  // map 'image' to 'Avatar'
                skills.add(skill);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return skills;
    }

    public boolean isSlotExist(int mentorId, Timestamp startAt) {
        String sql = """
        SELECT COUNT(*) FROM Slot s
        JOIN Schedule sch ON s.ScheduleID = sch.ScheduleID
        WHERE sch.MentorID = ? AND s.startAt = ?
    """;
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mentorId);
            ps.setTimestamp(2, startAt);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // nếu có slot => true
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
public void addSlot(int mentorId, Timestamp startAt, int skillID, String status, String link) {
        try {
            // Tìm week và year từ startAt
            Calendar cal = Calendar.getInstance();
            cal.setTime(startAt);
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            int year = cal.get(Calendar.YEAR);

            // Lấy ScheduleID (tạo mới nếu chưa có)
            int scheduleID = getOrCreateSchedule(mentorId, year, week);

            // ✅ Lấy phần giờ:phút (Time) từ startAt
            String timeString = new java.text.SimpleDateFormat("HH:mm").format(startAt);

            // Thêm vào Slot (có cả Time)
            String sql = """
            INSERT INTO Slot (Time, startAt, ScheduleID, SkillID, Status, Link)
            VALUES (?, ?, ?, ?, ?, ?)
        """;
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, timeString);              // Thêm vào Time
            ps.setTimestamp(2, startAt);              // startAt
            ps.setInt(3, scheduleID);                 // ScheduleID
            ps.setInt(4, skillID);                    // SkillID
            ps.setString(5, status);                  // Status
            ps.setString(6, link);  // ✅ mới thêm
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
