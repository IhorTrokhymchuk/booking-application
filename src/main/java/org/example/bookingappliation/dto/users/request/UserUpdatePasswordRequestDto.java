package org.example.bookingappliation.dto.users.request;

import lombok.Data;
import org.example.bookingappliation.validation.PasswordValues;

@Data
public class UserUpdatePasswordRequestDto {
    @PasswordValues
    private String oldPassword;
    @PasswordValues
    private String newPassword;
    @PasswordValues
    private String repeatNewPassword;
}
