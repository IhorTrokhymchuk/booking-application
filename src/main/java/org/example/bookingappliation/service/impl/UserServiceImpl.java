package org.example.bookingappliation.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.users.request.UserRequestDto;
import org.example.bookingappliation.dto.users.request.UserUpdateInfoRequestDto;
import org.example.bookingappliation.dto.users.request.UserUpdatePasswordRequestDto;
import org.example.bookingappliation.dto.users.request.UserUpdateRolesRequestDto;
import org.example.bookingappliation.dto.users.response.UserResponseDto;
import org.example.bookingappliation.exception.EntityAlreadyExistsException;
import org.example.bookingappliation.exception.EntityNotFoundException;
import org.example.bookingappliation.exception.PasswordNotValidException;
import org.example.bookingappliation.mapper.UserMapper;
import org.example.bookingappliation.model.user.RoleType;
import org.example.bookingappliation.model.user.User;
import org.example.bookingappliation.repository.roletype.RoleTypeRepository;
import org.example.bookingappliation.repository.user.UserRepository;
import org.example.bookingappliation.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final RoleType.RoleName CUSTOMER_ROLE_TYPE = RoleType.RoleName.CUSTOMER;
    private final RoleTypeRepository roleTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponseDto getInfo(String email) {
        return userMapper.toResponseDto(getUser(email));
    }

    @Override
    public UserResponseDto updateInfo(String email, UserUpdateInfoRequestDto requestDto) {
        User user = getUser(email);
        setUserInfo(user, requestDto);
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public void updatePassword(String email, UserUpdatePasswordRequestDto requestDto) {
        User user = getUser(email);
        passwordEncoder.matches(requestDto.getOldPassword(), user.getPassword());
        isPasswordsValid(requestDto.getNewPassword(), requestDto.getRepeatNewPassword());
        setPassword(user, requestDto.getNewPassword());
        userRepository.save(user);
    }

    @Override
    public UserResponseDto updateRoles(Long id, UserUpdateRolesRequestDto requestDto) {
        User user = getUser(id);
        setRoleType(user, requestDto.getRoleName());
        return userMapper.toResponseDto(userRepository.save(user));
    }

    @Override
    public UserResponseDto register(UserRequestDto requestDto) {
        isUserAlreadyExist(requestDto.getEmail());
        User newUser = userMapper.toModelWithoutPasswordAndRoles(requestDto);
        isPasswordsValid(requestDto.getPassword(), requestDto.getRepeatPassword());
        setPassword(newUser, requestDto.getPassword());
        setRoleType(newUser, CUSTOMER_ROLE_TYPE);
        return userMapper.toResponseDto(userRepository.save(newUser));
    }

    private void setUserInfo(User user, UserUpdateInfoRequestDto requestDto) {
        isUserAlreadyExist(requestDto.getEmail());
        userMapper.setUpdateInfoToUser(user, requestDto);
    }

    private void isUserAlreadyExist(String email) {
        Optional<User> userByEmail = userRepository.findUserByEmailWithRoles(email);
        if (userByEmail.isPresent()) {
            throw new EntityAlreadyExistsException("User with email: " + email + " is exist");
        }
    }

    private User getUser(String email) {
        Optional<User> userByEmail = userRepository.findUserByEmailWithRoles(email);
        if (userByEmail.isEmpty()) {
            throw new EntityNotFoundException("Cant find user with email: " + email);
        }
        return userByEmail.get();
    }

    private User getUser(Long id) {
        Optional<User> userById = userRepository.findUserByIdWithRoles(id);
        if (userById.isEmpty()) {
            throw new EntityNotFoundException("Cant find user with id: " + id);
        }
        return userById.get();
    }

    private void setPassword(User user, String password) {
        String encodePassword = passwordEncoder.encode(password);
        user.setPassword(encodePassword);
    }

    private void isPasswordsValid(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            throw new PasswordNotValidException("Passwords is different");
        }
    }

    private void setRoleType(User user, RoleType.RoleName highestRole) {
        List<RoleType.RoleName> roleNamesSubList = RoleType.RoleName.getRolesUpTo(highestRole);;
        List<RoleType> roleTypes = roleTypeRepository.findRoleTypesByNameIn(roleNamesSubList);
        Set<RoleType> newRoleTypes = new HashSet<>(roleTypes);
        user.setRoles(newRoleTypes);
    }
}
