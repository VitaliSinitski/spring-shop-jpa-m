package com.vitali.handler;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice(basePackages = "com.vitali.rest")
public class RestControllerExceptionHandler extends ResponseEntityExceptionHandler {
}
