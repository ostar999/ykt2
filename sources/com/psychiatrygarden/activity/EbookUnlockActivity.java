package com.psychiatrygarden.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.psychiatrygarden.activity.vip.Utils.MemInterface;
import com.psychiatrygarden.activity.vip.bean.MemCenterBean;
import com.yikaobang.yixue.R2;
import java.util.List;

/* loaded from: classes5.dex */
public class EbookUnlockActivity extends BaseActivity {
    private void hideNav() {
        getWindow().getDecorView().setSystemUiVisibility(R2.attr.triggerReceiver);
    }

    public static void setFullView(Activity context) {
        context.getWindow().addFlags(1024);
        int i2 = Build.VERSION.SDK_INT;
        context.getWindow().getDecorView().setSystemUiVisibility(1024);
        if (i2 >= 28) {
            WindowManager.LayoutParams attributes = context.getWindow().getAttributes();
            attributes.layoutInDisplayCutoutMode = 1;
            context.getWindow().setAttributes(attributes);
        }
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void init() {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity, cn.webdemo.com.supporfragment.base.SupportActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle savedInstanceState) {
        setFullView(this);
        super.onCreate(savedInstanceState);
        hideNav();
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void onEventMainThread(String str) {
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setContentView() {
        List<MemCenterBean.DataBeanX.WaysBean> list;
        this.mActionBar.hide();
        Uri data = getIntent().getData();
        if (data == null || !TextUtils.equals("ebook_unlock", data.getScheme())) {
            return;
        }
        String queryParameter = data.getQueryParameter("payways");
        if (TextUtils.isEmpty(queryParameter) || (list = (List) new Gson().fromJson(queryParameter, new TypeToken<List<MemCenterBean.DataBeanX.WaysBean>>() { // from class: com.psychiatrygarden.activity.EbookUnlockActivity.1
        }.getType())) == null || list.size() <= 0) {
            return;
        }
        MemInterface memInterface = MemInterface.getInstance();
        memInterface.setDisType(0);
        memInterface.putMemean(this, "选择解锁方式", list, false, 0);
    }

    @Override // com.psychiatrygarden.activity.BaseActivity
    public void setListenerForWidget() {
    }
}
