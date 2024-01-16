package com.company.config.security.details;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private final ProfileEntity profile;
    private List<GrantedAuthority> profileRoleList;

    public CustomUserDetails(ProfileEntity profile, List<ProfileRole> roleList) {
        this.profile = profile;
        List<GrantedAuthority> list = new LinkedList<>();
        for (ProfileRole role : roleList) {
            list.add(new SimpleGrantedAuthority("ROLE_" + role.name()));
        }
        profileRoleList = list;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return profileRoleList;
    }

    @Override
    public String getPassword() {
        return profile.getPassword();
    }

    @Override
    public String getUsername() {
        return profile.getPhone();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return profile.getStatus().equals(ProfileStatus.ACTIVE);
    }

    public ProfileEntity getProfile() {
        return profile;
    }
}
