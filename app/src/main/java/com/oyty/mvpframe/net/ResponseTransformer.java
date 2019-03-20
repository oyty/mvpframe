package com.oyty.mvpframe.net;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * Created by oyty on 2019/3/19.
 */
public class ResponseTransformer {

//    public static <T>ObservableTransformer<Response<T>, T> handleResult() {
//        return new ObservableTransformer<Response<T>, T>() {
//            @Override
//            public ObservableSource<T> apply(Observable<Response<T>> upstream) {
//                return upstream.flatMap(new Function<Response<T>, ObservableSource<?>>() {
//                    @Override
//                    public ObservableSource<?> apply(Response<T> response) throws Exception {
//                        if(response.isSuccess()) {
//                            return Observable.just(response.getData());
//                        } else {
//                            return Observable.error(new );
//                        }
//                    }
//                });
//            }
//        };
//    }

}
