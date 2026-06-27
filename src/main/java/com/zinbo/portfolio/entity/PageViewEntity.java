package com.zinbo.portfolio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "page_views")
public class PageViewEntity {

    @Id
    private String section; // "total", "hero", "about", etc.

    private long count;

    public String getSection() { return section; }
    public void setSection(String v) { this.section = v; }
    public long getCount() { return count; }
    public void setCount(long v) { this.count = v; }
    public void increment() { this.count++; }
}
