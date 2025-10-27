package com.psychiatrygarden.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import com.psychiatrygarden.utils.CommonUtil;
import com.psychiatrygarden.utils.ConstantUtil;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class DialogAttrShare extends AlertDialog {
    private Context context;
    private callBack mCallBack;
    private boolean mChangeFont;
    private int mFontIndex;
    private LinearLayout mFontP;
    private SliderFont mFontSlider;
    private float mLastX;
    private LinearLayout quxiao;
    private Window window;

    public interface callBack {
        void setFontSize(float font, int index);
    }

    public DialogAttrShare(Context context) {
        super(context, R.style.MyDialog);
        this.window = null;
        this.mChangeFont = false;
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$onCreate$0(View view) {
        dismiss();
    }

    @Override // android.app.AlertDialog, android.app.Dialog
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zitishezhi);
        Window window = getWindow();
        this.window = window;
        window.setGravity(80);
        int screenWidth = CommonUtil.getScreenWidth(this.context);
        this.window.setLayout(screenWidth, -2);
        this.mFontSlider = (SliderFont) findViewById(R.id.seekbar_font);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.colse);
        this.quxiao = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.psychiatrygarden.widget.d7
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f16399c.lambda$onCreate$0(view);
            }
        });
        this.mFontP = (LinearLayout) findViewById(R.id.ll_font);
        this.mFontSlider.setScreemWidth(screenWidth - 20);
        this.mLastX = screenWidth / 2.0f;
        this.mFontIndex = ConstantUtil.mFontIndex;
    }

    @Override // android.app.Dialog
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        float x2 = event.getX();
        float y2 = event.getY();
        if (event.getAction() == 2) {
            if ((y2 > this.mFontP.getY() && y2 < this.mFontP.getY() + this.mFontP.getHeight() + 30.0f) || this.mChangeFont) {
                this.mChangeFont = true;
                this.mFontSlider.move(x2 - this.mLastX);
                this.mLastX = x2;
                this.mFontSlider.invalidate();
            }
        } else if (event.getAction() == 0) {
            if (y2 > this.mFontP.getY() && y2 < this.mFontP.getY() + this.mFontP.getHeight()) {
                this.mChangeFont = true;
                this.mFontSlider.setCenter(x2);
                this.mLastX = x2;
                this.mFontSlider.invalidate();
            }
        } else if (event.getAction() == 1) {
            if (this.mChangeFont) {
                int iAdJustCenter = this.mFontSlider.adJustCenter(x2);
                this.mFontIndex = iAdJustCenter;
                this.mCallBack.setFontSize(this.mFontSlider.getFontSize(iAdJustCenter), this.mFontIndex);
                this.mLastX = x2;
            }
            this.mChangeFont = false;
        }
        return true;
    }

    public void setCallback(callBack callback) {
        this.mCallBack = callback;
    }
}
