package org.example.bookingappliation.service;

import org.example.bookingappliation.dto.user.UserRequestDto;
import org.example.bookingappliation.dto.user.UserResponseDto;

public interface UserService {
    UserResponseDto register(UserRequestDto requestDto);
}
