package com.oyty.mvpframe.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import com.oyty.mvpframe.base.ApiService;
import com.oyty.mvpframe.base.CommonCache;
import com.oyty.mvpframe.entity.User;
import com.oyty.mvpframe.mvp.contract.MarketContract;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.Reply;


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
@ActivityScope
public class MarketModel extends BaseModel implements MarketContract.Model {
    private static final int USERS_PRE_PAGE = 10;
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public MarketModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    @Override
    public Observable<List<User>> requestMarkets(int lastUserId, boolean ignoreCache) {
        return Observable.just(mRepositoryManager
                .obtainRetrofitService(ApiService.class)
                .getUsers(lastUserId, USERS_PRE_PAGE))
                .flatMap(new Function<Observable<List<User>>, ObservableSource<List<User>>>() {
                    @Override
                    public ObservableSource<List<User>> apply(Observable<List<User>> listObservable) throws Exception {
                        return mRepositoryManager.obtainCacheService(CommonCache.class)
                                .getUsers(listObservable,
                                        new DynamicKey(lastUserId),
                                        new EvictDynamicKey(ignoreCache))
                                .map(listReply -> listReply.getData());
                    }
                });
    }
}