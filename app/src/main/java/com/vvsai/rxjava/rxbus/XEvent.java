package com.vvsai.rxjava.rxbus;

/**
 * Created by lychee on 2016/5/4.
 */
public class XEvent<T> {
    long id;
    T message;

    public XEvent(long id, T message) {
        this.id = id;
        this.message = message;
    }

    public long getId() {
        return id;
    }

    public T getMessage() {
        return message;
    }
}
