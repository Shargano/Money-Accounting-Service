create table payment
(
    id          serial not null,
    date_time   timestamp,
    goal_id     int4,
    money_count numeric(19, 2),
    type        varchar(30),
    wallet_id   int4,
    primary key (id)
);

alter table payment
    add constraint fk_wallet_id foreign key (wallet_id) references wallet;