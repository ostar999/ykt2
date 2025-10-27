package com.ykb.common_share_lib.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.ykb.common_share_lib.CommonConfig;
import com.ykb.common_share_lib.R;
import com.ykb.common_share_lib.bean.ClickType;
import com.ykb.common_share_lib.bean.OnDialogShareClickListener;
import com.ykb.common_share_lib.util.CommonUtil;
import com.ykb.common_share_lib.util.ShareUtils;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public class CommonShareDialog2 extends AlertDialog implements View.OnClickListener {
    private OnDialogShareClickListener clickListener;
    private Integer clickType;
    private Activity context;
    private boolean dayTheme;
    private TextView dialog_share_btn_close;
    private boolean flag;
    private boolean isYkb;
    private LinearLayout layoutBottom;
    private LinearLayout layoutRoot;
    private String shareContent;
    private String shareTitle;
    private String shareUrl;
    private TextView tvQQ;
    private TextView tvSina;
    private TextView tvWxCircle;
    private TextView tvWxFriend;
    private final UMShareListener umShareListener;
    private View view_line;
    private Window window;
    private TextView yejianmoshi;
    private TextView zitishezhi;

    public CommonShareDialog2(Activity activity, OnDialogShareClickListener onDialogShareClickListener) {
        super(activity, R.style.MyDialog);
        this.window = null;
        this.isYkb = true;
        this.dayTheme = true;
        this.umShareListener = new UMShareListener() { // from class: com.ykb.common_share_lib.view.CommonShareDialog2.1
            @Override // com.umeng.socialize.UMShareListener
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(CommonShareDialog2.this.context, "用户取消分享", 0).show();
                if (CommonShareDialog2.this.clickListener != null) {
                    CommonShareDialog2.this.clickListener.shareCallBack(CommonShareDialog2.this.clickType, false);
                }
                CommonShareDialog2.this.dismiss();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onError(SHARE_MEDIA share_media, Throwable th) {
                if (CommonShareDialog2.this.clickListener != null) {
                    CommonShareDialog2.this.clickListener.shareCallBack(CommonShareDialog2.this.clickType, false);
                }
                CommonShareDialog2.this.dismiss();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(CommonShareDialog2.this.context, "分享成功", 0).show();
                if (CommonShareDialog2.this.clickListener != null) {
                    CommonShareDialog2.this.clickListener.shareCallBack(CommonShareDialog2.this.clickType, true);
                }
                CommonShareDialog2.this.dismiss();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onStart(SHARE_MEDIA share_media) {
            }
        };
        this.context = activity;
        this.clickListener = onDialogShareClickListener;
    }

    public static List<SnsPlatform> initPlatforms() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(SHARE_MEDIA.WEIXIN.toSnsPlatform());
        arrayList.add(SHARE_MEDIA.WEIXIN_CIRCLE.toSnsPlatform());
        arrayList.add(SHARE_MEDIA.QQ.toSnsPlatform());
        arrayList.add(SHARE_MEDIA.SINA.toSnsPlatform());
        arrayList.add(SHARE_MEDIA.QZONE.toSnsPlatform());
        arrayList.add(SHARE_MEDIA.TENCENT.toSnsPlatform());
        arrayList.add(SHARE_MEDIA.WEIXIN_FAVORITE.toSnsPlatform());
        return arrayList;
    }

    private void setDayTheme(boolean z2) {
        if (z2) {
            LinearLayout linearLayout = this.layoutRoot;
            Context context = getContext();
            int i2 = R.drawable.bg_white_round_conner;
            linearLayout.setBackground(context.getDrawable(i2));
            this.layoutBottom.setBackground(getContext().getDrawable(i2));
            TextView textView = this.tvWxFriend;
            Resources resources = getContext().getResources();
            int i3 = R.color.secondary_text_color;
            textView.setTextColor(resources.getColor(i3, null));
            this.tvWxCircle.setTextColor(getContext().getResources().getColor(i3, null));
            this.tvQQ.setTextColor(getContext().getResources().getColor(i3, null));
            this.tvSina.setTextColor(getContext().getResources().getColor(i3, null));
            TextView textView2 = this.yejianmoshi;
            Resources resources2 = getContext().getResources();
            int i4 = R.color.question_color;
            textView2.setTextColor(resources2.getColor(i4, null));
            this.dialog_share_btn_close.setTextColor(getContext().getResources().getColor(i4, null));
            this.yejianmoshi.setCompoundDrawables(null, getContext().getDrawable(R.drawable.yejianmoshi), null, null);
            this.zitishezhi.setTextColor(getContext().getResources().getColor(i3, null));
            Drawable drawable = getContext().getDrawable(R.drawable.zitishezhi);
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getMinimumHeight());
            this.zitishezhi.setCompoundDrawables(null, drawable, null, null);
            this.view_line.setBackgroundColor(getContext().getResources().getColor(R.color.divider, null));
            return;
        }
        LinearLayout linearLayout2 = this.layoutRoot;
        Context context2 = getContext();
        int i5 = R.drawable.bg_white_round_conner_night;
        linearLayout2.setBackground(context2.getDrawable(i5));
        this.layoutBottom.setBackground(getContext().getDrawable(i5));
        TextView textView3 = this.tvWxFriend;
        Resources resources3 = getContext().getResources();
        int i6 = R.color.secondary_text_color_night;
        textView3.setTextColor(resources3.getColor(i6, null));
        this.tvWxCircle.setTextColor(getContext().getResources().getColor(i6, null));
        this.tvQQ.setTextColor(getContext().getResources().getColor(i6, null));
        this.tvSina.setTextColor(getContext().getResources().getColor(i6, null));
        TextView textView4 = this.yejianmoshi;
        Resources resources4 = getContext().getResources();
        int i7 = R.color.question_color_night;
        textView4.setTextColor(resources4.getColor(i7, null));
        this.dialog_share_btn_close.setTextColor(getContext().getResources().getColor(i7, null));
        this.yejianmoshi.setCompoundDrawables(null, getContext().getDrawable(R.drawable.yejianmoshi_night), null, null);
        this.zitishezhi.setTextColor(getContext().getResources().getColor(i6, null));
        Drawable drawable2 = getContext().getDrawable(R.drawable.zitishezhi_night);
        drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), drawable2.getMinimumHeight());
        this.zitishezhi.setCompoundDrawables(null, drawable2, null, null);
        this.view_line.setBackgroundColor(getContext().getResources().getColor(R.color.divider_night, null));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.dialog_share_btn_wechat) {
            this.clickListener.shareClick(0);
            dismiss();
            this.clickType = 0;
            String str = this.shareUrl;
            ShareUtils.shareAppPicControl(str, str, this.shareContent, this.context, ClickType.WX_FRIEND, this.umShareListener);
            return;
        }
        if (id == R.id.dialog_share_btn_wxcircle) {
            this.clickListener.shareClick(1);
            this.clickType = 1;
            String str2 = this.shareUrl;
            ShareUtils.shareAppPicControl(str2, str2, this.shareContent, this.context, ClickType.WX_CIRCLE, this.umShareListener);
            dismiss();
            return;
        }
        if (id == R.id.dialog_share_btn_qq) {
            this.clickListener.shareClick(2);
            this.clickType = 2;
            String str3 = this.shareUrl;
            ShareUtils.shareAppPicControl(str3, str3, this.shareContent, this.context, ClickType.QQ, this.umShareListener);
            dismiss();
            return;
        }
        if (id == R.id.dialog_share_btn_wb) {
            if (this.isYkb) {
                this.clickType = 3;
                this.clickListener.shareClick(3);
                String str4 = this.shareUrl;
                ShareUtils.shareAppPicControl(str4, str4, this.shareContent, this.context, ClickType.SINA, this.umShareListener);
            }
            dismiss();
            return;
        }
        if (id == R.id.yejianmoshi) {
            this.clickListener.shareClick(4);
            dismiss();
        } else if (id == R.id.zitishezhi) {
            this.clickListener.shareClick(5);
            dismiss();
        } else if (id == R.id.dialog_share_btn_close) {
            dismiss();
        }
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_share2);
        Window window = getWindow();
        this.window = window;
        window.setGravity(80);
        this.window.setLayout(CommonUtil.getScreenWidth(this.context), -2);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.layoutRoot = (LinearLayout) findViewById(R.id.layoutRoot);
        this.tvWxFriend = (TextView) findViewById(R.id.tvWxFriend);
        this.tvWxCircle = (TextView) findViewById(R.id.tvWxCircle);
        this.tvQQ = (TextView) findViewById(R.id.tvQQ);
        this.tvSina = (TextView) findViewById(R.id.tvSina);
        this.yejianmoshi = (TextView) findViewById(R.id.yejianmoshi);
        this.zitishezhi = (TextView) findViewById(R.id.zitishezhi);
        this.layoutBottom = (LinearLayout) findViewById(R.id.layoutBottom);
        int i2 = R.id.view_line;
        this.view_line = findViewById(i2);
        int i3 = R.id.dialog_share_btn_close;
        this.dialog_share_btn_close = (TextView) findViewById(i3);
        findViewById(R.id.dialog_share_btn_wxcircle).setOnClickListener(this);
        findViewById(R.id.dialog_share_btn_qq).setOnClickListener(this);
        findViewById(R.id.dialog_share_btn_wechat).setOnClickListener(this);
        if (this.isYkb) {
            findViewById(R.id.dialog_share_btn_wb).setOnClickListener(this);
        } else {
            findViewById(R.id.dialog_share_btn_wb).setVisibility(8);
        }
        findViewById(i3).setOnClickListener(this);
        this.yejianmoshi.setOnClickListener(this);
        this.zitishezhi.setOnClickListener(this);
        if (this.flag) {
            findViewById(i2).setVisibility(0);
            findViewById(R.id.line_two).setVisibility(0);
        } else {
            findViewById(i2).setVisibility(8);
            findViewById(R.id.line_two).setVisibility(8);
        }
        if (this.dayTheme) {
            this.yejianmoshi.setText("日间模式");
        } else {
            this.yejianmoshi.setText("夜间模式");
        }
        setDayTheme(this.dayTheme);
    }

    public CommonShareDialog2 setShareContent(String str, String str2, String str3) {
        this.shareUrl = str;
        this.shareTitle = str2;
        this.shareContent = str3;
        return this;
    }

    public CommonShareDialog2(Activity activity, boolean z2, boolean z3, OnDialogShareClickListener onDialogShareClickListener) {
        super(activity, R.style.MyDialog);
        this.window = null;
        this.isYkb = true;
        this.dayTheme = true;
        this.umShareListener = new UMShareListener() { // from class: com.ykb.common_share_lib.view.CommonShareDialog2.1
            @Override // com.umeng.socialize.UMShareListener
            public void onCancel(SHARE_MEDIA share_media) {
                Toast.makeText(CommonShareDialog2.this.context, "用户取消分享", 0).show();
                if (CommonShareDialog2.this.clickListener != null) {
                    CommonShareDialog2.this.clickListener.shareCallBack(CommonShareDialog2.this.clickType, false);
                }
                CommonShareDialog2.this.dismiss();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onError(SHARE_MEDIA share_media, Throwable th) {
                if (CommonShareDialog2.this.clickListener != null) {
                    CommonShareDialog2.this.clickListener.shareCallBack(CommonShareDialog2.this.clickType, false);
                }
                CommonShareDialog2.this.dismiss();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onResult(SHARE_MEDIA share_media) {
                Toast.makeText(CommonShareDialog2.this.context, "分享成功", 0).show();
                if (CommonShareDialog2.this.clickListener != null) {
                    CommonShareDialog2.this.clickListener.shareCallBack(CommonShareDialog2.this.clickType, true);
                }
                CommonShareDialog2.this.dismiss();
            }

            @Override // com.umeng.socialize.UMShareListener
            public void onStart(SHARE_MEDIA share_media) {
            }
        };
        this.context = activity;
        this.clickListener = onDialogShareClickListener;
        this.flag = z2;
        this.dayTheme = z3;
        this.isYkb = CommonConfig.INSTANCE.getYI_KAO_BANG();
    }
}
