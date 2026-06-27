package com.zinbo.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "hero")
public class HeroEntity {

    @Id
    private Long id = 1L;

    private String subhead;

    @Column(columnDefinition = "text")
    private String bio;

    private String photo;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getSubhead() { return subhead; }
    public void setSubhead(String subhead) { this.subhead = subhead; }
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }
}
