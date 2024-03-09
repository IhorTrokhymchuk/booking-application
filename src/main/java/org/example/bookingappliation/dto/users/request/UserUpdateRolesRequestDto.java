package org.example.bookingappliation.dto.users.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.bookingappliation.model.user.RoleType;

@Data
public class UserUpdateRolesRequestDto {
    @NotNull
    private RoleType.RoleName roleName;
}
