package org.example.bookingappliation.repository;

import org.example.bookingappliation.model.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
    RoleType findRoleTypeByName(RoleType.RoleName roleName);
}
