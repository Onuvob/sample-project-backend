package com.sampleproject.util;

import com.sampleproject.user.entity.User;
import com.sampleproject.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {

    private final UserRepository userRepository;

    public User getCurrentUser() {

        String username = SecurityUtil.getCurrentUsername()
                .orElseThrow(() -> new RuntimeException("User is not authenticated"));

        return userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
