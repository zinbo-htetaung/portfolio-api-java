package com.zinbo.portfolio.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinbo.portfolio.entity.*;
import com.zinbo.portfolio.repository.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PortfolioService {

    private final ProfileRepository  profileRepo;
    private final HeroRepository     heroRepo;
    private final AboutRepository    aboutRepo;
    private final EducationRepository  eduRepo;
    private final ExperienceRepository expRepo;
    private final ProjectRepository  projRepo;
    private final SkillPillRepository  pillRepo;
    private final SkillLanguageRepository langRepo;
    private final ObjectMapper mapper = new ObjectMapper();

    public PortfolioService(ProfileRepository profileRepo, HeroRepository heroRepo,
                            AboutRepository aboutRepo, EducationRepository eduRepo,
                            ExperienceRepository expRepo, ProjectRepository projRepo,
                            SkillPillRepository pillRepo, SkillLanguageRepository langRepo) {
        this.profileRepo = profileRepo;
        this.heroRepo    = heroRepo;
        this.aboutRepo   = aboutRepo;
        this.eduRepo     = eduRepo;
        this.expRepo     = expRepo;
        this.projRepo    = projRepo;
        this.pillRepo    = pillRepo;
        this.langRepo    = langRepo;
    }

    // ── Public read methods ────────────────────────────────────────

    public Map<String, Object> getProfile() {
        ProfileEntity e = profileRepo.findById(1L).orElseThrow();
        var links = new LinkedHashMap<String, Object>();
        links.put("github",    e.getGithubUrl());
        links.put("linkedin",  e.getLinkedinUrl());
        links.put("portfolio", e.getPortfolioUrl());
        links.put("resume",    e.getResumeUrl());
        var m = new LinkedHashMap<String, Object>();
        m.put("name",        e.getName());
        m.put("location",    e.getLocation());
        m.put("tagline",     e.getTagline());
        m.put("email",       e.getEmail());
        m.put("phonenumber", e.getPhonenumber());
        m.put("links",       links);
        return m;
    }

    public Map<String, Object> getHero() {
        HeroEntity e = heroRepo.findById(1L).orElseThrow();
        return Map.of("subhead", e.getSubhead(), "bio", e.getBio(), "photo", e.getPhoto());
    }

    public Map<String, Object> getAbout() {
        AboutEntity e = aboutRepo.findById(1L).orElseThrow();
        return Map.of("bio", e.getBio(), "highlights", fromJson(e.getHighlightsJson()));
    }

    public List<Map<String, Object>> getEducation() {
        return eduRepo.findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",          e.getId());
            m.put("institution", e.getInstitution());
            m.put("degree",      e.getDegree());
            m.put("period",      e.getPeriod());
            m.put("coursework",  fromJson(e.getCourseworkJson()));
            return (Map<String, Object>) m;
        }).toList();
    }

    public List<Map<String, Object>> getExperiences() {
        return expRepo.findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",      e.getId());
            m.put("role",    e.getRole());
            m.put("company", e.getCompany());
            m.put("type",    e.getType());
            m.put("period",  e.getPeriod());
            m.put("bullets", fromJson(e.getBulletsJson()));
            if (e.getLinkLabel() != null && e.getLinkUrl() != null) {
                m.put("link", Map.of("label", e.getLinkLabel(), "url", e.getLinkUrl()));
            }
            return (Map<String, Object>) m;
        }).toList();
    }

    public List<Map<String, Object>> getProjects() {
        return projRepo.findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",      e.getId());
            m.put("name",    e.getName());
            m.put("year",    e.getYear());
            m.put("tags",    fromJson(e.getTagsJson()));
            m.put("bullets", fromJson(e.getBulletsJson()));
            return (Map<String, Object>) m;
        }).toList();
    }

    public Map<String, Object> getSkills() {
        var pills = pillRepo.findAllByOrderByDisplayOrderAsc().stream().map(e ->
            Map.of("name", e.getName(), "icon", e.getIcon(), "summary", e.getSummary())
        ).toList();
        var langs = langRepo.findAllByOrderByDisplayOrderAsc().stream().map(e ->
            Map.of("lang", e.getLang(), "level", e.getLevel())
        ).toList();
        return Map.of("pills", pills, "languages", langs);
    }

    // ── JSON helpers ───────────────────────────────────────────────

    public List<String> fromJson(String json) {
        if (json == null || json.isBlank()) return List.of();
        try { return mapper.readValue(json, new TypeReference<>() {}); }
        catch (Exception e) { return List.of(); }
    }

    public String toJson(List<String> list) {
        try { return mapper.writeValueAsString(list); }
        catch (Exception e) { return "[]"; }
    }

    // ── Repo accessors (used by AdminController & DataSeeder) ──────

    public ProfileRepository  profileRepo()  { return profileRepo; }
    public HeroRepository     heroRepo()     { return heroRepo; }
    public AboutRepository    aboutRepo()    { return aboutRepo; }
    public EducationRepository  eduRepo()    { return eduRepo; }
    public ExperienceRepository expRepo()    { return expRepo; }
    public ProjectRepository  projRepo()     { return projRepo; }
    public SkillPillRepository  pillRepo()   { return pillRepo; }
    public SkillLanguageRepository langRepo(){ return langRepo; }
    public ObjectMapper mapper() { return mapper; }
}
