package dev.alnat.ustorage.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Модель(таблица) для описания глобальной конфигурации приложения
 * Содерижит конфигурацию в формате ключ-значение
 *
 * Created by @author AlNat on 08.01.2020.
 * Licensed by Apache License, Version 2.0
 */
@Entity
@Table(name = "configuration")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "configurationID", scope = Configuration.class)
public class Configuration implements Serializable {

    private static final long serialVersionUID = 6335235252L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer configurationID;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Type(type="text")
    private String value;

    /**
     * Дата создания этого поля конфигурации
     */
    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", shape = JsonFormat.Shape.STRING)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private LocalDateTime created;


    public Configuration() {
    }


    public Integer getConfigurationID() {
        return configurationID;
    }

    public void setConfigurationID(Integer configurationID) {
        this.configurationID = configurationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Configuration that = (Configuration) o;
        return Objects.equals(configurationID, that.configurationID) &&
                Objects.equals(name, that.name) &&
                Objects.equals(value, that.value) &&
                Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(configurationID, name, value, created);
    }

}
