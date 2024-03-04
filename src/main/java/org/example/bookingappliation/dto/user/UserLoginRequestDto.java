package org.example.bookingappliation.dto.user;

import lombok.Data;
import org.example.bookingappliation.validation.EmailValues;
import org.example.bookingappliation.validation.PasswordValues;

@Data
public class UserLoginRequestDto {
    @EmailValues
    private String email;
    @PasswordValues
    private String password;
}
