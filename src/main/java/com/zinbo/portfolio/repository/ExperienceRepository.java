package com.zinbo.portfolio.repository;
import com.zinbo.portfolio.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Long> {
    List<ExperienceEntity> findAllByOrderByDisplayOrderAsc();
}
