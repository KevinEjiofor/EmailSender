package com.mailsender.userservice.data.models;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.apache.tomcat.Jar;
import org.springframework.http.HttpStatus;

import java.util.Map;
@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class HttpResponse {

    protected String timestamp;
    protected int statusCode;
    protected HttpStatus status;
    protected String message;
    protected String developerMessage;
    protected String path;
    protected String requestMethod;
    protected Map<?, ?> data;

}
