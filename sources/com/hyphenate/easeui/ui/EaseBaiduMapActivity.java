package com.hyphenate.easeui.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.ui.base.EaseBaseActivity;
import com.hyphenate.easeui.utils.EaseCommonUtils;
import com.hyphenate.easeui.widget.EaseTitleBar;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes4.dex */
public class EaseBaiduMapActivity extends EaseBaseActivity implements EaseTitleBar.OnBackPressListener, EaseTitleBar.OnRightClickListener {
    protected String address;
    private BaiduMap baiduMap;
    private BDLocation lastLocation;
    protected double latitude;
    protected double longtitude;
    private BaiduSDKReceiver mBaiduReceiver;
    private LocationClient mLocClient;
    private MapView mapView;
    private EaseTitleBar titleBarMap;

    public class BaiduSDKReceiver extends BroadcastReceiver {
        public BaiduSDKReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (TextUtils.equals(action, "permission check error")) {
                EaseBaiduMapActivity easeBaiduMapActivity = EaseBaiduMapActivity.this;
                easeBaiduMapActivity.showErrorToast(easeBaiduMapActivity.getResources().getString(R.string.please_check));
            } else if (TextUtils.equals(action, "network error")) {
                EaseBaiduMapActivity easeBaiduMapActivity2 = EaseBaiduMapActivity.this;
                easeBaiduMapActivity2.showErrorToast(easeBaiduMapActivity2.getResources().getString(R.string.Network_error));
            }
        }
    }

    public class EaseBDLocationListener implements BDLocationListener {
        public EaseBDLocationListener() {
        }

        public void onReceiveLocation(BDLocation bDLocation) {
            EaseBaiduMapActivity.this.onReceiveBDLocation(bDLocation);
        }
    }

    public static void actionStart(Context context, double d3, double d4, String str) {
        Intent intent = new Intent(context, (Class<?>) EaseBaiduMapActivity.class);
        intent.putExtra("latitude", d3);
        intent.putExtra("longtitude", d4);
        intent.putExtra("address", str);
        context.startActivity(intent);
    }

    public static void actionStartForResult(Fragment fragment, int i2) {
        fragment.startActivityForResult(new Intent(fragment.getContext(), (Class<?>) EaseBaiduMapActivity.class), i2);
    }

    private void initData() {
        if (this.latitude == 0.0d) {
            this.mapView = new MapView(this, new BaiduMapOptions());
            this.baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.NORMAL, true, (BitmapDescriptor) null));
            showMapWithLocationClient();
        } else {
            this.mapView = new MapView(this, new BaiduMapOptions().mapStatus(new MapStatus.Builder().target(new LatLng(this.latitude, this.longtitude)).build()));
            showMap(this.latitude, this.longtitude);
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("permission check error");
        intentFilter.addAction("network error");
        BaiduSDKReceiver baiduSDKReceiver = new BaiduSDKReceiver();
        this.mBaiduReceiver = baiduSDKReceiver;
        registerReceiver(baiduSDKReceiver, intentFilter);
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
        this.mapView = findViewById(R.id.bmapView);
        this.titleBarMap.setRightTitleResource(R.string.button_send);
        if (getIntent().getDoubleExtra("latitude", 0.0d) != 0.0d) {
            this.titleBarMap.getRightLayout().setVisibility(8);
        } else {
            this.titleBarMap.getRightLayout().setVisibility(0);
            this.titleBarMap.getRightLayout().setClickable(false);
        }
        ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.titleBarMap.getLayoutParams())).topMargin = (int) EaseCommonUtils.dip2px(this, 24.0f);
        this.titleBarMap.setBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
        this.titleBarMap.getRightText().setTextColor(ContextCompat.getColor(this, R.color.white));
        this.titleBarMap.getRightText().setBackgroundResource(R.drawable.ease_title_bar_right_selector);
        int iDip2px = (int) EaseCommonUtils.dip2px(this, 10.0f);
        int iDip2px2 = (int) EaseCommonUtils.dip2px(this, 5.0f);
        this.titleBarMap.getRightText().setPadding(iDip2px, iDip2px2, iDip2px, iDip2px2);
        ViewGroup.LayoutParams layoutParams = this.titleBarMap.getRightLayout().getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(0, 0, iDip2px, 0);
        }
        BaiduMap map = this.mapView.getMap();
        this.baiduMap = map;
        map.setMapStatus(MapStatusUpdateFactory.zoomTo(15.0f));
        this.mapView.setLongClickable(true);
    }

    private void sendLocation() {
        Intent intent = getIntent();
        intent.putExtra("latitude", this.lastLocation.getLatitude());
        intent.putExtra("longitude", this.lastLocation.getLongitude());
        intent.putExtra("address", this.lastLocation.getAddrStr());
        intent.putExtra("buildingName", this.lastLocation.getBuildingName() == null ? "" : this.lastLocation.getBuildingName());
        setResult(-1, intent);
        finish();
    }

    @Override // com.hyphenate.easeui.widget.EaseTitleBar.OnBackPressListener
    public void onBackPress(View view) {
        onBackPressed();
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        super.onCreate(bundle);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.ease_activity_baidumap);
        setFitSystemForTheme(false, R.color.transparent, true);
        initIntent();
        initView();
        initListener();
        initData();
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        LocationClient locationClient = this.mLocClient;
        if (locationClient != null) {
            locationClient.stop();
        }
        this.mapView.onDestroy();
        unregisterReceiver(this.mBaiduReceiver);
        super.onDestroy();
    }

    @Override // androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        this.mapView.onPause();
        LocationClient locationClient = this.mLocClient;
        if (locationClient != null) {
            locationClient.stop();
        }
        super.onPause();
        this.lastLocation = null;
    }

    public void onReceiveBDLocation(BDLocation bDLocation) {
        if (bDLocation == null) {
            return;
        }
        BDLocation bDLocation2 = this.lastLocation;
        if (bDLocation2 != null && bDLocation2.getLatitude() == bDLocation.getLatitude() && this.lastLocation.getLongitude() == bDLocation.getLongitude()) {
            Log.d("map", "same location, skip refresh");
            return;
        }
        this.titleBarMap.getRightLayout().setClickable(true);
        this.lastLocation = bDLocation;
        this.baiduMap.clear();
        showMap(this.lastLocation.getLatitude(), this.lastLocation.getLongitude());
    }

    @Override // com.hyphenate.easeui.ui.base.EaseBaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        this.mapView.onResume();
        LocationClient locationClient = this.mLocClient;
        if (locationClient != null && !locationClient.isStarted()) {
            this.mLocClient.start();
        }
        super.onResume();
    }

    @Override // com.hyphenate.easeui.widget.EaseTitleBar.OnRightClickListener
    public void onRightClick(View view) {
        sendLocation();
    }

    public void showErrorToast(String str) {
        Toast.makeText(this, str, 0).show();
    }

    public void showMap(double d3, double d4) {
        LatLng latLng = new LatLng(d3, d4);
        CoordinateConverter coordinateConverter = new CoordinateConverter();
        coordinateConverter.coord(latLng);
        coordinateConverter.from(CoordinateConverter.CoordType.COMMON);
        LatLng latLngConvert = coordinateConverter.convert();
        this.baiduMap.addOverlay(new MarkerOptions().position(latLngConvert).icon(BitmapDescriptorFactory.fromResource(R.drawable.ease_icon_marka)).zIndex(4).draggable(true));
        this.baiduMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(latLngConvert, 17.0f));
    }

    public void showMapWithLocationClient() {
        LocationClient locationClient = new LocationClient(this);
        this.mLocClient = locationClient;
        locationClient.registerLocationListener(new EaseBDLocationListener());
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setOpenGps(true);
        locationClientOption.setCoorType("bd09ll");
        locationClientOption.setScanSpan(30000);
        locationClientOption.setAddrType("all");
        locationClientOption.setIgnoreKillProcess(true);
        this.mLocClient.setLocOption(locationClientOption);
        if (this.mLocClient.isStarted()) {
            return;
        }
        this.mLocClient.start();
    }

    public static void actionStartForResult(Activity activity, int i2) {
        activity.startActivityForResult(new Intent(activity, (Class<?>) EaseBaiduMapActivity.class), i2);
    }
}
