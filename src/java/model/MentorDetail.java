/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author zzz
 */
public class MentorDetail {
    private int mentorID;
    private int totalRequests;
    private int acceptedRequests;
    private int completedRequests;
    private float rating;
    private String account;
    private String profession;
    private boolean active;

    public MentorDetail(int mentorID, float rating, int acceptedRequests, int completedRequests, String account, String profession, boolean active) {
        this.mentorID = mentorID;
        this.rating = rating;
        this.acceptedRequests = acceptedRequests;
        this.completedRequests = completedRequests;
        this.account = account;
        this.profession = profession;
        this.active = active;
    }

    public int getMentorID() {
        return mentorID;
    }

    public float getRate() { // Thêm phương thức này để tránh lỗi trong JSP
        return rating;
    }

    public int getRequests() {
        return totalRequests;
    }

    public float getCompletePercent() { // Thêm phương thức này để tránh lỗi trong JSP
        return (acceptedRequests > 0) ? ((float) completedRequests / acceptedRequests) * 100 : 0;
    }

    public String getAccount() {
        return account;
    }

    public boolean isActive() {
        return active;
    }

    public String getProfession() {
        return profession;
    }

    public void setRequests(int totalRequests) {
        this.totalRequests = totalRequests;
    }
}

