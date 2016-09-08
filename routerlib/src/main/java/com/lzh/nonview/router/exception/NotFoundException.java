package com.lzh.nonview.router.exception;

/**
 * Created by admin on 16/9/7.
 */
public class NotFoundException extends RuntimeException {

    private NotFoundType type;
    private String notFoundName;

    public NotFoundException(String detailMessage, NotFoundType type,String notFoundName) {
        super(detailMessage);
        this.type = type;
        this.notFoundName = notFoundName;
    }

    public NotFoundType getType() {
        return type;
    }

    public String getNotFoundName() {
        return notFoundName;
    }

    public enum NotFoundType {
        CLZ,
        SCHEME,
    }
}
