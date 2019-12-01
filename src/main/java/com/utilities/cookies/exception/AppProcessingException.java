package com.utilities.cookies.exception;

public class AppProcessingException extends Exception{

    public AppProcessingException(AppExceptionCode appExceptionCode) {
        this.appExceptionCode = appExceptionCode;
    }

    public AppExceptionCode getAppExceptionCode() {
        return appExceptionCode;
    }

    private AppExceptionCode appExceptionCode;

    public AppProcessingException(Throwable cause, AppExceptionCode appExceptionCode) {
        super(cause);
        this.appExceptionCode = appExceptionCode;
    }

    public AppProcessingException(String message, AppExceptionCode appExceptionCode) {
        super(message);
        this.appExceptionCode = appExceptionCode;
    }

    public AppProcessingException(String message, Throwable cause, AppExceptionCode appExceptionCode) {
        super(message, cause);
        this.appExceptionCode = appExceptionCode;
    }
}
