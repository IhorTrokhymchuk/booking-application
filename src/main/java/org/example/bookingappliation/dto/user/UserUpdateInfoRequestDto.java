package org.example.bookingappliation.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.bookingappliation.validation.EmailValues;
import org.example.bookingappliation.validation.PasswordValues;

@Data
public class UserUpdateInfoRequestDto {
    @EmailValues
    private String email;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
