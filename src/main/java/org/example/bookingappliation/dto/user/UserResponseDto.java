package org.example.bookingappliation.dto.user;

import java.util.Set;
import lombok.Data;

@Data
public class UserResponseDto {
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roleNames;
}
