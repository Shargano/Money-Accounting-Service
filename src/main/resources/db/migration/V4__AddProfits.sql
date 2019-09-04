create table profit
(
    id          serial not null,
    wallet_id   int4   not null,
    name        varchar(255),
    money_count numeric(19, 2),
    date_time   timestamp,
    state       VARCHAR(10),
    primary key (id)
);

alter table profit
    add constraint fk_wallet_id foreign key (wallet_id) references wallet on delete cascade;