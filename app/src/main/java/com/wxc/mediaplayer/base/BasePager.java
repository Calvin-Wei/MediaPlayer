package com.wxc.mediaplayer.base;

import android.content.Context;
import android.view.View;

/**
 * Created by wxc on 2017/2/8.
 */

public abstract class BasePager {
    //上下文
    public final Context context;
    //接受个页面的实例
    public View rootView;
    public boolean isInitData;

    public BasePager(Context context) {
        this.context = context;
        rootView = initView();
    }

    /**
     * 子类必须实现该方法，实现想要的特定的效果
     *
     * @return
     */
    public abstract View initView();

    /**
     * 档子页面，需要绑定数据，或者联网请求数据并绑定时，使用该方法
     */
    public void initData() {

    }
}
