package com.itwill.backend_smart_campus.controller;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Response {

    private int status;
    private String message;
    private Object data;

    private HashMap<String, Object> extra = new HashMap<>();

    public Response() {
        this.status = 0;
        this.message = "";
        this.data = new HashMap<>();
        this.extra = new HashMap<>();
    }

    // ✅ [여기에 추가하세요]
    public Response(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.extra = new HashMap<>();
    }

    public void setExtra(String key, Object value) {
        this.extra.put(key, value);
    }

    public Object getExtra(String key) {
        return this.extra.get(key);
    }

    public HashMap<String, Object> getExtra() {
        return this.extra;
    }
}