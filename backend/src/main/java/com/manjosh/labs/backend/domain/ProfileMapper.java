package com.manjosh.labs.backend.domain;

class ProfileMapper {

    public static final String MASKED_PASSWORD = "********";

    private ProfileMapper() {}

    static Profile toProfile(final ProfileEntity profileEntity, boolean hidePassword) {
        return new Profile(
                profileEntity.getId(),
                profileEntity.getFullName(),
                profileEntity.getEmail(),
                hidePassword ? toPassword() : profileEntity.getPassword(),
                profileEntity.getProfileImageUrl(),
                profileEntity.getCreatedAt(),
                profileEntity.getUpdatedAt(),
                profileEntity.getActivationToken());
    }

    static Profile toProfile(final ProfileEntity profileEntity) {
        return new Profile(
                profileEntity.getId(),
                profileEntity.getFullName(),
                profileEntity.getEmail(),
                toPassword(),
                profileEntity.getProfileImageUrl(),
                profileEntity.getCreatedAt(),
                profileEntity.getUpdatedAt(),
                profileEntity.getActivationToken());
    }

    static ProfileEntity toProfileEntity(final Profile profile) {
        return ProfileEntity.builder()
                .id(profile.id())
                .fullName(profile.fullName())
                .email(profile.email())
                .profileImageUrl(profile.profileImageUrl())
                .password(profile.password())
                .createdAt(profile.createdAt())
                .updatedAt(profile.updatedAt())
                .build();
    }

    private static String toPassword() {
        return MASKED_PASSWORD;
    }
}
