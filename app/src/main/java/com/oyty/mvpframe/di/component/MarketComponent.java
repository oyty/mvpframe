package com.oyty.mvpframe.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.oyty.mvpframe.di.module.MarketModule;
import com.oyty.mvpframe.mvp.contract.MarketContract;
import com.oyty.mvpframe.mvp.ui.activity.MarketActivity;

import dagger.BindsInstance;
import dagger.Component;


@ActivityScope
@Component(modules = MarketModule.class, dependencies = AppComponent.class)
public interface MarketComponent {
    void inject(MarketActivity activity);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MarketComponent.Builder view(MarketContract.View view);

        MarketComponent.Builder appComponent(AppComponent appComponent);

        MarketComponent build();
    }
}