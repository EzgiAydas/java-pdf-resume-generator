
package com.cvgenerator.model;

public class Experience {
    private String company;
    private String role;
    private String period;
    private String description;

    public Experience(String company, String role, String period, String description) {
        this.company = company;
        this.role = role;
        this.period = period;
        this.description = description;
    }

    public String getCompany() { return company; }
    public String getRole() { return role; }
    public String getPeriod() { return period; }
    public String getDescription() { return description; }
}