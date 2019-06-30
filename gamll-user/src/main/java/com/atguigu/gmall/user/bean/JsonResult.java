package com.atguigu.gmall.user.bean;

import lombok.Data;

@Data
public class JsonResult {

    private Long total;
    private String message;
    private boolean status;
    private Object data;

    public JsonResult(String message, boolean status) {

        this.message = message;
        this.status = status;
    }

    public JsonResult(Long total, String message, boolean status) {
        this.total = total;
        this.message = message;
        this.status = status;
    }

    public JsonResult(String message, boolean status, Object data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public JsonResult(Long total, String message, boolean status, Object data) {
        this.total = total;
        this.message = message;
        this.status = status;
        this.data = data;
    }
}
