package model;

import java.sql.Date;
import java.sql.Timestamp;


public class Transaction {
    int id;
    int uid;
    int balance;
    String type;
    float fee;
    String content;
    Date time;
    String status;

    public Transaction(int id, int uid, int balance, String type, String content, Date time, String status) {
        this.id = id;
        this.uid = uid;
        this.balance = balance;
        this.type = type;
        this.content = content;
        this.time = time;
        this.status = status;
    }
    
    public Transaction() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }
    
    
}
