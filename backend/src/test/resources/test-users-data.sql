-- Truncate the table and reset the sequence
TRUNCATE TABLE tbl_profiles RESTART IDENTITY CASCADE;

-- Reset the sequence to start from 1
SELECT setval('ft_seq', 1, false);

INSERT INTO tbl_profiles (full_name,
                          email,
                          password,
                          profile_image_url,
                          created_at,
                          updated_at,
                          is_active,
                          activation_token)
VALUES ('Admin User',
        'admin@example.com',
        '$2a$10$7TEFlAIk2Rpum5AmNWQ2dOLZVI5n8Ywi6l./GPd3U5rEpd7XB8BGC', -- Replace with hashed password in real app (admin)
        NULL,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        TRUE,
        NULL),
       ('Invalid User',
        'invalid@example.com',
        'guest123', -- Replace with hashed password in real app
        NULL,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        TRUE,
        NULL),
       ('Valid User',
        'valid@example.com',
        '$2a$10$3cAnf3u6xUvRTL8Au6zt0uhRdOEwUEWOTt/Qu/.YJVIVjwzjfBChC', -- Replace with hashed password in real app
        NULL,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        TRUE,
        NULL);
