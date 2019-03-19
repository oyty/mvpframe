package com.oyty.mvpframe.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.LogUtils;
import com.oyty.mvpframe.R;
import com.oyty.mvpframe.di.component.DaggerMarketComponent;
import com.oyty.mvpframe.entity.MarketEntity;
import com.oyty.mvpframe.entity.User;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.mvp.presenter.MarketPresenter;
import com.oyty.mvpframe.mvp.ui.adapter.MarketAdapter;
import com.oyty.mvpframe.widget.custom.MultipleStatusView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by oyty on 2019/3/18.
 */
@ActivityScope
public class MarketActivity extends BaseActivity<MarketPresenter> implements MarketContract.View {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSmartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;
    @BindView(R.id.mStatusView)
    MultipleStatusView mStatusView;

    private MarketAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMarketComponent
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initViewID() {
        return R.layout.activity_market;
    }

    @Override
    public void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new MarketAdapter(R.layout.adapter_market, new ArrayList<>(0));
        mRecyclerView.setAdapter(adapter);

        mStatusView.showLoading();
    }

    @Override
    public void initViewListener() {
        mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onRefresh();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.onLoadMore();
            }

        });
    }

    @Override
    public void process(@Nullable Bundle savedInstanceState) {
        mPresenter.start();
    }


    @Override
    public void showData(List<User> entities) {
        mSmartRefreshLayout.finishRefresh();
        mSmartRefreshLayout.finishLoadMore();
        mStatusView.showContent();


        Type listType = new TypeToken<List<MarketEntity>>(){}.getType();
        Gson gson = new Gson();
        LogUtils.debugInfo(gson.toJson(entities, listType));
//        adapter.setNewData(entities);
    }
}
