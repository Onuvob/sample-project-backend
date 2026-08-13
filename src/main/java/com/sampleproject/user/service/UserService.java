package com.sampleproject.user.service;

import com.sampleproject.user.dto.UserResponse;
import com.sampleproject.user.entity.User;

import java.util.List;

public interface UserService {
    List<UserResponse> getAllUsers();

    UserResponse getCurrentUser();

    User getUserById(Long id);
}
