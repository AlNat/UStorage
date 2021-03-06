package dev.alnat.ustorage.core.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Модель(таблица) для описания конфигурации системы хранения
 * Содерижит саму конфигурация в JSON (индивидуальна под систему) и метаданные
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Table(name = "systemconfiguration")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "systemConfigurationID", scope = SystemConfiguration.class)
public final class SystemConfiguration implements Serializable {

    private static final long serialVersionUID = 283208373206759005L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "systemConfigurationID")
    private Integer systemConfigurationID;

    /**
     * Идентификатор системы
     */
    @Column(unique = true, nullable = false)
    private String key;

    /**
     * Человеческое имя системы
     */
    @Column(nullable = false)
    private String name;

    /**
     * Полное имя класса, по которому будет создана системы
     */
    @Column(nullable = false, name = "classhandler")
    private String classHandler;

    /**
     * Тип системы
     * TODO Сделать через таблицу-справочник
     */
    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private StorageTypeEnum type;

    /**
     * Кол-во файлов в этой системе
     * Поля нет в БД, подгружается в RunTime
     */
    @Formula(value = "(SELECT COUNT(1) FROM FileStorage fs WHERE fs.systemConfigurationID = systemConfigurationID)")
    private Integer fileCount;

    /**
     * JSON конфигурация системы
     */
    @JsonIgnore
    @XmlTransient
    @Column(nullable = false)
    @Type(type = "text")
    private String configuration;

    /**
     * Cписок файлов в этой системе
     * Классическая связь много-ко-многим
     *
     * TODO В связи с https://github.com/FasterXML/jackson-dataformat-xml/issues/27 необходимо переделать это имя
     */
    @JacksonXmlElementWrapper(localName = "storageSystemList")
    @JacksonXmlProperty(localName = "storageSystem")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    @JoinTable(name = "filestorage",
            joinColumns = {
                    @JoinColumn(name = "systemConfigurationID", nullable = false, foreignKey = @ForeignKey(name = "filestorage_systemconfigurationid_fkey"))
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "fileID", nullable = false, foreignKey = @ForeignKey(name = "filestorage_fileid_fkey"))
            }
    )
    private List<File> fileList;


    public SystemConfiguration() {
        this.fileList = new ArrayList<>();
    }


    public Integer getSystemConfigurationID() {
        return systemConfigurationID;
    }

    public void setSystemConfigurationID(Integer systemConfigurationID) {
        this.systemConfigurationID = systemConfigurationID;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public StorageTypeEnum getType() {
        return type;
    }

    public void setType(StorageTypeEnum type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassHandler() {
        return classHandler;
    }

    public void setClassHandler(String classHandler) {
        this.classHandler = classHandler;
    }

    public String getConfiguration() {
        return configuration;
    }

    public void setConfiguration(String configuration) {
        this.configuration = configuration;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SystemConfiguration that = (SystemConfiguration) o;
        return Objects.equals(systemConfigurationID, that.systemConfigurationID) &&
                Objects.equals(key, that.key) &&
                Objects.equals(name, that.name) &&
                Objects.equals(classHandler, that.classHandler) &&
                type == that.type &&
                Objects.equals(fileCount, that.fileCount) &&
                Objects.equals(configuration, that.configuration) &&
                Objects.equals(fileList, that.fileList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(systemConfigurationID, key, name, classHandler, type, fileCount, configuration, fileList);
    }

}
