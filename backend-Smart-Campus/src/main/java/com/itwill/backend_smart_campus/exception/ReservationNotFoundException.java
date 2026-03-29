package com.itwill.backend_smart_campus.exception;

public class ReservationNotFoundException extends RuntimeException{
    private Object data;

    public ReservationNotFoundException(String msg) {
        super(msg);
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
