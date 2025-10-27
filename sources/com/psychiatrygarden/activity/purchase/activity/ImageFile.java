package com.psychiatrygarden.activity.purchase.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.TextView;
import com.psychiatrygarden.activity.BaseActivity;
import com.psychiatrygarden.activity.purchase.adapter.FolderAdapter;
import com.psychiatrygarden.activity.purchase.util.Bimp;
import com.psychiatrygarden.activity.purchase.util.Res;
import com.yikaobang.yixue.R;

/* loaded from: classes5.dex */
public class ImageFile extends BaseActivity {
    private Button bt_cancel;
    private FolderAdapter folderAdapter;
    private Context mContext;
    TextView tv_actionbar_right;

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
            ImageFile.this.finish();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Res.init(this);
        setContentView(Res.getLayoutID("plugin_camera_image_file"));
        this.mContext = this;
        setTitle("选择相册");
        TextView textView = (TextView) findViewById(R.id.tv_actionbar_right);
        this.tv_actionbar_right = textView;
        textView.setVisibility(0);
        this.tv_actionbar_right.setText("返回");
        this.tv_actionbar_right.setOnClickListener(new CancelListener());
        Button button = (Button) findViewById(Res.getWidgetID("cancel"));
        this.bt_cancel = button;
        button.setOnClickListener(new CancelListener());
        GridView gridView = (GridView) findViewById(Res.getWidgetID("fileGridView"));
        ((TextView) findViewById(Res.getWidgetID("headerTitle"))).setText(Res.getString("photo"));
        FolderAdapter folderAdapter = new FolderAdapter(this);
        this.folderAdapter = folderAdapter;
        gridView.setAdapter((ListAdapter) folderAdapter);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
        if (str.equals("finish")) {
            finish();
        }
    }

    @Override // androidx.appcompat.app.AppCompatActivity, android.app.Activity, android.view.KeyEvent.Callback
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 4) {
            return false;
        }
        finish();
        return false;
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
