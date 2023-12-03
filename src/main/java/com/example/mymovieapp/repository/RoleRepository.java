package com.example.mymovieapp.repository;

import com.example.mymovieapp.model.entity.Role;
import com.example.mymovieapp.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findAll();

    Role findByAuthority(UserRoleEnum role);

    List<Role> findAllByAuthorityIn(List<UserRoleEnum> roles);
}
