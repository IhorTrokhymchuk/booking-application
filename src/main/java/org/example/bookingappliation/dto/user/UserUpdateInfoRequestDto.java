package org.example.bookingappliation.dto.user;

import lombok.Data;
import org.example.bookingappliation.validation.EmailValues;

@Data
public class UserUpdateInfoRequestDto {
    @EmailValues
    private String email;
    private String firstName;
    private String lastName;
}
