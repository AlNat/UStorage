package dev.alnat.ustorage.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;

/**
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public class Util {

    public static String getExtensionFromFilename(String filename) {
        if (filename == null || filename.equals("")) {
            return null;
        }

        int lastDotIndex = filename.toLowerCase().lastIndexOf(".");
        return filename.substring(lastDotIndex);
    }

    /**
     * Метод форматирования временного интервала
     *
     * @param duration интервал
     * @return форматированный интервал
     */
    public static String formatDuration(Duration duration) {
        long seconds = duration.getSeconds();
        long absSeconds = Math.abs(seconds);
        String positive = String.format(
                "%d ч. %02d м. %02d с.",
                absSeconds / 3600, // часы
                (absSeconds % 3600) / 60, // минуты
                absSeconds % 60); // секунды
        return seconds < 0 ? "-" + positive : positive;
    }

    /**
     * Метод получения полного текста ошибки
     *
     * @param e exception
     * @return полный стек-трейс в виде строки
     */
    public static String getStackTrace(Exception e) {
        final StringWriter sw = new StringWriter();
        final PrintWriter pw = new PrintWriter(sw, true);
        e.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

}
