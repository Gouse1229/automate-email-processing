-- Create the database
CREATE DATABASE email_db;

-- Use the existing database (or create it if necessary)
USE email_db;

-- Drop the old emails table if exists (be careful with this on production systems)
DROP TABLE IF EXISTS email;

-- Create the updated emails table
CREATE TABLE email (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- Auto-generated primary key
    message_id VARCHAR(255) UNIQUE NOT NULL,  -- Unique field for message ID
    sender VARCHAR(255),
    subject VARCHAR(255),
    content TEXT,
    category VARCHAR(100),
    created_at DATE NOT NULL
);

