package com.oyty.mvpframe.widget.custom;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oyty.mvpframe.R;
import com.oyty.mvpframe.util.CommonUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PublicTitleView extends FrameLayout {

    private final Context mContext;
    @BindView(R.id.mLeftImg)
    ImageView mLeftImg;
    @BindView(R.id.mLeftLabel)
    TextView mLeftLabel;
    @BindView(R.id.mLeftAction)
    RelativeLayout mLeftAction;
    @BindView(R.id.mSecondLeftImg)
    ImageView mSecondLeftImg;
    @BindView(R.id.mSecondLeftLabel)
    TextView mSecondLeftLabel;
    @BindView(R.id.mSecondLeftAction)
    RelativeLayout mSecondLeftAction;
    @BindView(R.id.mMiddleLabel)
    TextView mMiddleLabel;
    @BindView(R.id.mRightImg)
    ImageView mRightImg;
    @BindView(R.id.mRightLabel)
    TextView mRightLabel;
    @BindView(R.id.mMsgImg)
    ImageView mMsgImg;
    @BindView(R.id.mMsgView)
    ImageView mMsgView;
    @BindView(R.id.mMessageLayout)
    RelativeLayout mMessageLayout;
    @BindView(R.id.mRightAction)
    RelativeLayout mRightAction;
    @BindView(R.id.mSecondRightImg)
    ImageView mSecondRightImg;
    @BindView(R.id.mSecondRightAction)
    RelativeLayout mSecondRightAction;
    @BindView(R.id.mBaseView)
    View mBaseView;
    @BindView(R.id.mStatusBarView)
    View mStatusBarView;

    public PublicTitleView(@NonNull Context context) {
        this(context, null);
    }

    public PublicTitleView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PublicTitleView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
    }

    private void initView() {
        View view = View.inflate(mContext, R.layout.view_public_title, this);
        ButterKnife.bind(this, view);
//        CommonUtil.updateStatusBarHeight(mContext, mStatusBarView);
        mMessageLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                mContext.startActivity(new Intent(mContext, MessageActivity.class));
            }
        });
    }

    public void hideStatusBarView() {
        mStatusBarView.setVisibility(GONE);
    }

    public void setTitle(int recourseId) {
        mMiddleLabel.setVisibility(VISIBLE);
        mMiddleLabel.setText(recourseId);
    }

    public void setTitle(int recourseId, int color) {
        mMiddleLabel.setVisibility(VISIBLE);
        mMiddleLabel.setText(recourseId);
    }

    public void setLeftIcon(int resource_id, OnClickListener onClickListener) {
        mLeftImg.setVisibility(View.VISIBLE);
        mLeftImg.setImageResource(resource_id);
        mLeftAction.setOnClickListener(onClickListener);
    }

    public void setRightIcon(int resource_id, OnClickListener onClickListener) {
        mRightImg.setVisibility(VISIBLE);
        mRightImg.setImageResource(resource_id);
        mRightAction.setOnClickListener(onClickListener);
    }

    public void setLeftBackAction(OnClickListener onClickListener) {
        setLeftIcon(R.mipmap.back_white, onClickListener);
    }

    public void setTitle(String title) {
        mMiddleLabel.setVisibility(VISIBLE);
        mMiddleLabel.setText(title);
        mMiddleLabel.setSelected(true);
        mMiddleLabel.setTextColor(getResources().getColor(R.color.flash_black));
    }

    public void setSecondLeftLabel(int resource_id, OnClickListener onClickListener) {
        mSecondLeftLabel.setVisibility(View.VISIBLE);
        mSecondLeftLabel.setText(resource_id);
        mSecondLeftLabel.setTextColor(getResources().getColor(R.color.black));
        mSecondLeftAction.setOnClickListener(onClickListener);
    }

    public void setBackgroundColor(int color) {
        mBaseView.setBackgroundColor(color);
    }

    public void setLeftInvisible() {
        mLeftAction.setVisibility(GONE);
    }

    public void setRightLabel(int resId, OnClickListener listener) {
        mRightLabel.setVisibility(VISIBLE);
        mRightLabel.setText(resId);
        mRightLabel.setTextColor(getResources().getColor(R.color.flash_black));
        mRightAction.setOnClickListener(listener);
    }

    public void setRightLabel(String resId, OnClickListener listener) {
        mRightLabel.setVisibility(VISIBLE);
        mRightLabel.setText(resId);
        mRightLabel.setTextColor(getResources().getColor(R.color.flash_black));
        mRightAction.setOnClickListener(listener);
    }

    public void setCartRightLabel(String resId, OnClickListener listener) {
        mRightLabel.setText(resId);
        mRightLabel.setTextColor(getResources().getColor(R.color.flash_black));
        mRightAction.setOnClickListener(listener);
    }

    public void setRightLabel(String resId) {
        mRightLabel.setVisibility(VISIBLE);
        mRightLabel.setText(resId);
        mRightLabel.setTextColor(getResources().getColor(R.color.flash_black));
    }

    public void setCartRightLabel(String resId) {
        mRightLabel.setText(resId);
        mRightLabel.setTextColor(getResources().getColor(R.color.flash_black));
    }

    public void setRightLabel(int resId, int color, OnClickListener listener) {
        setRightLabel(resId, listener);
        mRightLabel.setTextColor(color);
    }

    public void showMessageView() {
        mMessageLayout.setVisibility(VISIBLE);
    }

    public View getRightView() {
        return mRightAction;
    }

    public void setMessageNum(int totalCount) {
        if (totalCount > 0) {
            mMsgView.setVisibility(View.VISIBLE);
//            mMsgView.setText(totalCount > 99 ? "99+" : String.valueOf(totalCount));
        } else {
            mMsgView.setVisibility(View.GONE);
        }
    }

    public void setRightInvisible() {
        if(mRightLabel != null) {
            mRightLabel.setVisibility(INVISIBLE);
        }
    }

    public void setRightVisible() {
        if(mRightLabel != null) {
            mRightLabel.setVisibility(VISIBLE);
        }
    }
}
