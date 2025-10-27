package com.psychiatrygarden.widget;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.lxj.xpopup.core.BottomPopupView;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

/* loaded from: classes6.dex */
public class CircleEditSavePop extends BottomPopupView implements View.OnClickListener {
    private ClickListenerEditSave clickListenerEditSave;

    public interface ClickListenerEditSave {
        void noSave();

        void save();
    }

    public CircleEditSavePop(@NonNull Context context, ClickListenerEditSave clickListenerEditSave) {
        super(context);
        this.clickListenerEditSave = clickListenerEditSave;
    }

    @Override // com.lxj.xpopup.core.BottomPopupView, com.lxj.xpopup.core.BasePopupView
    public int getImplLayoutId() {
        return R.layout.popu_circle_edit_save;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View v2) {
        int id = v2.getId();
        if (id == R.id.tv_cancel) {
            dismiss();
            return;
        }
        if (id == R.id.tv_no_save) {
            this.clickListenerEditSave.noSave();
            dismiss();
        } else {
            if (id != R.id.tv_save) {
                return;
            }
            this.clickListenerEditSave.save();
            dismiss();
        }
    }

    @Override // com.lxj.xpopup.core.BasePopupView
    public void onCreate() {
        super.onCreate();
        TextView textView = (TextView) findViewById(R.id.tv_save);
        TextView textView2 = (TextView) findViewById(R.id.tv_no_save);
        TextView textView3 = (TextView) findViewById(R.id.tv_cancel);
        textView.setOnClickListener(this);
        textView2.setOnClickListener(this);
        textView3.setOnClickListener(this);
        ((TextView) findViewById(R.id.title)).setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? "#b0b0b0" : "#575F79"));
        textView3.setTextColor(Color.parseColor(SkinManager.getCurrentSkinType(getContext()) == 0 ? "#7f000000" : "#7380A9"));
    }
}
