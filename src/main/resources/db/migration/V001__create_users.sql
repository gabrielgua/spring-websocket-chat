CREATE TABLE users(
    id bigint not null auto_increment,
    username varchar(255) not null,
    password varchar(255) not null,
    status varchar(10) not null,

    unique(username),
    primary key (id)
);