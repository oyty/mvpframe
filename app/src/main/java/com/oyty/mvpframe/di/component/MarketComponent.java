package com.oyty.mvpframe.di.component;

import dagger.BindsInstance;
import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import com.oyty.mvpframe.di.module.MarketModule;
import com.oyty.mvpframe.mvp.contract.MarketContract;

import com.jess.arms.di.scope.ActivityScope;
import com.oyty.mvpframe.mvp.ui.activity.MarketActivity;
import com.oyty.mvpframe.mvp.ui.fragment.MarketFragment;


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
@Component(modules = MarketModule.class, dependencies = AppComponent.class)
public interface MarketComponent {
    void inject(MarketActivity activity);

    void inject(MarketFragment fragment);

    @Component.Builder
    interface Builder {
        @BindsInstance
        MarketComponent.Builder view(MarketContract.View view);

        MarketComponent.Builder appComponent(AppComponent appComponent);

        MarketComponent build();
    }
}