package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

public class PostVo implements Response {
    private String origin;
    private String url;
    private String dataProperty;
    private String contentType;
    private String jsonProperty;
    private String host;

    public PostVo(String origin, String url, String dataProperty, String contentType,
                  String jsonProperty, String host) {
        this.origin = "\""+origin+"\"";
        this.url = "\""+url+"\"";
        this.dataProperty = dataProperty;
        this.contentType = contentType;
        this.jsonProperty = jsonProperty;
        this.host = "\""+host+"\"";
    }

    @Override
    public StringBuilder voResponseBody() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("   \"args\": {},").append(lineSeparator())
                    .append("   \"data\": ")
                    .append(dataProperty).append(lineSeparator())
                    .append("   \"files\": {},").append(lineSeparator())
                    .append("   \"form\": {},").append(lineSeparator())
                    .append("   \"headers\": {").append(lineSeparator())
                    .append("      \"Accept\": \"*/*\",").append(lineSeparator())
                    .append("      \"Content-Length\": ").append("\"").append(dataProperty.length()).append("\",").append(lineSeparator())
                    .append("      \"Content-Type\": ").append("\"").append(contentType).append("\",").append(lineSeparator())
                    .append("      \"Host\": \"").append(host).append("\",").append(lineSeparator())
                    .append("      \"User-Agent\": \"curl/7.64.1\"").append(lineSeparator())
                    .append("   },").append(lineSeparator())
                    .append("   \"json\": {").append(lineSeparator())
                    .append(jsonProperty)
                    .append("   }").append(lineSeparator())
                    .append("   \"origin\": \"").append(origin).append(",").append(lineSeparator())
                    .append("   \"url\": ").append(url).append(lineSeparator())
                    .append("}").append(lineSeparator());
        return responseBody;
    }
}
