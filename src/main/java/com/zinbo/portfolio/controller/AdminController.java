package com.zinbo.portfolio.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinbo.portfolio.entity.*;
import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.repository.VisitorLogRepository;
import com.zinbo.portfolio.service.BackupService;
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

    private final PortfolioService     svc;
    private final ViewController       viewCtrl;
    private final BackupService        backup;
    private final VisitorLogRepository visitorRepo;
    private final ObjectMapper         mapper = new ObjectMapper();

    public AdminController(PortfolioService svc, ViewController viewCtrl,
                           BackupService backup, VisitorLogRepository visitorRepo) {
        this.svc         = svc;
        this.viewCtrl    = viewCtrl;
        this.backup      = backup;
        this.visitorRepo = visitorRepo;
    }

    // ── Insights ───────────────────────────────────────────────────

    @GetMapping("/insights")
    public ResponseEntity<?> getInsights() {
        var counts = new LinkedHashMap<String, Object>();
        counts.put("education",  svc.eduRepo().count());
        counts.put("experience", svc.expRepo().count());
        counts.put("projects",   svc.projRepo().count());
        counts.put("skills",     svc.pillRepo().count());
        counts.put("languages",  svc.langRepo().count());

        var m = new LinkedHashMap<String, Object>();
        m.put("views",  viewCtrl.snapshot());
        m.put("counts", counts);
        m.put("health", Map.of("status", "ok", "db", "connected"));
        return ResponseEntity.ok(ApiResponse.ok(m));
    }

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
        backup.snapshot("profile", svc.getProfile());
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
        backup.snapshot("hero", svc.getHero());
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
        backup.snapshot("about", svc.getAbout());
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
        backup.snapshot("education", svc.getEducation());
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
        backup.snapshot("education", svc.getEducation());
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
        backup.snapshot("education", svc.getEducation());
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
        backup.snapshot("experience", svc.getExperiences());
        var e = new ExperienceEntity();
        applyExpBody(e, body);
        if (!body.containsKey("displayOrder"))
            e.setDisplayOrder((int) svc.expRepo().count());
        svc.expRepo().save(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Experience created"));
    }

    @PutMapping("/experiences/{id}")
    public ResponseEntity<?> updateExperience(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        backup.snapshot("experience", svc.getExperiences());
        var e = svc.expRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        applyExpBody(e, body);
        svc.expRepo().save(e);
        return ResponseEntity.ok(ApiResponse.ok("Experience updated"));
    }

    @DeleteMapping("/experiences/{id}")
    public ResponseEntity<?> deleteExperience(@PathVariable Long id) {
        backup.snapshot("experience", svc.getExperiences());
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
        backup.snapshot("projects", svc.getProjects());
        var p = new ProjectEntity();
        applyProjBody(p, body);
        if (!body.containsKey("displayOrder"))
            p.setDisplayOrder((int) svc.projRepo().count());
        svc.projRepo().save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Project created"));
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<?> updateProject(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        backup.snapshot("projects", svc.getProjects());
        var p = svc.projRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        applyProjBody(p, body);
        svc.projRepo().save(p);
        return ResponseEntity.ok(ApiResponse.ok("Project updated"));
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Long id) {
        backup.snapshot("projects", svc.getProjects());
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
        backup.snapshot("skills", svc.getSkills());
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
        backup.snapshot("skills", svc.getSkills());
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
        backup.snapshot("skills", svc.getSkills());
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
            m.put("level",        e.getLevel() != null ? e.getLevel() : "");
            m.put("displayOrder", e.getDisplayOrder());
            return m;
        }).toList();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @PostMapping("/skills/languages")
    public ResponseEntity<?> createLanguage(@RequestBody Map<String, Object> body) {
        backup.snapshot("skills", svc.getSkills());
        var l = new SkillLanguageEntity();
        l.setLang((String) body.getOrDefault("lang", ""));
        l.setLevel(body.containsKey("level") ? body.get("level").toString() : "");
        l.setDisplayOrder(body.containsKey("displayOrder")
            ? ((Number) body.get("displayOrder")).intValue()
            : (int) svc.langRepo().count());
        svc.langRepo().save(l);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Language created"));
    }

    @PutMapping("/skills/languages/{id}")
    public ResponseEntity<?> updateLanguage(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        backup.snapshot("skills", svc.getSkills());
        var l = svc.langRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        if (body.containsKey("lang"))  l.setLang((String) body.get("lang"));
        if (body.containsKey("level")) l.setLevel(body.get("level").toString());
        if (body.containsKey("displayOrder")) l.setDisplayOrder(((Number) body.get("displayOrder")).intValue());
        svc.langRepo().save(l);
        return ResponseEntity.ok(ApiResponse.ok("Language updated"));
    }

    @DeleteMapping("/skills/languages/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable Long id) {
        backup.snapshot("skills", svc.getSkills());
        svc.langRepo().deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Language deleted"));
    }

    private String parseLevel(String level) {
        return level != null ? level : "";
    }

    // ── Backups ────────────────────────────────────────────────────

    @GetMapping("/backups")
    public ResponseEntity<?> listBackups(@RequestParam(required = false) String section) {
        var list = section != null && !section.isBlank()
            ? backup.listBySection(section)
            : backup.listAll();
        return ResponseEntity.ok(ApiResponse.ok(list));
    }

    @GetMapping("/backups/{id}")
    public ResponseEntity<?> getBackup(@PathVariable Long id) {
        var json = backup.getJson(id);
        if (json == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Backup not found"));
        try {
            var data = mapper.readValue(json, Object.class);
            return ResponseEntity.ok(ApiResponse.ok(data));
        } catch (Exception e) {
            return ResponseEntity.ok(ApiResponse.ok(json));
        }
    }

    // ── Visitors ───────────────────────────────────────────────────

    @GetMapping("/visitors")
    public ResponseEntity<?> getVisitors() {
        // Country breakdown
        var countries = visitorRepo.countByCountry().stream().map(row -> {
            String country     = (String) row[0];
            String countryCode = (String) row[1];
            long   count       = ((Number) row[2]).longValue();
            var m = new LinkedHashMap<String, Object>();
            m.put("country",     country);
            m.put("countryCode", countryCode);
            m.put("flag",        flag(countryCode));
            m.put("count",       count);
            return m;
        }).toList();

        // 20 most recent visits
        var recent = visitorRepo.findTop20ByOrderByVisitedAtDesc().stream().map(v -> {
            var m = new LinkedHashMap<String, Object>();
            m.put("country",     v.getCountry());
            m.put("countryCode", v.getCountryCode());
            m.put("flag",        flag(v.getCountryCode()));
            m.put("city",        v.getCity());
            m.put("isp",         v.getIsp());
            m.put("browser",     v.getBrowser());
            m.put("os",          v.getOs());
            m.put("device",      v.getDevice());
            m.put("referrer",    v.getReferrer());
            m.put("language",    v.getLanguage());
            m.put("screen",      v.getScreen());
            m.put("timezone",    v.getTimezone());
            m.put("darkMode",    v.getDarkMode());
            m.put("visitedAt",   v.getVisitedAt().toString());
            return m;
        }).toList();

        var result = new LinkedHashMap<String, Object>();
        result.put("total",     visitorRepo.count());
        result.put("countries", countries);
        result.put("recent",    recent);
        return ResponseEntity.ok(ApiResponse.ok(result));
    }

    private String flag(String code) {
        if (code == null || code.length() != 2) return "🌐";
        int a = code.toUpperCase().charAt(0) - 'A' + 0x1F1E6;
        int b = code.toUpperCase().charAt(1) - 'A' + 0x1F1E6;
        return new String(Character.toChars(a)) + new String(Character.toChars(b));
    }
}
