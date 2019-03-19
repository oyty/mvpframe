package com.oyty.mvpframe.di.module;

import com.jess.arms.di.scope.ActivityScope;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.mvp.model.MarketModel;

import dagger.Binds;
import dagger.Module;

/**
 * Created by oyty on 2019/3/18.
 */
@Module
public abstract class MarketModule {

    @ActivityScope
    @Binds
    abstract MarketContract.Model provideModel(MarketModel model);

}
