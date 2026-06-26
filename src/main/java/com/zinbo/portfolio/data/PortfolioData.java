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
            "subhead", "Software Developer · Full-Stack Developer · RPA Engineer · Open to Work",
            "bio",     "Diploma in Information Technology graduate from Singapore Polytechnic with a habit of turning repetitive processes into high-impact automations and ideas into production-ready, full-stack applications.",
            "photo",   "/ProfilePic.webp"
        );
    }

    public Map<String, Object> getAbout() {
        return Map.of(
            "bio", """
                I graduated with a Diploma in Information Technology (Software Development) from
                Singapore Polytechnic. During my year-long internship at PSA Corporation Limited,
                I engineered and delivered production RPA workflows that eliminated thousands of
                manual hours across departments. Beyond automation, I specialize in building robust
                full-stack web applications, managing the entire software lifecycle from secure
                REST API design and database architecture to creating polished frontend user experiences.""",
            "highlights", List.of(
                "Full-stack development — Java, Spring Boot, JavaScript, React, Node.js, Express.js",
                "Robotic Process Automation (RPA) — UiPath, Power Automate, Python",
                "Database engineering — PostgreSQL, SQL schema design, and query optimization",
                "REST API design — JWT authentication, web services, and secure integrations",
                "CI/CD pipelines — Azure DevOps, GitHub Actions, and cloud deployment",
                "Cybersecurity — OWASP Top 10 security auditing and defensive remediation"
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
                    "Computer Science Fundamentals",
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
            "Engineered and delivered 5+ production RPA workflows using UiPath, Power Automate, and Python, automating data extraction, report generation, and email handling across port terminal operations.",
            "Engineered an attendance automation report deployed across 18 departments, eliminating manual tracking and saving an estimated 6,500+ staff-hours annually.",
            "Automated a container onboarding workflow that eliminated repetitive manual data entry, replacing a 10–20 min process and saving 180+ hours annually while improving consistency and process reliability.",
            "Restructured fragmented reporting workflows into a centralized automation system, reducing workflow complexity and improving maintainability and scalability across departments.",
            "Performed systematic debugging, documentation, and maintenance of live automation solutions, applying Agile methodologies across the full project lifecycle.",
            "Leveraged Microsoft 365 tools to build Power BI dashboards, integrate SharePoint with Power Automate for workflow triggers, and extract and analyze operational data using SQL.",
            "Internship extended by 2 months beyond official end date at PSA's request to onboard and support incoming automation projects, reflecting strong performance and trust."
        ));

        var baeKitchen = new java.util.LinkedHashMap<String, Object>();
        baeKitchen.put("id",      2);
        baeKitchen.put("role",    "Owner");
        baeKitchen.put("company", "Bae Kitchen (Self-Employed)");
        baeKitchen.put("type",    "Myanmar");
        baeKitchen.put("period",  "2021 – 2022");
        baeKitchen.put("bullets", List.of(
            "Founded and led a self-run online F&B business end-to-end, managing operations, sourcing, cooking, delivery, and customer service.",
            "Demonstrated ownership, decision-making, and accountability for business outcomes in a fast-paced, self-managed environment.",
            "Used Facebook as the primary sales channel, independently managing orders, enquiries, promotions, and building a loyal repeat customer base."
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
            "Contributed to a full-scale deployment of 50+ CCTV cameras across a supermarket site, handling structured cable management, mounting, and system configuration.",
            "Developed hands-on hardware troubleshooting and diagnostic skills across multiple client site installations.",
            "Gained practical experience in IT infrastructure setup, network configuration, and on-site client support."
        ));
        cloud9.put("link", Map.of(
            "label", "View on Facebook",
            "url",   "https://www.facebook.com/cloud9itsolution"
        ));

        return List.of(psa, baeKitchen, cloud9);
    }

    public List<Map<String, Object>> getProjects() {
        return List.of(
            Map.of("id", 1, "name", "Personal Portfolio Website (Full Stack)", "year", "2025",
                   "tags", List.of("React 19", "Spring Boot", "Java", "Vercel", "Railway"),
                   "bullets", List.of(
                       "Designed and launched a full-stack personal portfolio at zinbohtetaung.com with a React 19 frontend and a Spring Boot REST API, connected via a custom subdomain with full DNS and SSL configuration.",
                       "Secured the API with API key authentication, JWT-based admin login, per-IP rate limiting (Bucket4j), and a configured CORS policy restricting access to approved origins.",
                       "Built a contact form pipeline integrating hCaptcha verification, server-side token validation, honeypot spam detection, and the Resend API for transactional email delivery.",
                       "Integrated an AI-powered chatbot (Gemini 2.5 Flash), interactive Canvas animations, glassmorphism UI with auto light/dark theme, and verified SEO best practices via Google Search Console."
                   )),
            Map.of("id", 2, "name", "Library Book Rental Platform", "year", "2025",
                   "tags", List.of("Java", "Node.js", "PostgreSQL", "Azure", "CI/CD", "JWT", "Playwright"),
                   "bullets", List.of(
                       "Developed an online rental platform with rental tracking, borrowing history, and an in-app book preview feature, backed by a relational PostgreSQL database with normalized tables.",
                       "Integrated an AI-powered chatbot for real-time rental support and book recommendations, with secure JWT authentication and full CRUD admin management.",
                       "Hosted on Microsoft Azure with an automated CI/CD pipeline, cutting release cycle time and eliminating manual deployment effort."
                   )),
            Map.of("id", 3, "name", "Online Furniture Website", "year", "2024",
                   "tags", List.of("Agile", "Git/GitHub", "Full-Stack", "JavaScript"),
                   "bullets", List.of(
                       "Collaborated in a 3-person Agile team with weekly requirement reviews, sprint planning, and requirement validation each sprint.",
                       "Delivered a dynamic promotions display and a 3D interactive room-view feature, practicing version control and Git-based collaboration with peer code reviews."
                   )),
            Map.of("id", 4, "name", "Cleaning Service Platform", "year", "2024",
                   "tags", List.of("Java", "REST API", "Cloud"),
                   "bullets", List.of(
                       "Co-developed a cloud-hosted REST API with a full booking lifecycle (Pending -> Approved -> Completed), booking history, secure checkout, and admin status management.",
                       "Designed a relational database schema for service listings, customer accounts, and booking records with full CRUD operations exposed via web services."
                   )),
            Map.of("id", 5, "name", "Web Security Project", "year", "2024",
                   "tags", List.of("Cybersecurity", "OWASP Top 10"),
                   "bullets", List.of(
                       "Audited and patched a demo web application against OWASP Top 10, fixing SQL injection, XSS, and authentication bypass flaws via parameterized queries, input sanitization, and secure session handling.",
                       "Documented vulnerability findings with severity assessments and applied targeted code-level fixes, demonstrating both offensive identification and defensive remediation skills."
                   )),
            Map.of("id", 6, "name", "Sustainability Awareness Website", "year", "2023",
                   "tags", List.of("JavaScript", "HTML/CSS", "JWT"),
                   "bullets", List.of(
                       "Created a gamified platform with daily sustainability quests, in-app currency, and a virtual store, backed by a full database layer with user progress tracking.",
                       "Developed a responsive frontend using JavaScript, HTML/CSS, with backend handling user accounts, currency balances, quest states, and secure store transactions via JWT."
                   ))
        );
    }

    public Map<String, Object> getSkills() {
        return Map.of(
            "pills", List.of(
                Map.of("name", "Java",           "icon", "☕", "summary", "Spring Boot REST APIs, MVC, and backend development"),
                Map.of("name", "JavaScript",     "icon", "⚡", "summary", "Node.js, Express.js, async/await, and modern web apps"),
                Map.of("name", "TypeScript",     "icon", "📘", "summary", "Typed JavaScript for safer, more maintainable code bases"),
                Map.of("name", "Python",         "icon", "🐍", "summary", "Scripting and automation — heavily utilized in production RPA workflows"),
                Map.of("name", "SQL",            "icon", "🗄️", "summary", "PostgreSQL, schema design, and relational query optimization"),
                Map.of("name", "React",          "icon", "⚛️", "summary", "Component-driven UIs with hooks, Vite, and complex state management"),
                Map.of("name", "REST APIs",      "icon", "🔗", "summary", "API design, JWT authentication, and secure third-party web services"),
                Map.of("name", "UiPath",         "icon", "🤖", "summary", "Production-scale enterprise RPA workflows for complex operations"),
                Map.of("name", "Power Automate", "icon", "⚙️", "summary", "Microsoft M365 stack integration, SharePoint triggers, and flows"),
                Map.of("name", "CI/CD & Git",    "icon", "🔄", "summary", "Azure DevOps pipelines, GitHub Actions, and version control branches"),
                Map.of("name", "Docker",         "icon", "🐳", "summary", "Containerizing software applications for consistent environments"),
                Map.of("name", "Cloud Deploy",   "icon", "☁️", "summary", "Hosting apps securely on Vercel, Railway, Azure, and AWS"),
                Map.of("name", "OWASP Top 10",   "icon", "🔐", "summary", "Security auditing, vulnerability identification, and defensive patching"),
                Map.of("name", "Agile / Scrum",  "icon", "🏃", "summary", "Sprint planning, daily stand-ups, reviews, and team delivery")
            ),
            "languages", List.of(
                Map.of("lang", "Burmese", "level", "Native"),
                Map.of("lang", "English", "level", "Proficient"),
                Map.of("lang", "Chinese", "level", "Limited Working Proficiency")
            )
        );
    }
}