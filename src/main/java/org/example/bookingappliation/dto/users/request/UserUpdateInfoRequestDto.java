package org.example.bookingappliation.dto.users.request;

import lombok.Data;
import org.example.bookingappliation.validation.EmailValues;

@Data
public class UserUpdateInfoRequestDto {
    @EmailValues
    private String email;
    private String firstName;
    private String lastName;
}
