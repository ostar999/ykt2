package com.umeng.socialize.shareboard;

import android.content.Context;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import com.alipay.sdk.util.h;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.umeng.socialize.utils.SocializeSpUtils;
import java.util.List;

/* loaded from: classes6.dex */
public class ShareBoard extends PopupWindow {
    private ShareBoardConfig mShareBoardConfig;

    public ShareBoard(Context context, List<SnsPlatform> list) {
        this(context, list, null);
    }

    private void saveShareboardConfig(Context context, ShareBoardConfig shareBoardConfig) {
        if (context == null || shareBoardConfig == null) {
            return;
        }
        String str = shareBoardConfig.mShareboardPosition == ShareBoardConfig.SHAREBOARD_POSITION_BOTTOM ? "0" : "1";
        int i2 = shareBoardConfig.mMenuBgShape;
        String str2 = i2 != ShareBoardConfig.BG_SHAPE_NONE ? i2 == ShareBoardConfig.BG_SHAPE_CIRCULAR ? "1" : i2 == ShareBoardConfig.BG_SHAPE_ROUNDED_SQUARE ? shareBoardConfig.mMenuBgShapeAngle != 0 ? "2" : "3" : null : "0";
        if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            return;
        }
        SocializeSpUtils.putShareBoardConfig(context, str2 + h.f3376b + str);
    }

    public void setShareBoardlistener(final ShareBoardlistener shareBoardlistener) {
        if (this.mShareBoardConfig == null) {
            return;
        }
        this.mShareBoardConfig.setShareBoardlistener(new ShareBoardlistener() { // from class: com.umeng.socialize.shareboard.ShareBoard.3
            @Override // com.umeng.socialize.utils.ShareBoardlistener
            public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
                ShareBoard.this.setOnDismissListener(null);
                ShareBoard.this.dismiss();
                ShareBoardlistener shareBoardlistener2 = shareBoardlistener;
                if (shareBoardlistener2 != null) {
                    shareBoardlistener2.onclick(snsPlatform, share_media);
                }
            }
        });
    }

    public ShareBoard(Context context, List<SnsPlatform> list, ShareBoardConfig shareBoardConfig) throws Resources.NotFoundException {
        super(context);
        setWindowLayoutMode(-1, -1);
        boolean z2 = context.getResources().getConfiguration().orientation == 2;
        shareBoardConfig = shareBoardConfig == null ? new ShareBoardConfig() : shareBoardConfig;
        this.mShareBoardConfig = shareBoardConfig;
        shareBoardConfig.setOrientation(z2);
        UMActionFrame uMActionFrame = new UMActionFrame(context);
        uMActionFrame.setSnsPlatformData(list, shareBoardConfig);
        uMActionFrame.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        uMActionFrame.setDismissListener(new PopupWindow.OnDismissListener() { // from class: com.umeng.socialize.shareboard.ShareBoard.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                ShareBoard.this.dismiss();
            }
        });
        setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: com.umeng.socialize.shareboard.ShareBoard.2
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                PopupWindow.OnDismissListener onDismissListener = ShareBoard.this.mShareBoardConfig != null ? ShareBoard.this.mShareBoardConfig.getOnDismissListener() : null;
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });
        setContentView(uMActionFrame);
        setFocusable(true);
        saveShareboardConfig(context, shareBoardConfig);
    }
}
