package com.vvsai.rxjava.rxbus;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;


public class RxBus {
    private static volatile RxBus MyRxBus;
    private final Subject<Object, Object> bus;

    public RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    // 单例RxBus
    public static RxBus getMyRxBus() {
        RxBus rxBus = MyRxBus;
        if (MyRxBus == null) {
            synchronized (RxBus.class) {
                rxBus = MyRxBus;
                if (MyRxBus == null) {
                    rxBus = new RxBus();
                    MyRxBus = rxBus;
                }
            }
        }
        return rxBus;
    }

    // 提供了一个新的事件
    public void post(Object o) {
        bus.onNext(o);
    }

    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable toObserverable(Class<T> eventType) {
        return bus.ofType(eventType);

    }
}