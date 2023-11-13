package com.example.mymovieapp.model.entity;

import com.example.mymovieapp.model.enums.UserRoleEnum;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private UserRoleEnum authority;

    public UserRoleEnum getAuthority() {
        return authority;
    }

    public void setAuthority(UserRoleEnum role) {
        this.authority = role;
    }
}
