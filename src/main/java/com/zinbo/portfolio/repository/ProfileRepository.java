package com.zinbo.portfolio.repository;
import com.zinbo.portfolio.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {}
