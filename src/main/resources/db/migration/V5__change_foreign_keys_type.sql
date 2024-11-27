--UPDATE EXISTING POSTS TABLE
ALTER TABLE posts
ALTER COLUMN user_id TYPE BIGINT;

--UPDATE POST_LIKES TABLE
ALTER TABLE post_likes
ALTER COLUMN user_id TYPE BIGINT;
ALTER COLUMN post_id TYPE BIGINT;

--UPDATE COMMENTS TABLE
ALTER TABLE comments
ALTER COLUMN post_id TYPE BIGINT;
ALTER COLUMN commenter_id TYPE BIGINT;

--UPDATE COMMENT_LIKES TABLE
ALTER TABLE comments_likes
ALTER COLUMN user_id TYPE BIGINT;
ALTER COLUMN comment_id TYPE BIGINT;

--UPDATE CHATS TABLE
ALTER TABLE chats
ALTER COLUMN user_1_id TYPE BIGINT;
ALTER COLUMN user_2_id TYPE BIGINT;

--UPDATE MESSAGES TABLE
ALTER TABLE messages
ALTER COLUMN sender_id TYPE BIGINT;
ALTER COLUMN chat_id TYPE BIGINT;

--UPDATE FRIENDSHIPS TABLE
ALTER TABLE friendships
ALTER COLUMN sender_id TYPE BIGINT;
ALTER COLUMN receiver_id TYPE BIGINT;

--UPDATE NOTIFICATIONS TABLE
ALTER TABLE notifications
ALTER COLUMN sender_id TYPE BIGINT;
ALTER COLUMN receiver_id TYPE BIGINT;
ALTER COLUMN post_id TYPE BIGINT;
ALTER COLUMN comment_id TYPE BIGINT;
ALTER COLUMN friendship_id TYPE BIGINT;
ALTER COLUMN chat_id TYPE BIGINT;


