-- Общий скрипт инициализации БД для проекта
create database ustorage;

-- TODO Создание пользователя, переключение на БД

-- Таблица пользователей
create table "user"
(
    userID serial not null, -- PK
    name varchar(255) not null, -- Имя
    password varchar(1024) not null, -- BCrypt пароль
    register_at timestamptz not null, -- Дата регистрации
    email varchar(1024) not null, -- Адрес почты
    role varchar(1024) -- Роли пользователя
                       -- По хорошему нужно вынести в отдельную таблицу с правами (много-ко-многим связь),
                       -- но для упрощения пока просто в строке храним права через запятую
);

-- Индексы
create unique index user_id_uindex on "user" (userID);
create index user_name_index on "user" (name);
create unique index user_name_uindex on "user" (name);
create unique index user_email_uindex on "user" (email);
create index user_email_index on "user" (email);
create index user_register_at_index on "user" (register_at);
alter table "user" add constraint user_pk primary key (userID);



-- Конфигурация систем


-- Глобальная конфигурация


-- Файл




-- TODO Отдельной БД\схемой - точно отдельный DataSource
-- ФайлСторадж (хранение файлов в БД)

-- TODO Добавить при Security
-- Логин лог

-- TODO Добавить при реализации уникальных ссылок на файл
-- Ссылки
-- Скачивания

