package model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Request {

    int id, sendID, userID;
    String mentor;
    String send;
    String reason, status, subject;
    String rejectReason;
    Timestamp DeadlineTime;
    Timestamp requestTime;
    private int totalRequest;
    Timestamp startdate;
    Timestamp enddate;
    boolean rated = false;
    private String mentorName;
    private String skillName;
    private String menteeName;
   
    public ArrayList<Skill> skills = new ArrayList();

    public Request(int id, int sendID, int userID, String mentor, String send, String reason, String status, String subject, String rejectReason, Timestamp DeadlineTime, Timestamp requestTime, int totalRequest, Timestamp startdate, Timestamp enddate, String mentorName, String skillName) {
        this.id = id;
        this.sendID = sendID;
        this.userID = userID;
        this.mentor = mentor;
        this.send = send;
        this.reason = reason;
        this.status = status;
        this.subject = subject;
        this.rejectReason = rejectReason;
        this.DeadlineTime = DeadlineTime;
        this.requestTime = requestTime;
        this.totalRequest = totalRequest;
        this.startdate = startdate;
        this.enddate = enddate;
        this.mentorName = mentorName;
        this.skillName = skillName;
    }

    public Request() {
    }

    public Request(int id, int sendID, int userID, String reason, String status, String subject, Timestamp requestTime, Timestamp DeadlineTime) {
        this.id = id;
        this.sendID = sendID;
        this.userID = userID;
        this.reason = reason;
        this.status = status;
        this.subject = subject;
        this.requestTime = requestTime;
        this.DeadlineTime = DeadlineTime;
    }

    public String getMenteeName() {
        return menteeName;
    }

    public void setMenteeName(String menteeName) {
        this.menteeName = menteeName;
    }
    
    public Timestamp getStartdate() {
        return startdate;
    }

    public void setStartdate(Timestamp startdate) {
        this.startdate = startdate;
    }

    public Timestamp getEnddate() {
        return enddate;
    }

    public void setEnddate(Timestamp enddate) {
        this.enddate = enddate;
    }

    public boolean isRated() {
        return rated;
    }

    public void setRated(boolean rated) {
        this.rated = rated;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public String getSend() {
        return send;
    }

    public void setSend(String send) {
        this.send = send;
    }

    public String getSkillsName() {
        if (skills.isEmpty()) {
            return "Không có kỹ năng";
        }
        StringBuilder name = new StringBuilder();
        for (int i = 0; i < skills.size(); i++) {
            name.append(skills.get(i).getName());
            if (i < skills.size() - 1) {
                name.append(", ");
            }
        }
        return name.toString();
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public String getMentor() {
        return mentor;
    }

    public void setMentor(String mentor) {
        this.mentor = mentor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSendID() {
        return sendID;
    }

    public void setSendID(int sendID) {
        this.sendID = sendID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Timestamp getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Timestamp requestTime) {
        this.requestTime = requestTime;
    }

    public Timestamp getDeadlineTime() {
        return DeadlineTime;
    }

    public void setDeadlineTime(Timestamp DeadlineTime) {
        this.DeadlineTime = DeadlineTime;
    }

    @Override
    public String toString() {
        return "Request{" + "id=" + id + ", sendID=" + sendID + ", userID=" + userID + ", mentor=" + mentor + ", send=" + send + ", reason=" + reason + ", status=" + status + ", subject=" + subject + ", rejectReason=" + rejectReason + ", DeadlineTime=" + DeadlineTime + ", requestTime=" + requestTime + ", rated=" + rated + ", skills=" + skills + '}';
    }

    public int getTotalRequest() {
        return totalRequest;
    }

    public void setTotalRequest(int totalRequest) {
        this.totalRequest = totalRequest;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
    
    

}