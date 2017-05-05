package com.lzh.nonview.router.compiler.exception;

import javax.lang.model.element.Element;

public class RouterException extends RuntimeException {

    Element element;

    public RouterException(String message, Element element) {
        super(message);
        this.element = element;
    }

    public RouterException(String message, Throwable cause, Element element) {
        super(message, cause);
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
