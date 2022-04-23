package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

import java.util.Date;

public class GetVo {
    private String args = "";
    private String origin;
    private String url;

    public GetVo(String origin, String url) {
        this.origin = origin;
        this.url = url;
    }

    public GetVo(String args, String origin, String url) {
        this.args = args;
        this.origin = origin;
        this.url = url;
    }

    public StringBuilder getsResponseBody() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("  \"args\": {").append(args).append(lineSeparator())
                    .append("  \"headers\": {").append(lineSeparator())
                    .append("    \"Accept\": \"*/*\",").append(lineSeparator())
                    .append("    \"Host\": \"test-vm.com\",").append(lineSeparator())
                    .append("    \"User-Agent\": \"curl/7.64.1\",").append(lineSeparator())
                    .append("  },").append(lineSeparator())
                    .append("  \"origin\": ").append(origin).append(lineSeparator())
                    .append("  \"url\": ").append(url).append(lineSeparator())
                    .append("}").append(lineSeparator()).append(lineSeparator());
        return responseBody;
    }



}
