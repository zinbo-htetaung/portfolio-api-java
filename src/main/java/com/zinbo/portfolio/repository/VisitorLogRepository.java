package com.zinbo.portfolio.repository;

import com.zinbo.portfolio.entity.VisitorLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VisitorLogRepository extends JpaRepository<VisitorLogEntity, Long> {

    List<VisitorLogEntity> findTop20ByOrderByVisitedAtDesc();

    @Query("SELECT v.country, v.countryCode, COUNT(v) as cnt FROM VisitorLogEntity v " +
           "WHERE v.country IS NOT NULL GROUP BY v.country, v.countryCode ORDER BY cnt DESC")
    List<Object[]> countByCountry();
}
