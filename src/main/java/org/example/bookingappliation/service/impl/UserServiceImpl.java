package org.example.bookingappliation.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.bookingappliation.dto.user.UserRequestDto;
import org.example.bookingappliation.dto.user.UserResponseDto;
import org.example.bookingappliation.exception.EntityAlreadyExistsException;
import org.example.bookingappliation.exception.PasswordNotValidException;
import org.example.bookingappliation.mapper.UserMapper;
import org.example.bookingappliation.model.user.RoleType;
import org.example.bookingappliation.model.user.User;
import org.example.bookingappliation.repository.RoleTypeRepository;
import org.example.bookingappliation.repository.UserRepository;
import org.example.bookingappliation.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleTypeRepository roleTypeRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponseDto register(UserRequestDto requestDto) {
        isUserAlreadyExist(requestDto.getEmail());
        User newUser = userMapper.toModelWithoutPasswordAndRoles(requestDto);
        setPassword(newUser, requestDto);
        setRoleTypeCustomer(newUser);
        return userMapper.toResponseDto(userRepository.save(newUser));
    }

    private void isUserAlreadyExist(String email) {
        Optional<User> userByEmail = userRepository.findUserByEmail(email);
        if (userByEmail.isPresent()) {
            throw new EntityAlreadyExistsException("Cant register user with email: " + email);
        }
    }

    private void setPassword(User user, UserRequestDto requestDto) {
        isPasswordsValid(requestDto.getPassword(), requestDto.getRepeatPassword());
        String encodePassword = passwordEncoder.encode(requestDto.getPassword());
        user.setPassword(encodePassword);
    }

    private void isPasswordsValid(String password, String repeatPassword) {
        if (!password.equals(repeatPassword)) {
            throw new PasswordNotValidException("Passwords is different");
        }
    }

    private void setRoleTypeCustomer(User user) {
        Set<RoleType> roles = new HashSet<>();
        roles.add(roleTypeRepository.findRoleTypeByName(RoleType.RoleName.CUSTOMER));
        user.setRoles(roles);
    }
}
