package com.zstu.mijazz.wms;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultReturn<T> {
    private Integer code;
    private String message;
    private T content;

    public ResultReturn(Integer code, String message, T content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }
}
