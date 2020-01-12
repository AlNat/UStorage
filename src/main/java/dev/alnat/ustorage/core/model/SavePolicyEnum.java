package dev.alnat.ustorage.core.model;

/**
 * Указание на политику сохранения файлов по умолчанию
 *
 * Created by @author AlNat on 12.01.2020.
 * Licensed by Apache License, Version 2.0
 */
public enum SavePolicyEnum {
    ONE("В 1-ой системе"),
    TWO("В 2-х системах"),
    THREE("В 3-х системах"),
    EVERY("В каждой системе");

    private final String text;

    SavePolicyEnum(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public static SavePolicyEnum getByText(String text) {
        if (text == null) {
            throw new NullPointerException("Text is null");
        }

        for (SavePolicyEnum value : SavePolicyEnum.values()) {
            if (value.text.equals(text)) {
                return value;
            }
        }

        throw new IllegalArgumentException("No enum constant " + text);
    }

}
