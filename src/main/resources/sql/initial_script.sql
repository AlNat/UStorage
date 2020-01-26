INSERT INTO "user" (registerat, email, role, login, password)
VALUES (current_timestamp, 'test@test.ru',  'ROLE_USER',                        'test',     '$2a$10$s6hbCRVLOkbIT3W8B/ucD.4WctXGLgDNfrmPqy2ha3pfix46FSnCG'), -- user
       (current_timestamp, 'api@test.ru',   'ROLE_API',                         'api',      '$2a$10$FEYQVFje/e3YZoXo2DEBZ.tdDBBoZOKaJ9MRIWYhMzdJ1faFIVzoq'), -- api
       (current_timestamp, 'admin@test.ru', 'ROLE_ADMIN, ROLE_API, ROLE_USER',  'admin',    '$2a$10$Lfrvvv8X6Jv9PgzkUSj5j.uHpV0nTPp0CKuSuX1/X4bIIqPtWVTUy'); -- admin

INSERT INTO configuration (created, name, value)
VALUES (current_timestamp, 'VERSION',                               '1.0'),
       (current_timestamp, 'SAVE_POLICY',                           'ONE'),
       (current_timestamp, 'FILE_SYSTEM_DEFAULT_STORAGE_SYSTEM',    'SIMPLE_FILE_STORAGE');

INSERT INTO systemconfiguration (classhandler, configuration, key, name, type)
VALUES ('dev.alnat.ustorage.core.system.filesystem.SimpleFileSystemStorage', '{}', 'TEST', 'Test', 0),
       ('dev.alnat.ustorage.core.system.filesystem.SimpleFileSystemStorage', '{
	"path" : "C:/Work/DIR/"
}', 'SIMPLE_FILE_STORAGE', 'Простая система для сохранения файлов в ФС', 0);

INSERT INTO file (creationdate, extension, filename, storagefilename, userid)
VALUES ('2020-01-11 14:54:58.000000', 'txt', 'test.txt', 'test', (SELECT userid FROM "user" WHERE login = 'test'));

INSERT INTO public.filestorage (systemconfigurationid, fileid)
VALUES ((SELECT systemconfigurationid FROM systemconfiguration WHERE key = 'TEST'),
        (SELECT fileid FROM file WHERE storagefilename = 'test'));