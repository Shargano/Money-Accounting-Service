insert into "loss"(id, wallet_id, name, money_count, date_time, state)
values (3, 2, 'myProfit', '100', '2019-08-14 18:39:12.704', 'ACTIVE');

update "wallet" set money_count = 23.55 where id = 2;

insert into "payment"(id, date_time, goal_id, money_count, type, wallet_id, parent_id)
values (2, '2019-08-14 18:39:12.704', 3, '100', 'CREATE_LOSS', 2, null);
