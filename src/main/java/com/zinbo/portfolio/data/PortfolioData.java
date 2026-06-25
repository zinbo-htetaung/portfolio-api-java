package com.zinbo.portfolio.data;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

// Single source of truth — mirrors the frontend data.js
// In a future iteration, these would come from a PostgreSQL database via JPA.
@Component
public class PortfolioData {

    public Map<String, Object> getProfile() {
        return Map.of(
            "name",     "Zin Bo Htet Aung",
            "location", "Singapore",
            "tagline",  "Software Engineer · Open to Opportunities",
            "email",    "zinbohtetaung@gmail.com",
            "links", Map.of(
                "github",    "https://github.com/zinbo-htetaung",
                "linkedin",  "https://www.linkedin.com/in/zinbohtetaung/",
                "portfolio", "https://zinbohtetaung.com"
            )
        );
    }

    public Map<String, Object> getHero() {
        return Map.of(
            "subhead", "Full-Stack Developer · Intelligent Automations · Open to Work",
            "bio",     "Fresh IT graduate passionate about building reliable software and intelligent automation solutions that create real impact."
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
            Map.of("id", 1, "name", "Library Book Rental Platform",    "year", "2025",
                   "tags", List.of("Java","Node.js","PostgreSQL","Azure","CI/CD","JWT"),
                   "description", "Full-stack rental platform with AI chatbot, JWT auth, CRUD admin dashboard, and Azure CI/CD pipeline."),
            Map.of("id", 2, "name", "Online Furniture Website",         "year", "2024",
                   "tags", List.of("Full-Stack","Agile","Git/GitHub"),
                   "description", "Built in a 3-person Agile team with dynamic promotions and a 3D room view."),
            Map.of("id", 3, "name", "Cleaning Service Platform",        "year", "2024",
                   "tags", List.of("Java","REST API","Cloud","CRUD"),
                   "description", "Cloud-hosted REST API with booking lifecycle, secure checkout, and role-based access control."),
            Map.of("id", 4, "name", "Web Security Project",             "year", "2024",
                   "tags", List.of("Cybersecurity","OWASP Top 10","SQL Injection","XSS"),
                   "description", "Audited and patched a demo app against OWASP Top 10 vulnerabilities."),
            Map.of("id", 5, "name", "Sustainability Awareness Website", "year", "2023",
                   "tags", List.of("JavaScript","HTML/CSS","JWT","Full-Stack"),
                   "description", "Gamified platform with daily quests, in-app currency, virtual store, and JWT authentication.")
        );
    }

    public Map<String, Object> getSkills() {
        return Map.of(
            "technical", List.of(
                Map.of("name", "Java (Web)",      "icon", "☕"),
                Map.of("name", "Backend Dev",     "icon", "⚙️"),
                Map.of("name", "SQL / Databases", "icon", "🗄️"),
                Map.of("name", "REST APIs",       "icon", "🔗"),
                Map.of("name", "Python",          "icon", "🐍"),
                Map.of("name", "JavaScript",      "icon", "⚡"),
                Map.of("name", "RPA Tools",       "icon", "🤖"),
                Map.of("name", "React (UI)",      "icon", "⚛️"),
                Map.of("name", "CI/CD & Git",     "icon", "🔄"),
                Map.of("name", "Agile Practice",  "icon", "🏃"),
                Map.of("name", "OWASP Top 10",    "icon", "🔐")
            ),
            "languages", List.of(
                Map.of("lang", "Burmese", "level", "Native"),
                Map.of("lang", "English", "level", "Proficient"),
                Map.of("lang", "Chinese", "level", "Limited Working Proficiency")
            )
        );
    }
}
