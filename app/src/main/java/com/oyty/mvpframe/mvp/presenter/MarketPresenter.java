package com.oyty.mvpframe.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.oyty.mvpframe.mvp.contract.MarketContract;

import javax.inject.Inject;

/**
 * Created by oyty on 2019/3/18.
 */
@ActivityScope
public class MarketPresenter extends BasePresenter<MarketContract.Model, MarketContract.View> {

    private int offset = 0;

    @Inject
    public MarketPresenter(MarketContract.Model model, MarketContract.View rootView) {
        super(model, rootView);
    }

    @Override
    public void start() {
        onRefresh();
    }

    public void onRefresh() {
        offset = 0;
//        mModel.getMarkets(offset, 0);
//
    }

    public void onLoadMore() {

    }
}
