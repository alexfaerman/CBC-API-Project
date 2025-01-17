-- Create the items table
CREATE TABLE items (
    id UUID PRIMARY KEY,
    external_id VARCHAR(255),
    title VARCHAR(255),
    author VARCHAR(255),
    published_year INT,
    type VARCHAR(50)
);