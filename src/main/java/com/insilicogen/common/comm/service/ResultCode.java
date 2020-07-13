package com.insilicogen.common.comm.service;

public enum ResultCode {

    SUCCESS(200, "SUCCESS")
    ,ERROR(500, "ERROR");
    
    private final int value;

    private final String reasonPhrase;

    ResultCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }


    /**
     * Return the integer value of this status code.
     */
    public int value() {
        return this.value;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getReasonPhrase() {
        return this.reasonPhrase;
    }
    
    public static ResultCode valueOf(int statusCode) {
        ResultCode status = resolve(statusCode);
        if (status == null) {
            throw new IllegalArgumentException("No matching constant for [" + statusCode + "]");
        }
        return status;
    }
    
    public static ResultCode resolve(int statusCode) {
        for (ResultCode status : values()) {
            if (status.value == statusCode) {
                return status;
            }
        }
        return null;
    }
}