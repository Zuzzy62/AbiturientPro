package com.example.abiturientpro.data.models;

public class User {
    private String id;
    private String login;
    private String password;
    private String fullName;
    private int age;
    private String role; // "applicant", "parent", "admin"
    private String parentId;

    public User(String id, String login, String password, String fullName, int age, String role, String parentId) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.role = role;
        this.parentId = parentId;
    }

    public String getId() { return id; }
    public String getLogin() { return login; }
    public String getPassword() { return password; }
    public String getFullName() { return fullName; }
    public int getAge() { return age; }
    public String getRole() { return role; }
    public String getParentId() { return parentId; }

    public boolean isMinor() {
        return age < 18;
    }

    public void setParentId(String parentId) { this.parentId = parentId; }
}