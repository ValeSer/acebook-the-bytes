CREATE TABLE friendships (
  id bigserial PRIMARY KEY,
  sender_id INT,
  receiver_id INT,
  status VARCHAR(20),
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);
