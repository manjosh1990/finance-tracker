-- Create table tbl_categories

CREATE TABLE tbl_categories (
    id BIGINT NOT NULL DEFAULT nextval('ft_seq'),
    name VARCHAR(255) NOT NULL,
    type VARCHAR(255),
    icon VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    profile_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_category_profile FOREIGN KEY (profile_id)
        REFERENCES tbl_profiles(profile_id)
        ON DELETE CASCADE
);
