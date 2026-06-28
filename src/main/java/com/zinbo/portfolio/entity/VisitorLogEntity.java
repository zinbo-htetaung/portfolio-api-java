package com.zinbo.portfolio.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "visitor_logs")
public class VisitorLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String country;
    private String countryCode;
    private String city;

    private Instant visitedAt;

    @PrePersist
    void prePersist() { this.visitedAt = Instant.now(); }

    public Long getId() { return id; }
    public String getCountry() { return country; }
    public void setCountry(String v) { this.country = v; }
    public String getCountryCode() { return countryCode; }
    public void setCountryCode(String v) { this.countryCode = v; }
    public String getCity() { return city; }
    public void setCity(String v) { this.city = v; }
    public Instant getVisitedAt() { return visitedAt; }
}
