package com.oyty.mvpframe.mvp.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.oyty.mvpframe.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void process(@Nullable Bundle savedInstanceState) {

    }

    @OnClick(R.id.mGotoMarketAction)
    public void gotoMarketAction() {
        startActivity(new Intent(this, MarketActivity.class));
    }
}

