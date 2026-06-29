package com.zinbo.portfolio.service;

import com.zinbo.portfolio.entity.VisitorLogEntity;
import com.zinbo.portfolio.repository.VisitorLogRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class GeoService {

    private final VisitorLogRepository repo;
    private final RestTemplate http = new RestTemplate();
    private final ExecutorService pool = Executors.newSingleThreadExecutor();
    private final String ipinfoToken;

    private final ConcurrentHashMap<String, GeoResult> cache = new ConcurrentHashMap<>();

    public GeoService(VisitorLogRepository repo,
                      @Value("${app.ipinfo.token:}") String ipinfoToken) {
        this.repo        = repo;
        this.ipinfoToken = ipinfoToken;
    }

    public void logAsync(String ip, VisitorData data) {
        System.out.println("🌍 GeoService received IP: " + ip);
        if (ip == null || ip.isBlank()) { System.out.println("⚠️ blank IP, skipping"); return; }
        if (isPrivate(ip)) { System.out.println("⚠️ private IP " + ip + ", skipping"); return; }

        pool.submit(() -> {
            try {
                GeoResult geo = cache.computeIfAbsent(ip, this::lookup);
                System.out.println("🌍 resolved: " + ip + " → " + geo.country() + ", " + geo.city() + " | " + geo.isp());

                var e = new VisitorLogEntity();
                e.setCountry(geo.country());
                e.setCountryCode(geo.countryCode());
                e.setCity(geo.city());
                e.setIsp(geo.isp());
                e.setBrowser(data.browser());
                e.setOs(data.os());
                e.setDevice(data.device());
                e.setReferrer(data.referrer());
                e.setLanguage(data.language());
                e.setScreen(data.screen());
                e.setTimezone(data.timezone());
                e.setDarkMode(data.darkMode());
                repo.save(e);
            } catch (Exception ex) {
                System.out.println("⚠️ GeoService error: " + ex.getMessage());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private GeoResult lookup(String ip) {
        try {
            String url = "https://ipinfo.io/" + ip + "/json?token=" + ipinfoToken;
            Map<String, Object> r = http.getForObject(url, Map.class);
            System.out.println("🌍 IPinfo response: " + r);
            if (r != null && !r.containsKey("error")) {
                String countryCode = (String) r.getOrDefault("country", "");
                String city        = (String) r.getOrDefault("city", "");
                String region      = (String) r.getOrDefault("region", "");
                String org         = (String) r.getOrDefault("org", "");
                // Strip AS number from org e.g. "AS7473 Singtel" → "Singtel"
                String isp = org.replaceFirst("^AS\\d+\\s*", "");
                String fullCity = (!city.isBlank() && !region.isBlank() && !city.equals(region))
                                  ? city + ", " + region
                                  : city.isBlank() ? region : city;
                return new GeoResult(countryName(countryCode), countryCode, fullCity, isp);
            }
        } catch (Exception e) {
            System.out.println("⚠️ IPinfo error for " + ip + ": " + e.getMessage());
        }
        return new GeoResult("Unknown", "", "", "");
    }

    private String countryName(String code) {
        return switch (code) {
            case "SG" -> "Singapore";
            case "MY" -> "Malaysia";
            case "US" -> "United States";
            case "GB" -> "United Kingdom";
            case "AU" -> "Australia";
            case "IN" -> "India";
            case "CN" -> "China";
            case "JP" -> "Japan";
            case "KR" -> "South Korea";
            case "TH" -> "Thailand";
            case "ID" -> "Indonesia";
            case "PH" -> "Philippines";
            case "VN" -> "Vietnam";
            case "DE" -> "Germany";
            case "FR" -> "France";
            case "CA" -> "Canada";
            case "NZ" -> "New Zealand";
            case "HK" -> "Hong Kong";
            case "TW" -> "Taiwan";
            default   -> code;
        };
    }

    public boolean isPrivate(String ip) {
        if (ip == null || ip.isBlank()) return true;
        return ip.equals("127.0.0.1") || ip.equals("::1") ||
               ip.startsWith("10.") || ip.startsWith("192.168.") ||
               ip.startsWith("172.16.") || ip.startsWith("172.17.") ||
               ip.startsWith("172.18.") || ip.startsWith("172.19.") ||
               ip.startsWith("172.2")   || ip.startsWith("172.30.") ||
               ip.startsWith("172.31.");
    }

    public record GeoResult(String country, String countryCode, String city, String isp) {}
    public record VisitorData(String browser, String os, String device,
                              String referrer, String language, String screen,
                              String timezone, Boolean darkMode) {}
}
