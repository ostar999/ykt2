package com.psychiatrygarden.activity.purchase.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ToggleButton;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.AlbumGridViewAdapter;
import com.psychiatrygarden.activity.purchase.util.AlbumHelper;
import com.psychiatrygarden.activity.purchase.util.Bimp;
import com.psychiatrygarden.activity.purchase.util.ImageBucket;
import com.psychiatrygarden.activity.purchase.util.ImageItem;
import com.psychiatrygarden.activity.purchase.util.PublicWay;
import com.psychiatrygarden.activity.purchase.util.Res;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.NewToast;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* loaded from: classes5.dex */
public class AlbumActivity extends BaseActivity {
    public static Bitmap bitmap;
    public static List<ImageBucket> contentList;
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.purchase.activity.AlbumActivity.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            AlbumActivity.this.gridImageAdapter.notifyDataSetChanged();
        }
    };
    private ArrayList<ImageItem> dataList;
    private AlbumGridViewAdapter gridImageAdapter;
    private Intent intent;
    private Button okButton;
    private Button preview;

    public class AlbumSendListener implements View.OnClickListener {
        private AlbumSendListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            ArrayList<ImageItem> arrayList;
            if (CommonUtil.isFastClick() || (arrayList = Bimp.tempSelectBitmap) == null || arrayList.size() == 0) {
                return;
            }
            AlbumActivity.this.overridePendingTransition(R.anim.activity_translate_in, R.anim.activity_translate_out);
            Bimp.temp.clear();
            AlbumActivity.this.finish();
        }
    }

    public class BackListener implements View.OnClickListener {
        private BackListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            if (Bimp.temp.size() > 0) {
                for (int i2 = 0; i2 < Bimp.tempSelectBitmap.size(); i2++) {
                    for (int i3 = 0; i3 < Bimp.temp.size(); i3++) {
                        if (Bimp.temp.get(i3) == Bimp.tempSelectBitmap.get(i2)) {
                            Bimp.tempSelectBitmap.remove(i2);
                            Bimp.max--;
                        }
                    }
                }
            }
            AlbumActivity.this.intent.setClass(AlbumActivity.this, ImageFile.class);
            AlbumActivity albumActivity = AlbumActivity.this;
            albumActivity.startActivity(albumActivity.intent);
        }
    }

    public class CancelListener implements View.OnClickListener {
        private CancelListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (CommonUtil.isFastClick()) {
                return;
            }
            if (Bimp.temp.size() > 0) {
                for (int i2 = 0; i2 < Bimp.tempSelectBitmap.size(); i2++) {
                    for (int i3 = 0; i3 < Bimp.temp.size(); i3++) {
                        if (Bimp.temp.get(i3) == Bimp.tempSelectBitmap.get(i2)) {
                            Bimp.tempSelectBitmap.remove(i2);
                            Bimp.max--;
                        }
                    }
                }
            }
            AlbumActivity.this.finish();
        }
    }

    public class PreviewListener implements View.OnClickListener {
        private PreviewListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (!CommonUtil.isFastClick() && Bimp.tempSelectBitmap.size() > 0) {
                AlbumActivity.this.intent.putExtra("position", "1");
                AlbumActivity.this.intent.setClass(AlbumActivity.this, GalleryActivity.class);
                AlbumActivity albumActivity = AlbumActivity.this;
                albumActivity.startActivity(albumActivity.intent);
            }
        }
    }

    private void Init() {
        AlbumHelper helper = AlbumHelper.getHelper();
        helper.init(getApplicationContext());
        contentList = helper.getImagesBucketList(false);
        this.dataList = new ArrayList<>();
        for (int i2 = 0; i2 < contentList.size(); i2++) {
            this.dataList.addAll(contentList.get(i2).imageList);
        }
        this.mBtnActionbarBack.setOnClickListener(new CancelListener());
        Button button = (Button) findViewById(Res.getWidgetID("back"));
        ((Button) findViewById(Res.getWidgetID("cancel"))).setOnClickListener(new CancelListener());
        button.setOnClickListener(new BackListener());
        Button button2 = (Button) findViewById(Res.getWidgetID("preview"));
        this.preview = button2;
        button2.setOnClickListener(new PreviewListener());
        this.intent = getIntent();
        GridView gridView = (GridView) findViewById(Res.getWidgetID("myGrid"));
        AlbumGridViewAdapter albumGridViewAdapter = new AlbumGridViewAdapter(this, this.dataList, Bimp.tempSelectBitmap);
        this.gridImageAdapter = albumGridViewAdapter;
        gridView.setAdapter((ListAdapter) albumGridViewAdapter);
        gridView.setEmptyView((TextView) findViewById(Res.getWidgetID("myText")));
        Button button3 = (Button) findViewById(Res.getWidgetID("ok_button"));
        this.okButton = button3;
        button3.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
    }

    private void initListener() {
        this.gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.j
            @Override // com.psychiatrygarden.activity.purchase.adapter.AlbumGridViewAdapter.OnItemClickListener
            public final void onItemClick(ToggleButton toggleButton, int i2, boolean z2, Button button) {
                this.f13608a.lambda$initListener$0(toggleButton, i2, z2, button);
            }
        });
        this.okButton.setOnClickListener(new AlbumSendListener());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$0(ToggleButton toggleButton, int i2, boolean z2, Button button) {
        if (Bimp.tempSelectBitmap.size() >= PublicWay.num) {
            toggleButton.setChecked(false);
            button.setVisibility(8);
            if (removeOneData(this.dataList.get(i2))) {
                return;
            }
            NewToast.showShort(this, Res.getString("only_choose_num"), 0).show();
            return;
        }
        if (z2) {
            button.setVisibility(0);
            Bimp.tempSelectBitmap.add(this.dataList.get(i2));
            Bimp.temp.add(this.dataList.get(i2));
            this.okButton.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
        } else {
            Bimp.tempSelectBitmap.remove(this.dataList.get(i2));
            Bimp.temp.remove(this.dataList.get(i2));
            Bimp.max--;
            button.setVisibility(8);
            this.okButton.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
        }
        isShowOkBt();
    }

    private boolean removeOneData(ImageItem imageItem) {
        if (!Bimp.tempSelectBitmap.contains(imageItem)) {
            return false;
        }
        Bimp.tempSelectBitmap.remove(imageItem);
        Bimp.max--;
        this.okButton.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
        return true;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    public void isShowOkBt() {
        if (Bimp.tempSelectBitmap.size() > 0) {
            this.okButton.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
            this.preview.setPressed(true);
            this.okButton.setPressed(true);
            this.preview.setClickable(true);
            this.okButton.setClickable(true);
            this.okButton.setTextColor(-1);
            this.preview.setTextColor(-1);
            return;
        }
        this.okButton.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
        this.preview.setPressed(false);
        this.preview.setClickable(false);
        this.okButton.setPressed(false);
        this.okButton.setClickable(false);
        this.okButton.setTextColor(Color.parseColor("#E1E0DE"));
        this.preview.setTextColor(Color.parseColor("#E1E0DE"));
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plugin_camera_album);
        setTitle("选择图片");
        registerReceiver(this.broadcastReceiver, new IntentFilter("data.broadcast.action"));
        bitmap = BitmapFactory.decodeResource(getResources(), Res.getDrawableID("plugin_camera_no_pictures"));
        Init();
        initListener();
        isShowOkBt();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(this.broadcastReceiver);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4) {
            if (Bimp.temp.size() > 0) {
                for (int i2 = 0; i2 < Bimp.tempSelectBitmap.size(); i2++) {
                    for (int i3 = 0; i3 < Bimp.temp.size(); i3++) {
                        if (Bimp.temp.get(i3) == Bimp.tempSelectBitmap.get(i2)) {
                            Bimp.tempSelectBitmap.remove(i2);
                            Bimp.max--;
                        }
                    }
                }
            }
            this.intent.setClass(this, ImageFile.class);
            startActivity(this.intent);
            finish();
        }
        return false;
    }

    @Override // android.app.Activity
    public void onRestart() {
        isShowOkBt();
        super.onRestart();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
