package com.psychiatrygarden.activity.chat;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.hyphenate.easeui.ui.base.EaseBaseActivity;
import com.hyphenate.easeui.widget.EaseTitleBar;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes5.dex */
public class ChatBaiduMapActivity extends EaseBaseActivity implements EaseTitleBar.OnBackPressListener, EaseTitleBar.OnRightClickListener {
    protected String address;
    protected double latitude;
    protected double longtitude;
    private BaiduSDKReceiver mBaiduReceiver;
    private EaseTitleBar titleBarMap;

    public class BaiduSDKReceiver extends BroadcastReceiver {
        public BaiduSDKReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            intent.getAction();
        }
    }

    public static void actionStart(Context context, double latitude, double longtitude, String address) {
        Intent intent = new Intent(context, (Class<?>) ChatBaiduMapActivity.class);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longtitude", longtitude);
        intent.putExtra("address", address);
        context.startActivity(intent);
    }

    public static void actionStartForResult(Fragment fragment, int requestCode) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), (Class<?>) ChatBaiduMapActivity.class), requestCode);
    }

    private void initData() {
    }

    private void initIntent() {
        this.latitude = getIntent().getDoubleExtra("latitude", 0.0d);
        this.longtitude = getIntent().getDoubleExtra("longtitude", 0.0d);
        this.address = getIntent().getStringExtra("address");
    }

    private void initListener() {
        this.titleBarMap.setOnBackPressListener(this);
        this.titleBarMap.setOnRightClickListener(this);
    }

    private void initView() {
        this.titleBarMap = (EaseTitleBar) findViewById(R.id.title_bar_map);
    }

    private void sendLocation() {
    }

    @Override // com.hyphenate.easeui.widget.EaseTitleBar.OnBackPressListener
    public void onBackPress(View view) {
        onBackPressed();
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ease_activity_baidumap);
        setFitSystemForTheme(false, R.color.transparent, true);
        initIntent();
        initView();
        initListener();
        initData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.hyphenate.easeui.widget.EaseTitleBar.OnRightClickListener
    public void onRightClick(View view) {
        sendLocation();
    }

    public void showErrorToast(String message) {
        NewToast.showShort(this, message, 0).show();
    }

    public void showMap(double latitude, double longtitude) {
    }

    public void showMapWithLocationClient() {
    }

    public static void actionStartForResult(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent(activity, (Class<?>) ChatBaiduMapActivity.class), requestCode);
    }
}
