package com.zinbo.portfolio.repository;
import com.zinbo.portfolio.entity.HeroEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface HeroRepository extends JpaRepository<HeroEntity, Long> {}
