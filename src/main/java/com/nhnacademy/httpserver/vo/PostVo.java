package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

public class PostVo implements Response {
    private String origin;
    private String url;
    private String data;
    private String host;

    public PostVo(String host, String origin, String url) {
        this.host = "\""+host+"\"";
        this.origin = origin;
        this.url = url;
    }

    @Override
    public StringBuilder voResponseBody() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("  \"args\": {}").append(lineSeparator())
                    .append("  \"data\": \"").append(data).append("\",").append(lineSeparator())
                    .append("  \"files\": {},").append(lineSeparator())
                    .append("  \"form\": {},").append(lineSeparator())
                    .append("  \"headers\": {").append(lineSeparator())
                    .append("    \"Accept\": \"*/*\",").append(lineSeparator())
                    .append("    \"Host\": ").append(host).append(",").append(lineSeparator())
                    .append("    \"User-Agent\": \"curl/7.64.1\",").append(lineSeparator())
                    .append("  },").append(lineSeparator());
        return responseBody;
    }
}
