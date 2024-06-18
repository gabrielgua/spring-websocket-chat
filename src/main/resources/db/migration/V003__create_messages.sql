CREATE TABLE messages(
    id bigint not null auto_increment,
    chat_id varchar(32) not null,
    sender_id bigint not null,
    content varchar(255) not null,
    timestamp datetime not null,

    primary key (id),
    constraint fk_messages_sender foreign key (sender_id) references users (id),
    constraint fk_messages_chat foreign key (chat_id) references chats (id)
);