package com.insilicogen.common.exception;

/**
 * 
 * 비지니스 로직처리용
 * transaction level
 *
 */
public class BizException extends Exception {

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }
    public BizException(String message) {
        super(message);
    }
    public BizException(Throwable cause) {
        super(cause);
    }
}
