package com.example.abiturientpro.data.models;

public class Application {
    private String id;
    private String applicantId;
    private String specialtyCode;
    private String specialtyName;
    private boolean confirmedByParent;
    private boolean confirmedByAdmin;

    public Application(String id, String applicantId, String specialtyCode, String specialtyName) {
        this.id = id;
        this.applicantId = applicantId;
        this.specialtyCode = specialtyCode;
        this.specialtyName = specialtyName;
        this.confirmedByParent = false;
        this.confirmedByAdmin = false;
    }

    public String getId() { return id; }
    public String getApplicantId() { return applicantId; }
    public String getSpecialtyCode() { return specialtyCode; }
    public String getSpecialtyName() { return specialtyName; }
    public boolean isConfirmedByParent() { return confirmedByParent; }
    public boolean isConfirmedByAdmin() { return confirmedByAdmin; }
    public void setConfirmedByParent(boolean confirmedByParent) { this.confirmedByParent = confirmedByParent; }
    public void setConfirmedByAdmin(boolean confirmedByAdmin) { this.confirmedByAdmin = confirmedByAdmin; }

    public String getStatus() {
        if (confirmedByAdmin) return "Зачислен";
        if (confirmedByParent) return "Ожидает подтверждения администратора";
        return "Ожидает подтверждения родителя";
    }
}