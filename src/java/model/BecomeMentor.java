package model;

import java.sql.Date;

public class BecomeMentor {
    private int mentorID; // MentorID sẽ là UserID
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private boolean sex; // true cho Male, false cho Female
    private Date dob;
    private String skills; // Danh sách các SkillID (cách nhau bằng dấu phẩy)
    private String professionIntroduction;
    private String serviceDescription;
    private int cashPerSlot;

    // Constructor
    public BecomeMentor(int mentorID, String fullName, String phoneNumber, String email, 
                         String address, boolean sex, Date dob, String skills, 
                         String professionIntroduction, String serviceDescription, 
                         int cashPerSlot) {
        this.mentorID = mentorID;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.address = address;
        this.sex = sex;
        this.dob = dob;
        this.skills = skills;
        this.professionIntroduction = professionIntroduction;
        this.serviceDescription = serviceDescription;
        this.cashPerSlot = cashPerSlot;
    }

    // Getters and Setters
    public int getMentorID() {
        return mentorID;
    }

    public void setMentorID(int mentorID) {
        this.mentorID = mentorID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getProfessionIntroduction() {
        return professionIntroduction;
    }

    public void setProfessionIntroduction(String professionIntroduction) {
        this.professionIntroduction = professionIntroduction;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }

    public int getCashPerSlot() {
        return cashPerSlot;
    }

    public void setCashPerSlot(int cashPerSlot) {
        this.cashPerSlot = cashPerSlot;
    }

    // Optional: Override toString() method for easy printing
    @Override
    public String toString() {
        return "BecomeMentor{" +
                "mentorID=" + mentorID +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", sex=" + sex +
                ", dob=" + dob +
                ", skills='" + skills + '\'' +
                ", professionIntroduction='" + professionIntroduction + '\'' +
                ", serviceDescription='" + serviceDescription + '\'' +
                ", cashPerSlot=" + cashPerSlot +
                '}';
    }
}
