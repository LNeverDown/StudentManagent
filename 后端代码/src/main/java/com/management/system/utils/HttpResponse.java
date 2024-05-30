package com.management.system.utils;


import java.util.HashMap;

public class HttpResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = 1L;

    public static final String SUCCESS = "Success";

    public static final String ERROR = "Error";
    public HttpResponse(){
        super.put("data",null);
        super.put("meta",new Meta(200,SUCCESS));
    }

    public HttpResponse(Object data, Meta meta){
        super.put("data",data);//new Data(data));
        super.put("meta",meta);
    }

    public HttpResponse(Object data, int code, String msg){
        super.put("data",data);
        super.put("meta",new Meta(code,msg));
    }

    public HttpResponse(int code, String msg){
        super.put("data",null);
        super.put("meta",new Meta(code,msg));
    }

    public static HttpResponse success(Object data, Meta meta){
        return new HttpResponse(data,meta);
    }

    public static HttpResponse success(Object data, int code, String msg){
        return new HttpResponse(data,code,msg);
    }

    public static HttpResponse success(Object data){
        return new HttpResponse(data,new Meta(200,SUCCESS));
    }

    public static HttpResponse success(String msg){
        return new HttpResponse(200,msg);
    }

    public static HttpResponse success() {
        return new HttpResponse();
    }

    public static HttpResponse error(){
        return new HttpResponse(null,404 ,ERROR);
    }

    public static HttpResponse error(Object data, Meta meta){
        return new HttpResponse(data,meta);
    }

    public static HttpResponse error(Object data, int code, String msg){
        return new HttpResponse(data,code,msg);
    }

    public static HttpResponse error (Object data){
        return new HttpResponse(data,new Meta(404,ERROR));
    }

    public static HttpResponse error (String msg){
        return new HttpResponse(404,msg);
    }

}




