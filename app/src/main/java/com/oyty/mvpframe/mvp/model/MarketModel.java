package com.oyty.mvpframe.mvp.model;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.oyty.mvpframe.entity.MarketEntity;
import com.oyty.mvpframe.entity.User;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.net.ApiService;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

/**
 * Created by oyty on 2019/3/18.
 */
@ActivityScope
public class MarketModel extends BaseModel implements MarketContract.Model {

    @Inject
    public MarketModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<List<MarketEntity>> getMarkets(int offset, int categoryId) {
        return mRepositoryManager
                .obtainRetrofitService(ApiService.class)
                .getMarkets(String.valueOf(offset), String.valueOf(categoryId));
    }

    @Override
    public Observable<List<User>> getUsers(int lastIdQueried, int perPage) {
        return mRepositoryManager
                .obtainRetrofitService(ApiService.class)
                .getUsers(lastIdQueried, 10);
    }
}
