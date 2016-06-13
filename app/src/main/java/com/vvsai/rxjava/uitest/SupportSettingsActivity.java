package com.vvsai.rxjava.uitest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;

import com.vvsai.rxjava.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SupportSettingsActivity extends AppCompatActivity {

    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_settings);
        ButterKnife.bind(this);
        if (savedInstanceState == null) {
            SupportSettingsFragment fragment = new SupportSettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }
    }
}
