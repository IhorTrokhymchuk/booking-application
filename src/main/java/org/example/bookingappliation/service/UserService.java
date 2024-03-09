package org.example.bookingappliation.service;

import org.example.bookingappliation.dto.users.request.UserRequestDto;
import org.example.bookingappliation.dto.users.request.UserUpdateInfoRequestDto;
import org.example.bookingappliation.dto.users.request.UserUpdatePasswordRequestDto;
import org.example.bookingappliation.dto.users.request.UserUpdateRolesRequestDto;
import org.example.bookingappliation.dto.users.response.UserResponseDto;

public interface UserService {
    UserResponseDto getInfo(String email);

    UserResponseDto updateInfo(String email,
                               UserUpdateInfoRequestDto requestDto);

    void updatePassword(String email, UserUpdatePasswordRequestDto requestDto);

    UserResponseDto updateRoles(Long id, UserUpdateRolesRequestDto requestDto);

    UserResponseDto register(UserRequestDto requestDto);
}
