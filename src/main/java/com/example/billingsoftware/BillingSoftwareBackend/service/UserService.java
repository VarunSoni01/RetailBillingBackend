package com.example.billingsoftware.BillingSoftwareBackend.service;

import com.example.billingsoftware.BillingSoftwareBackend.io.UserRequest;
import com.example.billingsoftware.BillingSoftwareBackend.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readusers();

    void deleteUser(String id);
}
