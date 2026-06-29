package com.zinbo.portfolio.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "visitor_logs")
public class VisitorLogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Geo (from IPinfo)
    private String country;
    private String countryCode;
    private String city;
    private String isp;

    // Device (from User-Agent)
    private String browser;
    private String os;
    private String device;

    // Request context
    private String referrer;
    private String language;
    private String screen;
    private String timezone;
    private Boolean darkMode;

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
    public String getIsp() { return isp; }
    public void setIsp(String v) { this.isp = v; }
    public String getBrowser() { return browser; }
    public void setBrowser(String v) { this.browser = v; }
    public String getOs() { return os; }
    public void setOs(String v) { this.os = v; }
    public String getDevice() { return device; }
    public void setDevice(String v) { this.device = v; }
    public String getReferrer() { return referrer; }
    public void setReferrer(String v) { this.referrer = v; }
    public String getLanguage() { return language; }
    public void setLanguage(String v) { this.language = v; }
    public String getScreen() { return screen; }
    public void setScreen(String v) { this.screen = v; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String v) { this.timezone = v; }
    public Boolean getDarkMode() { return darkMode; }
    public void setDarkMode(Boolean v) { this.darkMode = v; }
    public Instant getVisitedAt() { return visitedAt; }
}
