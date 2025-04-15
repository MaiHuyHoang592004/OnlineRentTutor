/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package model;

/**
 *
 * @author HP
 */
public class Schedule {
    
    private int scheduleID;
    private int mentorID;
    private int year;
    private int week;
    private String status;
    private String rejectReason;

    public Schedule(int scheduleID, int mentorID, int year, int week, String status) {
        this.scheduleID = scheduleID;
        this.mentorID = mentorID;
        this.year = year;
        this.week = week;
        this.status = status;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }
    
    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Schedule{");
        sb.append("scheduleID=").append(scheduleID);
        sb.append(", mentorID=").append(mentorID);
        sb.append(", year=").append(year);
        sb.append(", week=").append(week);
        sb.append(", status=").append(status);
        sb.append(", rejectReason=").append(rejectReason);
        sb.append('}');
        return sb.toString();
    }
    
    
}

