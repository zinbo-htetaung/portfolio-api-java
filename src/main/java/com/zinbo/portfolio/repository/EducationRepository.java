package com.zinbo.portfolio.repository;
import com.zinbo.portfolio.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface EducationRepository extends JpaRepository<EducationEntity, Long> {
    List<EducationEntity> findAllByOrderByDisplayOrderAsc();
}
