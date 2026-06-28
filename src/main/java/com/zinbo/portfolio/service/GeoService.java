package com.zinbo.portfolio.service;

import com.zinbo.portfolio.entity.VisitorLogEntity;
import com.zinbo.portfolio.repository.VisitorLogRepository;
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

    // Cache IP → result so we don't hammer ip-api.com on repeat visits
    private final ConcurrentHashMap<String, GeoResult> cache = new ConcurrentHashMap<>();

    public GeoService(VisitorLogRepository repo) { this.repo = repo; }

    public void logAsync(String ip) {
        if (isPrivate(ip)) return;
        pool.submit(() -> {
            try {
                GeoResult geo = cache.computeIfAbsent(ip, this::lookup);
                var entity = new VisitorLogEntity();
                entity.setCountry(geo.country);
                entity.setCountryCode(geo.countryCode);
                entity.setCity(geo.city);
                repo.save(entity);
            } catch (Exception e) {
                System.out.println("⚠️ GeoService error: " + e.getMessage());
            }
        });
    }

    @SuppressWarnings("unchecked")
    private GeoResult lookup(String ip) {
        try {
            String url = "http://ip-api.com/json/" + ip + "?fields=status,country,countryCode,city";
            Map<String, Object> r = http.getForObject(url, Map.class);
            if (r != null && "success".equals(r.get("status"))) {
                return new GeoResult(
                    (String) r.getOrDefault("country", "Unknown"),
                    (String) r.getOrDefault("countryCode", ""),
                    (String) r.getOrDefault("city", "")
                );
            }
        } catch (Exception ignored) {}
        return new GeoResult("Unknown", "", "");
    }

    private boolean isPrivate(String ip) {
        if (ip == null || ip.isBlank()) return true;
        return ip.equals("127.0.0.1") || ip.equals("::1") ||
               ip.startsWith("10.") || ip.startsWith("192.168.") ||
               ip.startsWith("172.16.") || ip.startsWith("172.17.") ||
               ip.startsWith("172.18.") || ip.startsWith("172.19.") ||
               ip.startsWith("172.2") || ip.startsWith("172.30.") ||
               ip.startsWith("172.31.");
    }

    public record GeoResult(String country, String countryCode, String city) {}
}
