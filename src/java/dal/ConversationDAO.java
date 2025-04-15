
package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Conversation;
import model.User;

public class ConversationDAO extends DBContext {
    public int getConversationID(int menteeID, int mentorID) {
        String sql = "SELECT conversationID FROM Conversation WHERE MenteeID = ? AND MentorID = ?";
        try (Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, menteeID);
            ps.setInt(2, mentorID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("conversationID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1; // Trả về -1 nếu không có cuộc trò chuyện nào tồn tại
    }
     public List<Conversation> getConversationsByMentee(int menteeId) {
    List<Conversation> conversations = new ArrayList<>();
    String sql = "SELECT conversationID, MentorID FROM Conversation WHERE MenteeID = ?";

    try (Connection conn = connection;
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, menteeId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Conversation conversation = new Conversation();
            conversation.setConversationID(rs.getInt("conversationID"));
            conversation.setMentorID(rs.getInt("MentorID")); // Chỉ lưu mentorID

            conversations.add(conversation);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return conversations;
}
      public List<Conversation> getConversationsByUser(int userId) {
        List<Conversation> conversations = new ArrayList<>();
        String sql = "SELECT * FROM Conversation WHERE MentorID = ? OR MenteeID = ?";
        
        try (Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Conversation conversation = new Conversation(
                    rs.getInt("conversationID"),
                    rs.getInt("MentorID"),
                    rs.getInt("MenteeID")
                );
                conversations.add(conversation);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conversations;
    }
}
