package org.example.bookingappliation.service;

import org.example.bookingappliation.dto.user.UserRequestDto;
import org.example.bookingappliation.dto.user.UserResponseDto;
import org.example.bookingappliation.dto.user.UserUpdateInfoRequestDto;
import org.example.bookingappliation.dto.user.UserUpdatePasswordDto;
import org.example.bookingappliation.dto.user.UserUpdateRolesRequestDto;

public interface UserService {
    UserResponseDto getInfo(String email);

    UserResponseDto updateInfo(String email,
                               UserUpdateInfoRequestDto requestDto);

    void updatePassword(String email, UserUpdatePasswordDto requestDto);

    UserResponseDto updateRoles(Long id, UserUpdateRolesRequestDto requestDto);

    UserResponseDto register(UserRequestDto requestDto);
}
