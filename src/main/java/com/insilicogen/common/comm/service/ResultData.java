package com.insilicogen.common.comm.service;

import java.util.Arrays;
import java.util.List;
import org.springframework.http.HttpStatus;
import lombok.Data;


@Data
public class ResultData<T> {
    
    //@JsonIgnore
    private HttpStatus status;
    
    private String message;
    
    private int code;
    
    private List<String> errors;
    
    private T data;
    private int draw;//data table draw순서용
    private int recordsTotal;
    private int recordsFiltered;
    
    public ResultData() {
      this(HttpStatus.OK);
    }
    
    public ResultData(HttpStatus status) {
        this(status, status.getReasonPhrase(), status.value(), "");
    }
    
    public ResultData(HttpStatus status, String error) {
        this(status, status.getReasonPhrase(), status.value(), error);
    }
    
    public ResultData(HttpStatus status, List<String> errors) {
        this(status, status.getReasonPhrase(), status.value(), errors);
    }

    public ResultData(HttpStatus status, String message, int code, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.code = code;
        this.errors = errors;
    }

    public ResultData(HttpStatus status, String message, int code, String error) {
        super();
        this.status = status;
        this.message = message;
        this.code = code;
        errors = Arrays.asList(error);
    }
}