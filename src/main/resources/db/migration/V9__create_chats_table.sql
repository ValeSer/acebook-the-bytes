CREATE TABLE chats (
  id bigserial PRIMARY KEY,
  user_1_id INT,
  user_2_id INT,
  CONSTRAINT fk_user_1 FOREIGN KEY (user_1_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_user_2 FOREIGN KEY (user_2_id) REFERENCES users(id) ON DELETE CASCADE
);
