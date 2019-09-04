create table "user"
(
    id         serial not null,
    first_name varchar(255),
    last_name  varchar(255),
    login      varchar(255) unique,
    password   varchar(255),
    patronymic varchar(255),
    primary key (id)
);
create table user_role
(
    user_id int4 not null,
    roles   varchar(255)
);
alter table user_role
    add constraint fk_user_id foreign key (user_id) references "user" on delete cascade;