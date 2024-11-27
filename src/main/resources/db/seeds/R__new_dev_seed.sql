--CREATE POSTS TABLE
CREATE TABLE posts (
  id bigserial PRIMARY KEY,
  content varchar(250) NOT NULL,
  photo_url TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  user_id bigint,  -- Change from INT to BIGINT
  CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE POST_LIKES TABLE
CREATE TABLE post_likes (
  id bigserial PRIMARY KEY,
  user_id bigint,  -- Change from INT to BIGINT
  post_id bigint,  -- Change from INT to BIGINT
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_postlikes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_postlikes_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

--CREATE COMMENTS TABLE
CREATE TABLE comments (
  id bigserial PRIMARY KEY,
  post_id bigint,  -- Change from INT to BIGINT
  content TEXT,
  commenter_id bigint,  -- Change from INT to BIGINT
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  CONSTRAINT fk_comments_commenter FOREIGN KEY (commenter_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE COMMENT_LIKES TABLE
CREATE TABLE comment_likes (
  id bigserial PRIMARY KEY,
  user_id bigint,  -- Change from INT to BIGINT
  comment_id bigint,  -- Change from INT to BIGINT
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_commentlikes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_commentlikes_comment FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);

--CREATE CHATS TABLE
CREATE TABLE chats (
  id bigserial PRIMARY KEY,
  user_1_id bigint,  -- Change from INT to BIGINT
  user_2_id bigint,  -- Change from INT to BIGINT
  CONSTRAINT fk_chats_user_1 FOREIGN KEY (user_1_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_chats_user_2 FOREIGN KEY (user_2_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE MESSAGES TABLE
CREATE TABLE messages (
  id bigserial PRIMARY KEY,
  sender_id bigint,  -- Change from INT to BIGINT
  chat_id bigint,  -- Change from INT to BIGINT
  message TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_messages_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);

--CREATE FRIENDSHIPS TABLE
CREATE TABLE friendships (
  id bigserial PRIMARY KEY,
  sender_id bigint,  -- Change from INT to BIGINT
  receiver_id bigint,  -- Change from INT to BIGINT
  status VARCHAR(20) DEFAULT 'pending',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_friendships_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_friendships_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE NOTIFICATIONS TABLE
CREATE TABLE notifications (
  id bigserial PRIMARY KEY,
  sender_id bigint,  -- Change from INT to BIGINT
  receiver_id bigint,  -- Change from INT to BIGINT
  type VARCHAR(50),
  content VARCHAR(255),
  is_read BOOLEAN DEFAULT FALSE,
  post_id bigint,  -- Change from INT to BIGINT
  comment_id bigint,  -- Change from INT to BIGINT
  friendship_id bigint,  -- Change from INT to BIGINT
  chat_id bigint,  -- Change from INT to BIGINT
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_notifications_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_comment FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_friendship FOREIGN KEY (friendship_id) REFERENCES friendships(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);
