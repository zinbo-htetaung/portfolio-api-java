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
        profile.put("tagline",     "Software Developer · Automation Engineer · Open to Opportunities");
        profile.put("email",       "zinbohtetaung@gmail.com");
        profile.put("phonenumber", "+65 98509604");
        profile.put("links",       links);
        return profile;
    }

    public Map<String, Object> getHero() {
        return Map.of(
            "subhead", "Software Developer · Automation Engineer · Open to Work",
            "bio",     "Information Technology graduate from Singapore Polytechnic specializing in building secure full-stack applications and high-impact enterprise automations that eliminate thousands of manual hours.",
            "photo",   "/ProfilePic.webp"
        );
    }

    public Map<String, Object> getAbout() {
        return Map.of(
            "bio", """
                I graduated with a Diploma in Information Technology (Software Development) from
                Singapore Polytechnic. During my year-long internship at PSA Corporation Limited,
                I engineered production RPA workflows that automated manual operations across terminal
                departments. Beyond automation, I specialize in full-stack web development, handling
                everything from relational database design and secure REST APIs to dynamic frontend UIs.""",
            "highlights", List.of(
                "Full-stack development — Java, Spring Boot, JavaScript, React, Node.js",
                "Robotic Process Automation — UiPath, Power Automate, Python scripting",
                "Database engineering — PostgreSQL schema design and query optimization",
                "REST API design — JWT authentication, secure endpoints, and web services",
                "CI/CD & Cloud — Azure DevOps pipelines, Git, Vercel, and Railway deployment",
                "Cybersecurity — OWASP Top 10 auditing and defensive vulnerability patching"
            )
        );
    }

    public List<Map<String, Object>> getEducation() {
        return List.of(
            Map.of(
                "id",          1,
                "institution", "Singapore Polytechnic",
                "degree",      "Diploma in Information Technology (Software Development)",
                "period",      "2023 – 2026",
                "coursework",  List.of(
                    "Data Structures & Algorithms",
                    "Full-Stack Web Development",
                    "Database Systems & SQL",
                    "Cybersecurity (OWASP)",
                    "UI/UX Design & Figma",
                    "CI/CD Pipelines",
                    "Agile Software Engineering",
                    "Automated Testing (Playwright)"
                )
            )
        );
    }

    public List<Map<String, Object>> getExperiences() {
        var psa = new java.util.LinkedHashMap<String, Object>();
        psa.put("id",      1);
        psa.put("role",    "Robotic Process Automation Intern");
        psa.put("company", "PSA Corporation Limited");
        psa.put("type",    "Year-long Internship · Extended by 2 months");
        psa.put("period",  "2025 – 2026");
        psa.put("bullets", List.of(
            "Engineered 5+ production RPA workflows using UiPath, Power Automate, and Python across port terminal operations.",
            "Deployed an attendance automation report across 18 departments, saving an estimated 6,500+ staff-hours annually.",
            "Automated a container onboarding workflow, replacing a manual process and saving 180+ hours annually.",
            "Centralized scattered reporting systems to improve maintainability and lifecycle scalability using Agile methodologies.",
            "Built Power BI dashboards, integrated SharePoint workflow triggers, and extracted operational data using SQL.",
            "Extended for 2 months beyond official internship end date at PSA's request to onboard and support incoming projects."
        ));

        var baeKitchen = new java.util.LinkedHashMap<String, Object>();
        baeKitchen.put("id",      2);
        baeKitchen.put("role",    "Owner");
        baeKitchen.put("company", "Bae Kitchen (Self-Employed)");
        baeKitchen.put("type",    "Myanmar");
        baeKitchen.put("period",  "2021 – 2022");
        baeKitchen.put("bullets", List.of(
            "Founded and ran an online F&B business end-to-end, managing operations, finances, and delivery logistics.",
            "Managed customer acquisition, orders, and promotions independently via Facebook to build a loyal client base."
        ));
        baeKitchen.put("link", Map.of(
            "label", "View on Facebook",
            "url",   "https://www.facebook.com/Bae.tgi/"
        ));

        var cloud9 = new java.util.LinkedHashMap<String, Object>();
        cloud9.put("id",      3);
        cloud9.put("role",    "IT Trainee");
        cloud9.put("company", "Cloud-9 IT Solutions");
        cloud9.put("type",    "Myanmar");
        cloud9.put("period",  "2020 – 2022");
        cloud9.put("bullets", List.of(
            "Assisted in full-scale deployment and hardware configuration of 50+ CCTV cameras across a supermarket site.",
            "Provided on-site client technical support, infrastructure troubleshooting, and diagnostic services."
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
                   "tags", List.of("React 19", "Spring Boot", "Vercel", "Railway"),
                   "bullets", List.of(
                       "Launched a full-stack portfolio at zinbohtetaung.com with a React 19 frontend and a Spring Boot REST API backend.",
                       "Secured API with custom subdomain SSL, JWT admin login, rate limiting (Bucket4j), and precise CORS policies.",
                       "Integrated a verified hCaptcha contact form pipeline, transactional Resend email API, and a Gemini-powered chatbot."
                   )),
            Map.of("id", 2, "name", "Library Book Rental Platform", "year", "2025",
                   "tags", List.of("Java", "Node.js", "PostgreSQL", "Azure", "CI/CD"),
                   "bullets", List.of(
                       "Developed a rental management system featuring tracking, secure JWT authentication, and full CRUD admin dashboards.",
                       "Backed by a normalized PostgreSQL database and hosted on Azure with an automated, zero-manual CI/CD pipeline."
                   )),
            Map.of("id", 3, "name", "Online Furniture Website", "year", "2024",
                   "tags", List.of("Agile", "Git/GitHub", "Full-Stack"),
                   "bullets", List.of(
                       "Collaborated in a 3-person Agile team managing features, branch workflows, and peer reviews each sprint.",
                       "Delivered a dynamic user promotions engine alongside an interactive 3D product room-view layout."
                   )),
            Map.of("id", 4, "name", "Cleaning Service Platform", "year", "2024",
                   "tags", List.of("Java", "REST API", "PostgreSQL"),
                   "bullets", List.of(
                       "Co-developed a cloud-hosted REST API exposing full CRUD operations for service listings and customer accounts.",
                       "Engineered a secure relational booking lifecycle database matching status modifications from pending to complete."
                   )),
            Map.of("id", 5, "name", "Web Security Project", "year", "2024",
                   "tags", List.of("Cybersecurity", "OWASP Top 10"),
                   "bullets", List.of(
                       "Audited and patched a web application against OWASP Top 10 security flaws, including SQL injection and XSS exploits.",
                       "Applied targeted code remediation via parameterized queries and input sanitization, generating risk severity docs."
                   )),
            Map.of("id", 6, "name", "Sustainability Awareness Website", "year", "2023",
                   "tags", List.of("JavaScript", "HTML/CSS", "JWT"),
                   "bullets", List.of(
                       "Created a gamified web platform with daily quests, custom store token economies, and user leaderboards.",
                       "Secured persistent user profile progress, authentication states, and balances using backend JWT validation."
                   ))
        );
    }

    public Map<String, Object> getSkills() {
        return Map.of(
            "pills", List.of(
                Map.of("name", "Java",           "icon", "☕", "summary", "Spring Boot REST APIs, MVC, and backend development"),
                Map.of("name", "JavaScript",     "icon", "⚡", "summary", "Node.js, Express.js, async/await, and modern web apps"),
                Map.of("name", "TypeScript",     "icon", "📘", "summary", "Typed JavaScript for safer, more maintainable code bases"),
                Map.of("name", "Python",         "icon", "🐍", "summary", "Scripting and automation used in production RPA workflows"),
                Map.of("name", "SQL",            "icon", "🗄️", "summary", "PostgreSQL, schema design, and relational query optimization"),
                Map.of("name", "React",          "icon", "⚛️", "summary", "Component UIs with hooks, Vite, and state management"),
                Map.of("name", "REST APIs",      "icon", "🔗", "summary", "API design, JWT authentication, and secure integrations"),
                Map.of("name", "UiPath",         "icon", "🤖", "summary", "Production enterprise RPA workflows for complex operations"),
                Map.of("name", "Power Automate", "icon", "⚙️", "summary", "Microsoft M365 stack integration, SharePoint triggers, and flows"),
                Map.of("name", "CI/CD & Git",    "icon", "🔄", "summary", "Azure DevOps pipelines, GitHub Actions, and version control branches"),
                Map.of("name", "Docker",         "icon", "🐳", "summary", "Containerizing software applications for consistent environments"),
                Map.of("name", "Cloud Deploy",   "icon", "☁️", "summary", "Hosting apps securely on Vercel, Railway, Azure, and AWS"),
                Map.of("name", "OWASP Top 10",   "icon", "🔐", "summary", "Security auditing, vulnerability patching, and defense remediation"),
                Map.of("name", "Agile / Scrum",  "icon", "🏃", "summary", "Sprint planning, daily stand-ups, and team delivery cycles")
            ),
            "languages", List.of(
                Map.of("lang", "Burmese", "level", "Native"),
                Map.of("lang", "English", "level", "Proficient"),
                Map.of("lang", "Chinese", "level", "Limited Working Proficiency")
            )
        );
    }
}