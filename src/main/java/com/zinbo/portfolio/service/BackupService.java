package com.zinbo.portfolio.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zinbo.portfolio.entity.DataBackupEntity;
import com.zinbo.portfolio.repository.DataBackupRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BackupService {

    private final DataBackupRepository repo;
    private final ObjectMapper mapper = new ObjectMapper();

    public BackupService(DataBackupRepository repo) { this.repo = repo; }

    public void snapshot(String section, Object data) {
        try {
            var b = new DataBackupEntity();
            b.setSection(section);
            b.setDataJson(mapper.writeValueAsString(data));
            repo.save(b);
            repo.pruneOldBackups(section);
        } catch (Exception e) {
            System.out.println("⚠️ Backup failed for " + section + ": " + e.getMessage());
        }
    }

    public List<Map<String, Object>> listBySection(String section) {
        return repo.findTop10BySectionOrderByCreatedAtDesc(section).stream().map(b ->
            Map.<String, Object>of(
                "id",        b.getId(),
                "section",   b.getSection(),
                "createdAt", b.getCreatedAt().toString(),
                "preview",   truncate(b.getDataJson())
            )
        ).toList();
    }

    public List<Map<String, Object>> listAll() {
        return repo.findTop30ByOrderByCreatedAtDesc().stream().map(b ->
            Map.<String, Object>of(
                "id",        b.getId(),
                "section",   b.getSection(),
                "createdAt", b.getCreatedAt().toString(),
                "preview",   truncate(b.getDataJson())
            )
        ).toList();
    }

    public String getJson(Long id) {
        return repo.findById(id).map(DataBackupEntity::getDataJson).orElse(null);
    }

    private String truncate(String s) {
        if (s == null) return "";
        return s.length() > 120 ? s.substring(0, 120) + "…" : s;
    }
}
