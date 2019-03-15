package com.oyty.mvpframe.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.SystemClock;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.jess.arms.base.App;
import com.jess.arms.integration.AppManager;
import com.oyty.mvpframe.R;

import org.json.JSONObject;
import org.simple.eventbus.EventBus;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import static android.view.MotionEvent.ACTION_DOWN;

/**
 * Created by oyty on 12/16/16.
 */

public class CommonUtil {

    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String PROVINCE = "province";
    private static final String CITY = "city";
    private static final int USER_TOUCH_TYPE_1 = 0x01;
    private static long clickTime;

    public static int dp2px(Context context, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static int dp2px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
    }

    public static SpannableString getColorfulString(String originStr, int color, int start, int end) {
        SpannableString ss = new SpannableString(originStr);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableString getColorfulString(String originStr, int color, int start, int end, int start1, int end1) {
        SpannableString ss = new SpannableString(originStr);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color), start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableString getColorfulString(String originStr, int color, int color1, int start, int end, int start1, int end1) {
        SpannableString ss = new SpannableString(originStr);
        ss.setSpan(new ForegroundColorSpan(color), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(color1), start1, end1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static SpannableString getMultiSizeString(String originStr, int size, int start, int end) {
        SpannableString ss = new SpannableString(originStr);
        ss.setSpan(new AbsoluteSizeSpan(size, true), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    public static void hideSoftInput(Context mContext, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && inputMethodManager.isActive(view)) {
            inputMethodManager
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void showSoftInput(Context mContext, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && inputMethodManager.isActive(view)) {
            inputMethodManager
                    .showSoftInput(view, 0);
        }
    }

    public static boolean isMobileValid(String mobile) {
        if (TextUtils.isEmpty(mobile) || !TextUtils.isDigitsOnly(mobile)) {
            return false;
        }
        return mobile.length() == 11;
    }

    public static boolean isEmailValid(String email) {
        if (TextUtils.isEmpty(email)) return false;

        String regex = "^([a-z0-9_\\.-]{1,50})@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$";
        return Pattern.matches(regex, email);
    }

    public static boolean isChinesValid(String mobile) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]+");
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

    public static String getStarMobile(String mobile) {
        if (TextUtils.isEmpty(mobile)) return "";
        int length = mobile.length();
        if (length == 11) {
            StringBuilder builder = new StringBuilder();
            builder.append(mobile.substring(0, 3)).append("****").append(mobile.substring(7));
            return builder.toString();
        } else if (length > 7 && length < 11) {
            StringBuilder builder = new StringBuilder();
            builder.append(mobile.substring(0, 2)).append("****").append(mobile.substring(6));
            return builder.toString();
        } else {
            return mobile;
        }
    }

    /**
     * 动态设置listview显示的条目数量
     */
    public static void setListViewHeightBasedOnChildren(ListView listView, int count) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < count; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (count - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
//        if (listAdapter.getCount() > count) {
//            params.height += 80;
//        }
        listView.setLayoutParams(params);
    }

    public static void setGridViewHeightBasedOnChildren(GridView gridview, int count) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int numColumns = gridview.getNumColumns();
        int countSize = count % numColumns == 0 ? count / numColumns : count / numColumns + 1;
        int totalHeight = 0;
        for (int i = 0; i < countSize; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            params.height = totalHeight + (gridview.getVerticalSpacing() * (countSize - 1));
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
//        if (listAdapter.getCount() > count) {
//            params.height += 80;
//        }
        gridview.setLayoutParams(params);
    }

    public static void setGridViewWidthBasedOnChildren(GridView gridview, int count) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gridview.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int numColumns = gridview.getNumColumns();
        int countSize = count % numColumns == 0 ? count / numColumns : count / numColumns + 1;
        int totalHeight = 0;
        for (int i = 0; i < countSize; i++) { // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridview);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredWidth(); // 统计所有子项的总高度
        }

        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        params.height = totalHeight;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            params.height = totalHeight + (gridview.getHorizontalSpacing() * (countSize - 1));
        }
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
//        if (listAdapter.getCount() > count) {
//            params.height += 80;
//        }
        gridview.setLayoutParams(params);
    }

    public static boolean isSave(String denominator, String numerator) {
        float deno = Float.parseFloat(denominator);
        float nume = Float.parseFloat(numerator);
        return (deno - nume) > 0;
    }

    public static String getMoneyLabel(String money) {
        if (TextUtils.isEmpty(money)) {
            money = "0";
        }
        float aFloat = Float.parseFloat(money);
        BigDecimal bd = new BigDecimal(aFloat);
        String string = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return string;
    }

    public static String getNoZeroMoneyLabel(String money) {
        if (TextUtils.isEmpty(money)) {
            money = "0";
        }
        float aFloat = Float.parseFloat(money);
        BigDecimal bd = new BigDecimal(aFloat);
        String string = bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
        return subZeroAndDot(string);
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     *
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

    public static String getFloatLabel(float money) {
        BigDecimal bd = new BigDecimal(money);
        return bd.setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    public static boolean isThing(int product_type) {
        return product_type == 0;
    }

    public static boolean isQQCoin(int product_type) {
        return product_type == 2;
    }

    public static boolean isAlipay(int product_type) {
        return product_type == 1;
    }

    public static boolean isMobileFee(int product_type) {
        return product_type == 4;
    }

    public static boolean isOthers(int product_type) {
        return product_type == 8 || product_type == 9 || product_type == 99;
    }

    public static String halfToFull(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 32) //半角空格
            {
                c[i] = (char) 12288;
                continue;
            }

            //根据实际情况，过滤不需要转换的符号
            //if (c[i] == 46) //半角点号，不转换
            // continue;

            if (c[i] > 32 && c[i] < 127)    //其他符号都转换为全角
                c[i] = (char) (c[i] + 65248);
        }
        return new String(c);
    }

    /**
     * Set whether this window is touch modal or if outside touches will be sent
     * to
     * other windows behind it.
     */
    public static void setPopupWindowTouchModal(PopupWindow popupWindow,
                                                boolean touchModal) {
        if (null == popupWindow) {
            return;
        }
        Method method;
        try {
            method = PopupWindow.class.getDeclaredMethod("setTouchModal",
                    boolean.class);
            method.setAccessible(true);
            method.invoke(popupWindow, touchModal);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*---   代码设置点击水波纹    ---*/
    public static void setRipple(Context context, View view) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, typedValue, true);
        int[] attribute = new int[]{android.R.attr.selectableItemBackground};
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(typedValue.resourceId, attribute);
        view.setBackgroundDrawable(typedArray.getDrawable(0));
        typedArray.recycle();
    }

    public static Bitmap obtainBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(screenshot);
        canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上
        return screenshot;
    }

    private static Bitmap getBitmap(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap tBitmap = v.getDrawingCache();
        // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
        tBitmap = Bitmap.createBitmap(tBitmap);
        v.setDrawingCacheEnabled(false);
        return tBitmap;
    }

    /**
     * 判断view是否被隐藏（如scrollview中被滑动到不可见位置的view）
     */
    public static boolean isViewCovered(final View view) {
        View currentView = view;

        Rect currentViewRect = new Rect();
        boolean partVisible = currentView.getGlobalVisibleRect(currentViewRect);
        boolean totalHeightVisible = (currentViewRect.bottom - currentViewRect.top) >= currentView.getMeasuredHeight();
        boolean totalWidthVisible = (currentViewRect.right - currentViewRect.left) >= currentView.getMeasuredWidth();
        boolean totalViewVisible = partVisible && totalHeightVisible && totalWidthVisible;
        if (!totalViewVisible) // if any part of the view is clipped by any of its parents,return true
            return true;

        while (currentView.getParent() instanceof ViewGroup) {
            ViewGroup currentParent = (ViewGroup) currentView.getParent();
            if (currentParent.getVisibility() != View.VISIBLE) // if the parent of view is not visible,return true
                return true;

            int start = indexOfViewInParent(currentView, currentParent);
            for (int i = start + 1; i < currentParent.getChildCount(); i++) {
                Rect viewRect = new Rect();
                view.getGlobalVisibleRect(viewRect);
                View otherView = currentParent.getChildAt(i);
                Rect otherViewRect = new Rect();
                otherView.getGlobalVisibleRect(otherViewRect);
                if (Rect.intersects(viewRect, otherViewRect)) // if view intersects its older brother(covered),return
                    // true
                    return true;
            }
            currentView = currentParent;
        }
        return false;
    }

    private static int indexOfViewInParent(View view, ViewGroup parent) {
        int index;
        for (index = 0; index < parent.getChildCount(); index++) {
            if (parent.getChildAt(index) == view)
                break;
        }
        return index;
    }

    /**
     * 点击是否过快
     */
    public static boolean isQuickClick() {
        if (System.currentTimeMillis() - clickTime > 1000) {
            clickTime = System.currentTimeMillis();
            return false;
        } else {
            return true;
        }
    }

    public static String getSignature(Context context) {
        try {
            /** 通过包管理器获得指定包名包含签名的包信息 **/
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            /******* 通过返回的包信息获得签名数组 *******/
            Signature[] signatures = packageInfo.signatures;
            /******* 循环遍历签名数组拼接应用签名 *******/
            return signatures[0].toCharsString();
            /************** 得到应用签名 **************/
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 模拟用户滑动操作
     *
     * @param view 要触发操作的view
     * @param type 模拟操作类型：均匀滑动、快速滑动
     * @param p1x  滑动的起始点x坐标
     * @param p1y  滑动的起始点y坐标
     * @param p2x  滑动的终点x坐标
     * @param p2y  滑动的终点y坐标
     */
    public static void analogUserScroll(View view, final int type, final float p1x, final float p1y, final float p2x, final float p2y) {
        if (view == null) {
            return;
        }
        long downTime = SystemClock.uptimeMillis();//模拟按下去的时间

        long eventTime = downTime;

        float pX = p1x;
        float pY = p1y;
        int speed = 0;//快速滑动
        float touchTime = 116;//模拟滑动时发生的触摸事件次数

        //平均每次事件要移动的距离
        float perX = (p2x - p1x) / touchTime;
        float perY = (p2y - p1y) / touchTime;

        boolean isReversal = perX < 0 || perY < 0;//判断是否反向：手指从下往上滑动，或者手指从右往左滑动
        boolean isHandY = Math.abs(perY) > Math.abs(perX);//判断是左右滑动还是上下滑动

        if (type == USER_TOUCH_TYPE_1) {//加速滑动
            touchTime = 10;//如果是快速滑动，则发生的触摸事件比均匀滑动更少
            speed = isReversal ? -20 : 20;//反向移动则坐标每次递减
        }

        //模拟用户按下
        MotionEvent downEvent = MotionEvent.obtain(downTime, eventTime,
                ACTION_DOWN, pX, pY, 0);
        view.dispatchTouchEvent(downEvent);

        //模拟移动过程中的事件
        List<MotionEvent> moveEvents = new ArrayList<>();
        boolean isSkip = false;
        for (int i = 0; i < touchTime; i++) {

            pX += (perX + speed);
            pY += (perY + speed);
            if ((isReversal && pX < p2x) || (!isReversal && pX > p2x)) {
                pX = p2x;
                isSkip = !isHandY;
            }

            if ((isReversal && pY < p2y) || (!isReversal && pY > p2y)) {
                pY = p2y;
                isSkip = isHandY;
            }
            eventTime += 20.0f;//事件发生的时间要不断递增
            MotionEvent moveEvent = getMoveEvent(downTime, eventTime, pX, pY);
            moveEvents.add(moveEvent);
            view.dispatchTouchEvent(moveEvent);
            if (type == USER_TOUCH_TYPE_1) {//加速滑动
                speed += (isReversal ? -70 : 70);
            }
            if (isSkip) {
                break;
            }
        }

        //模拟手指离开屏幕
        MotionEvent upEvent = MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_UP, pX, pY, 0);
        view.dispatchTouchEvent(upEvent);

        //回收触摸事件
        downEvent.recycle();
        for (int i = 0; i < moveEvents.size(); i++) {
            moveEvents.get(i).recycle();
        }
        upEvent.recycle();
    }

    private static MotionEvent getMoveEvent(long downTime, long eventTime, float pX, float pY) {
        return MotionEvent.obtain(downTime, eventTime,
                MotionEvent.ACTION_MOVE, pX, pY, 0);
    }

    public static void copy(Context context, String txt) {
        // 获取系统剪贴板
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
        ClipData clipData = ClipData.newPlainText(null, txt);
        // 把数据集设置（复制）到剪贴板
        clipboard.setPrimaryClip(clipData);
    }

    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
            Log.i("TAG", "statusHeight:" + statusHeight);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    private static WeakReference<Typeface> sPriceTypeface = null;

    public static Typeface getPriceTypeface(Context context) {
        if (sPriceTypeface == null || sPriceTypeface.get() == null) {
            AssetManager am = context.getAssets();
            sPriceTypeface = new WeakReference<>(Typeface.createFromAsset(am, "font/DINPro-Medium.otf"));
        }

        return sPriceTypeface.get();
    }

    public static void updateStatusBarHeight(Context mContext, View mStatusBarView) {
        ViewGroup.LayoutParams params = mStatusBarView.getLayoutParams();
        params.height = CommonUtil.getStatusHeight(mContext);
        mStatusBarView.setLayoutParams(params);
    }

    public static void setStatusBarHeightMarginTop(Context mContext, View mStatusBarView) {
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mStatusBarView.getLayoutParams();
        params.topMargin = CommonUtil.getStatusHeight(mContext);
        mStatusBarView.setLayoutParams(params);
    }

    /*public static void forwardClickAction(Context context, BaseFragment fragment, ForwardEntity entity) {
        if(isQuickClick()) {
            return;
        }
        Intent intent = new Intent();
        String function = entity.function;
        if (function.startsWith("wake:")) {


        } else if (function.startsWith("method:")) {
            String[] config = function.substring(7).split("-");
            try {
                Class clz = Class.forName(config[0]);
                Method method = clz.getMethod(config[1]);
                if (clz.isInstance(fragment)) {
                    method.invoke(fragment);
                } else {
                    method.invoke(context);
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } else {
            List<SliderParam> params = entity.params;
            if (params != null && !params.isEmpty()) {
                for (SliderParam param : params) {
                    intent.putExtra(param.key, param.value);
                }
            }
            try {
                intent.setClass(context, Class.forName(function));
                context.startActivity(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }*/

    public static boolean isActivityDestroyed(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return activity.isFinishing() || activity.isDestroyed();
        } else {
            return activity.isFinishing();
        }
    }

    /**
     * 处理InputMethodManager内存泄漏方法
     */
    public static void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager == null) {
            return;
        }

