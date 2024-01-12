package org.if22b179.server.http;

import lombok.Getter;
import lombok.Setter;

@Getter
public class Response {

    private int statusCode;

    private String statusMessage;

    private String contentType;

    @Setter
    private String body;

    public void setStatus(HttpStatus httpStatus) {
        this.statusCode = httpStatus.getCode();
        this.statusMessage = httpStatus.getMessage();
    }

    public void setContentType(HttpContentType httpContentType) {
        this.contentType = httpContentType.getMimeType();
    }

}
