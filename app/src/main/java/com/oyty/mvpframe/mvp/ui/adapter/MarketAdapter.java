package com.oyty.mvpframe.mvp.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.oyty.mvpframe.entity.MarketEntity;

import java.util.List;

/**
 * Created by oyty on 2019/3/18.
 */
public class MarketAdapter extends BaseQuickAdapter<MarketEntity, BaseViewHolder> {

    public MarketAdapter(int layoutResId, @Nullable List<MarketEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketEntity item) {

    }
}
