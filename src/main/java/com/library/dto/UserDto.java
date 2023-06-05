package com.library.dto;

import com.library.entity.Role;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Iterator;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;

    private String username;

    @Size(min = 6, message ="The password must be at least 6 characters.")
    private String password;

    private String repeatPassword;

    private String role;

    private Collection<Role> roles;

    @Size(min = 5, max = 15, message ="First name should have 5-15 characters")
    private String fullName;

    private String email;

    public boolean hasRole(String roleName) {
        Iterator<Role> iterator = this.roles.iterator();
        while (iterator.hasNext()) {
            Role role = iterator.next();
            if (role.getName().equals(roleName)) {
                return true;
            }
        }
        return false;
    }
}
