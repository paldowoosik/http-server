package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

public class GetVo {
    private String args = "{},";
    private String headers;
    private String origin = "\"192.168.71.79\",";
    private String url = "\"http://test-vm.com/get\"";

    @Override
    public String toString() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("  \"args\": ").append(args).append(lineSeparator())
                    .append("  \"headers\": {").append(lineSeparator())
                    .append("    \"Accept\": \"*/*\",").append(lineSeparator())
                    .append("    \"Host\": \"test-vm.com\",").append(lineSeparator())
                    .append("    \"User-Agent\": \"curl/7.64.1\",").append(lineSeparator())
                    .append("    \"X-Amzn-Trace-Id\": \"Root=1-62625943-1b9f17e345c4b26f4ff586a4\"").append(lineSeparator())
                    .append("  },").append(lineSeparator())
                    .append("  \"origin\": ").append(origin).append(lineSeparator())
                    .append("  \"url\": ").append(url).append(lineSeparator())
                    .append("}").append(lineSeparator()).append(lineSeparator());
        return responseBody.toString();
    }
}
