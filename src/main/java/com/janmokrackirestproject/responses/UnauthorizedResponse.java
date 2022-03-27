package com.janmokrackirestproject.responses;

public class UnauthorizedResponse extends Response{
    public UnauthorizedResponse(String message) {
        super(401, message);
    }
}
