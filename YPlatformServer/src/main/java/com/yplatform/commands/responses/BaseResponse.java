package com.yplatform.commands.responses;

public abstract class BaseResponse {
    String error;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

