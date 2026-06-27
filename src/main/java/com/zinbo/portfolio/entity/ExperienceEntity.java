package com.zinbo.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "experiences")
public class ExperienceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String role;
    private String company;
    private String type;
    private String period;

    @Column(columnDefinition = "text")
    private String bulletsJson;

    private String linkLabel;
    private String linkUrl;

    @Column(name = "display_order")
    private int displayOrder;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getRole() { return role; }
    public void setRole(String v) { this.role = v; }
    public String getCompany() { return company; }
    public void setCompany(String v) { this.company = v; }
    public String getType() { return type; }
    public void setType(String v) { this.type = v; }
    public String getPeriod() { return period; }
    public void setPeriod(String v) { this.period = v; }
    public String getBulletsJson() { return bulletsJson; }
    public void setBulletsJson(String v) { this.bulletsJson = v; }
    public String getLinkLabel() { return linkLabel; }
    public void setLinkLabel(String v) { this.linkLabel = v; }
    public String getLinkUrl() { return linkUrl; }
    public void setLinkUrl(String v) { this.linkUrl = v; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int v) { this.displayOrder = v; }
}
