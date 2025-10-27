package top.defaults.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/* loaded from: classes9.dex */
public class BottomFullWidthDialog extends Dialog {
    private Context context;
    private float heightPercentage;

    public BottomFullWidthDialog(Context context, float f2) {
        this(context, f2, R.style.BottomFullWidthDialog);
    }

    @Override // android.app.Dialog
    public void setContentView(int i2) {
        super.setContentView(i2);
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = this.context.getResources().getDisplayMetrics().widthPixels;
            int i3 = (int) (r1.heightPixels * this.heightPercentage);
            if (i3 <= 0) {
                i3 = -2;
            }
            attributes.height = i3;
            attributes.gravity = 80;
            window.setAttributes(attributes);
        }
    }

    private BottomFullWidthDialog(Context context, float f2, int i2) {
        super(context, i2);
        this.context = context;
        this.heightPercentage = f2;
    }
}
