package dev.alnat.ustorage.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Центральная сущность - файл.
 * С ней происходит все связи, но cам файл лежит во внешних хранилищах, доступ получается через системы
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Table(name = "file")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "fileID", scope = File.class)
public final class File implements Serializable {

    private static final long serialVersionUID = 6331500916245459452L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fileID;

    /**
     * Дата создания файла
     */
    @Column(nullable = false, name = "creationdate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime creationDate;

    /**
     * Оригинальное имя файла
     */
    @Column(nullable = false)
    private String filename;

    /**
     * Расширение файла
     */
    @Column(nullable = false)
    private String extension;

    /**
     * UUID файла для поиска в хранилищах
     */
    @Column(nullable = false, length = 45, name = "storagefilename")
    private String storageFileName;

    /**
     * Кол-во мест, где файл сохранен
     * Поля нет в БД, подгружается в RunTime
     */
    @Formula(value = "(SELECT COUNT(1) FROM FileStorage fs WHERE fs.fileID = fileID)")
    private Integer storageSystemCount;

    /**
     * Пользователь, который загрузил данный файл
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userID", nullable = false, foreignKey = @ForeignKey(name = "file_userid_fkey"))
    private User user;

    /**
     * Набор ключей систем, в которых сохраненн этот файл
     * Зачастую будет удобнее получать системы хранения по этому ключу и уже манипулировать ими
     * чем тащить полную сущность SystemConfiguration
     *
     * TODO Разобраться почему валиться с ошибкой!
     */
    @LazyCollection(LazyCollectionOption.FALSE) // Необходимо, т.к. иначе это поле становиться LazyLoad
    @ElementCollection(targetClass=String.class)
    @Formula("(SELECT sc.key FROM FileStorage as fs " +
            "LEFT JOIN systemConfiguration as sc ON fs.systemConfigurationID = sc.systemConfigurationID " +
            "WHERE fs.fileID = fileID)")
    private List<String> storageSystemKeyList;

    /**
     * Связь с системами хранения этого файла
     * Классическая связь много-ко-многим
     *
     * TODO В связи с https://github.com/FasterXML/jackson-dataformat-xml/issues/27 необходимо переделать это имя
     */
    @JacksonXmlElementWrapper(localName = "storageSystemList")
    @JacksonXmlProperty(localName = "storageSystem")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "filestorage",
            joinColumns        = {
                @JoinColumn(name = "fileID", nullable = false, foreignKey = @ForeignKey(name = "filestorage_fileid_fkey"))
            },
            inverseJoinColumns = {
                @JoinColumn(name = "systemConfigurationID", nullable = false, foreignKey = @ForeignKey(name = "filestorage_systemconfigurationid_fkey"))
            }
    )
    private List<SystemConfiguration> storageSystemList;


    public File() {
        this.storageSystemList = new ArrayList<>();
    }


    public Integer getFileID() {
        return fileID;
    }

    public void setFileID(Integer fileID) {
        this.fileID = fileID;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getStorageSystemCount() {
        return storageSystemCount;
    }

    public void setStorageSystemCount(Integer storageSystemCount) {
        this.storageSystemCount = storageSystemCount;
    }

    public String getStorageFileName() {
        return storageFileName;
    }

    public void setStorageFileName(String storageFileName) {
        this.storageFileName = storageFileName;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<SystemConfiguration> storageSystemList() {
        return storageSystemList;
    }

    public void storageSystemList(List<SystemConfiguration> storageSystem) {
        this.storageSystemList = storageSystem;
    }

    public void setStorageSystem(SystemConfiguration storageSystem) {
        if (this.storageSystemList == null) {
            this.storageSystemList = Collections.singletonList(storageSystem);
        } else {
            this.storageSystemList().add(storageSystem);
        }
    }

    public List<String> getStorageSystemKeyList() {
        return storageSystemKeyList;
    }

    public void setStorageSystemKeyList(List<String> storageSystemKeyList) {
        this.storageSystemKeyList = storageSystemKeyList;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        File file = (File) o;
        return Objects.equals(fileID, file.fileID) &&
                Objects.equals(creationDate, file.creationDate) &&
                Objects.equals(filename, file.filename) &&
                Objects.equals(extension, file.extension) &&
                Objects.equals(storageFileName, file.storageFileName) &&
                Objects.equals(storageSystemCount, file.storageSystemCount) &&
                Objects.equals(user, file.user) &&
                Objects.equals(storageSystemKeyList, file.storageSystemKeyList) &&
                Objects.equals(storageSystemList, file.storageSystemList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileID, creationDate, filename, extension, storageFileName, storageSystemCount, user, storageSystemKeyList, storageSystemList);
    }

}
