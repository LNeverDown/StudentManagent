package com.management.system.utils;


import java.io.Serializable;

import lombok.Data;

@Data
public class Meta implements Serializable {

    private static final long serialVersionUID = 1L;
    private int status;
    private String msg;

    public Meta() {}

    public Meta(Meta meta) {
        this.status = meta.status;
        this.msg = meta.msg;
    }

    public Meta(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public void set(int code, String msg) {
        this.status = code;
        this.msg = msg;
    }

    public void setMsg( String msg) { this.msg = msg;}

    public void setStatus(int code, String msg) { this.status = code; }

    public int getStatus() { return status; }

    public void setStatus(int status) { this.status = status; }

    public static long getSerialversionuid() { return serialVersionUID; }

    public String getMsg() { return msg; }
}
