package com.example.billingsoftware.BillingSoftwareBackend.service.implementation;

import com.example.billingsoftware.BillingSoftwareBackend.entity.UserEntity;
import com.example.billingsoftware.BillingSoftwareBackend.io.UserRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.UserResponse;
import com.example.billingsoftware.BillingSoftwareBackend.repository.UserRepository;
import com.example.billingsoftware.BillingSoftwareBackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserResponse createUser(UserRequest request) {
        UserEntity newUser = convertToEntity(request);
        newUser = userRepository.save(newUser);
        return convertToUserResponse(newUser);
    }

    private UserResponse convertToUserResponse(UserEntity newUser) {
        return UserResponse.builder()
                .name(newUser.getName())
                .email(newUser.getEmail())
                .userId(newUser.getUserId())
                .createdAt(newUser.getCreatedAt())
                .updatedAt(newUser.getUpdatedAt())
                .role(newUser.getRole())
                .build();
    }

    private UserEntity convertToEntity(UserRequest request) {
        return UserEntity.builder()
                .userId(UUID.randomUUID().toString())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole().toUpperCase())
                .name(request.getName())
                .build();
    }

    @Override
    public String getUserRole(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found for email - "+email));
        return user.getRole();
    }

    @Override
    public List<UserResponse> readusers() {
        return userRepository.findAll()
                .stream()
                .map(user -> convertToUserResponse(user))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(String id) {
        UserEntity user = userRepository.findByUserId(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
