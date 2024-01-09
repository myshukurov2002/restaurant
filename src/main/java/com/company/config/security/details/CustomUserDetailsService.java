package com.company.config.security.details;

import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.exp.AppBadRequestException;
import com.company.repository.ProfileRepository;
import com.company.repository.ProfileRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<ProfileEntity> optional = profileRepository.findById(username);
        if (optional.isEmpty()) {
            throw new AppBadRequestException("Login or password wrong");
        }
        ProfileEntity profileEntity = optional.get();
        List<ProfileRole> roleList = profileRoleRepository.findAllRoleList(profileEntity.getId());

        return new CustomUserDetails(profileEntity, roleList);
    }
}
