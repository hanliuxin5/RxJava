package com.vvsai.rxjava.rxbus;

/**
 * Created by lychee on 2016/3/29.
 */
public class LycheeEvent<T> {
    long id;
    T message;

    public LycheeEvent(long id, T message) {
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
