package com.psychiatrygarden.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.adapter.LoadImageAdapter;
import com.psychiatrygarden.utils.NewToast;
import com.psychiatrygarden.widget.RequestMediaPermissionPop;
import com.psychiatrygarden.widget.imagezoom.MutipleTouchViewPager;
import com.yikaobang.yixue.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/* loaded from: classes5.dex */
public class LoadImageActivity extends BaseActivity {
    private LinearLayout linearLayout2;
    private ArrayList<String> mList;
    LoadImageAdapter mLoadAdapter;
    private List<View> mViews;
    private int position = 0;
    private String downloadurl = "";

    @SuppressLint({"HandlerLeak"})
    private final Handler mFinishHandler = new Handler() { // from class: com.psychiatrygarden.activity.LoadImageActivity.1
        @Override // android.os.Handler
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                LoadImageActivity.this.finish();
            }
        }
    };

    public class SaveImage extends AsyncTask<String, Void, String> {
        private SaveImage() {
        }

        @Override // android.os.AsyncTask
        public String doInBackground(String... params) throws IOException {
            try {
                String string = Environment.getExternalStorageDirectory().toString();
                File file = new File(string + "/Download/yikaobang/");
                if (!file.exists()) {
                    file.mkdirs();
                }
                File file2 = new File(string + "/Download/yikaobang/" + new Date().getTime() + ".jpg");
                HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(LoadImageActivity.this.downloadurl.equals("") ? (String) LoadImageActivity.this.mList.get(LoadImageActivity.this.position) : LoadImageActivity.this.downloadurl).openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(20000);
                InputStream inputStream = httpURLConnection.getResponseCode() == 200 ? httpURLConnection.getInputStream() : null;
                byte[] bArr = new byte[4096];
                FileOutputStream fileOutputStream = new FileOutputStream(file2);
                while (true) {
                    int i2 = inputStream.read(bArr);
                    if (i2 == -1) {
                        fileOutputStream.close();
                        String str = "图片已保存至：" + file2.getAbsolutePath();
                        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                        intent.setData(Uri.fromFile(file2));
                        LoadImageActivity.this.sendBroadcast(intent);
                        return str;
                    }
                    fileOutputStream.write(bArr, 0, i2);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
                return "保存失败！" + e2.getLocalizedMessage();
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String result) {
            NewToast.showShort(LoadImageActivity.this.mContext, result, 1).show();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$0() {
        ActivityCompat.requestPermissions(this, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$init$1(View view) {
        try {
            if (ContextCompat.checkSelfPermission(ProjectApp.instance(), Permission.WRITE_EXTERNAL_STORAGE) != 0) {
                new XPopup.Builder(this).asCustom(new RequestMediaPermissionPop(this, new OnConfirmListener() { // from class: com.psychiatrygarden.activity.md
                    @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                    public final void onConfirm() {
                        this.f12762a.lambda$init$0();
                    }
                })).show();
            } else {
                new SaveImage().execute(new String[0]);
            }
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() throws Resources.NotFoundException {
        this.linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        TextView textView = (TextView) findViewById(R.id.saveimg);
        if (this.mList.size() == 1) {
            this.linearLayout2.setVisibility(8);
        }
        for (int i2 = 0; i2 < this.mList.size(); i2++) {
            ImageView imageView = new ImageView(ProjectApp.instance());
            if (this.position == i2) {
                imageView.setBackgroundResource(R.drawable.scroll_but_active);
            } else {
                imageView.setBackgroundResource(R.drawable.scroll_but);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.setMargins(5, 0, 5, 0);
            this.linearLayout2.addView(imageView, layoutParams);
        }
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.activity.nd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f13044c.lambda$init$1(view);
            }
        });
        MutipleTouchViewPager mutipleTouchViewPager = (MutipleTouchViewPager) findViewById(R.id.view_pager);
        LoadImageAdapter loadImageAdapter = new LoadImageAdapter(this, this.mList, this.mFinishHandler, getIntent().getBooleanExtra("methodTrue", false), getIntent().getBooleanExtra("longpic", false));
        this.mLoadAdapter = loadImageAdapter;
        mutipleTouchViewPager.setAdapter(loadImageAdapter);
        mutipleTouchViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.psychiatrygarden.activity.LoadImageActivity.2
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int arg0) {
                LoadImageActivity.this.position = arg0;
                LoadImageActivity.this.linearLayout2.removeAllViews();
                for (int i3 = 0; i3 < LoadImageActivity.this.mList.size(); i3++) {
                    ImageView imageView2 = new ImageView(ProjectApp.instance());
                    if (arg0 == i3) {
                        imageView2.setBackgroundResource(R.drawable.scroll_but_active);
                    } else {
                        imageView2.setBackgroundResource(R.drawable.scroll_but);
                    }
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-2, -2);
                    layoutParams2.setMargins(5, 0, 5, 0);
                    LoadImageActivity.this.linearLayout2.addView(imageView2, layoutParams2);
                }
            }
        });
        mutipleTouchViewPager.setCurrentItem(this.position);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        this.mFinishHandler.removeCallbacksAndMessages(null);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
    }

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, android.app.Activity
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 2) {
            if (grantResults.length <= 0 || grantResults[0] != 0) {
                NewToast.showShort(this, "请手动打开软件存储权限", 0).show();
            } else {
                new SaveImage().execute(new String[0]);
            }
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        setSwipeBackEnable(false);
        this.mActionBar.hide();
        setNewStyleStatusBarColor();
        this.position = getIntent().getIntExtra("position", 0);
        this.downloadurl = getIntent().getExtras().getString("downloadurl", "");
        this.mList = getIntent().getStringArrayListExtra("list");
        setContentView(R.layout.activity_view_pager);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
