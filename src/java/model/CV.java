package model;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CV {

    int id;
    String ProfessionIntroduction;
    String ServiceDescription;
    int CashPerSlot;
    String rejectReason;
    ArrayList<Skill> skills = new ArrayList();


    public CV(int id, String ProfessionIntroduction, String ServiceDescription, int CashPerSlot) {
        this.id = id;
        this.ProfessionIntroduction = ProfessionIntroduction;
        this.ServiceDescription = ServiceDescription;
        this.CashPerSlot = CashPerSlot;
    }

    public String getRejectReason() {
        return rejectReason;
    }

    public void setRejectReason(String rejectReason) {
        this.rejectReason = rejectReason;
    }

    public CV(int id, String ProfessionIntroduction, String ServiceDescription, String rejectReason) {
        this.id = id;
        this.ProfessionIntroduction = ProfessionIntroduction;
        this.ServiceDescription = ServiceDescription;
        this.rejectReason = rejectReason;
    }

    public CV(int id, String ProfessionIntroduction, String ServiceDescription) {
        this.id = id;
        this.ProfessionIntroduction = ProfessionIntroduction;
        this.ServiceDescription = ServiceDescription;
    }
    
    

    

    public String MoneyFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        return decimalFormat.format(CashPerSlot);
    }

    public int getCashPerSlot() {
        return CashPerSlot;
    }

    public void setCashPerSlot(int CashPerSlot) {
        this.CashPerSlot = CashPerSlot;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }

    public String getSkillString() {
        String ret = "";
        for (int i = 0; i < skills.size(); i++) {
            ret += skills.get(i).getName();
            if (i != skills.size() - 1) {
                ret += ", ";
            }
        }
        return ret;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProfessionIntroduction() {
        return ProfessionIntroduction;
    }

    public void setProfessionIntroduction(String ProfessionIntroduction) {
        this.ProfessionIntroduction = ProfessionIntroduction;
    }

    public String getServiceDescription() {
        return ServiceDescription;
    }

    public void setServiceDescription(String Description) {
        this.ServiceDescription = ServiceDescription;
    }

}
