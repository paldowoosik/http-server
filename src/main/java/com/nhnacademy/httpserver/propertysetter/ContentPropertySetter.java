package com.nhnacademy.httpserver.propertysetter;

import static java.lang.System.lineSeparator;

public class ContentPropertySetter extends CommonPropertySetter {
    public String getContentProperty() {
        return super.getProperties();
    }

    @Override
    public void setProperty(String request) {
        String[] contentTypeStream = request.split(lineSeparator());
        for (int i = 0; i < contentTypeStream.length; i++) {
            if (contentTypeStream[i].startsWith("Content-Type:")) {
                super.setProperties(contentTypeStream[i].split(" ")[1].split("\r")[0]);
                break;
            }
        }
    }
}
