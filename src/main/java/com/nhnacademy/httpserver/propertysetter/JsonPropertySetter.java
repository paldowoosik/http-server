package com.nhnacademy.httpserver.propertysetter;

import static java.lang.System.lineSeparator;

public class JsonPropertySetter extends CommonPropertySetter {
    @Override
    public void setProperty(String request) {
        String[] strings = request.split("\r\n\r\n")[1].split(",");
        strings[0] = strings[0].split("\\{")[1];
        strings[strings.length - 1] = strings[strings.length - 1].split("\\}")[0];

        StringBuilder jsonProperty = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            jsonProperty.append("    ").append(strings[i]).append(lineSeparator());
        }
        super.setProperties(jsonProperty.toString());
    }

    public String getJsonProperty() {
        System.out.println(super.getProperties());
        return super.getProperties();
    }
}
