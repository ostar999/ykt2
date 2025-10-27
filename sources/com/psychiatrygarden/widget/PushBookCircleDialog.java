package com.psychiatrygarden.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.webdemo.com.supporfragment.base.SupportFragment;
import com.psychiatrygarden.bean.CirclrListBean;
import com.psychiatrygarden.bean.PushBookData;
import com.psychiatrygarden.forum.PushCircleAndArticleAct;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes6.dex */
public class PushBookCircleDialog extends Dialog {
    public static final int TWO_BUTTON = 2;
    private OnChoosedLisenter chooseCircleLisenter;
    private Context context;
    private boolean isOutTouchDismiss;
    private boolean isShowFirst;
    private List<String> mChooseCircleIds;
    private List<PushBookData> mChooseCircleItems;
    private ClearEditText mEtContent;
    private SupportFragment[] mFragments;
    private List<String> mLocalSaveIds;
    private TextView mTvCount;
    private RelativeLayout relView;
    private View viewRoot;

    public static abstract class OnChoosedLisenter {
        public abstract void onCircleChoosed(List<String> oldCircleIds, List<String> newCircleIds, List<CirclrListBean.DataBean> mChooseCircleItems);
    }

    public PushBookCircleDialog(Context context, PushCircleAndArticleAct activity, List<PushBookData> chooseList) {
        super(context, R.style.MyDialog);
        this.isOutTouchDismiss = true;
        this.mChooseCircleIds = new ArrayList();
        this.mChooseCircleItems = new ArrayList();
        this.mLocalSaveIds = new ArrayList();
        this.mFragments = new SupportFragment[2];
        this.isShowFirst = true;
        this.context = context;
        this.relView = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.layout_custom_dialog, (ViewGroup) null);
        View viewInflate = LayoutInflater.from(context).inflate(R.layout.dialog_push_book_view, (ViewGroup) null);
        this.viewRoot = viewInflate;
        View viewFindViewById = viewInflate.findViewById(R.id.view_dialog_root);
        this.viewRoot = viewFindViewById;
        TextView textView = (TextView) viewFindViewById.findViewById(R.id.btn_sure);
        this.mTvCount = (TextView) this.viewRoot.findViewById(R.id.tv_count);
        this.mEtContent = (ClearEditText) this.viewRoot.findViewById(R.id.ed_search);
        ImageView imageView = (ImageView) this.viewRoot.findViewById(R.id.btn_cancel);
        setContentView(this.relView);
        this.viewRoot.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
        final RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -2);
        setOnShowListener(new DialogInterface.OnShowListener() { // from class: com.psychiatrygarden.widget.vd
            @Override // android.content.DialogInterface.OnShowListener
            public final void onShow(DialogInterface dialogInterface) {
                this.f16997c.lambda$new$0(layoutParams, dialogInterface);
            }
        });
        final InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        this.viewRoot.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.wd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PushBookCircleDialog.lambda$new$1(view);
            }
        });
        this.relView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.xd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f17095c.lambda$new$2(view);
            }
        });
        this.mEtContent.setOnEditorActionListener(new TextView.OnEditorActionListener() { // from class: com.psychiatrygarden.widget.yd
            @Override // android.widget.TextView.OnEditorActionListener
            public final boolean onEditorAction(TextView textView2, int i2, KeyEvent keyEvent) {
                return this.f17127c.lambda$new$3(inputMethodManager, textView2, i2, keyEvent);
            }
        });
        this.mEtContent.addTextChangedListener(new TextWatcher() { // from class: com.psychiatrygarden.widget.PushBookCircleDialog.1
            @Override // android.text.TextWatcher
            public void afterTextChanged(Editable s2) {
                s2.length();
            }

            @Override // android.text.TextWatcher
            public void beforeTextChanged(CharSequence s2, int start, int count, int after) {
            }

            @Override // android.text.TextWatcher
            public void onTextChanged(CharSequence s2, int start, int before, int count) {
            }
        });
        textView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.zd
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                PushBookCircleDialog.lambda$new$4(view);
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.ae
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16318c.lambda$new$5(view);
            }
        });
        this.mEtContent.requestFocus();
        new Handler().postDelayed(new Runnable() { // from class: com.psychiatrygarden.widget.be
            @Override // java.lang.Runnable
            public final void run() {
                this.f16345c.lambda$new$6(inputMethodManager);
            }
        }, 200L);
    }

    private int getContextRect(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        return rect.height();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0(RelativeLayout.LayoutParams layoutParams, DialogInterface dialogInterface) {
        this.relView.removeAllViews();
        this.relView.addView(this.viewRoot, layoutParams);
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.viewRoot.getLayoutParams();
        layoutParams2.addRule(12);
        this.viewRoot.setLayoutParams(layoutParams2);
        this.viewRoot.setPivotY(0.0f);
        this.viewRoot.setPivotX(((getContext().getResources().getDisplayMetrics().widthPixels * 2.0f) / 3.0f) / 2.0f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$1(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$2(View view) {
        if (this.isOutTouchDismiss) {
            dismiss();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ boolean lambda$new$3(InputMethodManager inputMethodManager, TextView textView, int i2, KeyEvent keyEvent) {
        if (i2 != 3) {
            return false;
        }
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(textView.getApplicationWindowToken(), 0);
        }
        this.mEtContent.getText().toString().trim().equals("");
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$4(View view) {
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$5(View view) {
        dismiss();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$6(InputMethodManager inputMethodManager) {
        inputMethodManager.showSoftInput(this.mEtContent, 1);
    }

    private void setBottomTxtValue(int count) {
        String str = "已推荐" + count + "本书";
        if (this.mTvCount != null) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(str);
            if (SkinManager.getCurrentSkinType(this.context) == 0) {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#DD594A")), 3, str.length() - 1, 34);
            } else {
                spannableStringBuilder.setSpan(new ForegroundColorSpan(Color.parseColor("#B2575C")), 3, str.length() - 1, 34);
            }
            this.mTvCount.setText(spannableStringBuilder);
        }
    }

    public void dismissNoAnimaltion() {
        super.dismiss();
    }

    public void isOutTouchDismiss(boolean isOutTouchDismiss) {
        this.isOutTouchDismiss = isOutTouchDismiss;
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams attributes = getWindow().getAttributes();
        attributes.height = -1;
        attributes.width = -1;
        getWindow().setAttributes(attributes);
    }

    public void setOnChoosedLisenter(OnChoosedLisenter lisenter) {
        this.chooseCircleLisenter = lisenter;
    }
}
