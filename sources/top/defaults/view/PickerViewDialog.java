package top.defaults.view;

import android.content.Context;
import android.view.ViewStub;

/* loaded from: classes9.dex */
public class PickerViewDialog extends BottomFullWidthDialog {
    public PickerViewDialog(Context context) {
        super(context, 0.0f);
        super.setContentView(R.layout.top_defaults_view_pickerview_dialog);
    }

    @Override // top.defaults.view.BottomFullWidthDialog, android.app.Dialog
    public void setContentView(int i2) {
        ViewStub viewStub = (ViewStub) findViewById(R.id.content);
        viewStub.setLayoutResource(i2);
        viewStub.inflate();
    }
}
