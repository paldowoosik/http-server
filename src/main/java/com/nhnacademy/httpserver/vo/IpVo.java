package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

public class IpVo {
    private String origin;

    public IpVo(String origin) {
        this.origin = "\""+origin+"\"";
    }

    public StringBuilder ipsResponseBody() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("  \"origin\": ").append(this.origin)
                    .append(lineSeparator()).append("}");
        return responseBody;
    }

}
