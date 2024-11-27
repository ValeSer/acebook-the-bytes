--COMMAND TO RUN: psql -d acebook_springboot_development -f src/main/resources/db/seeds/R__dev_seed.sql
--WILL NEED USERS WITH IDS 1-5 (columns: id, username, enabled, my_status, profile_photo_url, bio, first_name, last_name)

--DROP TABLES
DROP TABLE IF EXISTS notifications CASCADE;
DROP TABLE IF EXISTS post_likes CASCADE;
DROP TABLE IF EXISTS posts CASCADE;
DROP TABLE IF EXISTS comment_likes CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS messages CASCADE;
DROP TABLE IF EXISTS chats CASCADE;
DROP TABLE IF EXISTS friendships CASCADE;

--CREATE POSTS TABLE
CREATE TABLE posts (
  id bigserial PRIMARY KEY,
  content varchar(250) NOT NULL,
  photo_url TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  user_id INT,
  CONSTRAINT fk_posts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE POST_LIKES TABLE
CREATE TABLE post_likes (
  id bigserial PRIMARY KEY,
  user_id INT,
  post_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_postlikes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_postlikes_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE
);

--CREATE COMMENTS TABLE
CREATE TABLE comments (
  id bigserial PRIMARY KEY,
  post_id INT,
  content TEXT,
  commenter_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_comments_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  CONSTRAINT fk_comments_commenter FOREIGN KEY (commenter_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE COMMENT_LIKES TABLE
CREATE TABLE comment_likes (
  id bigserial PRIMARY KEY,
  user_id INT,
  comment_id INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_commentlikes_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_commentlikes_comment FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE
);

--CREATE CHATS TABLE
CREATE TABLE chats (
  id bigserial PRIMARY KEY,
  user_1_id INT,
  user_2_id INT,
  CONSTRAINT fk_chats_user_1 FOREIGN KEY (user_1_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_chats_user_2 FOREIGN KEY (user_2_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE MESSAGES TABLE
CREATE TABLE messages (
  id bigserial PRIMARY KEY,
  sender_id INT,
  chat_id INT,
  message TEXT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_messages_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);

--CREATE FRIENDSHIPS TABLE
CREATE TABLE friendships (
  id bigserial PRIMARY KEY,
  sender_id INT,
  receiver_id INT,
  status VARCHAR(20) DEFAULT 'pending',
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_friendships_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_friendships_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE
);

--CREATE NOTIFICATIONS TABLE
CREATE TABLE notifications (
  id bigserial PRIMARY KEY,
  sender_id INT,
  receiver_id INT,
  type VARCHAR(50),
  content VARCHAR(255),
  is_read BOOLEAN DEFAULT FALSE,
  post_id INT NULL,
  comment_id INT NULL,
  friendship_id INT NULL,
  chat_id INT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_notifications_sender FOREIGN KEY (sender_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_receiver FOREIGN KEY (receiver_id) REFERENCES users(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_post FOREIGN KEY (post_id) REFERENCES posts(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_comment FOREIGN KEY (comment_id) REFERENCES comments(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_friendship FOREIGN KEY (friendship_id) REFERENCES friendships(id) ON DELETE CASCADE,
  CONSTRAINT fk_notifications_chat FOREIGN KEY (chat_id) REFERENCES chats(id) ON DELETE CASCADE
);

--INSERT POSTS SEEDS
INSERT INTO posts (content, photo_url, user_id, created_at) VALUES
('Sunday brunch 🥑🍞', 'https://plus.unsplash.com/premium_photo-1673581430690-0b42ab287ae9?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1, '2024-11-25 08:00:00'),
('Anyone else obsessed with the new season of Stranger Things? Can’t stop watching! 📺🍿', NULL, 2, '2024-11-01 09:00:00'),
('Had the best weekend hike! Nature really does wonders for the soul. 🌲🌄', 'https://images.unsplash.com/photo-1501554728187-ce583db33af7?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 3, '2024-11-01 10:00:00'),
('First day at my new job today! Excited for this new chapter. ✨ #NewBeginnings', NULL, 1, '2024-11-02 08:00:00'),
('Halloween activities #Boo', 'https://plus.unsplash.com/premium_photo-1714618933590-febf07fce40c?q=80&w=2660&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1, '2024-10-31 18:00:00'),
('Starting the weekend right 💪 #FitnessGoals #Crossfit', 'https://images.unsplash.com/photo-1623874514711-0f321325f318?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 2, '2024-11-22 09:30:00'),
('Caught a sunset that looked like it was straight out of a painting tonight. 🌅', 'https://plus.unsplash.com/premium_photo-1673002094195-f18084be89ce?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 3, '2024-11-02 10:45:00'),
('He just knocked over my coffee. Good job he''s cute. 🐱☕️', 'https://images.unsplash.com/photo-1513360371669-4adf3dd7dff8?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 4, '2024-11-02 12:00:00'),
('Tried a new recipe today: vegan meatballs! It was surprisingly delicious. 🍝 #CookingAdventures', 'https://images.unsplash.com/photo-1515516969-d4008cc6241a?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D', 1, '2024-11-24 19:00:00'),
('Weekend plans: Netflix and pizza, obviously. 🍕🍿 #LazySaturday', NULL, 2, '2024-10-28 16:00:00');

--INSERT POST_LIKES SEEDS
INSERT INTO post_likes (user_id, post_id, created_at) VALUES
(5, 1, '2024-11-25 08:05:23'),
(2, 1, '2024-11-25 09:12:47'),
(3, 1, '2024-11-25 10:25:10'),
(4, 1, '2024-11-26 14:15:00'),
(1, 2, '2024-11-01 10:30:00'),
(3, 2, '2024-11-01 15:40:23'),
(5, 2, '2024-11-02 11:10:50'),
(2, 3, '2024-11-02 12:45:00'),
(4, 3, '2024-11-03 16:30:23'),
(2, 5, '2024-11-02 08:25:30'),
(1, 6, '2024-11-22 09:50:00'),
(5, 6, '2024-11-22 10:35:45'),
(3, 6, '2024-11-22 11:20:30'),
(4, 6, '2024-11-22 13:55:40'),
(4, 7, '2024-11-02 12:05:12'),
(1, 8, '2024-10-29 15:25:33'),
(5, 8, '2024-10-30 10:40:20'),
(2, 9, '2024-11-24 20:05:02'),
(1, 10, '2024-11-02 12:10:00'),
(3, 10, '2024-11-02 12:30:15'),
(4, 10, '2024-11-02 13:00:45');

--INSERT COMMENT SEEDS
INSERT INTO comments (post_id, content, commenter_id, created_at) VALUES
(1, 'Looks delicious! 🍽️', 2, '2024-11-25 08:10:00'),
(1, 'I love brunch! Can I join next time? 😅', 3, '2024-11-25 09:20:30'),
(2, 'I’m obsessed too! Just finished the new season. 😱', 1, '2024-11-01 10:10:00'),
(3, 'Sounds like an amazing hike! Where did you go?', 4, '2024-11-01 14:25:50'),
(3, 'I need to get outdoors more! 🥾', 1, '2024-11-03 08:00:00'),
(4, 'Congratulations! Wishing you all the best! 🎉', 2, '2024-11-02 08:15:00'),
(4, 'Exciting! What’s your new role? ✨', 5, '2024-11-02 09:00:00'),
(4, 'Good luck on your new journey! 👏', 3, '2024-11-02 10:30:30'),
(5, 'Love the Halloween spirit! 🎃👻', 2, '2024-11-01 18:15:00'),
(5, 'Halloween is my favorite time of year! 🎃', 1, '2024-11-02 09:00:00'),
(6, 'I need to start working out like you! 🤩', 5, '2024-11-22 11:10:20'),
(6, 'I love your motivation! 💯', 4, '2024-11-22 13:20:45'),
(7, 'That’s a breathtaking view! 🌅', 5, '2024-11-02 11:00:00'),
(8, 'Cats are the best troublemakers! 😹', 3, '2024-11-02 12:10:00'),
(8, 'Haha, my cat does the same thing. So cute though! 🐱', 5, '2024-11-02 12:25:30'),
(9, 'I need to try this recipe! Vegan meatballs sound amazing!', 3, '2024-11-24 20:10:00'),
(9, 'This looks so good! Can you share the recipe? 😍', 5, '2024-11-24 20:30:30');

--INSERT COMMENT LIKES
INSERT INTO comment_likes (user_id, comment_id, created_at) VALUES
(1, 1, '2024-11-25 08:15:00'),
(3, 1, '2024-11-25 08:25:00'),
(4, 2, '2024-11-25 09:30:00'),
(1, 2, '2024-11-25 09:40:00'),
(5, 3, '2024-11-25 10:00:00'),
(2, 5, '2024-11-01 14:15:00'),
(5, 5, '2024-11-01 15:00:00'),
(1, 6, '2024-11-01 14:40:00'),
(1, 7, '2024-11-01 15:00:00'),
(1, 8, '2024-11-02 11:00:00'),
(1, 9, '2024-11-02 09:15:00'),
(4, 9, '2024-11-02 09:30:00'),
(1, 10, '2024-11-22 11:15:00'),
(2, 10, '2024-11-22 12:00:00'),
(1, 11, '2024-11-02 11:10:00'),
(4, 12, '2024-11-02 12:30:00'),
(3, 13, '2024-11-02 12:30:00'),
(4, 13, '2024-11-02 12:35:00'),
(4, 14, '2024-11-24 20:40:00'),
(4, 15, '2024-11-24 20:45:00');

--INSERT CHATS
INSERT INTO chats (user_1_id, user_2_id) VALUES
(1, 2),
(1, 3),
(4, 5);

--INSERT MESSAGES
INSERT INTO messages (sender_id, chat_id, message, created_at) VALUES
(1, 1, 'Hey, how are you?', '2024-11-15 09:00:00'),
(2, 1, 'I''m good! How about you?', '2024-11-15 09:05:00'),
(1, 1, 'Doing well! Just got back from a trip.', '2024-11-15 09:15:00'),
(2, 1, 'That sounds awesome! Tell me more.', '2024-11-15 09:20:00'),

(1, 2, 'Hey, just wanted to check in.', '2024-11-10 08:00:00'),
(3, 2, 'Everything is great here! How are you?', '2024-11-10 08:10:00'),
(1, 2, 'Can''t complain. Work is a bit hectic though.', '2024-11-10 08:15:00'),
(3, 2, 'Same here! But we''ll get through it.', '2024-11-10 08:30:00'),

(4, 3, 'Hey, thanks for adding me! It was great meeting you last night.', '2024-11-26 14:30:00'),
(5, 3, 'No problem! It was so fun meeting you too. How''s everything going?', '2024-11-26 14:35:00');

--INSERT FRIENDSHIP SEEDS
--only pending, confirmed or blocked
INSERT INTO friendships (sender_id, receiver_id, status, created_at) VALUES
(1, 2, 'confirmed', '2024-09-15 10:00:00'),
(1, 3, 'confirmed', '2024-09-20 14:30:00'),
(1, 4, 'blocked', '2024-09-25 09:00:00'),
(1, 5, 'confirmed', '2024-09-28 16:45:00'),
(2, 3, 'confirmed', '2024-10-05 11:00:00'),
(2, 4, 'confirmed', '2024-10-06 13:10:00'),
(3, 5, 'pending', '2024-10-07 14:25:00'),
(4, 5, 'confirmed', '2024-10-09 09:45:00');

--INSERT NOTIFICATION SEEDS
-- Notifications for Post Likes
INSERT INTO notifications (sender_id, receiver_id, type, content, post_id, created_at, is_read) VALUES
(5, 1, 'like', 'Jess liked your post', 1, '2024-11-25 08:05:23', TRUE),
(2, 1, 'like', 'Tobi liked your post', 1, '2024-11-25 09:12:47', TRUE),
(3, 1, 'like', 'Shaeera liked your post', 1, '2024-11-25 10:25:10', TRUE),
(4, 1, 'like', 'Alessandro liked your post', 1, '2024-11-26 14:15:00', FALSE),
(1, 2, 'like', 'Valeria liked your post', 2, '2024-11-01 10:30:00', TRUE),
(3, 2, 'like', 'Shaeera liked your post', 2, '2024-11-01 15:40:23', TRUE),
(5, 2, 'like', 'Jess liked your post', 2, '2024-11-02 11:10:50', TRUE),
(2, 3, 'like', 'Tobi liked your post', 3, '2024-11-02 12:45:00', TRUE),
(4, 3, 'like', 'Alessandro liked your post', 3, '2024-11-03 16:30:23', TRUE),
(2, 1, 'like', 'Tobi liked your post', 5, '2024-11-02 08:25:30', TRUE),
(1, 2, 'like', 'Valeria liked your post', 6, '2024-11-22 09:50:00', TRUE),
(5, 2, 'like', 'Jess liked your post', 6, '2024-11-22 10:35:45', TRUE),
(3, 2, 'like', 'Shaeera liked your post', 6, '2024-11-22 11:20:30', TRUE),
(4, 2, 'like', 'Alessandro liked your post', 6, '2024-11-22 13:55:40', TRUE),
(4, 3, 'like', 'Alessandro liked your post', 7, '2024-11-02 12:05:12', TRUE),
(1, 4, 'like', 'Valeria liked your post', 8, '2024-10-29 15:25:33', TRUE),
(5, 4, 'like', 'Jess liked your post', 8, '2024-10-30 10:40:20', TRUE),
(2, 1, 'like', 'Tobi liked your post', 9, '2024-11-24 20:05:02', TRUE),
(1, 2, 'like', 'Valeria liked your post', 10, '2024-11-02 12:10:00', TRUE),
(3, 2, 'like', 'Shaeera liked your post', 10, '2024-11-02 12:30:15', TRUE),
(4, 2, 'like', 'Alessandro liked your post', 10, '2024-11-02 13:00:45', TRUE);

-- Notifications for Comments
INSERT INTO notifications (sender_id, receiver_id, type, content, post_id, created_at, is_read) VALUES
(2, 1, 'comment', 'Tobi commented on your post', 1, '2024-11-25 08:10:00', TRUE),
(3, 1, 'comment', 'Shaeera commented on your post', 1, '2024-11-25 09:20:30', TRUE),
(1, 2, 'comment', 'Valeria commented on your post', 2, '2024-11-01 10:10:00', TRUE),
(4, 3, 'comment', 'Alessandro commented on your post', 3, '2024-11-01 14:25:50', TRUE),
(1, 3, 'comment', 'Valeria commented on your post', 3, '2024-11-03 08:00:00', TRUE),
(2, 1, 'comment', 'Tobi commented on your post', 4, '2024-11-02 08:15:00', TRUE),
(5, 1, 'comment', 'Jess commented on your post', 4, '2024-11-02 09:00:00', TRUE),
(3, 1, 'comment', 'Shaeera commented on your post', 4, '2024-11-02 10:30:30', TRUE),
(2, 1, 'comment', 'Tobi commented on your post', 5, '2024-11-01 18:15:00', TRUE),
(1, 1, 'comment', 'Valeria commented on your post', 5, '2024-11-02 09:00:00', TRUE),
(5, 2, 'comment', 'Jess commented on your post', 6, '2024-11-22 11:10:20', TRUE),
(4, 2, 'comment', 'Alessandro commented on your post', 6, '2024-11-22 13:20:45', TRUE),
(5, 3, 'comment', 'Jess commented on your post', 7, '2024-11-02 11:00:00', TRUE),
(3, 4, 'comment', 'Shaeera commented on your post', 8, '2024-11-02 12:10:00', TRUE),
(5, 4, 'comment', 'Jess commented on your post', 8, '2024-11-02 12:25:30', TRUE),
(3, 1, 'comment', 'Shaeera commented on your post', 9, '2024-11-24 20:10:00', TRUE),
(5, 1, 'comment', 'Jess commented on your post', 9, '2024-11-24 20:30:30', TRUE);

-- Notifications for Comment Likes
INSERT INTO notifications (sender_id, receiver_id, type, content, comment_id, created_at, is_read) VALUES
(1, 2, 'like', 'Valeria liked your comment', 1, '2024-11-25 08:15:00', TRUE),
(3, 2, 'like', 'Shaeera liked your comment', 1, '2024-11-25 08:25:00', TRUE),
(4, 3, 'like', 'Alessandro liked your comment', 2, '2024-11-25 09:30:00', TRUE),
(1, 3, 'like', 'Valeria liked your comment', 2, '2024-11-25 09:40:00', TRUE),
(5, 1, 'like', 'Jess liked your comment', 3, '2024-11-25 10:00:00', TRUE),
(2, 1, 'like', 'Tobi liked your comment', 5, '2024-11-01 14:15:00', TRUE),
(5, 1, 'like', 'Jess liked your comment', 5, '2024-11-01 15:00:00', TRUE),
(1, 2, 'like', 'Valeria liked your comment', 6, '2024-11-01 14:40:00', TRUE),
(1, 5, 'like', 'Valeria liked your comment', 7, '2024-11-01 15:00:00', TRUE),
(1, 3, 'like', 'Valeria liked your comment', 8, '2024-11-02 11:00:00', TRUE),
(1, 2, 'like', 'Valeria liked your comment', 9, '2024-11-02 09:15:00', TRUE),
(4, 2, 'like', 'Alessandro liked your comment', 9, '2024-11-02 09:30:00', TRUE),
(1, 1, 'like', 'Valeria liked your comment', 10, '2024-11-22 11:15:00', TRUE),
(2, 1, 'like', 'Valeria liked your comment', 10, '2024-11-22 12:00:00', TRUE),
(1, 5, 'like', 'Valeria liked your comment', 11, '2024-11-02 11:10:00', TRUE),
(4, 4, 'like', 'Alessandro liked your comment', 12, '2024-11-02 12:30:00', TRUE),
(3, 5, 'like', 'Shaeera liked your comment', 13, '2024-11-02 12:30:00', TRUE),
(4, 5, 'like', 'Alessandro liked your comment', 13, '2024-11-02 12:35:00', TRUE),
(4, 3, 'like', 'Alessandro liked your comment', 14, '2024-11-24 20:40:00', TRUE),
(4, 5, 'like', 'Alessandro liked your comment', 15, '2024-11-24 20:45:00', TRUE);

-- Notifications for Friend Requests
INSERT INTO notifications (sender_id, receiver_id, type, content, friendship_id, created_at, is_read) VALUES
(1, 2, 'friend_request', 'Valeria sent you a friend request', 1, '2024-09-15 10:00:00', TRUE),
(1, 3, 'friend_request', 'Valeria sent you a friend request', 2, '2024-09-20 14:30:00', TRUE),
(1, 4, 'friend_request', 'Valeria sent you a friend request', 3, '2024-09-28 16:45:00', TRUE),
(1, 5, 'friend_request', 'Valeria sent you a friend request', 4, '2024-10-05 11:00:00', TRUE),
(2, 3, 'friend_request', 'Tobi sent you a friend request', 5, '2024-10-06 13:10:00', TRUE),
(2, 4, 'friend_request', 'Tobi sent you a friend request', 6, '2024-10-07 14:25:00', TRUE),
(3, 5, 'friend_request', 'Shaeera sent you a friend request', 7, '2024-10-07 14:25:00', TRUE),
(4, 5, 'friend_request', 'Alessandro sent you a friend request', 8, '2024-10-09 09:45:00', TRUE);

-- Notifications for Messages
INSERT INTO notifications (sender_id, receiver_id, type, content, chat_id, created_at, is_read) VALUES
(1, 2, 'message', 'Valeria sent you a message: "Hey, how are you?"', 1, '2024-11-15 09:00:00', TRUE),
(2, 1, 'message', 'Tobi sent you a message: "I''m good! How about you?"', 1, '2024-11-15 09:05:00', TRUE),
(1, 2, 'message', 'Valeria sent you a message: "Doing well! Just got back from a trip."', 1, '2024-11-15 09:15:00', TRUE),
(2, 1, 'message', 'Tobi sent you a message: "That sounds awesome! Tell me more."', 1, '2024-11-15 09:20:00', TRUE),

(1, 3, 'message', 'Valeria sent you a message: "Hey, just wanted to check in."', 2, '2024-11-10 08:00:00', TRUE),
(3, 1, 'message', 'Shaeera sent you a message: "Everything is great here! How are you?"', 2, '2024-11-10 08:10:00', TRUE),
(1, 3, 'message', 'Valeria sent you a message: "Can''t complain. Work is a bit hectic though."', 2, '2024-11-10 08:15:00', TRUE),
(3, 1, 'message', 'Shaeera sent you a message: "Same here! But we''ll get through it."', 2, '2024-11-10 08:30:00', TRUE),

(4, 5, 'message', 'Alessandro sent you a message: "Hey, thanks for adding me! It was great meeting you last night."', 3, '2024-11-26 14:30:00', TRUE),
(5, 4, 'message', 'Jess sent you a message: "No problem! It was so fun meeting you too. How''s everything going?"', 3, '2024-11-26 14:35:00', FALSE);

