create table wallet
(
    id          serial not null,
    money_count numeric(19, 2),
    name        varchar(255),
    user_id     int4,
    primary key (id)
);
alter table wallet
    add constraint fk_user_id foreign key (user_id) references "user" on delete cascade;