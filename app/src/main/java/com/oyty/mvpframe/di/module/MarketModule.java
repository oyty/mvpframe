package com.oyty.mvpframe.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.mvp.model.MarketModel;

import dagger.Module;
import dagger.Provides;

/**
 * Created by oyty on 2019/3/18.
 */
@Module
public class MarketModule {

    @ActivityScope
    @Provides
    MarketContract.Model provideModel(MarketModel model) {
        return model;
    }


}
