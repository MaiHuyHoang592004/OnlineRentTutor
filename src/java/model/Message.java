/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.sql.Timestamp;

/**
 *
 * @author zzz
 */
public class Message {
    private int messageID;
    private int conversationID;
    private int senderID;
    private Timestamp sentAt;
    private String content;

    public Message() {}

    public Message(int messageID, int conversationID, int senderID, Timestamp sentAt, String content) {
        this.messageID = messageID;
        this.conversationID = conversationID;
        this.senderID = senderID;
        this.sentAt = sentAt;
        this.content = content;
    }

    public int getMessageID() { return messageID; }
    public void setMessageID(int messageID) { this.messageID = messageID; }

    public int getConversationID() { return conversationID; }
    public void setConversationID(int conversationID) { this.conversationID = conversationID; }

    public int getSenderID() { return senderID; }
    public void setSenderID(int senderID) { this.senderID = senderID; }

    public Timestamp getSentAt() { return sentAt; }
    public void setSentAt(Timestamp sentAt) { this.sentAt = sentAt; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}