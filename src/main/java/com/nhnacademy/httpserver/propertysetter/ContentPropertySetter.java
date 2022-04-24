package com.nhnacademy.httpserver.propertysetter;

import static java.lang.System.lineSeparator;

public class ContentPropertySetter implements PropertySetter{

    String contentProperty ="";
    public String getContentProperty() {
        return contentProperty;
    }

    @Override
    public void setProperty(String request) {
        String[] contentTypeStream = request.split(lineSeparator());
        for (int i = 0; i < contentTypeStream.length; i++) {
            if(contentTypeStream[i].startsWith("Content-Type:")){
                contentProperty = contentTypeStream[i].split(" ")[1].split("\r")[0];
                break;
            }
        }
    }

}
