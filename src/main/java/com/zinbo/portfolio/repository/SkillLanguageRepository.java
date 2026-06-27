package com.zinbo.portfolio.repository;
import com.zinbo.portfolio.entity.SkillLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface SkillLanguageRepository extends JpaRepository<SkillLanguageEntity, Long> {
    List<SkillLanguageEntity> findAllByOrderByDisplayOrderAsc();
}
