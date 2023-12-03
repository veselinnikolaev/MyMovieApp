package com.example.mymovieapp.testutils;

import com.example.mymovieapp.model.entity.UserEntity;
import com.example.mymovieapp.model.enums.UserRoleEnum;
import com.example.mymovieapp.repository.RoleRepository;
import com.example.mymovieapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class UserTestDataUtil {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository userRoleRepository;

    public UserEntity createTestUser(String username) {
        return createUser(username, List.of(UserRoleEnum.USER));
    }

    public UserEntity createTestAdmin(String username) {
        return createUser(username, List.of(UserRoleEnum.ADMIN));
    }

    private UserEntity createUser(String username, List<UserRoleEnum> roles) {

        var roleEntities = userRoleRepository.findAllByAuthorityIn(roles);

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setAuthorities(new HashSet<>(roleEntities));

        return userRepository.save(newUser);
    }

    public void cleanUp() {
        userRepository.deleteAll();
    }

}
