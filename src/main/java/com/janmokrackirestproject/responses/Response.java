package com.janmokrackirestproject.responses;

public abstract class Response {
    private int statusCode;
    private Object data;

    public int getStatusCode() {
        return statusCode;
    }

    public Object getMessage() {
        return data;
    }

    public Response(int statusCode, Object data) {
        this.statusCode = statusCode;
        this.data = data;
    }
}
