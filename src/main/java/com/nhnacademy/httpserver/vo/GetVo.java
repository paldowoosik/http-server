package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

public class GetVo implements Response{
    private String args;
    private String hostName;
    private String origin;
    private String url;

    public GetVo(String args, String hostName, String origin, String url) {
        this.args = args;
        this.hostName = "\""+hostName+"\"";
        this.origin = "\""+origin+"\"";
        this.url = "\""+url+"\"";
    }

    @Override
    public StringBuilder voResponseBody() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("  \"args\": {").append(args).append("},").append(lineSeparator())
                    .append("  \"headers\": {").append(lineSeparator())
                    .append("    \"Accept\": \"*/*\",").append(lineSeparator())
                    .append("    \"Host\": ").append(hostName).append(",").append(lineSeparator())
                    .append("    \"User-Agent\": \"curl/7.64.1\",").append(lineSeparator())
                    .append("  },").append(lineSeparator())
                    .append("  \"origin\": ").append(origin).append(lineSeparator())
                    .append("  \"url\": ").append(url).append(lineSeparator())
                    .append("}").append(lineSeparator()).append(lineSeparator());
        return responseBody;
    }



}
