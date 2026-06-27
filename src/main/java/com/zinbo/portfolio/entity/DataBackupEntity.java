package com.zinbo.portfolio.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "data_backups")
public class DataBackupEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String section;

    @Column(columnDefinition = "text")
    private String dataJson;

    private Instant createdAt;

    @PrePersist
    void prePersist() { this.createdAt = Instant.now(); }

    public Long getId() { return id; }
    public String getSection() { return section; }
    public void setSection(String v) { this.section = v; }
    public String getDataJson() { return dataJson; }
    public void setDataJson(String v) { this.dataJson = v; }
    public Instant getCreatedAt() { return createdAt; }
}
