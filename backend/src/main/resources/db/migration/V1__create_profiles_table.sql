create sequence ft_seq start with 1 increment by 50;

-- Create table
CREATE TABLE tbl_profiles (
    profile_id bigint NOT NULL DEFAULT nextval('ft_seq'),
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    profile_image_url VARCHAR(255),
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    is_active boolean DEFAULT false,
    activation_token VARCHAR(255),
    primary key (profile_id)
);