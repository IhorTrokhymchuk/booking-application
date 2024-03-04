package org.example.bookingappliation.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
public class UserLoginResponseDto {
    private String token;
}
