package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Message;

public class MessageDAO extends DBContext {

    public void saveMessage(Message message) {
        String sql = "INSERT INTO Message (conversationID, SenderID, sentAt, msgContent) VALUES (?, ?, GETDATE(), ?)";
        try (Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, message.getConversationID());
            ps.setInt(2, message.getSenderID());
            ps.setString(3, message.getContent());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessagesByConversation(int conversationID) {
        List<Message> messages = new ArrayList<>();
        String sql = "SELECT * FROM Message WHERE conversationID = ? ORDER BY sentAt ASC";
        try (Connection conn = connection;
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, conversationID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                messages.add(new Message(
                    rs.getInt("MessageID"),
                    rs.getInt("conversationID"),
                    rs.getInt("SenderID"),
                    rs.getTimestamp("sentAt"),
                    rs.getString("msgContent")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return messages;
    }
}
