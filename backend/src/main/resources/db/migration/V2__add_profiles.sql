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
    'admin123',                  -- Replace with hashed password in real app
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
    'guest123',                  -- Replace with hashed password in real app
    NULL,
    CURRENT_TIMESTAMP,
    CURRENT_TIMESTAMP,
    TRUE,
    NULL
);
