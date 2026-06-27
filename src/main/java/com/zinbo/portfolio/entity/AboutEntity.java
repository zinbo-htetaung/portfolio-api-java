package com.zinbo.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "about")
public class AboutEntity {

    @Id
    private Long id = 1L;

    @Column(columnDefinition = "text")
    private String bio;

    @Column(columnDefinition = "text")
    private String highlightsJson;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getHighlightsJson() { return highlightsJson; }
    public void setHighlightsJson(String highlightsJson) { this.highlightsJson = highlightsJson; }
}
