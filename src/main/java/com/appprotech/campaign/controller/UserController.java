package com.appprotech.campaign.controller;

import com.appprotech.campaign.common.APIResponse;
import com.appprotech.campaign.dto.UserRequest;
import com.appprotech.campaign.dto.UserResponse;
import com.appprotech.campaign.entity.User;
import com.appprotech.campaign.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private  UserService userService;

    @PostMapping("/create")
    public ResponseEntity<APIResponse<UserResponse>> createUser(@Valid @RequestBody UserRequest request) {
        UserResponse user = userService.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(APIResponse.<UserResponse>builder()
                        .success(true)
                        .statusCode(201)
                        .message("User created successfully")
                        .data(user)
                        .build());
    }

//    @GetMapping("/getUser")
//    public ResponseEntity<APIResponse<UserResponse>> getUser(HttpServletRequest requests) {
//        return ResponseEntity.ok(APIResponse.<UserResponse>builder()
//                .success(true)
//                .statusCode(200)
//                .message("User fetched successfully")
//                .data(userService.getUserById(requests))
//                .build());
//    }

//    @GetMapping
//    public ResponseEntity<APIResponse<List<User>>> getAllUsers() {
//        return ResponseEntity.ok(APIResponse.<List<User>>builder()
//                .success(true)
//                .statusCode(200)
//                .message("Users fetched successfully")
//                .data(userService.getAllUsers())
//                .build());
//    }

            //    @PutMapping("/{id}")
            //    public ResponseEntity<APIResponse<UserResponse>> updateUser(
            //            HttpServletRequest request,
            //            @Valid @RequestBody UserRequest userRequest) {
            //
            //        return ResponseEntity.ok(APIResponse.<UserResponse>builder()
            //                .success(true)
            //                .statusCode(200)
            //                .message("User updated successfully")
            //                .data(userService.updateUser(request, userRequest))
            //                .build());
            //    }

//    @DeleteMapping("/deleteuser")
//    public ResponseEntity<APIResponse<Void>> deleteUser(HttpServletRequest requests) {
//        userService.deleteUser(requests);
//        return ResponseEntity.ok(APIResponse.<Void>builder()
//                .success(true)
//                .statusCode(200)
//                .message("User deleted successfully")
//                .build());
//    }



}
