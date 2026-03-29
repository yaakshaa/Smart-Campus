package com.itwill.backend_smart_campus.exception;

public class ExistedPasswordException extends Exception {
    private Object data;

    public ExistedPasswordException(String msg) {
        super(msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
