alter table chats add column description varchar(255) null;
alter table chats add column creator_id bigint not null;

SET FOREIGN_KEY_CHECKS=0;

alter table chats add constraint fk_chats_creator foreign key (creator_id) references users (id);

SET FOREIGN_KEY_CHECKS=1;
