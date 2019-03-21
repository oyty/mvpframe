package com.oyty.mvpframe.net;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by oyty on 2019/3/19.
 */
public class ResponseTransformer<T> implements ObservableTransformer<Response<T>, T> {

    @Override
    public ObservableSource<T> apply(Observable<Response<T>> upstream) {

        return upstream.onErrorResumeNext((Function<Throwable, ObservableSource<? extends Response<T>>>) Observable::error).flatMap((Function<Response<T>, ObservableSource<T>>) response -> {
            if(response.isSuccess()) {
                return Observable.just(response.getData());
            } else {
                return Observable.error(new ApiException(response.getCode(), response.getMessage()));
            }
        });
    }

}
