/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author ACER
 */
public class ScheduleView {

    private int slotID;
    private String time;
    private LocalDate startAt;
    private String link;
    private int scheduleID;
    private int skillID;
    private int menteeID;
    private int mentorID;
    private int year;
    private int week;
    private String status; // hoặc slotStatus
    private String scheduleStatus;
    private String rejectReason;
    private String skillName;

    // Thêm cho lịch hiển thị
    private int slot;
    private String date;
    
    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getSkillName() {
        return skillName;
    }

    // Getter/setter cho tất cả:
    public void setSkillName(String skillName) {    
        this.skillName = skillName;
    }

    public int getSlotID() {
        return slotID;
    }

    public void setSlotID(int slotID) {
        this.slotID = slotID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public LocalDate getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDate startAt) {
        this.startAt = startAt;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getSkillID() {
        return skillID;
    }

    public void setSkillID(int skillID) {
        this.skillID = skillID;
    }

    public int getMenteeID() {
        return menteeID;
    }

    public void setMenteeID(int menteeID) {
        this.menteeID = menteeID;
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
    } // Hoặc getSlotStatus() nếu bạn dùng tên khác

    public void setStatus(String status) {
        this.status = status;
    }

    public String getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(String scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ScheduleView{");
        sb.append("slotID=").append(slotID);
        sb.append(", time=").append(time);
        sb.append(", startAt=").append(startAt);
        sb.append(", link=").append(link);
        sb.append(", skillID=").append(skillID);
        sb.append(", menteeID=").append(menteeID);
        sb.append(", slotStatus=").append(status);
        sb.append(", mentorID=").append(mentorID);
        sb.append(", year=").append(year);
        sb.append(", week=").append(week);
        sb.append(", scheduleStatus=").append(scheduleStatus);
        sb.append(", rejectReason=").append(rejectReason);
        sb.append(", slot=").append(slot);
        sb.append(", date=").append(date);
        sb.append('}');
        return sb.toString();
    }

}
