-- Триггерная Функция для конфигурации
-- CREATE OR REPLACE FUNCTION public.configuration_create_trigger_function()
--   RETURNS trigger AS
--   $BODY$
--     BEGIN
--       NEW.created = now();
--       RETURN NEW;
--     END
--   $BODY$
-- LANGUAGE plpgsql;
-- ALTER FUNCTION public.configuration_create_trigger_function() OWNER TO ustorage;

-- Сам триггер для конфигурации
DROP TRIGGER IF EXISTS configuration_create_trigger on ustorage.public.configuration;
CREATE TRIGGER configuration_create_trigger BEFORE INSERT ON ustorage.public.configuration FOR EACH ROW EXECUTE PROCEDURE configuration_create_trigger_function();



-- Аналогично для пользователя
-- CREATE OR REPLACE FUNCTION public.configuration_registerat_trigger_function()
-- RETURNS trigger AS
--     $BODY$
--         BEGIN
--             NEW.registerat = now();
--             RETURN NEW;
--         END
--     $BODY$
-- LANGUAGE plpgsql;
-- ALTER FUNCTION public.configuration_registerat_trigger_function() OWNER TO ustorage;

-- Сам триггер
DROP TRIGGER IF EXISTS configuration_registerat_trigger on ustorage.public."user";
CREATE TRIGGER configuration_registerat_trigger BEFORE INSERT ON ustorage.public."user" FOR EACH ROW EXECUTE PROCEDURE configuration_registerat_trigger_function();
