-- Create table tbl_categories

CREATE TABLE tbl_income (
    id BIGINT NOT NULL DEFAULT nextval('ft_seq'),
    name VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    icon VARCHAR(255),
    transaction_date DATE NOT NULL,
    amount DECIMAL(19, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    profile_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_income_profile FOREIGN KEY (profile_id)
        REFERENCES tbl_profiles(profile_id)
        ON DELETE CASCADE,
    CONSTRAINT fk_income_category FOREIGN KEY (category_id)
            REFERENCES tbl_categories(id)
            ON DELETE CASCADE
);
