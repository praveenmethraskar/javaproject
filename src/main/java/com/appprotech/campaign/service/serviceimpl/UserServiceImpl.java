package com.appprotech.campaign.service.serviceimpl;


import com.appprotech.campaign.common.JwtUtil;
import com.appprotech.campaign.dto.UserRequest;
import com.appprotech.campaign.dto.UserResponse;
import com.appprotech.campaign.entity.BoothNumbers;
import com.appprotech.campaign.entity.User;
import com.appprotech.campaign.entity.UserBoothAccess;
import com.appprotech.campaign.repository.BoothNumbersRepository;
import com.appprotech.campaign.repository.UserBoothAccesseRepository;
import com.appprotech.campaign.repository.UserRepository;
import com.appprotech.campaign.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository    userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private BoothNumbersRepository boothNumbersRepository;

    @Autowired
    private UserBoothAccesseRepository userBoothAccesseRepository;


    @Autowired
    private JwtUtil jwtUtil;


//    @Override
    @Transactional
    public UserResponse createUsers(UserRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .referredPerson(request.getReferredPerson())
//                .assignedBoothNumbers(request.getBooths())
                .build();
        User savedRecord=userRepository.save(user);

        UserBoothAccess userBoothAccess=new UserBoothAccess();
        List<BoothNumbers> booths=new ArrayList<>();

        // 1️⃣ Fetch available booth numbers from DB
        List<Long> existingBoothNumbers =
                boothNumbersRepository.availableBooths  ();

        if (existingBoothNumbers == null || existingBoothNumbers.isEmpty()) {
            throw new RuntimeException("No booth numbers available in system");
        }

// 2️⃣ Get requested booths from request
        List<Long> requestedBoothNumbers = request.getBooths();

        if (requestedBoothNumbers == null || requestedBoothNumbers.isEmpty()) {
            throw new RuntimeException("No booth numbers provided in request");
        }


// 4️⃣ Validate requested booths exist in DB
        // 4️⃣ Save user-booth access
//            for (Long bNum : requestedBoothNumbers) {
//                UserBoothAccess userBoothAccesss = new UserBoothAccess();
//                userBoothAccesss.setUser(user);
//                userBoothAccesss.setBoothNumber(bNum);
//                userBoothAccesss.setCreatedDate(LocalDateTime.now());
//                userBoothAccesseRepository.save(userBoothAccesss);
//            }
        List<UserBoothAccess> boothAccessList = requestedBoothNumbers.stream()
                .map(bNum -> UserBoothAccess.builder()
                        .user(user)
                        .boothNumber(bNum)
                        .build()
                )
                .toList();

        userBoothAccesseRepository.saveAll(boothAccessList);

        return mapToUserResponse (savedRecord);
    }

    @Override
    @Transactional
    public UserResponse createUser(UserRequest request) {

        // 1️⃣ Email validation
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // 2️⃣ Create & save user
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .referredPerson(request.getReferredPerson())
                .build();

        User savedUser = userRepository.save(user);

        // 3️⃣ Fetch available booths
        List<Long> existingBoothNumbers =
                boothNumbersRepository.availableBooths();

        if (existingBoothNumbers == null || existingBoothNumbers.isEmpty()) {
            throw new RuntimeException("No booth numbers available in system");
        }

        // 4️⃣ Get requested booths
        List<Long> requestedBoothNumbers = request.getBooths();

        if (requestedBoothNumbers == null || requestedBoothNumbers.isEmpty()) {
            throw new RuntimeException("No booth numbers provided in request");
        }

        // 5️⃣ Validate requested booths exist
        List<Long> invalidBooths = requestedBoothNumbers.stream()
                .filter(b -> !existingBoothNumbers.contains(b))
                .toList();

        if (!invalidBooths.isEmpty()) {
            throw new RuntimeException("Invalid booth numbers: " + invalidBooths);
        }

        // 6️⃣ Create user-booth access records
        List<UserBoothAccess> boothAccessList = requestedBoothNumbers.stream()
                .distinct() // prevent duplicates
                .map(bNum -> UserBoothAccess.builder()
                        .user(savedUser)   // ✅ IMPORTANT
                        .boothNumber(bNum)
                        .build()
                )
                .toList();

        userBoothAccesseRepository.saveAll(boothAccessList);

        // 7️⃣ Attach booths to user to avoid NPE in response
        savedUser.setAssignedBoothNumbers(boothAccessList);

        // 8️⃣ Return response
        return mapToUserResponse(savedUser);
    }






    @Override
    public UserResponse updateUser(HttpServletRequest request, UserRequest userRequest) {
        Long userId = jwtUtil.getUserId(jwtUtil.getToken(request));
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPhoneNumber(userRequest.getPhoneNumber());
        User updatedUser=userRepository.save(user);
        return mapToUserResponse(updatedUser);
    }

    @Override
    public UserResponse getUserById(HttpServletRequest request) {
        // 1️⃣ Get logged-in userId from JWT
        Long userId = jwtUtil.getUserId(jwtUtil.getToken(request));
        User fetchedUser= userRepository.findWithBooths(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return  mapToUserResponse(fetchedUser);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(HttpServletRequest request) {
        Long userId = jwtUtil.getUserId(jwtUtil.getToken(request));
        User user= userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.delete(user);
    }

        public List<UserBoothAccess> getAssignedBooths(Long userId) {
        User user = userRepository.findByIdWithBooths(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user.getAssignedBoothNumbers();
    }


    @Transactional
    public User getUser(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        user.getAssignedBoothNumbers().size(); // forces load
        return user;
    }


    public UserResponse mapToUserResponse(User user) {
        List<Long> boothNumbers = user.getAssignedBoothNumbers()
                .stream()
                .map(UserBoothAccess::getBoothNumber)
                .toList();

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                user.getReferredPerson(),
                boothNumbers,
                user.getCreatedDate(),
                user.getUpdatedDate()
        );
    }




}
