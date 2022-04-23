package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

import java.util.Date;

public class IpVo {
    private String origin;
    private int responseBodyLength;

    public IpVo(String origin) {
        this.origin = origin;
    }

    public StringBuilder ipsResponseBody() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("  \"origin\": \"").append(this.origin)
                    .append("\"").append(lineSeparator())
                    .append("}");
        this.responseBodyLength = responseBody.length();
        return responseBody;
    }

}
