ALTER TABLE comments
ADD commenter_id INT,
ADD CONSTRAINT fk_commenter FOREIGN KEY (commenter_id) REFERENCES users(id) ON DELETE CASCADE;