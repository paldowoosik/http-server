package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

import java.util.Map;

public class PostVo {
    private String args;
    private String data;
    private String files;
    private String form;
    private String headers;
    private Map<String, String> json;
    private String origin;
    private String url;

    public PostVo(int contentLength, String origin, String url) {
        this.args = "{}";
        this.data = "";
        this.files = "{}";
        this.form = "{}";
        this.headers =
            "\"Accept\": \"*/*\",\r\n\"Content-Length\": \"" + contentLength + "\",\r\n" +
                "\"Content-Type\"";
        this.origin = origin;
        this.url = url;
    }

    @Override
    public String toString() {
        StringBuilder responseBody = new StringBuilder();
        responseBody
            .append("{").append(lineSeparator())
            .append("\"args\": ").append(args).append(",").append(lineSeparator())
            .append("\"data\": ").append(data).append(",").append(lineSeparator())
            .append("\"files\": ").append(files).append(",").append(lineSeparator())
            .append("\"form\": ").append(form).append(",").append(lineSeparator())
            .append("\"headers\": ").append("{").append(lineSeparator())
            .append(headers).append(lineSeparator()).append("},").append(lineSeparator())
            .append("\"json\": ").append(json).append(",").append(lineSeparator())
            .append("\"origin\": \"").append(origin).append("\",").append(lineSeparator())
            .append("\"url\": ").append(url).append(lineSeparator())
            .append("}");
        return responseBody.toString();
    }
}
