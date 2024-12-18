--UPDATE EXISTING USERS TABLE
ALTER TABLE users
ADD my_status VARCHAR(255),
ADD profile_photo_url TEXT,
ADD bio TEXT;

--UPDATE EXISTING POSTS TABLE
ALTER TABLE posts
ADD photo_url TEXT,
ADD created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
ADD user_id INT,
ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

--CREATE POST_LIKES TABLE
CREATE TABLE post_likes (
  id bigserial PRIMARY KEY,
  user_id INT,
  post_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

--CREATE COMMENTS TABLE
CREATE TABLE comments (
  id bigserial PRIMARY KEY,
  post_id INT,
  content TEXT,
  commenter_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  CONSTRAINT fk_commenter FOREIGN KEY (commenter_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE COMMENT_LIKES TABLE
CREATE TABLE comment_likes (
  id bigserial PRIMARY KEY,
  user_id INT,
  comment_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_comment FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);

--CREATE CHATS TABLE
CREATE TABLE chats (
  id bigserial PRIMARY KEY,
  user_1_id INT,
  user_2_id INT,
  CONSTRAINT fk_user_1 FOREIGN KEY (user_1_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_user_2 FOREIGN KEY (user_2_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE MESSAGES TABLE
CREATE TABLE messages (
  id bigserial PRIMARY KEY,
  sender_id INT,
  chat_id INT,
  message TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);

--CREATE FRIENDSHIPS TABLE
CREATE TABLE friendships (
  id bigserial PRIMARY KEY,
  sender_id INT,
  receiver_id INT,
  status VARCHAR(20) DEFAULT 'pending',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE NOTIFICATIONS TABLE
CREATE TABLE notifications (
  id bigserial PRIMARY KEY,
  sender_id INT,
  receiver_id INT,
  type VARCHAR(50),
  content VARCHAR(255),
  is_read BOOLEAN DEFAULT FALSE,
  post_id INT,
  comment_id INT,
  friendship_id INT,
  chat_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  CONSTRAINT fk_comment FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE,
  CONSTRAINT fk_friendship FOREIGN KEY (friendship_id) REFERENCES friendships(id) ON DELETE CASCADE,
  CONSTRAINT fk_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);