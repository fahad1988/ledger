package com.teya.ledger.exception;

public class ErrorResponse {

    private ErrorMessage Errors;

    public ErrorResponse(ErrorMessage Errors) {
        super();
        this.Errors = Errors;
    }

    public ErrorMessage getErrors() {
        return Errors;
    }

    public void setErrors(ErrorMessage Errors) {
        this.Errors = Errors;
    }

}
