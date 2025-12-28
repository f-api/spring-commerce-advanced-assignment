package com.advanced.user.service;

import com.advanced.common.config.PasswordEncoder;
import com.advanced.user.dto.*;
import com.advanced.user.entity.User;
import com.advanced.user.error.PasswordMismatchException;
import com.advanced.user.error.UserNotFoundException;
import com.advanced.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserRegisterResponse register(UserRegisterRequest request) {
        String password = request.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(
                request.getName(),
                request.getEmail(),
                encodedPassword
        );
        User savedUser = userRepository.save(user);
        return new UserRegisterResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    @Transactional(readOnly = true)
    public List<UserGetResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new UserGetResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                )).toList();
    }

    @Transactional(readOnly = true)
    public UserGetResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 없습니다.")
        );
        return new UserGetResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public UserUpdateResponse update(SessionUser sessionUser, UserUpdateRequest request) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 없습니다.")
        );
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        // 비밀번호가 일치하지 않을 때
        if (!matches) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        // 비밀번호가 일치할 때
        user.update(request.getName());
        return new UserUpdateResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    @Transactional
    public void delete(SessionUser sessionUser, UserDeleteRequest request) {
        User user = userRepository.findById(sessionUser.getId()).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 없습니다.")
        );
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        // 비밀번호가 일치하지 않을 때
        if (!matches) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        // 비밀번호가 일치할 때
        userRepository.delete(user);
    }

    @Transactional(readOnly = true)
    public SessionUser login(UserLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("해당 유저가 없습니다.")
        );
        boolean matches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        // 비밀번호가 일치하지 않을 때
        if (!matches) {
            throw new PasswordMismatchException("비밀번호가 일치하지 않습니다.");
        }
        // 비밀번호가 일치할 때
        return new SessionUser(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}
