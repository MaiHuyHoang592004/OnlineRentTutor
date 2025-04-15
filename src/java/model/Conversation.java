package model;

public class Conversation {
    private int conversationID;
    private int mentorID;
    private int menteeID;
    

    // Constructor không tham số
    public Conversation() {}

    // Constructor có tham số
    public Conversation(int conversationID, int mentorID, int menteeID) {
        this.conversationID = conversationID;
        this.mentorID = mentorID;
        this.menteeID = menteeID;
    }

    // Getter và Setter
    public int getConversationID() {
        return conversationID;
    }

    public void setConversationID(int conversationID) {
        this.conversationID = conversationID;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public int getMenteeID() {
        return menteeID;
    }

    public void setMenteeID(int menteeID) {
        this.menteeID = menteeID;
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "conversationID=" + conversationID +
                ", mentorID=" + mentorID +
                ", menteeID=" + menteeID +
                '}';
    }
}
