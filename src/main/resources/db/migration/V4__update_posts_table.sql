ALTER TABLE posts
ADD photo_url TEXT,
ADD created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD user_id INT,
ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;