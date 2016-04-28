package com.vvsai.rxjava.retrofit;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.widget.Toolbar;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.vvsai.rxjava.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class Retrofit2Activity extends RxAppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.container)
    NoScrollViewPager container;
    @Bind(R.id.main_content)
    CoordinatorLayout mainContent;
    @Bind(R.id.tab)
    TabLayout tab;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private List<String> tabList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit2);
        ButterKnife.bind(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        container.setAdapter(mSectionsPagerAdapter);
        tabList.add("呵呵呵");
        tabList.add("嘿嘿嘿");
        tabList.add("咦咦咦");
        tab.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为系统默认模式
        tab.addTab(tab.newTab().setText(tabList.get(0)));//添加tab选项卡
        tab.addTab(tab.newTab().setText(tabList.get(1)));
        tab.addTab(tab.newTab().setText(tabList.get(2)));
        tab.setupWithViewPager(container);//将TabLayout和ViewPager关联起来。
        container.setPagingEnabled(true);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabList.get(position);
        }

    }
}
