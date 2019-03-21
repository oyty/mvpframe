/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jess.arms.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.View;
import android.widget.Toast;

import com.jess.arms.base.delegate.IActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.integration.cache.CacheType;
import com.jess.arms.integration.lifecycle.ActivityLifecycleable;
import com.jess.arms.mvp.IPresenter;
import com.jess.arms.mvp.IView;
import com.jess.arms.utils.AndroidBugUtil;
import com.jess.arms.utils.ArmsUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import static com.jess.arms.utils.ThirdViewUtil.convertAutoView;

public abstract class BaseActivity<P extends IPresenter> extends SwipeBackActivity implements IActivity, ActivityLifecycleable, IView {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<ActivityEvent> mLifecycleSubject = BehaviorSubject.create();
    private Cache<String, Object> mCache;
    private Unbinder mUnbinder;
    protected Context mContext;

    @Inject
    @Nullable
    protected P mPresenter;//如果当前页面逻辑简单, Presenter 可以为 null

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        View view = convertAutoView(name, context, attrs);
        return view == null ? super.onCreateView(name, context, attrs) : view;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
        try {
            int layoutResID = initViewID();
            if (layoutResID != 0) {
                setContentView(layoutResID);
                AndroidBugUtil.assistActivity(findViewById(android.R.id.content));
                mUnbinder = ButterKnife.bind(this);
            }
            setSwipeBackEnable(Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT && enableSwipeBack());
            initView();
            initViewListener();
            process(savedInstanceState);
        } catch (Exception e) {
            if (e instanceof InflateException) throw e;
            e.printStackTrace();
        }
        process(savedInstanceState);
    }

    @Override
    public void initViewListener() {
    }

    public boolean enableSwipeBack() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null && mUnbinder != Unbinder.EMPTY) {
            this.mUnbinder.unbind();
        }
        this.mUnbinder = null;
        if (mPresenter != null) {
            mPresenter.onDestroy();
        }
        this.mPresenter = null;
    }

    /**
     * 是否使用 EventBus
     * Arms 核心库现在并不会依赖某个 EventBus, 要想使用 EventBus, 还请在项目中自行依赖对应的 EventBus
     * 现在支持两种 EventBus, greenrobot 的 EventBus 和畅销书 《Android源码设计模式解析与实战》的作者 何红辉 所作的 AndroidEventBus
     * 确保依赖后, 将此方法返回 true, Arms 会自动检测您依赖的 EventBus, 并自动注册
     * 这种做法可以让使用者有自行选择三方库的权利, 并且还可以减轻 Arms 的体积
     *
     * @return 返回 {@code true} (默认为 {@code true}), Arms 会自动注册 EventBus
     */
    @Override
    public boolean useEventBus() {
        return false;
    }

    /**
     * 这个 {@link Activity} 是否会使用 {@link Fragment}, 框架会根据这个属性判断是否注册 {@link android.support.v4.app.FragmentManager.FragmentLifecycleCallbacks}
     * 如果返回 {@code false}, 那意味着这个 {@link Activity} 不需要绑定 {@link Fragment}, 那你再在这个 {@link Activity} 中绑定继承于 {@link BaseFragment} 的 {@link Fragment} 将不起任何作用
     *
     * @return 返回 {@code true} (默认为 {@code true}), 则需要使用 {@link Fragment}
     */
    @Override
    public boolean useFragment() {
        return false;
    }

    @NonNull
    @Override
    public synchronized Cache<String, Object> provideCache() {
        if (mCache == null) {
            mCache = ArmsUtils.obtainAppComponentFromContext(this).cacheFactory().build(CacheType.ACTIVITY_CACHE);
        }
        return mCache;
    }

    @NonNull
    @Override
    public final Subject<ActivityEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }

    @Override
    public void showLoadingProgress() {

    }

    @Override
    public void showToast(String msg) {
        ArmsUtils.makeText(mContext, msg);
    }

    @Override
    public void hideLoadingProgress() {

    }
}
