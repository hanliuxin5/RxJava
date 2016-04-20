package com.vvsai.rxjava;

import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Lychee";
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.imageView)
    ImageView imageView;

    //    Observer observer = null;
    Subscriber subscriber = null;
    Observable observable = null;

    int imageRe = R.mipmap.ic_launcher;
    Integer[] imageRes = new Integer[]{R.mipmap.ic_launcher};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //根据响应式函数编程的概念，Subscribers更应该做的事情是“响应”，响应Observable发出的事件，而不是去修改
//
//        Observable.just("lychee").subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.v(TAG, "String--" + s);
//            }
//        });
//
//        Observable.just(1).subscribe(new Action1<Integer>() {
//            @Override
//            public void call(Integer integer) {
//                Log.v(TAG, "integer---" + integer);
//            }
//        });
        Observable
                .create(new Observable.OnSubscribe<Drawable>() {
                    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void call(Subscriber<? super Drawable> subscriber) {
                        Drawable drawable = getTheme().getDrawable(imageRe);
                        subscriber.onNext(drawable);
                        subscriber.onCompleted();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Drawable>() {
                    @Override
                    public void onCompleted() {
                        Log.v(TAG, "image---onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        imageView.setImageDrawable(drawable);
                    }
                });

        Observable
                .just(1, 2, 3, 4)
//                .create(new Observable.OnSubscribe<Integer>() {
//                    @Override
//                    public void call(Subscriber<? super Integer> subscriber) {
//                        subscriber.onNext(22);
////                        subscriber.onCompleted();
//                    }
//                })

                .map(new Func1<Integer, String>() {
                    @Override
                    public String call(Integer integer) {
                        return integer + "";
                    }
                })
//                .from(imageRes)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.v(TAG, "String---onCompleted");

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String integer) {
                        Log.v(TAG, "String--" + integer);
                    }
                });


//
//        Observable.just(R.mipmap.ic_launcher).map(new Func1<Integer, String>() {
//            @Override
//            public String call(Integer integer) {
//                return integer + "";
//            }
//        }).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.v(TAG, "int2String--" + s);
//            }
//        });

//        initObserver();
//        initObservable();
//        observable.subscribe(subscriber);
//        initAction();
//        String[] name = new String[]{"1", "2", "3", "4"};
//        observable.from(name).subscribe(new Action1<String>() {
//            @Override
//            public void call(String s) {
//                Log.i(TAG, s);
//            }
//        });
    }

    /**
     * 初始化观察者
     * 时间触发时候的行为
     */
    private void initObserver() {
//        observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.v(TAG, "onCompleted");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.v(TAG, "onError");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.v(TAG, "onNext");
//            }
//        };

        subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.v(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.v(TAG, "onError");
            }

            @Override
            public void onStart() {
                super.onStart();
                Log.v(TAG, "onStart");
            }

            @Override
            public void onNext(String s) {
                Log.v(TAG, "onNext");
            }
        };
    }

    /**
     * 初始化被观察者
     * 触发怎样的事件
     */
    private void initObservable() {
//      just就是用来创建只发出一个事件就结束的Observable对象
//      observable = Observable.just("Hello", "Hi", "Aloha");

//      String[] words = {"Hello", "Hi", "Aloha"};
//      observable = Observable.from(words);

        observable = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onStart();
                subscriber.onNext("1");
//                subscriber.onCompleted();
                subscriber.onError(new Throwable("what the hell?"));

            }
        });
    }

    /**
     * 不完整定义的回调
     */
    private void initAction() {
        Action1<String> action1 = new Action1<String>() {
            @Override
            public void call(String s) {
                Log.v(TAG, "action1--" + s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
                Log.v(TAG, "onErrorAction--" + throwable.getMessage());

            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                Log.v(TAG, "onCompletedAction--completed");
            }
        };
        observable.subscribe(action1, onErrorAction);
    }
}
