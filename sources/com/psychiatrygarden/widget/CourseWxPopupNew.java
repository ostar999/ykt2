package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.hjq.permissions.Permission;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.psychiatrygarden.ProjectApp;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CourseWxPopupNew extends CenterPopupView {
    public ImageView close;
    String content;
    String imgUrl;
    Context mcontext;
    public TextView tvSaveImg;
    String wechat_number;
    public ImageView wxPopCode;
    public TextView wxPopContent;
    public TextView wxPopWxChat;

    public CourseWxPopupNew(@NonNull Context context, String imgUrl) {
        super(context);
        this.imgUrl = imgUrl;
        this.mcontext = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$1() {
        ActivityCompat.requestPermissions((Activity) this.mcontext, new String[]{Permission.WRITE_EXTERNAL_STORAGE, Permission.READ_EXTERNAL_STORAGE}, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$2(View view) {
        if (ContextCompat.checkSelfPermission(ProjectApp.instance(), Permission.WRITE_EXTERNAL_STORAGE) != 0) {
            new XPopup.Builder(this.mcontext).asCustom(new RequestMediaPermissionPop(this.mcontext, new OnConfirmListener() { // from class: com.psychiatrygarden.widget.t4
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f16923a.lambda$onCreate$1();
                }
            })).show();
            return;
        }
        String str = this.imgUrl;
        if (str != null && !str.isEmpty()) {
            new CommonUtil.SaveImage(this.imgUrl, (Activity) this.mcontext).execute(new String[0]);
        }
        try {
            CommonUtil.copySelect(this.mcontext, this.wxPopContent);
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(268435456);
            intent.setComponent(componentName);
            this.mcontext.startActivity(intent);
            dismiss();
        } catch (Exception e2) {
            Log.e("error", e2.getMessage());
            ToastUtil.shortToast(this.mcontext, "检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_course_wx_pop_new;
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.wxPopContent = (TextView) findViewById(R.id.wxPopContent);
        this.wxPopWxChat = (TextView) findViewById(R.id.wxPopWxChat);
        this.close = (ImageView) findViewById(R.id.imgClose);
        this.wxPopCode = (ImageView) findViewById(R.id.wxPopCode);
        this.tvSaveImg = (TextView) findViewById(R.id.tvSaveImg);
        this.wxPopContent.setText(this.content);
        this.wxPopWxChat.setText(this.wechat_number);
        GlideApp.with(this.mcontext).load((Object) GlideUtils.generateUrl(this.imgUrl)).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.app_icon)).into(this.wxPopCode);
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.r4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16851c.lambda$onCreate$0(view);
            }
        });
        this.tvSaveImg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.s4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16889c.lambda$onCreate$2(view);
            }
        });
    }

    public CourseWxPopupNew(@NonNull Context context, String imgUrl, String content, String wechat_number) {
        super(context);
        this.imgUrl = imgUrl;
        this.mcontext = context;
        this.content = content;
        this.wechat_number = wechat_number;
    }
}
