create table friend_requests(
	requester_id bigint not null,
    receiver_id bigint not null,

    status varchar(20) not null,
    created_at timestamp default current_timestamp,

    primary key(requester_id, receiver_id),
    constraint fk_requester_friend_requests foreign key (requester_id) references users (id) on delete cascade,
    constraint fk_receiver_friend_requests foreign key (receiver_id) references users (id) on delete cascade
);