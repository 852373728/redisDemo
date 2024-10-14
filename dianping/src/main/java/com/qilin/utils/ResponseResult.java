package com.qilin.utils;

import java.io.Serializable;

public class ResponseResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private Object info;
    private String message;
    private boolean success = true;
    private String code;

    public ResponseResult() {
    }

    public ResponseResult(boolean success, String code, String message, Object info) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.info = info;
    }

    public ResponseResult(boolean success, String code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    public ResponseResult(boolean success, String code) {
        this.success = success;
        this.code = code;
    }

    public ResponseResult(String code, String message, Object info) {
        this.code = code;
        this.message = message;
        this.info = info;
    }

    public ResponseResult(String message, Object info) {
        this.message = message;
        this.info = info;
    }

    public ResponseResult(Object info) {
        this.info = info;
    }

    public ResponseResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(boolean success, Object info) {
        this.success = success;
        this.info = info;
    }

    public ResponseResult(boolean success, String code, Object info) {
        this.success = success;
        this.code = code;
        this.info = info;
    }

    public ResponseResult(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public ResponseResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Object getInfo() {
        return this.info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
