package com.ykb.common_share_lib.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.viewpager2.widget.ViewPager2;
import com.ykb.common_share_lib.R;
import com.ykb.common_share_lib.bean.OnDialogShareClickListener;
import com.ykb.common_share_lib.util.CommonUtil;
import java.util.List;

/* loaded from: classes4.dex */
public class ShowPicDialog extends AlertDialog {
    private OnDialogShareClickListener clickListener;
    private Activity context;
    private int mCurrentPos;
    private ImageView mImgClose;
    private List<String> mPicList;
    private ViewPager2 mPicViewPager;
    private TextView mTvNumber;
    private Window window;

    public ShowPicDialog(Activity activity, OnDialogShareClickListener onDialogShareClickListener) {
        super(activity, R.style.MyDialog);
        this.window = null;
        this.context = activity;
        this.clickListener = onDialogShareClickListener;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.dialog_show_pic);
        Window window = getWindow();
        this.window = window;
        window.setGravity(80);
        this.window.setLayout(CommonUtil.getScreenWidth(this.context), -1);
        setCanceledOnTouchOutside(true);
        setCancelable(true);
        this.mTvNumber = (TextView) findViewById(R.id.tv_number);
        this.mImgClose = (ImageView) findViewById(R.id.img_close);
        this.mPicViewPager = (ViewPager2) findViewById(R.id.pic_pager);
        this.mTvNumber.setText(this.mCurrentPos + "/" + this.mPicList.size());
        this.mImgClose.setOnClickListener(new View.OnClickListener() { // from class: com.ykb.common_share_lib.view.a
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f26091c.lambda$onCreate$0(view);
            }
        });
    }

    public ShowPicDialog(Activity activity, int i2, List<String> list, OnDialogShareClickListener onDialogShareClickListener) {
        super(activity, R.style.MyDialog);
        this.window = null;
        this.context = activity;
        this.clickListener = onDialogShareClickListener;
        this.mCurrentPos = i2;
        this.mPicList = list;
    }
}
