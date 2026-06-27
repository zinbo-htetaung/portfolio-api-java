package com.zinbo.portfolio.controller;

import com.zinbo.portfolio.entity.*;
import com.zinbo.portfolio.model.ApiResponse;
import com.zinbo.portfolio.service.PortfolioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// All routes under /api/admin/** are JWT-protected by ApiKeyFilter
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final PortfolioService svc;

    public AdminController(PortfolioService svc) { this.svc = svc; }

    // ── Profile ────────────────────────────────────────────────────

    @GetMapping("/profile")
    public ResponseEntity<?> getProfile() {
        var e = svc.profileRepo().findById(1L).orElseThrow();
        return ResponseEntity.ok(ApiResponse.ok(Map.of(
            "name", e.getName(), "location", e.getLocation(), "tagline", e.getTagline(),
            "email", e.getEmail(), "phonenumber", e.getPhonenumber(),
            "githubUrl", e.getGithubUrl(), "linkedinUrl", e.getLinkedinUrl(),
            "portfolioUrl", e.getPortfolioUrl(), "resumeUrl", e.getResumeUrl()
        )));
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> body) {
        var e = svc.profileRepo().findById(1L).orElse(new ProfileEntity());
        if (body.containsKey("name"))         e.setName(body.get("name"));
        if (body.containsKey("location"))     e.setLocation(body.get("location"));
        if (body.containsKey("tagline"))      e.setTagline(body.get("tagline"));
        if (body.containsKey("email"))        e.setEmail(body.get("email"));
        if (body.containsKey("phonenumber"))  e.setPhonenumber(body.get("phonenumber"));
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
        return ResponseEntity.ok(ApiResponse.ok(svc.getEducation()));
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
        e.setDisplayOrder(body.containsKey("displayOrder") ? ((Number) body.get("displayOrder")).intValue() : (int) svc.eduRepo().count());
        svc.eduRepo().save(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Education created"));
    }

    @PutMapping("/education/{id}")
    public ResponseEntity<?> updateEducation(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        var e = svc.eduRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        if (body.containsKey("institution")) e.setInstitution((String) body.get("institution"));
        if (body.containsKey("degree"))      e.setDegree((String) body.get("degree"));
        if (body.containsKey("period"))      e.setPeriod((String) body.get("period"));
        if (body.containsKey("coursework")) {
            @SuppressWarnings("unchecked")
            var list = (List<String>) body.get("coursework");
            e.setCourseworkJson(svc.toJson(list));
        }
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
        return ResponseEntity.ok(ApiResponse.ok(svc.getExperiences()));
    }

    @PostMapping("/experiences")
    public ResponseEntity<?> createExperience(@RequestBody Map<String, Object> body) {
        var e = new ExperienceEntity();
        applyExpBody(e, body);
        long count = svc.expRepo().count();
        e.setDisplayOrder((int) count);
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
        if (body.containsKey("displayOrder")) e.setDisplayOrder((int) body.get("displayOrder"));
    }

    // ── Projects ───────────────────────────────────────────────────

    @GetMapping("/projects")
    public ResponseEntity<?> getProjects() {
        return ResponseEntity.ok(ApiResponse.ok(svc.getProjects()));
    }

    @PostMapping("/projects")
    public ResponseEntity<?> createProject(@RequestBody Map<String, Object> body) {
        var p = new ProjectEntity();
        applyProjBody(p, body);
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
        if (body.containsKey("displayOrder")) p.setDisplayOrder((int) body.get("displayOrder"));
    }

    // ── Skills: Pills ──────────────────────────────────────────────

    @GetMapping("/skills")
    public ResponseEntity<?> getSkills() {
        return ResponseEntity.ok(ApiResponse.ok(svc.getSkills()));
    }

    @GetMapping("/skills/pills")
    public ResponseEntity<?> getPills() {
        return ResponseEntity.ok(ApiResponse.ok(svc.pillRepo().findAllByOrderByDisplayOrderAsc()));
    }

    @PostMapping("/skills/pills")
    public ResponseEntity<?> createPill(@RequestBody Map<String, String> body) {
        var p = new SkillPillEntity();
        p.setName(body.get("name")); p.setIcon(body.get("icon")); p.setSummary(body.get("summary"));
        p.setDisplayOrder((int) svc.pillRepo().count());
        svc.pillRepo().save(p);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Skill pill created"));
    }

    @PutMapping("/skills/pills/{id}")
    public ResponseEntity<?> updatePill(@PathVariable Long id, @RequestBody Map<String, String> body) {
        var p = svc.pillRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        if (body.containsKey("name"))    p.setName(body.get("name"));
        if (body.containsKey("icon"))    p.setIcon(body.get("icon"));
        if (body.containsKey("summary")) p.setSummary(body.get("summary"));
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
        return ResponseEntity.ok(ApiResponse.ok(svc.langRepo().findAllByOrderByDisplayOrderAsc()));
    }

    @PostMapping("/skills/languages")
    public ResponseEntity<?> createLanguage(@RequestBody Map<String, String> body) {
        var l = new SkillLanguageEntity();
        l.setLang(body.get("lang")); l.setLevel(body.get("level"));
        l.setDisplayOrder((int) svc.langRepo().count());
        svc.langRepo().save(l);
        return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.ok("Language created"));
    }

    @PutMapping("/skills/languages/{id}")
    public ResponseEntity<?> updateLanguage(@PathVariable Long id, @RequestBody Map<String, String> body) {
        var l = svc.langRepo().findById(id)
            .orElseThrow(() -> new org.springframework.web.server.ResponseStatusException(HttpStatus.NOT_FOUND));
        if (body.containsKey("lang"))  l.setLang(body.get("lang"));
        if (body.containsKey("level")) l.setLevel(body.get("level"));
        svc.langRepo().save(l);
        return ResponseEntity.ok(ApiResponse.ok("Language updated"));
    }

    @DeleteMapping("/skills/languages/{id}")
    public ResponseEntity<?> deleteLanguage(@PathVariable Long id) {
        svc.langRepo().deleteById(id);
        return ResponseEntity.ok(ApiResponse.ok("Language deleted"));
    }
}
