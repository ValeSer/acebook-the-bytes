DROP TABLE IF EXISTS messages;

CREATE TABLE messages (
    id bigserial PRIMARY KEY,
    sender_username VARCHAR(255) NOT NULL,
    receiver_username VARCHAR(255) NOT NULL,
    content VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (sender_username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (receiver_username) REFERENCES users(username) ON DELETE CASCADE
);
