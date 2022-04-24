package com.nhnacademy.httpserver.propertysetter;

public abstract class CommonPropertySetter implements PropertySetter {
    private String properties;

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }
}
