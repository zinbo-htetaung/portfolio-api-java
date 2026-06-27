package com.zinbo.portfolio.repository;

import com.zinbo.portfolio.entity.DataBackupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DataBackupRepository extends JpaRepository<DataBackupEntity, Long> {

    List<DataBackupEntity> findTop10BySectionOrderByCreatedAtDesc(String section);

    List<DataBackupEntity> findTop30ByOrderByCreatedAtDesc();

    // Keep only the latest 10 per section — delete older ones
    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM data_backups
        WHERE section = :section
          AND id NOT IN (
            SELECT id FROM data_backups
            WHERE section = :section
            ORDER BY created_at DESC
            LIMIT 10
          )
        """, nativeQuery = true)
    void pruneOldBackups(String section);
}
