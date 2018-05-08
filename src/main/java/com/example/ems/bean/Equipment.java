package com.example.ems.bean;

import org.springframework.context.annotation.Bean;

public class Equipment {
    String id;
    String attribute;
    String value;
    String rights;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public String getRights() {
        return rights;
    }

    public void setRights(String rights) {
        this.rights = rights;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "id='" + id + '\'' +
                ", attribute='" + attribute + '\'' +
                ", value='" + value + '\'' +
                ", rights='" + rights + '\'' +
                '}';
    }
}
