package com.vvsai.rxjava.rxbus;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.vvsai.rxjava.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;

@SuppressWarnings("ALL")
public class Test2Activity extends RxAppCompatActivity {
    private static final String TAG = "Test2Activity";

    @Bind(R.id.bt2)
    Button bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);
        ButterKnife.bind(this);

        RxBus.getMyRxBus().toObserverable(LycheeEvent.class)
                .compose(bindToLifecycle())
//                .filter(new Func1<LycheeEvent, Boolean>() {
//                    @Override
//                    public Boolean call(LycheeEvent lychee) {
//                        return lychee.getId() == 2;
//                    }
//                })
                .subscribe(new Action1<LycheeEvent>() {
                    @Override
                    public void call(LycheeEvent lychee) {
                        Log.d(TAG, "id: " + lychee.getId() + ",message: " + lychee.getMessage());

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.d(TAG, throwable.getMessage());
                    }
                });
    }

    @OnClick(R.id.bt2)
    public void onClick() {
        RxBus.getMyRxBus().post(new XEvent(-1L, "小龙"));
        Intent intent = new Intent();
        intent.setClass(Test2Activity.this, Test3Activity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        RxBus.getMyRxBus().post(new LycheeEvent(-1L, "小小"));
    }
}
