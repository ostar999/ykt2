package com.psychiatrygarden.activity.purchase.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ProgressBar;
import android.widget.ToggleButton;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.AlbumGridViewAdapter;
import com.psychiatrygarden.activity.purchase.util.Bimp;
import com.psychiatrygarden.activity.purchase.util.ImageItem;
import com.psychiatrygarden.activity.purchase.util.PublicWay;
import com.psychiatrygarden.activity.purchase.util.Res;
import com.psychiatrygarden.utils.NewToast;
import de.greenrobot.event.EventBus;
import java.util.ArrayList;
import java.util.Locale;

/* loaded from: classes5.dex */
public class ShowAllPhoto extends BaseActivity {
    public static ArrayList<ImageItem> dataList = new ArrayList<>();
    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.psychiatrygarden.activity.purchase.activity.ShowAllPhoto.1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            ShowAllPhoto.this.gridImageAdapter.notifyDataSetChanged();
        }
    };
    private AlbumGridViewAdapter gridImageAdapter;
    private Intent intent;
    private Button okButton;
    private Button preview;

    public class BackListener implements View.OnClickListener {
        Intent intent;

        public BackListener(Intent intent) {
            this.intent = intent;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
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
            this.intent.setClass(ShowAllPhoto.this, ImageFile.class);
            ShowAllPhoto.this.startActivity(this.intent);
        }
    }

    public class CancelListener implements View.OnClickListener {
        private CancelListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (Bimp.temp.size() > 0) {
                for (int i2 = 0; i2 < Bimp.tempSelectBitmap.size(); i2++) {
                    for (int i3 = 0; i3 < Bimp.temp.size(); i3++) {
                        if (Bimp.tempSelectBitmap.get(i2) == Bimp.temp.get(i3)) {
                            Bimp.tempSelectBitmap.remove(i2);
                            Bimp.max--;
                        }
                    }
                }
            }
            ShowAllPhoto.this.finish();
        }
    }

    public class PreviewListener implements View.OnClickListener {
        private PreviewListener() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View v2) {
            if (Bimp.tempSelectBitmap.size() > 0) {
                if (Bimp.temp.size() > 0) {
                    Bimp.temp.clear();
                }
                ShowAllPhoto.this.finish();
                ShowAllPhoto.this.intent.putExtra("position", "2");
                ShowAllPhoto.this.intent.setClass(ShowAllPhoto.this, GalleryActivity.class);
                ShowAllPhoto showAllPhoto = ShowAllPhoto.this;
                showAllPhoto.startActivity(showAllPhoto.intent);
            }
        }
    }

    private void Init() {
        registerReceiver(this.broadcastReceiver, new IntentFilter("data.broadcast.action"));
        ((ProgressBar) findViewById(Res.getWidgetID("showallphoto_progressbar"))).setVisibility(8);
        GridView gridView = (GridView) findViewById(Res.getWidgetID("showallphoto_myGrid"));
        AlbumGridViewAdapter albumGridViewAdapter = new AlbumGridViewAdapter(this, dataList, Bimp.tempSelectBitmap);
        this.gridImageAdapter = albumGridViewAdapter;
        gridView.setAdapter((ListAdapter) albumGridViewAdapter);
    }

    private void initListener() {
        this.gridImageAdapter.setOnItemClickListener(new AlbumGridViewAdapter.OnItemClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.f1
            @Override // com.psychiatrygarden.activity.purchase.adapter.AlbumGridViewAdapter.OnItemClickListener
            public final void onItemClick(ToggleButton toggleButton, int i2, boolean z2, Button button) {
                this.f13593a.lambda$initListener$0(toggleButton, i2, z2, button);
            }
        });
        this.okButton.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.purchase.activity.g1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13599c.lambda$initListener$1(view);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$0(ToggleButton toggleButton, int i2, boolean z2, Button button) {
        if (Bimp.tempSelectBitmap.size() >= PublicWay.num && z2) {
            button.setVisibility(8);
            toggleButton.setChecked(false);
            NewToast.showShort(this, Res.getString("only_choose_num"), 200).show();
            return;
        }
        if (z2) {
            button.setVisibility(0);
            Bimp.tempSelectBitmap.add(dataList.get(i2));
            Bimp.temp.add(dataList.get(i2));
            this.okButton.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
        } else {
            button.setVisibility(8);
            Bimp.temp.remove(dataList.get(i2));
            Bimp.tempSelectBitmap.remove(dataList.get(i2));
            Bimp.max--;
            this.okButton.setText(String.format(Locale.CHINA, "%s(%d/%d)", Res.getString("finish"), Integer.valueOf(Bimp.tempSelectBitmap.size()), Integer.valueOf(PublicWay.num)));
        }
        isShowOkBt();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$initListener$1(View view) {
        this.okButton.setClickable(false);
        if (Bimp.temp.size() > 0) {
            Bimp.temp.clear();
        }
        startActivity(new Intent(getApplicationContext(), (Class<?>) XiaoHongShuReplyActivity.class));
        EventBus.getDefault().post("finish");
        finish();
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
        Res.init(this);
        setContentView(Res.getLayoutID("plugin_camera_show_all_photo"));
        Button button = (Button) findViewById(Res.getWidgetID("showallphoto_back"));
        Button button2 = (Button) findViewById(Res.getWidgetID("showallphoto_cancel"));
        this.preview = (Button) findViewById(Res.getWidgetID("showallphoto_preview"));
        this.okButton = (Button) findViewById(Res.getWidgetID("showallphoto_ok_button"));
        Intent intent = getIntent();
        this.intent = intent;
        String stringExtra = intent.getStringExtra("folderName");
        if (stringExtra != null && stringExtra.length() > 8) {
            stringExtra = stringExtra.substring(0, 9) + "...";
        }
        setTitle(stringExtra);
        this.mBtnActionbarBack.setOnClickListener(new CancelListener());
        button2.setOnClickListener(new CancelListener());
        button.setOnClickListener(new BackListener(this.intent));
        this.preview.setOnClickListener(new PreviewListener());
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
