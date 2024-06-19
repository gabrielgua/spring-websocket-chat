CREATE TABLE chats_users(
    chat_id varchar(36) not null,
    user_id bigint not null,

    primary key(chat_id, user_id),
    constraint fk_chats_users_chats foreign key (chat_id) references chats (id),
    constraint fk_chats_users_users foreign key (user_id) references users (id)
);