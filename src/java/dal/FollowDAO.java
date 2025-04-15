package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Follow;
import model.FollowRequest;
import model.Mentee;
import model.Mentor;
import model.User;

public class FollowDAO extends DBContext {

    public ArrayList<Mentor> getFollowing(int uid) {
        ArrayList<Mentor> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] in (SELECT [MentorID] FROM [Follow] WHERE [MenteeID] = ?)");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Mentor m = new Mentor();
                m.setAvatar(rs.getString("Avatar"));
                m.setFullname(rs.getString("fullname"));
                m.setId(rs.getInt("UserID"));
                m.setAccount(rs.getString("username"));
                arr.add(m);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public ArrayList<Mentee> getFollower(int uid) {
        ArrayList<Mentee> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [User] WHERE [UserID] in (SELECT [MenteeID] FROM [Follow] WHERE [MentorID] = ?)");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Mentee m = new Mentee();
                m.setAvatar(rs.getString("Avatar"));
                m.setFullname(rs.getString("fullname"));
                m.setId(rs.getInt("UserID"));
                m.setAccount(rs.getString("username"));
                arr.add(m);
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public int following(int uid) {
        int following = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Count([FollowID]) as following FROM [Follow] WHERE [MenteeID] = ?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                following = rs.getInt("following");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return following;
    }

    public int follower(int uid) {
        int follower = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Count([FollowID]) as follower FROM [Follow] WHERE [MentorID] = ?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                follower = rs.getInt("follower");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return follower;
    }

    public int followRequest(int uid) {
        int requests = 0;
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT Count([RequestID]) as requests FROM [FollowRequest] WHERE [MentorID] = ?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                requests = rs.getInt("requests");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public void rejectFollowRequest(int uid, int rid) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM [FollowRequest] WHERE [MentorID] = ? AND [RequestID] = ?");
            ps.setInt(1, uid);
            ps.setInt(2, rid);
            ps.executeUpdate();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptFollowRequest(int uid, int rid) {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO [Follow] ([MentorID], [MenteeID]) VALUES (?, (SELECT [SenderID] FROM [FollowRequest] WHERE [RequestID] = ?))");
            ps.setInt(1, uid);
            ps.setInt(2, rid);
            ps.executeUpdate();
            ps = connection.prepareStatement("DELETE FROM [FollowRequest] WHERE [MentorID] = ? AND [RequestID] = ?");
            ps.setInt(1, uid);
            ps.setInt(2, rid);
            ps.executeUpdate();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelFollowRequest(int uid, int sid) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM [FollowRequest] WHERE [MentorID] = ? AND [SenderID] = ?");
            ps.setInt(1, uid);
            ps.setInt(2, sid);
            ps.executeUpdate();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unFollow(int uid, int sid) {
        try {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM [Follow] WHERE [MentorID] = ? AND [MenteeID] = ?");
            ps.setInt(1, uid);
            ps.setInt(2, sid);
            ps.executeUpdate();
            connection.commit();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<FollowRequest> getRequests(int uid) {
        ArrayList<FollowRequest> arr = new ArrayList();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT [RequestID],[RequestTime],[DeadLineTime],[Subject],\n"
                    + "[Content],[Status],[MentorID],[SenderID], (SELECT [fullname] \n"
                    + "FROM [User] WHERE [UserID] = [FollowRequest].[SenderID]) as [Sender],\n"
                    + "(SELECT [fullname]\n"
                    + "FROM [User] WHERE\n"
                    + "[UserID] = [FollowRequest].[MentorID]) as \n"
                    + "[Mentor] FROM [FollowRequest] WHERE [MentorID] = ?");
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                arr.add(new FollowRequest(rs.getInt("RequestID"), rs.getString("Subject"), rs.getString("Content"), rs.getInt("SenderID"), rs.getInt("MentorID"), rs.getString("Sender"), rs.getString("Mentor"), rs.getTimestamp("DeadLineTime"), rs.getTimestamp("RequestTime"), rs.getString("Status")));
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arr;
    }

    public boolean onPending(User u, int mentorId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [FollowRequest] WHERE [MentorID] = ? AND [SenderID] = ?");
            ps.setInt(1, mentorId);
            ps.setInt(2, u.getUserID());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                connection.close();
                return true; // Yêu cầu follow đang chờ xử lý
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean onFollow(User u, int mentorId) {
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM [Follow] WHERE [MentorID] = ? AND [MenteeID] = ?");
            ps.setInt(1, mentorId);
            ps.setInt(2, u.getUserID());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                connection.close();
                return true; // Mentee đã follow mentor này rồi
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void sendRequest(int mentorId, String title, String reason, int senderId) throws Exception {
        try {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO [FollowRequest] ([Subject], [Content], [Status], [MentorID], [SenderID]) VALUES (?, ?, N'Pending', ?, ?)");
            ps.setString(1, title);
            ps.setString(2, reason);
            ps.setInt(3, mentorId);
            ps.setInt(4, senderId);
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }

    public void addFollowRequest(FollowRequest followRequest) {
        String sql = "INSERT INTO FollowRequest (SenderID, MentorID, Subject, Content, RequestTime, Status) VALUES (?, ?, ?, ?, GETDATE(), ?)";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, followRequest.getSenderID());
            ps.setInt(2, followRequest.getMentorID());
            ps.setString(3, followRequest.getTitle());   // "Follow Request"
            ps.setString(4, followRequest.getContent()); // Lý do follow
            ps.setString(5, followRequest.getStatus());  // "Pending"
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Follow> getListFollowOfMentee(int menteeId) {
        List<Follow> list = new ArrayList<>();
        String sql = "select f.*, u.fullname, u.Avatar from Follow as f \n"
                + "join Mentor as m on f.MentorID = m.UserID\n"
                + "join [User] as u on m.UserID = u.UserID\n"
                + "where MenteeID = ?";
        try (Connection conn = connection; PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menteeId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Follow f = new Follow();
                f.setFollowId(rs.getInt("followId"));
                f.setFullname(rs.getString("fullname"));
                f.setMenteeId(menteeId);
                f.setMentorId(rs.getInt("mentorId"));
                f.setAvatar(rs.getString("avatar"));
                list.add(f);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertFollow(int menteeId, int mentorId) {
        if (!checkFollowerExist(menteeId, mentorId)) {
            String sql = "INSERT INTO Follow (menteeId, mentorId) VALUES ( ?, ?);";
            try {
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, menteeId);
                ps.setInt(2, mentorId);
                return ps.executeUpdate() > 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean checkFollowerExist(int menteeId, int mentorId) {
        String sql = "SELECT * FROM FOLLOW WHERE mentorId = ? and menteeId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, mentorId);
            ps.setInt(2, menteeId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean unfollowMentor(int followId) {
        String sql = "DELETE FROM FOLLOW WHERE FollowID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, followId);
            if (ps.executeUpdate() > 0) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        FollowDAO fd = new FollowDAO();
        fd.unfollowMentor(9);
    }
}