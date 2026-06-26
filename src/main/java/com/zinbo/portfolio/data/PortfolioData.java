package com.zinbo.portfolio.data;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class PortfolioData {

    public Map<String, Object> getProfile() {
        var links = new java.util.LinkedHashMap<String, Object>();
        links.put("github",    "https://github.com/zinbo-htetaung");
        links.put("linkedin",  "https://www.linkedin.com/in/zinbohtetaung/");
        links.put("portfolio", "https://zinbohtetaung.com");
        links.put("resume",    "/Zin Bo Htet Aung.pdf");

        var profile = new java.util.LinkedHashMap<String, Object>();
        profile.put("name",        "Zin Bo Htet Aung");
        profile.put("location",    "Singapore");
        profile.put("tagline",     "Software Developer · Open to Opportunities");
        profile.put("email",       "zinbohtetaung@gmail.com");
        profile.put("phonenumber", "+65 98509604");
        profile.put("links",       links);
        return profile;
    }

    public Map<String, Object> getHero() {
        return Map.of(
            "subhead", "Full-Stack Developer · RPA Engineer · Open to Work",
            "bio",     "Fresh IT graduate who loves turning repetitive processes into automations and ideas into full-stack web apps — with a habit of shipping things that actually save time.",
            "photo",   "/ProfilePic.webp"
        );
    }

    public Map<String, Object> getAbout() {
        return Map.of(
            "bio", """
                I graduated with a Diploma in Information Technology (Software Development) from
                Singapore Polytechnic. My year-long internship at PSA Corporation gave me real
                production experience — shipping RPA workflows that eliminated thousands of
                manual hours across departments. Outside of automation, I build full-stack web
                apps and enjoy the whole process: designing the database, wiring up the API,
                and making the frontend feel right.""",
            "highlights", List.of(
                "RPA development — UiPath, Power Automate, Python",
                "Full-stack web — Java, React, Node.js, Express.js",
                "Database engineering — PostgreSQL & SQL",
                "REST API design & third-party integrations",
                "CI/CD, automated testing & cloud deployment",
                "Web security — OWASP Top 10"
            )
        );
    }

    public List<Map<String, Object>> getEducation() {
        return List.of(
            Map.of(
                "id",          1,
                "institution", "Singapore Polytechnic",
                "degree",      "Diploma in Information Technology (Software Development)",
                "period",      "Graduated Apr 2026",
                "coursework",  List.of(
                    "Data Structures & Algorithms",
                    "Full-Stack Web Development",
                    "Database Systems & SQL",
                    "Cybersecurity (OWASP Top 10)",
                    "UI/UX Design & Figma",
                    "CI/CD Pipelines (Azure DevOps)",
                    "Agile Software Engineering",
                    "Cloud & Deployment (Azure, AWS)",
                    "Automated Testing (Playwright)"
                )
            )
        );
    }

    public List<Map<String, Object>> getExperiences() {
        var psa = new java.util.LinkedHashMap<String, Object>();
        psa.put("id",      1);
        psa.put("role",    "RPA Developer — Intern");
        psa.put("company", "PSA Corporation Limited");
        psa.put("type",    "Year-long Internship · Extended by 2 months");
        psa.put("period",  "Apr 2025 – Apr 2026");
        psa.put("bullets", List.of(
            "Built 5+ production RPA workflows using UiPath, Power Automate, and Python — covering data extraction, report generation, and email automation across port terminal operations.",
            "Attendance automation deployed across 18 departments — cut an estimated 6,500+ staff-hours of manual work per year.",
            "Container onboarding workflow replaced a 10–20 min manual process, saving 180+ hours annually and improving data consistency.",
            "Consolidated scattered reporting into a centralised automation system, making it easier to maintain and scale across teams.",
            "Built Power BI dashboards, integrated SharePoint with Power Automate for workflow triggers, and extracted data from databases using SQL.",
            "Internship extended by 2 months at PSA's request — stayed on to onboard and support incoming automation projects."
        ));

        var baeKitchen = new java.util.LinkedHashMap<String, Object>();
        baeKitchen.put("id",      2);
        baeKitchen.put("role",    "Owner");
        baeKitchen.put("company", "Bae Kitchen (Self-Employed)");
        baeKitchen.put("type",    "Myanmar");
        baeKitchen.put("period",  "2021 – 2022");
        baeKitchen.put("bullets", List.of(
            "Founded and ran a home-based F&B business — handled everything from sourcing and cooking to packaging, delivery coordination, and customer service.",
            "Used Facebook as the primary sales channel — managed orders, enquiries, and promotions independently.",
            "Built a loyal repeat customer base through consistent product quality and reliable service."
        ));
        baeKitchen.put("link", Map.of(
            "label", "View on Facebook",
            "url",   "https://www.facebook.com/Bae.tgi/"
        ));

        var cloud9 = new java.util.LinkedHashMap<String, Object>();
        cloud9.put("id",      3);
        cloud9.put("role",    "IT Trainee");
        cloud9.put("company", "Cloud-9 IT Solutions & Services");
        cloud9.put("type",    "Myanmar");
        cloud9.put("period",  "2020 – 2022");
        cloud9.put("bullets", List.of(
            "Supported full-scale deployment of 50+ CCTV cameras across a supermarket site — structured cabling, mounting, and system configuration.",
            "Assisted with hardware troubleshooting and diagnostics across multiple client site visits.",
            "Gained practical exposure to IT infrastructure setup, network configuration, and on-site client support."
        ));
        cloud9.put("link", Map.of(
            "label", "View on Facebook",
            "url",   "https://www.facebook.com/cloud9itsolution"
        ));

        return List.of(psa, baeKitchen, cloud9);
    }

    public List<Map<String, Object>> getProjects() {
        return List.of(
            Map.of("id", 1, "name", "Personal Portfolio Website", "year", "2025",
                   "tags", List.of("React", "Spring Boot", "Vercel", "Railway"),
                   "bullets", List.of(
                       "This site — React 19 + Spring Boot, custom domain, hCaptcha, Resend email API, Gemini-powered chatbot, and a cat loading animation.",
                       "Glassmorphism UI, dark/light theme, and fully responsive across all screen sizes."
                   )),
            Map.of("id", 2, "name", "Library Book Rental Platform", "year", "2025",
                   "tags", List.of("Java", "Node.js", "PostgreSQL", "Azure CI/CD", "JWT", "Playwright"),
                   "bullets", List.of(
                       "Full-stack rental system with JWT auth, admin dashboard, and full CRUD for books, users, and rentals.",
                       "Gemini-powered book recommendation chatbot and automated Playwright tests running on Azure DevOps CI/CD."
                   )),
            Map.of("id", 3, "name", "Online Furniture Website", "year", "2024",
                   "tags", List.of("Full-Stack", "Agile", "JavaScript"),
                   "bullets", List.of(
                       "Built in a 3-person Agile team — sprint planning, daily stand-ups, and Git collaboration across feature branches.",
                       "Implemented a dynamic promotions engine and a 3D interactive room-view feature."
                   )),
            Map.of("id", 4, "name", "Cleaning Service Platform", "year", "2024",
                   "tags", List.of("Java", "REST API", "JWT"),
                   "bullets", List.of(
                       "REST API with full booking lifecycle — create, confirm, reschedule, and cancel.",
                       "Role-based access control for customers and staff, with a secure checkout flow."
                   )),
            Map.of("id", 5, "name", "Web Security Project", "year", "2024",
                   "tags", List.of("Cybersecurity", "OWASP Top 10"),
                   "bullets", List.of(
                       "Audited a demo web app against the OWASP Top 10 — identified and patched SQL injection, XSS, and broken auth vulnerabilities."
                   )),
            Map.of("id", 6, "name", "Sustainability Awareness Website", "year", "2023",
                   "tags", List.of("JavaScript", "HTML/CSS", "JWT"),
                   "bullets", List.of(
                       "Gamified platform with daily quests, in-app currency, a virtual store, and a leaderboard.",
                       "JWT-secured authentication with session management and persistent user progress."
                   ))
        );
    }

    public Map<String, Object> getSkills() {
        return Map.of(
            "pills", List.of(
                Map.of("name", "Java",           "icon", "☕", "summary", "Spring Boot REST APIs, MVC, and backend services"),
                Map.of("name", "JavaScript",     "icon", "⚡", "summary", "ES6+, Node.js, async/await, and browser APIs"),
                Map.of("name", "TypeScript",     "icon", "📘", "summary", "Typed JavaScript for safer, more maintainable code"),
                Map.of("name", "Python",         "icon", "🐍", "summary", "Scripting and automation — mainly used in RPA workflows"),
                Map.of("name", "SQL",            "icon", "🗄️", "summary", "PostgreSQL, schema design, and query optimisation"),
                Map.of("name", "React",          "icon", "⚛️", "summary", "Component UIs with hooks, Vite, and state management"),
                Map.of("name", "REST APIs",      "icon", "🔗", "summary", "API design, JSON, auth, and third-party integrations"),
                Map.of("name", "UiPath",         "icon", "🤖", "summary", "Production RPA workflows for data extraction and automation"),
                Map.of("name", "Power Automate", "icon", "⚙️", "summary", "Microsoft-stack flows, SharePoint triggers, and reporting"),
                Map.of("name", "CI/CD & Git",    "icon", "🔄", "summary", "Azure DevOps, GitHub Actions, and branch workflows"),
                Map.of("name", "Docker",         "icon", "🐳", "summary", "Containerising apps for consistent environments"),
                Map.of("name", "Cloud Deploy",   "icon", "☁️", "summary", "Vercel, Railway, Render, and AWS"),
                Map.of("name", "OWASP Top 10",   "icon", "🔐", "summary", "Security auditing and vulnerability patching"),
                Map.of("name", "Agile / Scrum",  "icon", "🏃", "summary", "Sprint planning, stand-ups, and team delivery")
            ),
            "languages", List.of(
                Map.of("lang", "Burmese", "level", "Native"),
                Map.of("lang", "English", "level", "Proficient"),
                Map.of("lang", "Chinese", "level", "Limited Working Proficiency")
            )
        );
    }
}
