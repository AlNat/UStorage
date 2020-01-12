INSERT INTO "user" (password, registerat, email, role, login)
VALUES ('test', current_timestamp, 'test@test.ru', 'USER', 'test'),
       ('admin', current_timestamp, 'admin@test.ru', 'ADMIN, USER', 'admin');

INSERT INTO configuration (created, name, value)
VALUES (current_timestamp, 'VERSION', '1.0'),
       (current_timestamp, 'SAVE_POLICY', 'ONE'),
       (current_timestamp, 'FILE_SYSTEM_DEFAULT_STORAGE_SYSTEM', 'SIMPLE_FILE_STORAGE');

INSERT INTO systemconfiguration (classhandler, configuration, key, name, type)
VALUES ('dev.alnat.core.system.filesystem.SimpleFileSystemStorage', '{}', 'TEST', 'Test', 0),
       ('dev.alnat.core.system.filesystem.SimpleFileSystemStorage', '{
	"path" : "C:/Work/DIR/"
}', 'SIMPLE_FILE_STORAGE', 'Простая система для сохранения файлов в ФС', 0);

INSERT INTO file (creationdate, extension, filename, storagefilename, userid)
VALUES ('2020-01-11 14:54:58.000000', 'txt', 'test.txt', 'test', (SELECT userid FROM "user" WHERE login = 'test'));

INSERT INTO public.filestorage (systemconfigurationid, fileid)
VALUES ((SELECT systemconfigurationid FROM systemconfiguration WHERE key = 'TEST'),
        (SELECT fileid FROM file WHERE storagefilename = 'test'));