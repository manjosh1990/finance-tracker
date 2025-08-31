package com.manjosh.labs.backend.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.manjosh.labs.backend.AbstractIT;
import com.manjosh.labs.backend.domain.profile.Profile;
import com.manjosh.labs.backend.domain.profile.ProfileService;
import com.manjosh.labs.backend.utils.TestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

class NotificationServiceTest extends AbstractIT {

    @Autowired
    private NotificationService notificationService;

    @MockitoBean
    private EmailService emailService;

    @Autowired
    private ProfileService profileService;

    private final TestUtils testUtils = new TestUtils();
    private static final String BASE_URL = "src/test/resources/json/profile/";

    @BeforeEach
    void setUp() {
        // Clear any existing mocks
        reset(emailService);
        final Profile profile = testUtils.readJsonFromFile("profile_request.json", BASE_URL, Profile.class);
        Profile newProfile = new Profile(
                profile.id(),
                profile.fullName(),
                "test@example.com",
                profile.password(),
                profile.profileImageUrl(),
                profile.createdAt(),
                profile.updatedAt(),
                profile.activationCode());
        profileService.registerProfile(newProfile, false);
    }

    @AfterEach
    void tearDown() {
        profileService.deleteAllProfiles();
    }

    @Test
    void sendNotification_ShouldSendEmailToAllProfiles() {
        // Given - test profile is already set up

        // When
        notificationService.sendNotification();

        // Then
        verify(emailService, times(1))
                .sendEmail(eq("test@example.com"), eq("Xpensive: Add today's expenses"), anyString());
    }

    @Test
    void buildNotificationEmailBody_ShouldReturnValidHtml() {
        // Given
        String userName = "Test User";

        // When
        String emailBody = notificationService.buildNotificationEmailBody(userName);

        // Then
        assertThat(emailBody)
                .isNotBlank()
                .contains("<html")
                .contains("Hello, Test User!")
                .contains("Don't forget to log today's expenses!")
                .contains("View My Dashboard");
    }
}
