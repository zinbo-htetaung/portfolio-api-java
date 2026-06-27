package com.zinbo.portfolio.repository;

import com.zinbo.portfolio.entity.PageViewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PageViewRepository extends JpaRepository<PageViewEntity, String> {}
