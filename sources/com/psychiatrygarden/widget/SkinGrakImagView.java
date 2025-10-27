package com.psychiatrygarden.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.psychiatrygarden.utils.SkinManager;
import com.yikaobang.yixue.R;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes6.dex */
public class SkinGrakImagView extends ImageView {
    private Context mContext;

    public SkinGrakImagView(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (SkinManager.getCurrentSkinType(this.mContext) == 1) {
            canvas.drawColor(this.mContext.getResources().getColor(R.color.tran_img_color));
        }
    }

    public SkinGrakImagView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
    }

    public SkinGrakImagView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
    }
}
