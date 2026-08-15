package com.sampleproject.user.controller;

import com.sampleproject.util.ApiResponse;
import com.sampleproject.user.dto.UserResponse;
import com.sampleproject.user.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceImpl userService;

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUser(){
        List<UserResponse> userResponseList = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.success("User list retrieve successfully", userResponseList));
    }

    @GetMapping("/ownerList")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllOwner(){
        List<UserResponse> userResponseList = userService.getAllOwners();
        return ResponseEntity.ok(ApiResponse.success("Owner list retrieve successfully", userResponseList));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser(){
        UserResponse userResponse = userService.getCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Current user retrieve successfully", userResponse));
    }
}
