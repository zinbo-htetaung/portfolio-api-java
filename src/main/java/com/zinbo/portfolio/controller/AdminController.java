package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.entity.*;
import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final PortfolioService svc;

    public AdminController(PortfolioService svc) { this.svc = svc; }

    // ── Profile ────────────────────────────────────────────────────

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        var e = svc.profileRepo().findById(1L).orElseThrow();
        var m = new LinkedHashMap<String, Object>();
        m.put("name", e.getName()); m.put("location", e.getLocation());
        m.put("tagline", e.getTagline()); m.put("email", e.getEmail());
        m.put("phoneNumber", e.getPhonenumber());
        m.put("githubUrl", e.getGithubUrl()); m.put("linkedinUrl", e.getLinkedinUrl());
        m.put("portfolioUrl", e.getPortfolioUrl()); m.put("resumeUrl", e.getResumeUrl());
        return ResponseEntity.ok(ApiResponse.ok(m));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> body) {
        var e = svc.profileRepo().findById(1L).orElse(new ProfileEntity());
        if (body.containsKey("name"))         e.setName(body.get("name"));
        if (body.containsKey("location"))     e.setLocation(body.get("location"));
        if (body.containsKey("tagline"))      e.setTagline(body.get("tagline"));
        if (body.containsKey("email"))        e.setEmail(body.get("email"));
        if (body.containsKey("phoneNumber"))  e.setPhonenumber(body.get("phoneNumber"));
        if (body.containsKey("githubUrl"))    e.setGithubUrl(body.get("githubUrl"));
        if (body.containsKey("linkedinUrl"))  e.setLinkedinUrl(body.get("linkedinUrl"));
        if (body.containsKey("portfolioUrl")) e.setPortfolioUrl(body.get("portfolioUrl"));
        if (body.containsKey("resumeUrl"))    e.setResumeUrl(body.get("resumeUrl"));
        svc.profileRepo().save(e);
        return ResponseEntity.ok(ApiResponse.ok("Profile updated"));
    }

    // ── Hero ───────────────────────────────────────────────────────

    @GetMapping("/hero")
    public ResponseEntity<?> getHero() {
        var e = svc.heroRepo().findById(1L).orElseThrow();
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
            "subhead", e.getSubhead(), "bio", e.getBio(), "photo", e.getPhoto()
        )));
    }

    @PutMapping("/hero")
    public ResponseEntity<?> updateHero(@RequestBody Map<String, String> body) {
        var e = svc.heroRepo().findById(1L).orElse(new HeroEntity());
        if (body.containsKey("subhead")) e.setSubhead(body.get("subhead"));
        if (body.containsKey("bio"))     e.setBio(body.get("bio"));
        if (body.containsKey("photo"))   e.setPhoto(body.get("photo"));
        svc.heroRepo().save(e);
        return ResponseEntity.ok(ApiResponse.ok("Hero updated"));
    }

    // ── About ──────────────────────────────────────────────────────

    @GetMapping("/about")
    public ResponseEntity<?> getAbout() {
        var e = svc.aboutRepo().findById(1L).orElseThrow();
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
            "bio", e.getBio(),
            "highlights", svc.fromJson(e.getHighlightsJson())
        )));
    }

    @PutMapping("/about")
    public ResponseEntity<?> updateAbout(@RequestBody Map<String, Object> body) {
        var e = svc.aboutRepo().findById(1L).orElse(new AboutEntity());
        if (body.containsKey("bio")) e.setBio((String) body.get("bio"));
        if (body.containsKey("highlights")) {
            @SuppressWarnings("unchecked")
            var list = (List<String>) body.get("highlights");
            e.setHighlightsJson(svc.toJson(list));
        }
        svc.aboutRepo().save(e);
        return ResponseEntity.ok(ApiResponse.ok("About updated"));
    }

    // ── Education ──────────────────────────────────────────────────

    @GetMapping("/education")
    public ResponseEntity<?> getEducation() {
        var list = svc.eduRepo().findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",           e.getId());
            m.put("institution",  e.getInstitution());
            m.put("degree",       e.getDegree());
            m.put("period",       e.getPeriod());
            m.put("coursework",   svc.fromJson(e.getCourseworkJson()));
            m.put("displayOrder", e.getDisplayOrder());
            return m;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @PostMapping("/education")
    public ResponseEntity<?> createEducation(@RequestBody Map<String, Object> body) {
        var e = new EducationEntity();
        e.setInstitution((String) body.getOrDefault("institution", ""));
        e.setDegree((String) body.getOrDefault("degree", ""));
        e.setPeriod((String) body.getOrDefault("period", ""));
        if (body.containsKey("coursework")) {
            @SuppressWarnings("unchecked")
            var list = (List<String>) body.get("coursework");
            e.setCourseworkJson(svc.toJson(list));
        }
        e.setDisplayOrder(body.containsKey("displayOrder")
            ? ((Number) body.get("displayOrder")).intValue()
            : (int) svc.eduRepo().count());
        svc.eduRepo().save(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Education created"));
    }

    @PutMapping("/education/{id}")
    public ResponseEntity<?> updateEducation(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var e = svc.eduRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        if (body.containsKey("institution"))  e.setInstitution((String) body.get("institution"));
        if (body.containsKey("degree"))       e.setDegree((String) body.get("degree"));
        if (body.containsKey("period"))       e.setPeriod((String) body.get("period"));
        if (body.containsKey("coursework")) {
            @SuppressWarnings("unchecked")
            var list = (List<String>) body.get("coursework");
            e.setCourseworkJson(svc.toJson(list));
        }
        if (body.containsKey("displayOrder"))
            e.setDisplayOrder(((Number) body.get("displayOrder")).intValue());
        svc.eduRepo().save(e);
        return ResponseEntity.ok(ApiResponse.ok("Education updated"));
    }

    @DeleteMapping("/education/{id}")
    public ResponseEntity<?> deleteEducation(@PathVariable Long id) {
        svc.eduRepo().deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Education deleted"));
    }

    // ── Experiences ────────────────────────────────────────────────

    @GetMapping("/experiences")
    public ResponseEntity<?> getExperiences() {
        var list = svc.expRepo().findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",           e.getId());
            m.put("role",         e.getRole());
            m.put("company",      e.getCompany());
            m.put("type",         e.getType());
            m.put("period",       e.getPeriod());
            m.put("bullets",      svc.fromJson(e.getBulletsJson()));
            m.put("linkLabel",    e.getLinkLabel() != null ? e.getLinkLabel() : "");
            m.put("linkUrl",      e.getLinkUrl()   != null ? e.getLinkUrl()   : "");
            m.put("displayOrder", e.getDisplayOrder());
            return m;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @PostMapping("/experiences")
    public ResponseEntity<?> createExperience(@RequestBody Map<String, Object> body) {
        var e = new ExperienceEntity();
        applyExpBody(e, body);
        if (!body.containsKey("displayOrder"))
            e.setDisplayOrder((int) svc.expRepo().count());
        svc.expRepo().save(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Experience created"));
    }

    @PutMapping("/experiences/{id}")
    public ResponseEntity<?> updateExperience(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var e = svc.expRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        applyExpBody(e, body);
        svc.expRepo().save(e);
        return ResponseEntity.ok(ApiResponse.ok("Experience updated"));
    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long id) {
        svc.expRepo().deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Experience deleted"));
    }

    private void applyExpBody(ExperienceEntity e, Map<String, Object> body) {
        if (body.containsKey("role"))      e.setRole((String) body.get("role"));
        if (body.containsKey("company"))   e.setCompany((String) body.get("company"));
        if (body.containsKey("type"))      e.setType((String) body.get("type"));
        if (body.containsKey("period"))    e.setPeriod((String) body.get("period"));
        if (body.containsKey("linkLabel")) e.setLinkLabel((String) body.get("linkLabel"));
        if (body.containsKey("linkUrl"))   e.setLinkUrl((String) body.get("linkUrl"));
        if (body.containsKey("bullets")) {
            @SuppressWarnings("unchecked")
            var list = (List<String>) body.get("bullets");
            e.setBulletsJson(svc.toJson(list));
        }
        if (body.containsKey("displayOrder"))
            e.setDisplayOrder(((Number) body.get("displayOrder")).intValue());
    }

    // ── Projects ───────────────────────────────────────────────────

    @GetMapping("/projects")
    public ResponseEntity<?> getProjects() {
        var list = svc.projRepo().findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",           e.getId());
            m.put("name",         e.getName());
            m.put("year",         e.getYear());
            m.put("tags",         svc.fromJson(e.getTagsJson()));
            m.put("bullets",      svc.fromJson(e.getBulletsJson()));
            m.put("displayOrder", e.getDisplayOrder());
            return m;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody Map<String, Object> body) {
        var p = new ProjectEntity();
        applyProjBody(p, body);
        if (!body.containsKey("displayOrder"))
            p.setDisplayOrder((int) svc.projRepo().count());
        svc.projRepo().save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Project created"));
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var p = svc.projRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        applyProjBody(p, body);
        svc.projRepo().save(p);
        return ResponseEntity.ok(ApiResponse.ok("Project updated"));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        svc.projRepo().deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Project deleted"));
    }

    private void applyProjBody(ProjectEntity p, Map<String, Object> body) {
        if (body.containsKey("name")) p.setName((String) body.get("name"));
        if (body.containsKey("year")) p.setYear((String) body.get("year"));
        if (body.containsKey("tags")) {
            @SuppressWarnings("unchecked")
            var list = (List<String>) body.get("tags");
            p.setTagsJson(svc.toJson(list));
        }
        if (body.containsKey("bullets")) {
            @SuppressWarnings("unchecked")
            var list = (List<String>) body.get("bullets");
            p.setBulletsJson(svc.toJson(list));
        }
        if (body.containsKey("displayOrder"))
            p.setDisplayOrder(((Number) body.get("displayOrder")).intValue());
    }

    // ── Skills: Pills ──────────────────────────────────────────────

    @GetMapping("/skills/pills")
    public ResponseEntity<?> getPills() {
        var list = svc.pillRepo().findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",           e.getId());
            m.put("name",         e.getName());
            m.put("icon",         e.getIcon());
            m.put("summary",      e.getSummary());
            m.put("displayOrder", e.getDisplayOrder());
            return m;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @PostMapping("/skills/pills")
    public ResponseEntity<?> createPill(@RequestBody Map<String, Object> body) {
        var p = new SkillPillEntity();
        p.setName((String) body.getOrDefault("name", ""));
        p.setIcon((String) body.getOrDefault("icon", ""));
        p.setSummary((String) body.getOrDefault("summary", ""));
        p.setDisplayOrder(body.containsKey("displayOrder")
            ? ((Number) body.get("displayOrder")).intValue()
            : (int) svc.pillRepo().count());
        svc.pillRepo().save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Skill pill created"));
    }

    @PutMapping("/skills/pills/{id}")
    public ResponseEntity<?> updatePill(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var p = svc.pillRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        if (body.containsKey("name"))         p.setName((String) body.get("name"));
        if (body.containsKey("icon"))         p.setIcon((String) body.get("icon"));
        if (body.containsKey("summary"))      p.setSummary((String) body.get("summary"));
        if (body.containsKey("displayOrder")) p.setDisplayOrder(((Number) body.get("displayOrder")).intValue());
        svc.pillRepo().save(p);
        return ResponseEntity.ok(ApiResponse.ok("Skill pill updated"));
    }

    @DeleteMapping("/skills/pills/{id}")
    public ResponseEntity<?> deletePill(@PathVariable Long id) {
        svc.pillRepo().deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Skill pill deleted"));
    }

    // ── Skills: Languages ──────────────────────────────────────────

    @GetMapping("/skills/languages")
    public ResponseEntity<?> getLanguages() {
        var list = svc.langRepo().findAllByOrderByDisplayOrderAsc().stream().map(e -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("id",           e.getId());
            m.put("lang",         e.getLang());
            m.put("level",        parseLevel(e.getLevel()));
            m.put("displayOrder", e.getDisplayOrder());
            return m;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @PostMapping("/skills/languages")
    public ResponseEntity<?> createLanguage(@RequestBody Map<String, Object> body) {
        var l = new SkillLanguageEntity();
        l.setLang((String) body.getOrDefault("lang", ""));
        l.setLevel(body.containsKey("level") ? String.valueOf(((Number) body.get("level")).intValue()) : "0");
        l.setDisplayOrder(body.containsKey("displayOrder")
            ? ((Number) body.get("displayOrder")).intValue()
            : (int) svc.langRepo().count());
        svc.langRepo().save(l);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Language created"));
    }

    @PutMapping("/skills/languages/{id}")
    public ResponseEntity<?> updateLanguage(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var l = svc.langRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        if (body.containsKey("lang"))  l.setLang((String) body.get("lang"));
        if (body.containsKey("level")) l.setLevel(String.valueOf(((Number) body.get("level")).intValue()));
        if (body.containsKey("displayOrder")) l.setDisplayOrder(((Number) body.get("displayOrder")).intValue());
        svc.langRepo().save(l);
        return ResponseEntity.ok(ApiResponse.ok("Language updated"));
    }

    @DeleteMapping("/skills/languages/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable Long id) {
        svc.langRepo().deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Language deleted"));
    }

    private int parseLevel(String level) {
        try { return Integer.parseInt(level); } catch (Exception e) { return 0; }
    }
}
