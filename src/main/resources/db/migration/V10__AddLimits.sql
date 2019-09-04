create table "limit"
(
    id          serial not null,
    wallet_id   int4   not null,
    money_count numeric(19, 2),
    date_from   timestamp,
    date_to     timestamp,
    primary key (id)
);

alter table "limit"
    add constraint fk_wallet_id foreign key (wallet_id) references wallet on delete cascade;