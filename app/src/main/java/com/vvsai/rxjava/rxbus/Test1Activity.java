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
public class Test1Activity extends RxAppCompatActivity {
    private static final String TAG = "Test1Activity";
    @Bind(R.id.bt1)
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1);
        ButterKnife.bind(this);

        RxBus.getMyRxBus().toObserverable(LycheeEvent.class)
                .compose(bindToLifecycle())
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

    @OnClick(R.id.bt1)
    public void onClick() {


        Intent intent = new Intent();
        intent.setClass(this, Test2Activity.class);
        startActivity(intent);
    }
}
