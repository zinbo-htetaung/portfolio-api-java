package com.zinbo.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "education")
public class EducationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String institution;
    private String degree;
    private String period;

    @Column(columnDefinition = "text")
    private String courseworkJson;

    @Column(name = "display_order")
    private int displayOrder;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getInstitution() { return institution; }
    public void setInstitution(String v) { this.institution = v; }
    public String getDegree() { return degree; }
    public void setDegree(String v) { this.degree = v; }
    public String getPeriod() { return period; }
    public void setPeriod(String v) { this.period = v; }
    public String getCourseworkJson() { return courseworkJson; }
    public void setCourseworkJson(String v) { this.courseworkJson = v; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int v) { this.displayOrder = v; }
}
