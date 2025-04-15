package model;

import java.sql.Date;

/**
 * Model class that extends Transaction to include user information
 * for display in the transaction management page
 */
public class TransactionWithUser extends Transaction {
    private String userName;
    private int userID;
    
    public TransactionWithUser(int id, int uid, String userName, int balance, String type, 
                              float fee, String content, Date time, String status) {
        super(id, uid, balance, type, content, time, status);
        this.userName = userName;
        this.userID = uid;
        this.setFee(fee);
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public void setUserID(int userID) {
        this.userID = userID;
    }
}