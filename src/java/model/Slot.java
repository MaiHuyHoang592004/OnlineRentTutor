package model;

import java.sql.Timestamp;


public class Slot {

    int id;
    Timestamp SlotTime;
    float hour;
    String link;
    String skill;
    String mentee, mentor, Status;
    int menteeId, mentorId;

    public Slot(int id, Timestamp SlotTime, float hour, String link, String mentor, int mentorId) {
        this.id = id;
        this.SlotTime = SlotTime;
        this.hour = hour;
        this.link = link;
        this.mentor = mentor;
        this.mentorId = mentorId;
    }

    public Slot(int id, Timestamp SlotTime, float hour, String link, String mentor, String Status) {
        this.id = id;
        this.SlotTime = SlotTime;
        this.hour = hour;
        this.link = link;
        this.mentor = mentor;
        this.Status = Status;
    }

    public Slot(int id, Timestamp SlotTime, float hour, String link, String skill, String mentee, String mentor, String Status, int menteeId, int mentorId) {
        this.id = id;
        this.SlotTime = SlotTime;
        this.hour = hour;
        this.link = link;
        this.skill = skill;
        this.mentee = mentee;
        this.mentor = mentor;
        this.Status = Status;
        this.menteeId = menteeId;
        this.mentorId = mentorId;
    }
    
    

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getSlotTime() {
        return SlotTime;
    }

    public void setSlotTime(Timestamp SlotTime) {
        this.SlotTime = SlotTime;
    }

    public float getHour() {
        return hour;
    }

    public void setHour(float hour) {
        this.hour = hour;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSkill() {
        return skill;
    }

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public String getMentee() {
        return mentee;
    }

    public void setMentee(String mentee) {
        this.mentee = mentee;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public int getMenteeId() {
        return menteeId;
    }

    public void setMenteeId(int menteeId) {
        this.menteeId = menteeId;
    }

    public int getMentorId() {
        return mentorId;
    }

    public void setMentorId(int mentorId) {
        this.mentorId = mentorId;
    }
    
    

    @Override
    public String toString() {
        return "Slot{" + "id=" + id + ", SlotTime=" + SlotTime + ", hour=" + hour + ", link=" + link + ", skill=" + skill + ", mentee=" + mentee + ", mentor=" + mentor + ", Status=" + Status + ", menteeId=" + menteeId + ", mentorId=" + mentorId + '}';
    }

}
