CREATE TABLE chats(
    id varchar(36) not null,
    name varchar(255) null,
    type varchar(10) not null,
    created_at datetime not null,

    primary key (id)
);