CREATE table users_unread_messages (
    user_id bigint not null,
    message_id bigint not null,

    primary key(user_id, message_id),

    constraint fk_users_messages_users foreign key (user_id) references users (id),
    constraint fk_users_messages_messages foreign key (message_id) references messages (id)
);