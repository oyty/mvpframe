package com.oyty.mvpframe.mvp.contract;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.oyty.mvpframe.entity.MarketEntity;
import com.oyty.mvpframe.entity.User;
import com.oyty.mvpframe.net.Response;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by oyty on 2019/3/18.
 */
public interface MarketContract {

    interface View extends IView {

        void showData(List<User> entities);

        void showMarkets(List<MarketEntity> entities);
    }

    interface Model extends IModel {
        Observable<Response<List<MarketEntity>>> getMarkets(int offset, int categoryId);

        Observable<List<User>> getUsers(int lastIdQueried, int perPage);

        Observable<List<User>> test();
    }
}