        String[] viewArray = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field filed;
        Object filedObject;

        for (String view : viewArray) {
            try {
                filed = inputMethodManager.getClass().getDeclaredField(view);
                if (!filed.isAccessible()) {
                    filed.setAccessible(true);
                }
                filedObject = filed.get(inputMethodManager);
                if (filedObject != null && filedObject instanceof View) {
                    View fileView = (View) filedObject;
                    if (fileView.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        filed.set(inputMethodManager, null); // 置空，破坏掉path to gc节点
                    } else {
                        break;// 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                    }
                }
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public static String format(String format, Object... objects) {
        return String.format(Locale.CHINA, format, objects);
    }


    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }


    public static boolean isEnableV26(Context context, String pkg, int uid) {
        try {
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            Method sServiceField = notificationManager.getClass().getDeclaredMethod("getService");
            sServiceField.setAccessible(true);
            Object sService = sServiceField.invoke(notificationManager);

            Method method = sService.getClass().getDeclaredMethod("areNotificationsEnabledForPackage"
                    , String.class, Integer.TYPE);
            method.setAccessible(true);
            return (boolean) method.invoke(sService, pkg, uid);
        } catch (Exception e) {
            return true;
        }
    }

    public static void toSetting(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        context.startActivity(localIntent);
    }

    public static Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    public static ObjectAnimator tada(View view) {
        return tada(view, 1f);
    }

    public static ObjectAnimator tada(View view, float shakeFactor) {

        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofKeyframe(View.SCALE_X,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhScaleY = PropertyValuesHolder.ofKeyframe(View.SCALE_Y,
                Keyframe.ofFloat(0f, 1f),
                Keyframe.ofFloat(.1f, .9f),
                Keyframe.ofFloat(.2f, .9f),
                Keyframe.ofFloat(.3f, 1.1f),
                Keyframe.ofFloat(.4f, 1.1f),
                Keyframe.ofFloat(.5f, 1.1f),
                Keyframe.ofFloat(.6f, 1.1f),
                Keyframe.ofFloat(.7f, 1.1f),
                Keyframe.ofFloat(.8f, 1.1f),
                Keyframe.ofFloat(.9f, 1.1f),
                Keyframe.ofFloat(1f, 1f)
        );

        PropertyValuesHolder pvhRotate = PropertyValuesHolder.ofKeyframe(View.ROTATION,
                Keyframe.ofFloat(0f, 0f),
                Keyframe.ofFloat(.1f, -3f * shakeFactor),
                Keyframe.ofFloat(.2f, -3f * shakeFactor),
                Keyframe.ofFloat(.3f, 3f * shakeFactor),
                Keyframe.ofFloat(.4f, -3f * shakeFactor),
                Keyframe.ofFloat(.5f, 3f * shakeFactor),
                Keyframe.ofFloat(.6f, -3f * shakeFactor),
                Keyframe.ofFloat(.7f, 3f * shakeFactor),
                Keyframe.ofFloat(.8f, -3f * shakeFactor),
                Keyframe.ofFloat(.9f, 3f * shakeFactor),
                Keyframe.ofFloat(1f, 0)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhScaleX, pvhScaleY, pvhRotate).
                setDuration(1000);
    }

    public static ObjectAnimator nope(View view) {
        int delta = 10;

        PropertyValuesHolder pvhTranslateX = PropertyValuesHolder.ofKeyframe(View.TRANSLATION_X,
                Keyframe.ofFloat(0f, 0),
                Keyframe.ofFloat(.10f, -delta),
                Keyframe.ofFloat(.26f, delta),
                Keyframe.ofFloat(.42f, -delta),
                Keyframe.ofFloat(.58f, delta),
                Keyframe.ofFloat(.74f, -delta),
                Keyframe.ofFloat(.90f, delta),
                Keyframe.ofFloat(1f, 0f)
        );

        return ObjectAnimator.ofPropertyValuesHolder(view, pvhTranslateX).
                setDuration(500);
    }

    public static String getPrice(String holder, String price) {
        return String.format(Locale.CHINA, holder + "¥%1$s", CommonUtil.getMoneyLabel(price));
    }

    public static String getNoZeroPrice(String holder, String price) {
        return String.format(Locale.CHINA, holder + "¥%1$s", CommonUtil.getNoZeroMoneyLabel(price));
    }

}
