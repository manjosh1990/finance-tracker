truncate table tbl_profiles CASCADE;
INSERT INTO tbl_profiles (
    full_name,
    email,
    password,
    profile_image_url,
    created_at,
    updated_at,
    is_active,
    activation_token
) VALUES (
    'Invalid User',
    'invalid@example.com',
    'guest123',                  -- Replace with hashed password in real app
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE,
    NULL
),
(
    'Valid User',
    'valid@example.com',
    '$2a$10$3cAnf3u6xUvRTL8Au6zt0uhRdOEwUEWOTt/Qu/.YJVIVjwzjfBChC',                  -- Replace with hashed password in real app
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE,
    NULL
);