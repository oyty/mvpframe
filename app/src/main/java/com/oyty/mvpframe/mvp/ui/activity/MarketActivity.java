package com.oyty.mvpframe.mvp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.oyty.mvpframe.R;
import com.oyty.mvpframe.di.component.DaggerMarketComponent;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.mvp.presenter.MarketPresenter;
import com.paginate.Paginate;

import javax.inject.Inject;

import butterknife.BindView;


/**
 * ================================================
 * Description:
 * <p>
 * Created by MVPArmsTemplate on 03/15/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
public class MarketActivity extends BaseActivity<MarketPresenter> implements MarketContract.View {

    @BindView(R.id.mRefreshLayout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @Inject
    RecyclerView.LayoutManager layoutManager;
    @Inject
    RecyclerView.Adapter adapter;

    private Paginate paginate;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMarketComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_market; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    protected void initViewListener() {

        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }

    @Override
    public void process(@Nullable Bundle savedInstanceState) {
        if(paginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.requestMarkets();
                }

                @Override
                public boolean isLoading() {
                    return false;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return false;
                }
            };
            paginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            paginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void killMyself() {
        finish();
    }
}
