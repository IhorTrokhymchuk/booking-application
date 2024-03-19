package org.example.bookingappliation.repository.roletype;

import java.util.List;
import org.example.bookingappliation.model.user.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleTypeRepository extends JpaRepository<RoleType, Long> {
    RoleType findRoleTypeByName(RoleType.RoleName roleName);

    List<RoleType> findRoleTypesByNameIn(List<RoleType.RoleName> roleNames);
}
