create table users_friends(
    user_id bigint not null,
    friend_id bigint not null,

    primary key(user_id, friend_id),
    constraint fk_users_friends_users foreign key (user_id) references users (id),
    constraint fk_users_friends_friend foreign key (friend_id) references users (id)
);