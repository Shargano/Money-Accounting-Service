insert into "user"(id, first_name, last_name, patronymic, login, password,
                   time_zone, email, enable)
values (1, 'firstname', 'lastname', 'pat', 'Иван Неважников',
        '$2a$10$Ukx5LDavT8I.epdjeKqSX.n/Zj3j2.7a9XIoTiQYnGI.OegwhgsgO',
        'Asia/Omsk', 'ivneznakov123@gmail.com', false);

insert into user_role(user_id, roles)
values (1, 'USER');