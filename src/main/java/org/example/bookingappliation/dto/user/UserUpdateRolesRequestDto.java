package org.example.bookingappliation.dto.user;

import lombok.Data;
import org.example.bookingappliation.validation.UpdateRoleIdValues;

@Data
public class UserUpdateRolesRequestDto {
    @UpdateRoleIdValues
    private Long roleId;
}
