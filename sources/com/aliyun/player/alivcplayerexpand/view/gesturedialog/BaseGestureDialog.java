package com.aliyun.player.alivcplayerexpand.view.gesturedialog;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.aliyun.player.alivcplayerexpand.R;
import com.aliyun.player.aliyunplayerbase.util.AliyunScreenMode;

/* loaded from: classes2.dex */
public class BaseGestureDialog extends PopupWindow {
    private AliyunScreenMode mCurrentScreenMode = AliyunScreenMode.Small;
    private int mDialogWidthAndHeight;
    ImageView mImageView;
    TextView mTextView;

    public BaseGestureDialog(Context context) throws Resources.NotFoundException {
        View viewInflate = ((LayoutInflater) context.getApplicationContext().getSystemService("layout_inflater")).inflate(R.layout.alivc_dialog_gesture, (ViewGroup) null);
        viewInflate.measure(0, 0);
        setContentView(viewInflate);
        this.mTextView = (TextView) viewInflate.findViewById(R.id.gesture_text);
        this.mImageView = (ImageView) viewInflate.findViewById(R.id.gesture_image);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.alivc_player_gesture_dialog_size);
        this.mDialogWidthAndHeight = dimensionPixelSize;
        setWidth(dimensionPixelSize);
        setHeight(this.mDialogWidthAndHeight);
    }

    public void setScreenMode(AliyunScreenMode aliyunScreenMode) {
        this.mCurrentScreenMode = aliyunScreenMode;
    }

    public void show(View view) {
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int right = iArr[0] + (((view.getRight() - view.getLeft()) - this.mDialogWidthAndHeight) / 2);
        int bottom = iArr[1] + (((view.getBottom() - view.getTop()) - this.mDialogWidthAndHeight) / 2);
        if (this.mCurrentScreenMode == AliyunScreenMode.Small) {
            showAtLocation(view, 51, right, bottom);
        } else {
            showAtLocation(view, 17, 0, 0);
        }
    }
}
