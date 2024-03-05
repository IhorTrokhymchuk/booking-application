package org.example.bookingappliation.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.user.*;
import org.example.bookingappliation.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User management", description = "Endpoints manage user info")
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Get info about user",
            description = "Get all info about user")
    public UserResponseDto getInfo(Authentication authentication) {
        return userService.getInfo(authentication.getName());
    }

    @PutMapping("/me")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Update info about user",
            description = "Update email, first and last names user")
    public UserResponseDto updateInfo(
            Authentication authentication,
            @RequestBody @Valid UserUpdateInfoRequestDto requestDto) {
        return userService.updateInfo(authentication.getName(), requestDto);
    }

    @PutMapping("/me/password")
    @PreAuthorize("hasAuthority('CUSTOMER')")
    @Operation(summary = "Update info about user",
            description = "Update email, first and last names user")
    public void updatePassword(
            Authentication authentication,
            @RequestBody @Valid UserUpdatePasswordDto requestDto) {
        userService.updatePassword(authentication.getName(), requestDto);
    }

    @PatchMapping("/{id}/role")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update accommodation by id",
            description = "Update existing accommodation field by id")
    public UserResponseDto updateRoles(@PathVariable Long id,
                                  @RequestBody @Valid UserUpdateRolesRequestDto requestDto) {
        return userService.updateRoles(id, requestDto);
    }
}
