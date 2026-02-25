package com.example.abiturientpro.data.repository;

import com.example.abiturientpro.data.models.Application;
import com.example.abiturientpro.data.models.Specialty;
import com.example.abiturientpro.data.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MockRepository {
    private static MockRepository instance;
    private Map<String, User> users = new HashMap<>();
    private Map<String, Application> applications = new HashMap<>();

    private MockRepository() {
        initUsers();
        initApplications();
    }

    public static synchronized MockRepository getInstance() {
        if (instance == null) {
            instance = new MockRepository();
        }
        return instance;
    }

    private void initUsers() {
        users.put("admin", new User("admin1", "admin", "1", "Администратор", 30, "admin", null));
        users.put("parent", new User("parent1", "parent", "1", "Иванова Мария Петровна", 40, "parent", null));
        users.put("ivanov", new User("applicant1", "ivanov", "1", "Иванов Иван Иванович", 19, "applicant", null));
        User minor = new User("applicant2", "petrov", "1", "Петров Петр Петрович", 17, "applicant", "parent1");
        users.put("petrov", minor);
    }

    private void initApplications() {
        applications.put("app1", new Application("app1", "applicant1", "09.02.07", "Информационные системы и программирование"));
        Application app2 = new Application("app2", "applicant2", "38.02.01", "Экономика и бухгалтерский учет (по отраслям)");
        app2.setConfirmedByParent(true);
        applications.put("app2", app2);
    }

    public User getUserByLogin(String login) {
        return users.get(login);
    }

    public User getUserById(String id) {
        for (User u : users.values()) {
            if (u.getId().equals(id)) return u;
        }
        return null;
    }

    public List<Application> getApplicationsByApplicantId(String applicantId) {
        List<Application> result = new ArrayList<>();
        for (Application a : applications.values()) {
            if (a.getApplicantId().equals(applicantId)) result.add(a);
        }
        return result;
    }

    public List<Application> getAllApplications() {
        return new ArrayList<>(applications.values());
    }

    public List<Application> getApplicationsBySpecialty(String specialtyCode) {
        List<Application> result = new ArrayList<>();
        for (Application a : applications.values()) {
            if (a.getSpecialtyCode().equals(specialtyCode)) result.add(a);
        }
        return result;
    }

    public void addApplication(Application app) {
        applications.put(app.getId(), app);
    }

    public Application getApplicationById(String id) {
        return applications.get(id);
    }

    public void updateApplication(Application app) {
        applications.put(app.getId(), app);
    }

    public List<User> getChildrenByParentId(String parentId) {
        List<User> children = new ArrayList<>();
        for (User u : users.values()) {
            if ("applicant".equals(u.getRole()) && parentId.equals(u.getParentId())) {
                children.add(u);
            }
        }
        return children;
    }
}