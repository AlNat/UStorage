-- Общий скрипт инициализации БД для проекта
create database ustorage;

-- Создание пользователя
CREATE USER ustorage WITH PASSWORD 'ustorage';
GRANT ALL PRIVILEGES ON DATABASE "ustorage" to ustorage;



-- Таблица пользователей
create table "user"
(
    userID serial PRIMARY KEY, -- PK
    login varchar(255) not null, -- Имя
    password varchar(1024) not null, -- BCrypt пароль
    registerat timestamptz not null, -- Дата регистрации
    email varchar(1024) not null, -- Адрес почты
    role varchar(1024) -- Роли пользователя
                       -- По хорошему нужно вынести в отдельную таблицу с правами (много-ко-многим связь),
                       -- но для упрощения пока просто в строке храним права через запятую
);

ALTER TABLE "user" OWNER TO ustorage;

-- Индексы
create index user_name_index on "user" (login);
create unique index user_name_uindex on "user" (login);
create unique index user_email_uindex on "user" (email);
create index user_email_index on "user" (email);
create index user_register_at_index on "user" (registerat);



-- Глобальная конфигурация
create table configuration
(
    configurationid serial       PRIMARY KEY, -- PK
    created         timestamp    not null, -- Дата создания
    name            varchar(255) not null, -- Ключ
    value           text         not null  -- Значение
);

alter table configuration owner to ustorage;

-- Индексы
create index configuration_name_index on "configuration" (name);



-- Конфигурация систем
create table systemconfiguration
(
    systemconfigurationid  serial primary key,   -- PK
    classhandler           varchar(255) not null, -- Путь к классу-плагину
    configuration          text, -- Конфигурация системы
    key                    varchar(255) not null unique, -- Идентификатор
    name                   varchar(255) not null, -- Именование системы для отображений
    type                   integer      not null  -- Тип системы
);

alter table systemconfiguration owner to ustorage;

-- Индексы
create index systemconfiguration_classhandler_index on "systemconfiguration" (classhandler);
create index systemconfiguration_key_index on "systemconfiguration" (key);
create index systemconfiguration_type_index on "systemconfiguration" (type);


-- Файл
create table file
(
    fileid            serial PRIMARY KEY, -- PK
    creationdate      timestamp    not null, -- Дата создания
    extension         varchar(255) not null, -- Расширение файла
    filename          varchar(255) not null, -- Имя файла
    storagefilename   varchar(45)  not null, -- Имя файла в системе сохранения (GUID)
    userid            integer      not null references "user"(userID) -- Связь с владельцем
);

alter table file owner to ustorage;

-- Индексы
create index file_creationdate_index on "file" (creationdate desc);
create index file_filename_index on "file" (filename);
create index file_storagefilename_index on "file" (storagefilename);
create index file_userid_index on "file" (userid);



-- Хранение файлов
create table filestorage
(
    fileid integer not null references file(fileid),
    systemconfigurationid integer not null references systemconfiguration(systemconfigurationid)
);

alter table filestorage owner to ustorage;

-- Индексы
create index filestorage_fileid_index on "filestorage" (fileid);
create index filestorage_systemconfigurationid_index on "filestorage" (systemconfigurationid);



-- TODO Отдельной БД\схемой - точно отдельный DataSource
-- Файлы (хранение файлов в БД)

-- TODO Добавить при Security
-- Логин лог (полный лог входа всех пользователей)

-- TODO Добавить при реализации уникальных ссылок на файл
-- Ссылки
-- Скачивания

