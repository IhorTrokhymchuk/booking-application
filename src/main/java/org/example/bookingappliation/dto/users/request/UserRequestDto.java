package org.example.bookingappliation.dto.users.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.example.bookingappliation.validation.EmailValues;
import org.example.bookingappliation.validation.PasswordValues;

@Data
public class UserRequestDto {
    @EmailValues
    @NotNull
    private String email;
    @PasswordValues
    private String password;
    @PasswordValues
    private String repeatPassword;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
