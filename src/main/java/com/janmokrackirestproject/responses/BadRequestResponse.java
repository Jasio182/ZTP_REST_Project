package com.janmokrackirestproject.responses;

public class BadRequestResponse extends Response{
    public BadRequestResponse(String message) {
        super(400, message);
    }
}
