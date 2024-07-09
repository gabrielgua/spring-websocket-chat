CREATE TABLE notifications (
    id bigint not null auto_increment,
    user_id bigint not null,
    chat_id varchar(255) not null,
    count int default 0,

    primary key (id),
    constraint fk_notifications_users foreign key (user_id) references users (id),
    constraint fk_notifications_chats foreign key (chat_id) references chats (id)
);