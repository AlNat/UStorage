package dev.alnat.ustorage.core.conf;

/**
 * Класс-хранилище для параметров конфигурации
 * Сами параметры зашиты
 *
 * Можно было бы сделать через Enum, но смысла мало
 *
 * Created by @author AlNat on 16.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class Configuration {

    /**
     * Версия приложения
     */
    public static String VERSION = "VERSION";

    /**
     * Путь к папке, где хранятся jar файлы плагинов
     */
    public static String SYSTEM_PATH = "SYSTEM_PATH";

}
