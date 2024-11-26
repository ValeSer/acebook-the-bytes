CREATE TABLE messages (
  id bigserial PRIMARY KEY,
  sender_id INT,
  chat_id INT,
  message TEXT,
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);
