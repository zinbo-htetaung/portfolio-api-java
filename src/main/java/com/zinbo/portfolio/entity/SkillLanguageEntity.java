package com.zinbo.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "skill_languages")
public class SkillLanguageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lang;
    private String level;

    @Column(name = "display_order")
    private int displayOrder;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLang() { return lang; }
    public void setLang(String v) { this.lang = v; }
    public String getLevel() { return level; }
    public void setLevel(String v) { this.level = v; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int v) { this.displayOrder = v; }
}
