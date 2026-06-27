package com.zinbo.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "projects")
public class ProjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String year;

    @Column(columnDefinition = "text")
    private String tagsJson;

    @Column(columnDefinition = "text")
    private String bulletsJson;

    @Column(name = "display_order")
    private int displayOrder;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String v) { this.name = v; }
    public String getYear() { return year; }
    public void setYear(String v) { this.year = v; }
    public String getTagsJson() { return tagsJson; }
    public void setTagsJson(String v) { this.tagsJson = v; }
    public String getBulletsJson() { return bulletsJson; }
    public void setBulletsJson(String v) { this.bulletsJson = v; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int v) { this.displayOrder = v; }
}
