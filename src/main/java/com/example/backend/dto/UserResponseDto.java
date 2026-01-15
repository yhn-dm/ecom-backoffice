package com.example.backend.dto;

import com.example.backend.entity.Role;
import java.util.Set;

public class UserResponseDto {

    public Long id;
    public String username;
    public String email;
    public Set<Role> roles;
}
