package com.ykb.common_share_lib.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.ykb.common_share_lib.R;
import com.ykb.common_share_lib.bean.OnDialogShareClickListener;
import com.ykb.common_share_lib.util.CommonUtil;

/* loaded from: classes4.dex */
public class CommonShareDialog extends AlertDialog implements View.OnClickListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private boolean YKB;
    private TextView btnCancel;
    private final OnDialogShareClickListener clickListener;
    private Context context;
    private LinearLayout layout2;
    private LinearLayout lyShare;
    private boolean themeDay;
    private TextView tvQQ;
    private TextView tvSave;
    private TextView tvSina;
    private TextView tvTitle;
    private TextView tvWxCircle;
    private TextView tvWxFriend;
    private View viewLine;
    private Window window;

    public CommonShareDialog(Context context, OnDialogShareClickListener onDialogShareClickListener) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.YKB = false;
        this.themeDay = true;
        this.context = context;
        this.clickListener = onDialogShareClickListener;
    }

    private void setDayTheme(boolean z2) {
        if (z2) {
            this.lyShare.setBackground(getContext().getDrawable(R.drawable.shape_white_top_16));
            TextView textView = this.tvTitle;
            Resources resources = getContext().getResources();
            int i2 = R.color.first_txt_color;
            textView.setTextColor(resources.getColor(i2, null));
            this.btnCancel.setTextColor(getContext().getResources().getColor(i2, null));
            this.viewLine.setBackgroundColor(getContext().getResources().getColor(R.color.new_gray_line_color, null));
            TextView textView2 = this.tvSave;
            Resources resources2 = getContext().getResources();
            int i3 = R.color.secondary_text_color;
            textView2.setTextColor(resources2.getColor(i3, null));
            this.tvWxFriend.setTextColor(getContext().getResources().getColor(i3, null));
            this.tvWxCircle.setTextColor(getContext().getResources().getColor(i3, null));
            this.tvQQ.setTextColor(getContext().getResources().getColor(i3, null));
            this.tvSina.setTextColor(getContext().getResources().getColor(i3, null));
            return;
        }
        this.lyShare.setBackground(getContext().getDrawable(R.drawable.shape_white_top_night_16));
        TextView textView3 = this.tvTitle;
        Resources resources3 = getContext().getResources();
        int i4 = R.color.first_txt_color_night;
        textView3.setTextColor(resources3.getColor(i4, null));
        this.btnCancel.setTextColor(getContext().getResources().getColor(i4, null));
        this.viewLine.setBackgroundColor(getContext().getResources().getColor(R.color.new_gray_line_color_night, null));
        TextView textView4 = this.tvSave;
        Resources resources4 = getContext().getResources();
        int i5 = R.color.secondary_text_color_night;
        textView4.setTextColor(resources4.getColor(i5, null));
        this.tvWxFriend.setTextColor(getContext().getResources().getColor(i5, null));
        this.tvWxCircle.setTextColor(getContext().getResources().getColor(i5, null));
        this.tvQQ.setTextColor(getContext().getResources().getColor(i5, null));
        this.tvSina.setTextColor(getContext().getResources().getColor(i5, null));
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.btn_wechat) {
            this.clickListener.shareClick(0);
            dismiss();
            return;
        }
        if (id == R.id.btn_wxcircle) {
            this.clickListener.shareClick(1);
            dismiss();
            return;
        }
        if (id == R.id.btn_qq) {
            this.clickListener.shareClick(2);
            dismiss();
            return;
        }
        if (id == R.id.btn_sina) {
            if (this.YKB) {
                this.clickListener.shareClick(3);
            }
            dismiss();
        } else if (id == R.id.btn_save) {
            this.clickListener.shareClick(4);
            dismiss();
        } else if (id == R.id.btn_cancel) {
            dismiss();
        }
    }

    @Override // androidx.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, androidx.activity.ComponentDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_share_new);
        Window window = getWindow();
        this.window = window;
        window.setWindowAnimations(R.style.popupAnimation);
        this.window.setGravity(80);
        this.window.setLayout(CommonUtil.getScreenWidth(this.context), -2);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.lyShare = (LinearLayout) findViewById(R.id.ly_share);
        this.tvTitle = (TextView) findViewById(R.id.tvTitle);
        this.tvSave = (TextView) findViewById(R.id.tvSave);
        this.tvWxFriend = (TextView) findViewById(R.id.tvWxFriend);
        this.tvWxCircle = (TextView) findViewById(R.id.tvWxCircle);
        this.tvQQ = (TextView) findViewById(R.id.tvQQ);
        this.tvSina = (TextView) findViewById(R.id.tvSina);
        this.btnCancel = (TextView) findViewById(R.id.btn_cancel);
        this.viewLine = findViewById(R.id.viewLine);
        this.layout2 = (LinearLayout) findViewById(R.id.layout2);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.btn_wechat);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.btn_wxcircle);
        LinearLayout linearLayout3 = (LinearLayout) findViewById(R.id.btn_qq);
        LinearLayout linearLayout4 = (LinearLayout) findViewById(R.id.btn_save);
        LinearLayout linearLayout5 = (LinearLayout) findViewById(R.id.btn_sina);
        if (linearLayout != null && linearLayout2 != null && linearLayout3 != null && linearLayout4 != null) {
            linearLayout.setOnClickListener(this);
            linearLayout2.setOnClickListener(this);
            linearLayout3.setOnClickListener(this);
            linearLayout4.setOnClickListener(this);
        }
        if (!this.YKB) {
            this.layout2.setVisibility(8);
        } else if (linearLayout5 != null) {
            linearLayout5.setOnClickListener(this);
        }
        this.btnCancel.setOnClickListener(this);
        setDayTheme(this.themeDay);
    }

    public CommonShareDialog(Context context, boolean z2, boolean z3, OnDialogShareClickListener onDialogShareClickListener) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.context = context;
        this.clickListener = onDialogShareClickListener;
        this.themeDay = z2;
        this.YKB = z3;
    }
}
