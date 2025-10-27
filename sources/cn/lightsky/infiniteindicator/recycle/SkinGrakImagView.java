package cn.lightsky.infiniteindicator.recycle;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.ImageView;
import androidx.annotation.Nullable;

@SuppressLint({"AppCompatCustomView"})
/* loaded from: classes.dex */
public class SkinGrakImagView extends ImageView {
    private Context mContext;
    private int mTheme;

    public SkinGrakImagView(Context context) {
        super(context);
        this.mTheme = 0;
        this.mContext = context;
    }

    public int getmTheme() {
        return this.mTheme;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);
        } catch (Exception unused) {
        }
    }

    public void setmTheme(int i2) {
        this.mTheme = i2;
    }

    public SkinGrakImagView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mTheme = 0;
        this.mContext = context;
    }

    public SkinGrakImagView(Context context, @Nullable AttributeSet attributeSet, int i2) {
        super(context, attributeSet, i2);
        this.mTheme = 0;
        this.mContext = context;
    }
}
