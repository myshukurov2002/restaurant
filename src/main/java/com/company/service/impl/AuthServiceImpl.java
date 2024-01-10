package com.company.service.impl;

import com.company.config.i18n.ResourceBundleService;
import com.company.dto.ApiResponse;
import com.company.dto.ProfileDTO;
import com.company.dto.auth.AuthDTO;
import com.company.dto.auth.JwtDTO;
import com.company.dto.auth.RegistrationDTO;
import com.company.entity.ProfileEntity;
import com.company.entity.ProfileRoleEntity;
import com.company.enums.Language;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.repository.ProfileRepository;
import com.company.repository.ProfileRoleRepository;
import com.company.service.AuthService;
import com.company.util.JWTUtil;
import com.company.util.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ProfileRoleRepository profileRoleRepository;
    @Autowired
    private ResourceBundleService resourceBundleService;

    private ProfileDTO toDTO(ProfileEntity entity) {
        ProfileDTO dto = new ProfileDTO();
        dto.setFirstName(entity.getFirstName());
        dto.setSecondName(entity.getSecondName());
        dto.setThirdName(entity.getThirdName());
        dto.setEmail(entity.getEmail());
        dto.setLang(entity.getLang());
        dto.setBirthDate(entity.getBirthDate());
        dto.setNationality(entity.getNationality());
        dto.setPhone(entity.getPhone());
        dto.setStatus(entity.getStatus());

        List<ProfileRole> roleList = profileRoleRepository.findAllRoleList(entity.getId());

//        dto.setRoles(roleList);//TODO remove
        dto.setJwt(JWTUtil.encode(entity.getId(), roleList));
        return dto;
    }

    private ProfileEntity toEntity(RegistrationDTO reg) {
        ProfileEntity entity = new ProfileEntity();
        entity.setFirstName(reg.getFirstName());
        entity.setSecondName(reg.getSecondName());
        entity.setThirdName(reg.getThirdName());
        entity.setEmail(reg.getEmail());
        entity.setBirthDate(reg.getBirthDate());
        entity.setNationality(reg.getNationality());
        entity.setPhone(reg.getPhone());
        entity.setStatus(reg.getStatus());
        entity.setPassword(MD5Util.encode(reg.getPassword()));

        return entity;
    }

    @Override
    public ApiResponse<?> login(AuthDTO auth, Language lang) {
        Optional<ProfileEntity> optionalProfile = profileRepository
                .findByPhone(auth.getPhone());
        if (optionalProfile.isPresent()) {
            ProfileEntity profileEntity = optionalProfile.get();
            if (profileEntity.getPassword().equals(MD5Util.encode(auth.getPassword()))) {
                log.warn("login " + auth.getPhone());

                return new ApiResponse<>(true, toDTO(profileEntity));
            }
            return new ApiResponse<>(false, resourceBundleService.getMessage("incorrect.password", lang));
        }
        return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", lang));
    }

    @Override
    public ApiResponse<?> registration(RegistrationDTO reg, Language lang) {
        Optional<ProfileEntity> optionalProfile = profileRepository
                .findByPhone(reg.getPhone());
        if (optionalProfile.isPresent()) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("phone.already.exists", lang));
        }
        ProfileEntity profileEntity = toEntity(reg);
        profileEntity.setLang(lang);
        profileEntity.setStatus(ProfileStatus.ACTIVE);

        ProfileRoleEntity role = new ProfileRoleEntity();
        role.setProfileRole(ProfileRole.USER);
        role.setProfileId(profileEntity.getId());

        profileRepository.save(profileEntity);
        profileRoleRepository.save(role);

        log.info("registration " + reg.getPhone());

        return new ApiResponse<>(true, resourceBundleService.getMessage("success.registration", lang), toDTO(profileEntity));
    }

    @Override
    public ApiResponse<?> updateById(String id, JwtDTO dto) {

        if (!profileRepository.existsById(id) || dto == null) {
            return new ApiResponse<>(false, resourceBundleService.getMessage("item.not.found", Language.en));
        }
        dto.getProfileRoles().forEach(r -> {
            ProfileRoleEntity role = new ProfileRoleEntity();
            role.setProfileRole(r);
            role.setProfileId(id);
            profileRoleRepository.save(role);
        });
        log.warn("update profile role, id: " + id);
        return new ApiResponse<>(true, resourceBundleService.getMessage("success.registration", Language.en));
    }
}
