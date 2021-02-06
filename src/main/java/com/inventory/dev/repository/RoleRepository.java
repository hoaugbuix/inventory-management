package com.inventory.dev.repository;

import com.inventory.dev.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    public RoleEntity findByRoleName(String role);
}
