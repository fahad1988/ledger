package com.teya.ledger.exception;

import java.util.List;

public class ErrorMessage {
    private List<String> errorMessage;

    public ErrorMessage(List<String> Error) {
        super();
        this.errorMessage = Error;
    }

    public List<String> getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(List<String> errorMessage) {
        this.errorMessage = errorMessage;
    }
}
