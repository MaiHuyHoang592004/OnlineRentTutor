package model;

public class MentorSkill {
    private Integer skillId;
    private Integer mentorId; // Thay vì Mentor, chỉ lưu mentorId

    public MentorSkill() {
    }

    public MentorSkill(Integer skillId, Integer mentorId) {
        this.skillId = skillId;
        this.mentorId = mentorId;
    }

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public Integer getMentorId() {
        return mentorId;
    }

    public void setMentorId(Integer mentorId) {
        this.mentorId = mentorId;
    }
}
