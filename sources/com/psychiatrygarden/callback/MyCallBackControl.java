package com.psychiatrygarden.callback;

/* loaded from: classes5.dex */
public class MyCallBackControl {
    private static MyCallBackControl callback;
    public HomeTabSelectedLisenter homeTabSelectedLisenter;

    private MyCallBackControl() {
    }

    public static MyCallBackControl getIntence() {
        if (callback == null) {
            synchronized (MyCallBackControl.class) {
                if (callback == null) {
                    callback = new MyCallBackControl();
                }
            }
        }
        return callback;
    }

    public void setHomeTabChooseCallBack(HomeTabSelectedLisenter callBack) {
        this.homeTabSelectedLisenter = callBack;
    }
}
