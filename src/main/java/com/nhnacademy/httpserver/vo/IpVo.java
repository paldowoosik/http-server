package com.nhnacademy.httpserver.vo;

import static java.lang.System.lineSeparator;

public class IpVo implements Response{
    private String origin;

    public IpVo(String origin) {
        this.origin = "\""+origin+"\"";
    }

    @Override
    public StringBuilder voResponseBody() {
        StringBuilder responseBody = new StringBuilder();
        responseBody.append("{").append(lineSeparator())
                    .append("  \"origin\": ").append(this.origin)
                    .append(lineSeparator()).append("}");
        return responseBody;
    }

}
