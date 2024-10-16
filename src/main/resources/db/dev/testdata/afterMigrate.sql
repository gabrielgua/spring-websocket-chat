set foreign_key_checks = 0;

delete from chats;
delete from users;
delete from messages;
delete from chats_users;
delete from users_friends;
delete from users_unread_messages;

set foreign_key_checks = 1;

alter table users auto_increment = 1;
alter table messages auto_increment = 1;

insert into users (id, username, password, status, name, avatar_url) values
(1, "opaco", "$2a$12$DbC9J4gvw/I9OdZVKRxTr.Fe3FjkMsdaQa.fjPkae1hFY56IXAdBK", "OFFLINE", "Opaco horacio", "https://api.dicebear.com/9.x/bottts-neutral/svg?radius=50&seed=opacobfac823d-91c6-4c22-a0ad-ab96704cca65"),
(2, "gabrielgua", "$2a$12$9PjSs0gKed.jgBY58gcINe4YYrReCEecjgXf8ZUYq6wP2obDVhcIq", "OFFLINE", "Gabriel Guaitanele", "https://api.dicebear.com/9.x/bottts-neutral/svg?radius=50&seed=gabrielgua64a784d1-b204-4f6e-934c-4aee18fbca2d"),
(3, "usuario", "$2a$12$kY3O3n3Hi5o5ZmBvg2o4bOlksj7rjS1hlZGnzsGkZFjkySxtQvJsa", "OFFLINE", "Usuário da Silva Sauro Dinossauro", "https://api.dicebear.com/9.x/bottts-neutral/svg?radius=50&seed=usuario079add12-53a0-4fa2-8597-d1a6a25e4f84"),
(4, "guaitanele.gamer", "$2a$10$tYTxZX1qaliXQw1pJsIKD.92ryHD6CuD6l39IAFzP4G8Qk1w5/r7C", "OFFLINE", "Guaitanele Niszczak Gabriel", "https://api.dicebear.com/9.x/bottts-neutral/svg?radius=50&seed=guaitanele.gamer7cdd70f7-0aa6-4324-9954-996f32fc9487");

insert into users_friends (user_id, friend_id) values
(1, 2), (1, 3),
(2, 1),
(3, 1), (3, 4),
(4, 3);

insert into chats (id, name, type, created_at, description, creator_id) values
("86a8c397-be8a-4582-a1f4-7e1a95eab639", "Vasco OFC", "GROUP", "2024-06-19T11:11:11", "Grupinho dos guri do Vasco OFICIAL 👌🔥", 1),
("47a91172-9529-4917-9258-3486231ea12f", "opaco, usuario", "PRIVATE", "2024-04-18 12:13:22", null, 1),
("c198560d-496b-4f05-a2ac-f1f052113854", "usuario, guaitanele", "PRIVATE", "2024-10-08 12:04:55", null, 3),
("ca3eb690-7afa-4a5d-b65f-e07cca178cf2", "opaco, gabrielgua", "PRIVATE", "2024-06-18 17:19:45", null, 1);

insert into chats_users (chat_id, user_id) values
("86a8c397-be8a-4582-a1f4-7e1a95eab639", 1),
("86a8c397-be8a-4582-a1f4-7e1a95eab639", 2),
("86a8c397-be8a-4582-a1f4-7e1a95eab639", 3),
("47a91172-9529-4917-9258-3486231ea12f", 1),
("47a91172-9529-4917-9258-3486231ea12f", 3),
("c198560d-496b-4f05-a2ac-f1f052113854", 3),
("c198560d-496b-4f05-a2ac-f1f052113854", 4),
("ca3eb690-7afa-4a5d-b65f-e07cca178cf2", 1),
("ca3eb690-7afa-4a5d-b65f-e07cca178cf2", 2);

insert into messages (id, chat_id, sender_id, content, timestamp) values
(1, "86a8c397-be8a-4582-a1f4-7e1a95eab639", 2, "Vasco", "2024-07-10 00:03:26"),
(2, "86a8c397-be8a-4582-a1f4-7e1a95eab639", 2, "Salve", "2024-07-10 00:04:07"),
(3, "86a8c397-be8a-4582-a1f4-7e1a95eab639", 1, "Eae", "2024-07-10 00:04:19"),
(4, "86a8c397-be8a-4582-a1f4-7e1a95eab639", 2, "Boa noite opaco", "2024-07-10 00:05:20"),
(5, "86a8c397-be8a-4582-a1f4-7e1a95eab639", 1, "De boa gabriel?", "2024-07-10 00:10:21"),
(6, "86a8c397-be8a-4582-a1f4-7e1a95eab639", 3, "Boa noite galera", "2024-07-11 12:12:26"),
(7, "86a8c397-be8a-4582-a1f4-7e1a95eab639", 3, "A mente do paiaso 🤡", "2024-07-11 12:03:26"),

(8, "47a91172-9529-4917-9258-3486231ea12f", 1, "Eai man, tudo bem?", "2024-10-08 22:55:15"),
(9, "47a91172-9529-4917-9258-3486231ea12f", 3, "Tranquilo e vc?", "2024-10-08 23:00:15"),
(10, "47a91172-9529-4917-9258-3486231ea12f", 1, "tudo tbm", "2024-10-08 23:02:15"),

(11, "ca3eb690-7afa-4a5d-b65f-e07cca178cf2", 2, "Testando aqui", "2024-07-10 00:02:54"),
(12, "ca3eb690-7afa-4a5d-b65f-e07cca178cf2", 1, "Testa aqui então otario", "2024-07-10 00:10:54"),
(13, "ca3eb690-7afa-4a5d-b65f-e07cca178cf2", 2, "qual foi", "2024-07-10 00:12:54");







