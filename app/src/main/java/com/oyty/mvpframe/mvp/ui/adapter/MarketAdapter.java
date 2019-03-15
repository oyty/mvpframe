package com.oyty.mvpframe.mvp.ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.oyty.mvpframe.entity.User;

import java.util.List;

/**
 * Created by oyty on 2019/3/15.
 */
public class MarketAdapter extends BaseQuickAdapter<User, BaseViewHolder> {

    public MarketAdapter(List<User> users) {
        super(users);
    }

    @Override
    protected void convert(BaseViewHolder helper, User item) {

    }

}
