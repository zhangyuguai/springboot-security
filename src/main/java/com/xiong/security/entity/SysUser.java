package com.xiong.security.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xsy
 * @date 2022/10/19
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysUser implements UserDetails {

    private User user;

    private List<String> permissionValueList;

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        for (String permissionValue : permissionValueList) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permissionValue);
            authorities.add(authority);
        }
        return authorities;
    }

    @Override
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        Integer isAccountNonExpired = user.getIsAccountNonExpired();

        return isAccountNonExpired == 1;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        Integer isAccountNonLocked = user.getIsAccountNonLocked();
        return isAccountNonLocked==1;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        Integer isAccountNonExpired = user.getIsCredentialsNonExpired();
        return isAccountNonExpired==1;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        Integer isEnabled = user.getIsEnabled();
        return isEnabled==1;
    }
}