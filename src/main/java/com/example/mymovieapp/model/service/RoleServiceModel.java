package com.example.mymovieapp.model.service;

import com.example.mymovieapp.model.enums.UserRoleEnum;

public class RoleServiceModel extends BaseServiceModel{
    private UserRoleEnum authority;

    public UserRoleEnum getAuthority() {
        return authority;
    }

    public void setAuthority(UserRoleEnum authority) {
        this.authority = authority;
    }
}
