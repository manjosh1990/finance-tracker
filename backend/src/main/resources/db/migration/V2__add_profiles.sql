-- Insert admin profile
INSERT INTO tbl_profiles (
    profile_id,
    full_name,
    email,
    password,
    profile_image_url,
    created_at,
    updated_at,
    is_active,
    activation_token
) VALUES (
    nextval('ft_seq'),
    'Admin User',
    'admin@example.com',
    '$2a$10$7TEFlAIk2Rpum5AmNWQ2dOLZVI5n8Ywi6l./GPd3U5rEpd7XB8BGC',                  -- Replace with hashed password in real app (admin)
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE,
    NULL
);

-- Insert guest profile
INSERT INTO tbl_profiles (
    profile_id,
    full_name,
    email,
    password,
    profile_image_url,
    created_at,
    updated_at,
    is_active,
    activation_token
) VALUES (
    nextval('ft_seq'),
    'Guest User',
    'guest@example.com',
    '$2a$10$3cAnf3u6xUvRTL8Au6zt0uhRdOEwUEWOTt/Qu/.YJVIVjwzjfBChC',                  -- Replace with hashed password in real app (securePassword123)
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE,
    NULL
);
