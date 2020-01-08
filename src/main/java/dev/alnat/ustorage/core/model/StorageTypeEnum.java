package dev.alnat.ustorage.core.model;

/**
 * TODO Сделать через таблицу-справочник
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public enum StorageTypeEnum {
    FILE_SYSTEM("В файловой системе"),
    DATABASE("В СУБД"),
    OBJECT_STORAGE("Во внешнем Object Storage"),
    DROPBOX("В сервисе Dropbox");

    private final String text;

    StorageTypeEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static StorageTypeEnum getByText(String text) {
        if (text == null) {
            throw new NullPointerException("Text is null");
        }

        for (StorageTypeEnum value : StorageTypeEnum.values()) {
            if (value.text.equals(text)) {
                return value;
            }
        }

        throw new IllegalArgumentException("No enum constant " + text);
    }

}
