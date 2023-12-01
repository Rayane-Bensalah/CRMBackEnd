CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name character varying(100) NOT NULL UNIQUE,
    first_name character varying(100),
    last_name character varying(100),
    email character varying(255) UNIQUE,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone
);
​
CREATE TABLE channels (
    id SERIAL PRIMARY KEY,
    name character varying(100) NOT NULL,
    is_main boolean DEFAULT false NOT NULL,
    created_at timestamp without time zone
);
​
CREATE TABLE messages (
    id SERIAL PRIMARY KEY,
    user_id integer NOT NULL,
    channel_id integer NOT NULL,
    content text NOT NULL,
    send_date timestamp without time zone NOT NULL,
    CONSTRAINT fk_channel_message FOREIGN KEY (channel_id) REFERENCES channels(id),
    CONSTRAINT fk_user_message FOREIGN KEY (user_id) REFERENCES users(id)
);