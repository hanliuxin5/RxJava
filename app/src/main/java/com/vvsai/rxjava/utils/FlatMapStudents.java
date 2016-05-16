package com.vvsai.rxjava.utils;

import com.vvsai.rxjava.FlatMapActivity;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

/**
 * Created by lychee on 2016/5/16.
 */
public class FlatMapStudents implements Observable.Transformer<FlatMapActivity.Course, FlatMapActivity.Course> {
    @Override
    public Observable<FlatMapActivity.Course> call(Observable<FlatMapActivity.Course> tObservable) {
        return tObservable.filter(new Func1<FlatMapActivity.Course, Boolean>() {
            @Override
            public Boolean call(FlatMapActivity.Course course) {
                return course.getType() == 4;
            }
        }).doOnNext(new Action1<FlatMapActivity.Course>() {
            @Override
            public void call(FlatMapActivity.Course course) {
                LogUtil.i(course.getName() + "学生在教室里大喊！");
            }
        }).take(6);
    }
}
