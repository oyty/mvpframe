package com.oyty.mvpframe.mvp.presenter;

import android.app.Application;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.oyty.mvpframe.entity.User;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.widget.custom.PublicTitleView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


/**
 * ================================================
 * Description
 * <p>
 * Created by MVPArmsTemplate on 03/15/2019 16:42
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms">Star me</a>
 * <a href="https://github.com/JessYanCoding/MVPArms/wiki">See me</a>
 * <a href="https://github.com/JessYanCoding/MVPArmsTemplate">模版请保持更新</a>
 * ================================================
 */
@ActivityScope
public class MarketPresenter extends BasePresenter<MarketContract.Model, MarketContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    Application mApplication;
    @Inject
    ImageLoader mImageLoader;

    @Inject
    List<User> entities;
    @Inject
    RecyclerView.Adapter adapter;

    private int lastUserId = 1;
    private boolean isFirst = true;
    private int preEndIndex;

    @Inject
    public MarketPresenter(MarketContract.Model model, MarketContract.View rootView) {
        super(model, rootView);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        requestMarkets(true);
    }

    public void requestMarkets(boolean isPullToRefresh) {
        if(isPullToRefresh) {
            lastUserId = 1;
        }

        // 是否忽略缓存，下拉刷新需要更新数据，不使用缓存
        boolean ignoreCache = isPullToRefresh;

        // 默认第一次下拉刷新时使用缓存
        if(isPullToRefresh && isFirst) {
            isFirst = false;
            ignoreCache = false;
        }
        mModel.requestMarkets(lastUserId, ignoreCache)
                .subscribeOn(Schedulers.io())
                // 遇到错误时重试
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if(isPullToRefresh) {
                            mRootView.showLoading();
                        } else {
                            mRootView.startLoadMore();
                        }
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        if(isPullToRefresh) {
                            mRootView.hideLoading();
                        } else {
                            mRootView.endLoadMore();
                        }
                    }
                })
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new ErrorHandleSubscriber<List<User>>(mErrorHandler) {
                    @Override
                    public void onNext(List<User> users) {
                        lastUserId = users.get(users.size() - 1).getId();
                        if(isPullToRefresh) {
                            entities.clear();
                        }
                        preEndIndex = entities.size();
                        entities.addAll(users);
                        if(isPullToRefresh) {
                            adapter.notifyDataSetChanged();
                        } else {
                            adapter.notifyItemRangeInserted(preEndIndex, users.size());
                        }
                    }
                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mErrorHandler = null;
        this.mImageLoader = null;
        this.mApplication = null;
    }

}
