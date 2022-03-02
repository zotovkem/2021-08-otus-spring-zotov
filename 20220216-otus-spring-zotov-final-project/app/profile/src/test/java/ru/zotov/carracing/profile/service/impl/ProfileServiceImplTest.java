package ru.zotov.carracing.profile.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import ru.zotov.carracing.profile.entity.Profile;
import ru.zotov.carracing.profile.repo.ProfileRepository;
import ru.zotov.carracing.security.filter.CustomUser;
import ru.zotov.carracing.security.utils.SecurityService;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 23.02.2022
 */
@DisplayName("Тестирование сервиса профиля игрока ")
@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {
    @Mock private  ProfileRepository profileRepository;
    @Mock private  SecurityService securityService;
    @InjectMocks private ProfileServiceImpl profileService;

    @Test
    @DisplayName("Создать профиль")
    void createTest() {
        UUID externalId = UUID.randomUUID();
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Profile result = profileService.create(externalId);

        assertThat(result).isNotNull().extracting("externalId").isEqualTo(externalId);

        verify(profileRepository).save(any());
    }

    @Test
    @DisplayName("Получить текущий профиль")
    void getCurrentProfileTest() {
        var user = new CustomUser(UUID.randomUUID().toString(), "testUser", "pass", Collections.emptyList());
        when(securityService.getUser()).thenReturn(Optional.of(user));
        when(profileRepository.findByExternalId(any())).thenReturn(Optional.of(Profile.builder().build()));

        Optional<Profile> result = profileService.getCurrentProfile();

        assertThat(result).isPresent();

        verify(securityService).getUser();
        verify(profileRepository).findByExternalId(any());
    }

    @Test
    @DisplayName("Добавить игровых очков в профиле игрока ")
    void progressIncrementTest() {
        Profile profile = Profile.builder()
                .score(100L)
                .progress(0)
                .build();
        when(profileRepository.findByExternalId(any())).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        profileService.progressIncrement(UUID.randomUUID(),100L);

        verify(profileRepository).findByExternalId(any());
        verify(profileRepository).save(any());
    }

    @Test
    @DisplayName("Убавить игровых очков в профиле игрока ")
    void regressProgressTest() {
        Profile profile = Profile.builder()
                .score(100L)
                .progress(0)
                .build();
        when(profileRepository.findByExternalId(any())).thenReturn(Optional.of(profile));
        when(profileRepository.save(any(Profile.class))).thenAnswer(invocation -> invocation.getArgument(0));

        profileService.regressProgress(UUID.randomUUID(),100L);

        verify(profileRepository).findByExternalId(any());
        verify(profileRepository).save(any());
    }
}
