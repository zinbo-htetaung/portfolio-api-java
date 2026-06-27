package com.zinbo.portfolio.service;

import com.zinbo.portfolio.entity.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final PortfolioService svc;

    public DataSeeder(PortfolioService svc) { this.svc = svc; }

    @Override
    public void run(ApplicationArguments args) {
        seedProfile();
        seedHero();
        seedAbout();
        seedEducation();
        seedExperiences();
        seedProjects();
        seedSkills();
    }

    private void seedProfile() {
        if (svc.profileRepo().count() > 0) return;
        var e = new ProfileEntity();
        e.setName("Zin Bo Htet Aung");
        e.setLocation("Singapore");
        e.setTagline("Software Developer · Open to Opportunities");
        e.setEmail("zinbohtetaung@gmail.com");
        e.setPhonenumber("+65 98509604");
        e.setGithubUrl("https://github.com/zinbo-htetaung");
        e.setLinkedinUrl("https://www.linkedin.com/in/zinbohtetaung/");
        e.setPortfolioUrl("https://zinbohtetaung.com");
        e.setResumeUrl("/Zin Bo Htet Aung.pdf");
        svc.profileRepo().save(e);
    }

    private void seedHero() {
        if (svc.heroRepo().count() > 0) return;
        var e = new HeroEntity();
        e.setSubhead("Full-Stack Developer · RPA Engineer · Open to Work");
        e.setBio("Fresh IT graduate who loves turning repetitive processes into automations and ideas into full-stack web apps — with a habit of shipping things that actually save time.");
        e.setPhoto("/ProfilePic.webp");
        svc.heroRepo().save(e);
    }

    private void seedAbout() {
        if (svc.aboutRepo().count() > 0) return;
        var e = new AboutEntity();
        e.setBio("I graduated with a Diploma in Information Technology (Software Development) from Singapore Polytechnic. My year-long internship at PSA Corporation gave me real production experience — shipping RPA workflows that eliminated thousands of manual hours across departments. Outside of automation, I build full-stack web apps and enjoy the whole process: designing the database, wiring up the API, and making the frontend feel right.");
        e.setHighlightsJson(svc.toJson(List.of(
            "RPA development — UiPath, Power Automate, Python",
            "Full-stack web — Java, React, Node.js, Express.js",
            "Database engineering — PostgreSQL & SQL",
            "REST API design & third-party integrations",
            "CI/CD, automated testing & cloud deployment",
            "Web security — OWASP Top 10"
        )));
        svc.aboutRepo().save(e);
    }

    private void seedEducation() {
        if (svc.eduRepo().count() > 0) return;
        var e = new EducationEntity();
        e.setInstitution("Singapore Polytechnic");
        e.setDegree("Diploma in Information Technology (Software Development)");
        e.setPeriod("Graduated Apr 2026");
        e.setCourseworkJson(svc.toJson(List.of(
            "Data Structures & Algorithms",
            "Full-Stack Web Development",
            "Database Systems & SQL",
            "Cybersecurity (OWASP Top 10)",
            "UI/UX Design & Figma",
            "CI/CD Pipelines (Azure DevOps)",
            "Agile Software Engineering",
            "Cloud & Deployment (Azure, AWS)",
            "Automated Testing (Playwright)"
        )));
        e.setDisplayOrder(0);
        svc.eduRepo().save(e);
    }

    private void seedExperiences() {
        if (svc.expRepo().count() > 0) return;

        var psa = new ExperienceEntity();
        psa.setRole("RPA Developer — Intern");
        psa.setCompany("PSA Corporation Limited");
        psa.setType("Year-long Internship · Extended by 2 months");
        psa.setPeriod("Apr 2025 – Apr 2026");
        psa.setBulletsJson(svc.toJson(List.of(
            "Built 5+ production RPA workflows using UiPath, Power Automate, and Python — covering data extraction, report generation, and email automation across port terminal operations.",
            "Attendance automation deployed across 18 departments — cut an estimated 6,500+ staff-hours of manual work per year.",
            "Container onboarding workflow replaced a 10–20 min manual process, saving 180+ hours annually and improving data consistency.",
            "Consolidated scattered reporting into a centralised automation system, making it easier to maintain and scale across teams.",
            "Built Power BI dashboards, integrated SharePoint with Power Automate for workflow triggers, and extracted data from databases using SQL.",
            "Internship extended by 2 months at PSA's request — stayed on to onboard and support incoming automation projects."
        )));
        psa.setDisplayOrder(0);
        svc.expRepo().save(psa);

        var bae = new ExperienceEntity();
        bae.setRole("Owner");
        bae.setCompany("Bae Kitchen (Self-Employed)");
        bae.setType("Myanmar");
        bae.setPeriod("2021 – 2022");
        bae.setBulletsJson(svc.toJson(List.of(
            "Founded and ran a home-based F&B business — handled everything from sourcing and cooking to packaging, delivery coordination, and customer service.",
            "Used Facebook as the primary sales channel — managed orders, enquiries, and promotions independently.",
            "Built a loyal repeat customer base through consistent product quality and reliable service."
        )));
        bae.setLinkLabel("View on Facebook");
        bae.setLinkUrl("https://www.facebook.com/Bae.tgi/");
        bae.setDisplayOrder(1);
        svc.expRepo().save(bae);

        var cloud9 = new ExperienceEntity();
        cloud9.setRole("IT Trainee");
        cloud9.setCompany("Cloud-9 IT Solutions & Services");
        cloud9.setType("Myanmar");
        cloud9.setPeriod("2020 – 2022");
        cloud9.setBulletsJson(svc.toJson(List.of(
            "Supported full-scale deployment of 50+ CCTV cameras across a supermarket site — structured cabling, mounting, and system configuration.",
            "Assisted with hardware troubleshooting and diagnostics across multiple client site visits.",
            "Gained practical exposure to IT infrastructure setup, network configuration, and on-site client support."
        )));
        cloud9.setLinkLabel("View on Facebook");
        cloud9.setLinkUrl("https://www.facebook.com/cloud9itsolution");
        cloud9.setDisplayOrder(2);
        svc.expRepo().save(cloud9);
    }

    private void seedProjects() {
        if (svc.projRepo().count() > 0) return;
        String[][] projects = {
            {"Personal Portfolio Website","2025","[\"React\",\"Spring Boot\",\"Vercel\",\"Railway\"]",
             "[\"This site — React 19 + Spring Boot, custom domain, hCaptcha, Resend email API, Gemini-powered chatbot, and a cat loading animation.\",\"Glassmorphism UI, dark/light theme, and fully responsive across all screen sizes.\"]"},
            {"Library Book Rental Platform","2025","[\"Java\",\"Node.js\",\"PostgreSQL\",\"Azure CI/CD\",\"JWT\",\"Playwright\"]",
             "[\"Full-stack rental system with JWT auth, admin dashboard, and full CRUD for books, users, and rentals.\",\"Gemini-powered book recommendation chatbot and automated Playwright tests running on Azure DevOps CI/CD.\"]"},
            {"Online Furniture Website","2024","[\"Full-Stack\",\"Agile\",\"JavaScript\"]",
             "[\"Built in a 3-person Agile team — sprint planning, daily stand-ups, and Git collaboration across feature branches.\",\"Implemented a dynamic promotions engine and a 3D interactive room-view feature.\"]"},
            {"Cleaning Service Platform","2024","[\"Java\",\"REST API\",\"JWT\"]",
             "[\"REST API with full booking lifecycle — create, confirm, reschedule, and cancel.\",\"Role-based access control for customers and staff, with a secure checkout flow.\"]"},
            {"Web Security Project","2024","[\"Cybersecurity\",\"OWASP Top 10\"]",
             "[\"Audited a demo web app against the OWASP Top 10 — identified and patched SQL injection, XSS, and broken auth vulnerabilities.\"]"},
            {"Sustainability Awareness Website","2023","[\"JavaScript\",\"HTML/CSS\",\"JWT\"]",
             "[\"Gamified platform with daily quests, in-app currency, a virtual store, and a leaderboard.\",\"JWT-secured authentication with session management and persistent user progress.\"]"}
        };
        for (int i = 0; i < projects.length; i++) {
            var p = new ProjectEntity();
            p.setName(projects[i][0]);
            p.setYear(projects[i][1]);
            p.setTagsJson(projects[i][2]);
            p.setBulletsJson(projects[i][3]);
            p.setDisplayOrder(i);
            svc.projRepo().save(p);
        }
    }

    private void seedSkills() {
        if (svc.pillRepo().count() > 0) return;
        String[][] pills = {
            {"Java","☕","Spring Boot REST APIs, MVC, and backend services"},
            {"JavaScript","⚡","ES6+, Node.js, async/await, and browser APIs"},
            {"TypeScript","📘","Typed JavaScript for safer, more maintainable code"},
            {"Python","🐍","Scripting and automation — mainly used in RPA workflows"},
            {"SQL","🗄️","PostgreSQL, schema design, and query optimisation"},
            {"React","⚛️","Component UIs with hooks, Vite, and state management"},
            {"REST APIs","🔗","API design, JSON, auth, and third-party integrations"},
            {"UiPath","🤖","Production RPA workflows for data extraction and automation"},
            {"Power Automate","⚙️","Microsoft-stack flows, SharePoint triggers, and reporting"},
            {"CI/CD & Git","🔄","Azure DevOps, GitHub Actions, and branch workflows"},
            {"Docker","🐳","Containerising apps for consistent environments"},
            {"Cloud Deploy","☁️","Vercel, Railway, Render, and AWS"},
            {"OWASP Top 10","🔐","Security auditing and vulnerability patching"},
            {"Agile / Scrum","🏃","Sprint planning, stand-ups, and team delivery"}
        };
        for (int i = 0; i < pills.length; i++) {
            var p = new SkillPillEntity();
            p.setName(pills[i][0]);
            p.setIcon(pills[i][1]);
            p.setSummary(pills[i][2]);
            p.setDisplayOrder(i);
            svc.pillRepo().save(p);
        }

        String[][] langs = {{"Burmese","Native"},{"English","Proficient"},{"Chinese","Limited Working Proficiency"}};
        for (int i = 0; i < langs.length; i++) {
            var l = new SkillLanguageEntity();
            l.setLang(langs[i][0]);
            l.setLevel(langs[i][1]);
            l.setDisplayOrder(i);
            svc.langRepo().save(l);
        }
    }
}
