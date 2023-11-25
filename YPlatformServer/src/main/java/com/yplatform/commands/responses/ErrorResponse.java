package com.yplatform.commands.responses;

public class ErrorResponse extends BaseResponse {

    public ErrorResponse(String error) {
        super();
        super.error = error;
    }
}
