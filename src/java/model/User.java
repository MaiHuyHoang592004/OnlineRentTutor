package model;

import java.util.Date;

public class User {
    public int userID;//
    public Boolean sex;
    public Boolean activeStatus;//
    public String username;
    public String password;
    public Date dob;
    public String email;
    public String phoneNumber;
    public int wallet;//
    public String address;
    public int roleID;//
    public Boolean isValidate;//
    public String avatar;//
    public String fullname;

    // Constructor không tham số
    public User() {}

    // Constructor đầy đủ tham số
    public User(int userID, Boolean sex, Boolean activeStatus, String username, String password,
                Date dob, String email, String phoneNumber, int wallet, String address, int roleID,
                Boolean isValidate, String avatar, String fullname) {
        this.userID = userID;
        this.sex = sex;
        this.activeStatus = activeStatus;
        this.username = username;
        this.password = password;
        this.dob = dob;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.wallet = wallet;
        this.address = address;
        this.roleID = roleID;
        this.isValidate = isValidate;
        this.avatar = avatar;
        this.fullname = fullname;
    }

    public User(int userID, Boolean sex, Date dob, String email, String phoneNumber, String address, String fullname) {
        this.userID = userID;
        this.sex = sex;
        this.dob = dob;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.fullname = fullname;
    }

    // Getter và Setter
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public Boolean getActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(Boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getWallet() {
        return wallet;
    }

    public void setWallet(int wallet) {
        this.wallet = wallet;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public Boolean getIsValidate() {
        return isValidate;
    }

    public void setIsValidate(Boolean isValidate) {
        this.isValidate = isValidate;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getRole() {
    switch (this.roleID) {
        case 4:
            return "Mentee";
        case 1:
            return "Admin";
        case 2:
            return "Manager";
        case 3:
            return "Mentor";
        default:
            return "Unknown Role";
    }
}


    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", sex=" + sex +
                ", activeStatus=" + activeStatus +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", wallet=" + wallet +
                ", address='" + address + '\'' +
                ", roleID=" + roleID +
                ", isValidate=" + isValidate +
                ", avatar='" + avatar + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
