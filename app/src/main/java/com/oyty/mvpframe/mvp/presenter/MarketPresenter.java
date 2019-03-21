package com.oyty.mvpframe.mvp.presenter;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.LogUtils;
import com.oyty.mvpframe.entity.MarketEntity;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.net.CallbackWrapper;
import com.oyty.mvpframe.util.GsonUtil;

import java.util.List;

import javax.inject.Inject;

import me.jessyan.rxerrorhandler.core.RxErrorHandler;

/**
 * Created by oyty on 2019/3/18.
 */
@ActivityScope
public class MarketPresenter extends BasePresenter<MarketContract.Model, MarketContract.View> {

    @Inject
    RxErrorHandler mErrorHandler;

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

        mModel.getMarkets(0, 0)
                .subscribe(new CallbackWrapper<List<MarketEntity>>(mRootView) {
                    @Override
                    protected void onSuccess(List<MarketEntity> data) {
                        LogUtils.debugInfo("hahahhahha", GsonUtil.array2Json(data));
                    }

                    @Override
                    protected void onFailed(String code, String msg) {
//                        super.onFailed(code, msg);
                    }

                    @Override
                    protected void onNetworkFailed(String msg) {
//                        super.onNetworkFailed(msg);
                    }
                });
        /*mModel.getUsers(1, 10)
            .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<User>>() {
                    @Override
                    public void accept(List<User> marketEntities) throws Exception {
                        mRootView.showData(marketEntities);
                    }
                });*/

        /*mModel.getMarkets(0, 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<MarketEntity>>(mErrorHandler) {
                    @Override
                    public void onNext(List<MarketEntity> entities) {
                        mRootView.showMarkets(entities);
                    }
                });*/

//        })
    }

    public void onLoadMore() {

    }
}
