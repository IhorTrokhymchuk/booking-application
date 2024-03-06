package org.example.bookingappliation.dto.user;

import lombok.Data;
import org.example.bookingappliation.validation.PasswordValues;

@Data
public class UserUpdatePasswordDto {
    @PasswordValues
    private String oldPassword;
    @PasswordValues
    private String newPassword;
    @PasswordValues
    private String repeatNewPassword;
}
