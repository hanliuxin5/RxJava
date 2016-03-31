package com.vvsai.rxjava.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.vvsai.rxjava.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Retrofit1Activity extends RxAppCompatActivity {

    @Bind(R.id.bt1)
    Button bt1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit1);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt1)
    public void onClick() {
        Intent intent = new Intent();
        intent.setClass(this, Retrofit2Activity.class);
        startActivity(intent);
    }
}
