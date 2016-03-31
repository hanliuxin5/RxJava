package com.vvsai.rxjava.rxbus;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.vvsai.rxjava.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import rx.functions.Func1;

@SuppressWarnings("ALL")
public class Test3Activity extends RxAppCompatActivity {
    private static final String TAG = "Test3Activity";

    @Bind(R.id.bt3)
    Button bt3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test3);
        ButterKnife.bind(this);

        RxBus.getMyRxBus().toObserverable(LycheeEvent.class)
                .compose(bindToLifecycle())
                .filter(new Func1<LycheeEvent, Boolean>() {
                    @Override
                    public Boolean call(LycheeEvent lychee) {
                        return lychee.getId() == 2;
                    }
                })
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

    @OnClick(R.id.bt3)
    public void onClick() {
        finish();

    }
}
