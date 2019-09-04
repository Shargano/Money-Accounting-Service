alter table payment
    add parent_id int4 default null;

alter table payment
    add constraint fk_parent_id foreign key (parent_id) references payment;