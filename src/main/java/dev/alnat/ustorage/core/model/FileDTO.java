package dev.alnat.ustorage.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

/**
 * Небольшое DTO для более удобного способа передачи файла в API запросы
 * Можно было бы воспользоваться Map.Entry, но так будет лучше расширяться
 *
 * Created by @author AlNat on 19.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "fileName", scope = FileDTO.class)
public class FileDTO {

    private byte[] file;

    private String fileName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;

    public FileDTO() {
    }


    public FileDTO(File file, byte[] fileData) {
        this.file = fileData;
        this.creationDate = file.getCreationDate();
        this.fileName = file.getFilename();
    }


    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileDTO fileDTO = (FileDTO) o;
        return Arrays.equals(file, fileDTO.file) &&
                Objects.equals(fileName, fileDTO.fileName) &&
                Objects.equals(creationDate, fileDTO.creationDate);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(fileName, creationDate);
        result = 31 * result + Arrays.hashCode(file);
        return result;
    }

}
