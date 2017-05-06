package com.lzh.nonview.router.exception;

/**
 * Contains two types of not found<br>
 *     <li><i>route rule:</i> not found</li><br>
 *     <li><i>activity  :</i> not found</li><br>
 * @author haoge
 */
public class NotFoundException extends RuntimeException {

    private final NotFoundType type;
    private final String notFoundName;

    public NotFoundException(String detailMessage, NotFoundType type,String notFoundName) {
        super(detailMessage);
        this.type = type;
        this.notFoundName = notFoundName;
    }

    @SuppressWarnings("unused")
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
