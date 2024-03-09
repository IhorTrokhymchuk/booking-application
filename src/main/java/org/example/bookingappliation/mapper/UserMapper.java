package org.example.bookingappliation.mapper;

import java.util.Set;
import java.util.stream.Collectors;
import org.example.bookingappliation.config.MapperConfig;
import org.example.bookingappliation.dto.users.request.UserRequestDto;
import org.example.bookingappliation.dto.users.response.UserResponseDto;
import org.example.bookingappliation.dto.users.request.UserUpdateInfoRequestDto;
import org.example.bookingappliation.model.user.RoleType;
import org.example.bookingappliation.model.user.User;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(config = MapperConfig.class,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserMapper {
    @Mapping(target = "roleNames", ignore = true)
    UserResponseDto toResponseDto(User user);

    @AfterMapping
    default void setRoleNames(@MappingTarget UserResponseDto responseDto, User user) {
        Set<String> roleNames = user.getRoles().stream()
                .map(RoleType::getName)
                .map(Enum::toString)
                .collect(Collectors.toSet());
        responseDto.setRoleNames(roleNames);
    }

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toModelWithoutPasswordAndRoles(UserRequestDto requestDto);

    void setUpdateInfoToUser(@MappingTarget User user, UserUpdateInfoRequestDto requestDto);
}
