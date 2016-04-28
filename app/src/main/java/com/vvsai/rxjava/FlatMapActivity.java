package com.vvsai.rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.vvsai.rxjava.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class FlatMapActivity extends AppCompatActivity {

    private ArrayList<Student> list = new ArrayList<>();
    Student stu1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flat_map);
        initData();

        Observable.from(list)
                .doOnNext(new Action1<Student>() {
                    @Override
                    public void call(Student student) {
                        Log.v("lychee", student.getName() + "学生大喊！");
                    }
                })
                .take(6)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getClassList());
                    }
                })
                .filter(new Func1<Course, Boolean>() {
                    @Override
                    public Boolean call(Course course) {
                        return course.getType() == 4;
                    }
                })
//                .doOnNext(new Action1<Course>() {
//                    @Override
//                    public void call(Course course) {

//
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Course>() {
                    @Override
                    public void onCompleted() {
                        Log.v("lychee", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.v("lychee", "onError--" + e.getMessage());

                    }

                    @Override
                    public void onNext(Course course) {
                        Log.v("lychee", "course-name: " + course.getName() +
                                "\ncourse--type:" + course.getType());
                    }
                });

        Observable.from(Arrays.asList(new Integer[]{2, 4, 5, 7, 11}))
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtil.e(integer + "");
                    }
                })
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer % 2 == 0;
                    }
                })
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtil.e(integer + "");
                    }
                })
                .count()
                .doOnNext(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        LogUtil.e(integer + "");
                    }
                })
                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return "Contains " + integer;
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        LogUtil.e("Action1: " + s);

                    }
                });
    }

    private void initData() {
        for (int i = 0; i < 10; i++) {
            Student stu = new Student();
            stu.setName("name: " + i);
            for (int j = 0; j < i; j++) {
                Course c = new Course();
                c.setName("name：" + i);
                c.setType(j);
                stu.getClassList().add(c);
            }
            list.add(stu);
        }
    }

    private static class Student {
        private String name;
        private ArrayList<Course> classList = new ArrayList<>();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public ArrayList<Course> getClassList() {
            return classList;
        }

        public void setClassList(ArrayList<Course> classList) {
            this.classList = classList;
        }
    }

    private static class Course {
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        private String name;
        private int type;
    }
}
