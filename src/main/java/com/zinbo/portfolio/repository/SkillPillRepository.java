package com.zinbo.portfolio.repository;
import com.zinbo.portfolio.entity.SkillPillEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SkillPillRepository extends JpaRepository<SkillPillEntity, Long> {
    List<SkillPillEntity> findAllByOrderByDisplayOrderAsc();
}
