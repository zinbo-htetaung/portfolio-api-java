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
        this.repo         = repo;
        this.ipinfoToken  = ipinfoToken;
    }

    public void logAsync(String ip) {
        System.out.println("🌍 GeoService received IP: " + ip);
        if (ip == null || ip.isBlank()) { System.out.println("⚠️ GeoService: blank IP, skipping"); return; }
        if (isPrivate(ip)) { System.out.println("⚠️ GeoService: private IP " + ip + ", skipping"); return; }
        pool.submit(() -> {
            try {
                GeoResult geo = cache.computeIfAbsent(ip, this::lookup);
                System.out.println("🌍 GeoService resolved: " + ip + " → " + geo.country() + ", " + geo.city());
                var entity = new VisitorLogEntity();
                entity.setCountry(geo.country());
                entity.setCountryCode(geo.countryCode());
                entity.setCity(geo.city());
                repo.save(entity);
            } catch (Exception e) {
                System.out.println("⚠️ GeoService save error: " + e.getMessage());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private GeoResult lookup(String ip) {
        try {
            String url = "https://ipinfo.io/" + ip + "/json?token=" + ipinfoToken;
            System.out.println("🌍 GeoService calling IPinfo for: " + ip);
            Map<String, Object> r = http.getForObject(url, Map.class);
            System.out.println("🌍 GeoService response: " + r);
            if (r != null && !r.containsKey("error")) {
                String country     = (String) r.getOrDefault("country", "Unknown");
                String city        = (String) r.getOrDefault("city", "");
                String region      = (String) r.getOrDefault("region", "");
                // Show city + region for more detail e.g. "Choa Chu Kang, North West"
                String fullCity    = (!city.isBlank() && !region.isBlank() && !city.equals(region))
                                     ? city + ", " + region
                                     : city.isBlank() ? region : city;
                return new GeoResult(countryName(country), country, fullCity);
            }
        } catch (Exception e) {
            System.out.println("⚠️ GeoService lookup error for " + ip + ": " + e.getMessage());
        }
        return new GeoResult("Unknown", "", "");
    }

    // IPinfo returns ISO country code — convert to full name for display
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

    public record GeoResult(String country, String countryCode, String city) {}
}
