package com.nhnacademy.httpserver.propertysetter;

public class DataPropertySetter implements PropertySetter {

    String dataProperty;

    @Override
    public void setProperty(String request) {
        dataProperty = request.split("\r\n\r\n")[1];
    }

    public String getDataProperty() {
        return dataProperty;
    }
}
