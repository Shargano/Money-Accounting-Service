create table report
(
    id        serial not null,
    user_id   int4   not null,
    date_from timestamp,
    date_to   timestamp,
    primary key (id)
);

create table report_profit
(
    report_id int4 NOT NULL,
    profit_id int4 NOT NULL,
    PRIMARY KEY (report_id, profit_id),
    CONSTRAINT fk_report_id
        FOREIGN KEY (report_id) REFERENCES report (id),
    CONSTRAINT fk_profit_id
        FOREIGN KEY (profit_id) REFERENCES profit (id)
);

create table report_loss
(
    report_id int4 NOT NULL,
    loss_id   int4 NOT NULL,
    PRIMARY KEY (report_id, loss_id),
    CONSTRAINT fk_report_id
        FOREIGN KEY (report_id) REFERENCES report (id),
    CONSTRAINT fk_loss_id
        FOREIGN KEY (loss_id) REFERENCES loss (id)
);

create table report_limit
(
    report_id int4 NOT NULL,
    limit_id  int4 NOT NULL,
    PRIMARY KEY (report_id, limit_id),
    CONSTRAINT fk_report_id
        FOREIGN KEY (report_id) REFERENCES report (id),
    CONSTRAINT fk_limit_id
        FOREIGN KEY (limit_id) REFERENCES "limit" (id)
);

alter table report
    add constraint fk_user_id foreign key (user_id) references "user" on delete cascade;