package model;

public class Skill {
    int id;
    String name;
    boolean isEnable;
    String Avatar, Description;

    public Skill(int id, String name, boolean enable, String Avatar, String Description) {
        this.id = id;
        this.name = name;
        this.isEnable = enable;
        this.Avatar = Avatar;
        this.Description = Description;
    }
    
    public Skill (){}

    public String getAvatar() {
        return Avatar;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
