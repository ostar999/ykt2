package com.psychiatrygarden.widget;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
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
import com.psychiatrygarden.activity.circleactivity.CircleSchoolSectionCenterActivity;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.GlideUtils;
import com.psychiatrygarden.utils.ToastUtil;
import com.psychiatrygarden.widget.glideUtil.progress.GlideApp;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CourseWxPopwindow extends CenterPopupView {
    public ImageView close;
    String imgyurl;
    Context mcontext;
    public ImageView pictrueimg;
    public TextView saveimg;
    public TextView tipstxt;
    public TextView title;
    String wechat_number;
    String wechat_tips;

    public CourseWxPopwindow(@NonNull Context context, String imgyurl) {
        super(context);
        this.imgyurl = imgyurl;
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
            new XPopup.Builder(this.mcontext).asCustom(new RequestMediaPermissionPop(this.mcontext, new OnConfirmListener() { // from class: com.psychiatrygarden.widget.u4
                @Override // com.lxj.xpopup.interfaces.OnConfirmListener
                public final void onConfirm() {
                    this.f16946a.lambda$onCreate$1();
                }
            })).show();
            return;
        }
        String str = this.imgyurl;
        if (str != null && !"".equals(str)) {
            new CommonUtil.SaveImage(this.imgyurl, (Activity) this.mcontext).execute(new String[0]);
        }
        try {
            CommonUtil.copySelect(this.mcontext, this.tipstxt);
            Intent intent = new Intent();
            ComponentName componentName = new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI");
            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.addFlags(268435456);
            intent.setComponent(componentName);
            this.mcontext.startActivity(intent);
        } catch (Exception e2) {
            e2.printStackTrace();
            ToastUtil.shortToast(this.mcontext, "检查到您手机没有安装微信，请安装后使用该功能");
        }
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.layout_course_wx_pop;
    }

    @Override // com.lxj.xpopup.core.CenterPopupView, com.lxj.xpopup.core.BasePopupView
    public int getMaxWidth() {
        return CommonUtil.dip2px(getContext(), 300.0f);
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        this.tipstxt = (TextView) findViewById(R.id.tipstxt);
        this.close = (ImageView) findViewById(R.id.close);
        this.title = (TextView) findViewById(R.id.title);
        this.saveimg = (TextView) findViewById(R.id.saveimg);
        this.pictrueimg = (ImageView) findViewById(R.id.pictrueimg);
        if (this.mcontext instanceof CircleSchoolSectionCenterActivity) {
            this.title.setText("添加管理员微信，提交个人资料申请版主");
            this.tipstxt.setText("添加管理员微信");
        } else {
            this.title.setText("" + this.wechat_tips);
            this.tipstxt.setText("" + this.wechat_number);
        }
        GlideApp.with(this.mcontext).load((Object) GlideUtils.generateUrl(this.imgyurl)).apply((BaseRequestOptions<?>) new RequestOptions().placeholder(R.drawable.app_icon)).into(this.pictrueimg);
        this.close.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.v4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16982c.lambda$onCreate$0(view);
            }
        });
        this.saveimg.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.w4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17030c.lambda$onCreate$2(view);
            }
        });
    }

    public CourseWxPopwindow(@NonNull Context context, String imgyurl, String wechat_tips, String wechat_number) {
        super(context);
        this.imgyurl = imgyurl;
        this.mcontext = context;
        this.wechat_tips = wechat_tips;
        this.wechat_number = wechat_number;
    }
}
