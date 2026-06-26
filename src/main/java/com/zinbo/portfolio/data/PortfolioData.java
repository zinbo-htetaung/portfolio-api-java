package com.zinbo.portfolio.data;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// Single source of truth — mirrors the frontend data.js
// In a future iteration, these would come from a PostgreSQL database via JPA.
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
        profile.put("tagline",     "Software Engineer · Open to Opportunities");
        profile.put("email",       "zinbohtetaung@gmail.com");
        profile.put("phonenumber", "+65 98509604");
        profile.put("links",       links);
        return profile;
    }

    public Map<String, Object> getHero() {
        return Map.of(
            "subhead", "Full-Stack Developer · Intelligent Automations · Open to Work",
            "bio",     "Fresh IT graduate passionate about building reliable software and intelligent automation solutions that create real impact.",
            "photo",   "/ProfilePic.webp"
        );
    }

    public Map<String, Object> getAbout() {
        return Map.of(
            "bio", """
                I am a fresh graduate with a Diploma in Information Technology (Software Development)
                from Singapore Polytechnic. Through my internship at PSA Corporation Limited and
                academic projects, I have built hands-on experience in full-stack development, robotic
                process automation, and database engineering — with a strong focus on delivering clean,
                maintainable, and scalable solutions.""",
            "highlights", List.of(
                "Robotic process automation (UiPath & Power Automate)",
                "Full-stack web development (Java, React, Node.js)",
                "Database design & SQL engineering",
                "REST API development & integration",
                "CI/CD pipelines & Agile delivery",
                "Cybersecurity fundamentals (OWASP Top 10)"
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
                    "Database Systems",
                    "Cybersecurity (OWASP)",
                    "UI/UX Design",
                    "CI/CD Pipelines",
                    "Agile Software Engineering"
                )
            )
        );
    }

    public List<Map<String, Object>> getExperiences() {
        return List.of(
            Map.of(
                "id",      1,
                "role",    "Robotic Process Automation Developer — Intern",
                "company", "PSA Corporation Limited",
                "type",    "Year-long Internship · Extended by 2 months",
                "period",  "Apr 2025 – Apr 2026",
                "bullets", List.of(
                    "Designed and deployed 5+ production RPA workflows using UiPath and Power Automate.",
                    "Engineered an attendance automation report deployed across 18 departments, saving 6,500+ staff-hours annually.",
                    "Built a container onboarding automation saving 180+ hours annually.",
                    "Internship extended by 2 months at PSA's request."
                )
            ),
            Map.of(
                "id",      2,
                "role",    "Full-Stack Software Engineer — Academic Projects",
                "company", "Singapore Polytechnic",
                "type",    "Diploma in Information Technology",
                "period",  "2023 – 2026",
                "bullets", List.of(
                    "Library Book Rental Platform: Java, Node.js, PostgreSQL with AI chatbot, JWT auth, and Azure CI/CD.",
                    "Cleaning Service Platform: Cloud-hosted REST API with booking lifecycle and role-based access control.",
                    "Web Security Project: Audited and patched a demo app against OWASP Top 10."
                )
            ),
            Map.of(
                "id",      3,
                "role",    "IT Trainee",
                "company", "Cloud-9 IT Solutions & Services",
                "type",    "Myanmar",
                "period",  "2020 – 2022",
                "bullets", List.of(
                    "Contributed to full-scale deployment of 50+ CCTV cameras across a supermarket site.",
                    "Developed hands-on hardware troubleshooting and diagnostic skills."
                )
            )
        );
    }

    public List<Map<String, Object>> getProjects() {
        return List.of(
            Map.of("id", 1, "name", "Library Book Rental Platform", "year", "2025",
                   "tags", List.of("Java","Node.js","PostgreSQL","Azure","CI/CD","JWT"),
                   "bullets", List.of(
                       "Full-stack rental platform with JWT authentication and role-based admin dashboard.",
                       "Integrated an AI chatbot for book recommendations using the Gemini API.",
                       "Built CI/CD pipeline on Azure DevOps with automated deployment."
                   )),
            Map.of("id", 2, "name", "Online Furniture Website", "year", "2024",
                   "tags", List.of("Full-Stack","Agile","Git/GitHub"),
                   "bullets", List.of(
                       "Built in a 3-person Agile team with sprint planning and Git collaboration.",
                       "Implemented dynamic promotions engine and a 3D interactive room view."
                   )),
            Map.of("id", 3, "name", "Cleaning Service Platform", "year", "2024",
                   "tags", List.of("Java","REST API","Cloud","CRUD"),
                   "bullets", List.of(
                       "Cloud-hosted REST API with full booking lifecycle management.",
                       "Secure checkout flow and role-based access control for customers and staff."
                   )),
            Map.of("id", 4, "name", "Web Security Project", "year", "2024",
                   "tags", List.of("Cybersecurity","OWASP Top 10","SQL Injection","XSS"),
                   "bullets", List.of(
                       "Audited a demo web application against OWASP Top 10 vulnerabilities.",
                       "Patched SQL injection, XSS, and broken authentication issues with documented remediation."
                   )),
            Map.of("id", 5, "name", "Sustainability Awareness Website", "year", "2023",
                   "tags", List.of("JavaScript","HTML/CSS","JWT","Full-Stack"),
                   "bullets", List.of(
                       "Gamified platform with daily quests, in-app currency, and a virtual store.",
                       "JWT-secured authentication with session management across the full stack."
                   ))
        );
    }

    public Map<String, Object> getSkills() {
        return Map.of(
            "pills", List.of(
                Map.of("name", "Java (Web)",      "icon", "☕", "summary", "Spring Boot REST APIs, MVC, and backend services"),
                Map.of("name", "Backend Dev",     "icon", "⚙️", "summary", "Server-side logic, auth, and scalable architecture"),
                Map.of("name", "SQL / Databases", "icon", "🗄️", "summary", "PostgreSQL, schema design, and query optimisation"),
                Map.of("name", "REST APIs",       "icon", "🔗", "summary", "API design, JSON, HTTP methods, and integrations"),
                Map.of("name", "Python",          "icon", "🐍", "summary", "Scripting, automation, and data processing"),
                Map.of("name", "JavaScript",      "icon", "⚡", "summary", "ES6+, async/await, DOM, and browser APIs"),
                Map.of("name", "RPA Tools",       "icon", "🤖", "summary", "UiPath & Power Automate workflow automation"),
                Map.of("name", "React (UI)",      "icon", "⚛️", "summary", "Component-driven UIs with hooks and state management"),
                Map.of("name", "CI/CD & Git",     "icon", "🔄", "summary", "Azure DevOps pipelines, GitHub Actions, branching"),
                Map.of("name", "Agile Practice",  "icon", "🏃", "summary", "Scrum sprints, kanban boards, and team delivery"),
                Map.of("name", "OWASP Top 10",    "icon", "🔐", "summary", "Web security auditing and vulnerability patching")
            ),
            "languages", List.of(
                Map.of("lang", "Burmese", "level", "Native"),
                Map.of("lang", "English", "level", "Proficient"),
                Map.of("lang", "Chinese", "level", "Limited Working Proficiency")
            )
        );
    }
}
