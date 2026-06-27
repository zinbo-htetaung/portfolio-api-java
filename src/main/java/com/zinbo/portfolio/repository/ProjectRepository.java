package com.zinbo.portfolio.repository;
import com.zinbo.portfolio.entity.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
    List<ProjectEntity> findAllByOrderByDisplayOrderAsc();
}
