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
        return List.of(
            Map.of(
                "id",      1,
                "role",    "RPA Developer — Intern",
                "company", "PSA Corporation Limited",
                "type",    "Year-long Internship · Extended by 2 months",
                "period",  "Apr 2025 – Apr 2026",
                "bullets", List.of(
                    "Built 5+ production automation workflows with UiPath, Power Automate, and Python across port terminal operations.",
                    "Attendance automation deployed across 18 departments — saved an estimated 6,500+ staff-hours per year.",
                    "Container onboarding automation replaced a 10–20 min manual process, saving 180+ hours annually.",
                    "Built Power BI dashboards and integrated SharePoint with Power Automate for cross-department reporting.",
                    "Internship extended by 2 months at PSA's request."
                )
            ),
            Map.of(
                "id",      2,
                "role",    "Owner",
                "company", "Bae Kitchen (Self-Employed)",
                "type",    "Myanmar",
                "period",  "2021 – 2022",
                "bullets", List.of(
                    "Ran an online F&B business solo — handled everything from supply and operations to customer service."
                )
            ),
            Map.of(
                "id",      3,
                "role",    "IT Trainee",
                "company", "Cloud-9 IT Solutions & Services",
                "type",    "Myanmar",
                "period",  "2020 – 2022",
                "bullets", List.of(
                    "Helped deploy 50+ CCTV cameras across a supermarket site — cabling, mounting, and system configuration.",
                    "Picked up hands-on hardware troubleshooting across multiple client installations."
                )
            )
        );
    }

    public List<Map<String, Object>> getProjects() {
        return List.of(
            Map.of("id", 1, "name", "Personal Portfolio Website", "year", "2025",
                   "tags", List.of("React", "Spring Boot", "Vercel", "Railway"),
                   "bullets", List.of(
                       "This site — React 19 + Spring Boot, custom domain, hCaptcha, Resend email API, Gemini-powered chatbot, and a cat loading animation."
                   )),
            Map.of("id", 2, "name", "Library Book Rental Platform", "year", "2025",
                   "tags", List.of("Java", "Node.js", "PostgreSQL", "Azure CI/CD", "JWT", "Playwright"),
                   "bullets", List.of(
                       "Full-stack rental system with JWT auth, admin dashboard, Gemini-powered book chatbot, and automated Playwright tests on Azure DevOps."
                   )),
            Map.of("id", 3, "name", "Online Furniture Website", "year", "2024",
                   "tags", List.of("Full-Stack", "Agile", "JavaScript"),
                   "bullets", List.of(
                       "Team of 3, Agile sprints — built a promotions engine and 3D interactive room view."
                   )),
            Map.of("id", 4, "name", "Cleaning Service Platform", "year", "2024",
                   "tags", List.of("Java", "REST API", "JWT"),
                   "bullets", List.of(
                       "REST API with full booking lifecycle and role-based access for customers and staff."
                   )),
            Map.of("id", 5, "name", "Web Security Project", "year", "2024",
                   "tags", List.of("Cybersecurity", "OWASP Top 10"),
                   "bullets", List.of(
                       "Audited a demo app against OWASP Top 10 — found and patched SQL injection, XSS, and broken auth."
                   )),
            Map.of("id", 6, "name", "Sustainability Awareness Website", "year", "2023",
                   "tags", List.of("JavaScript", "HTML/CSS", "JWT"),
                   "bullets", List.of(
                       "Gamified platform with daily quests, in-app currency, and a virtual store — JWT auth throughout."
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
