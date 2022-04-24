package com.nhnacademy.httpserver.propertysetter;

public class DataPropertySetter extends CommonPropertySetter {
    @Override
    public void setProperty(String request) {
         super.setProperties(request.split("\r\n\r\n")[1]);
    }

    public String getDataProperty() {
        return super.getProperties();
    }
}
