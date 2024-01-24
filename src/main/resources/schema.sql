CREATE TABLE IF NOT EXISTS members {
    id INT AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    display_name VARCHAR(20) NOT NULL,
    social_type VARCHAR(255) NOT NULL,
    is_profile BOOLEAN DEFAULT FALSE,
    main_image VARCHAR(255),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS oauth_tokens (
    id INT AUTO_INCREMENT,
    members_id INT NOT NULL,
    refresh_token VARCHAR(255),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    FOREIGN KEY (members_id) REFERENCES members(id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS profiles (
    id INT AUTO_INCREMENT,
    members_id INT NOT NULL,
    nickname VARCHAR(20) UNIQUE NOT NULL,
    age INT NOT NULL,
    gender INT NOT NULL,
    personality VARCHAR(255) NOT NULL,
    introduce VARCHAR(200) NOT NULL,
    image_name VARCHAR(255) NOT NULL,
    job VARCHAR(50) NOT NULL,
    address_city VARCHAR(255) NOT NULL,
    address_district VARCHAR(255) NOT NULL,
    job_visible BOOLEAN,
    address_visible BOOLEAN,
    my_image_visibility BOOLEAN,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    FOREIGN KEY (members_id) REFERENCES members(id)
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS posts (
    id INT AUTO_INCREMENT,
    members_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    exhibition VARCHAR(255) NOT NULL,
    exhibition_attendance INT NOT NULL,
    possible_time DATETIME(6) NOT NULL,
    open_chat_url VARCHAR(255) NOT NULL,
    together_activity VARCHAR(255) NOT NULL,
    image_name VARCHAR(255),
    post_status VARCHAR(255) NOT NULL,
    view_count INT DEFAULT 0,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    FOREIGN KEY (members_id) REFERENCES members(id),
    PRIMARY KEY (id)
);