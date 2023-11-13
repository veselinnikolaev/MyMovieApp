package com.example.mymovieapp.init;

import com.example.mymovieapp.model.entity.Role;
import com.example.mymovieapp.model.enums.UserRoleEnum;
import com.example.mymovieapp.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInit implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RoleInit(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        long count = roleRepository.count();
        if (count == 0) {
            for (UserRoleEnum userRoleEnum : UserRoleEnum.values()) {
                Role role = new Role();
                role.setAuthority(userRoleEnum);
                roleRepository.save(role);
            }
        }
    }
}
