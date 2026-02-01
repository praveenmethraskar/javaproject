package com.appprotech.campaign.service;


import com.appprotech.campaign.dto.UserRequest;
import com.appprotech.campaign.dto.UserResponse;
import com.appprotech.campaign.entity.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    UserResponse updateUser(HttpServletRequest request, UserRequest userRequest);

    UserResponse getUserById(HttpServletRequest requests);

    List<User> getAllUsers();

    void deleteUser(HttpServletRequest request);
}
