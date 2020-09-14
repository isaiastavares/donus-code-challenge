insert into account (id, name, document, balance, created_at, updated_at)
values ('ddbc3ead-8f72-4cc4-8b27-dc08f1a2e7e7', 'Gustavo Vinicius Luan Campos', '67403678923', 0.00, '2020-09-06 21:20:50.000', '2020-09-06 21:20:50.000');
insert into account (id, name, document, balance, created_at, updated_at)
values ('25e7350c-2617-4293-ab15-4e69a940192e', 'Rafael Thomas Fernandes', '09711883350', 0.00, '2020-09-07 16:00:00.000', '2020-09-07 16:00:00.000');
insert into account (id, name, document, balance, created_at, updated_at)
values ('70aa215e-00b7-4125-a4ce-e669977884e3', 'Bryan Heitor Galvão', '15013545072', 0.00, '2020-09-05 10:00:00.000', '2020-09-05 10:00:00.000');
insert into account (id, name, document, balance, created_at, updated_at)
values ('60059135-e98b-4acb-a42b-743f4c798341', 'Raul Sebastião Moraes', '31577232194', 20.10, '2020-09-12 08:25:00.000', '2020-09-12 08:25:00.000');

insert into transaction (id, account_id, account_id_to, amount, balance_from, balance_to, created_at, type)
values ('e4b03499-5444-40bd-94a1-4f56d98f6658', '60059135-e98b-4acb-a42b-743f4c798341', null, 20.00, 0.00, 20.10, '2020-09-12 17:26:27.938734', 'DEPOSIT');