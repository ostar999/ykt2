package com.umeng.socialize.shareboard;

import android.graphics.Color;
import android.text.TextUtils;
import android.widget.PopupWindow;
import com.plv.socket.user.PLVAuthorizationBean;
import com.umeng.socialize.utils.ShareBoardlistener;

/* loaded from: classes6.dex */
public class ShareBoardConfig {
    public static int BG_SHAPE_CIRCULAR = 1;
    public static int BG_SHAPE_NONE = 0;
    public static int BG_SHAPE_ROUNDED_SQUARE = 2;
    static final int CANCEL_BTN_HEIGHT = 50;
    static final int CANCEL_BTN_TEXT_SIZE_IN_SP = 15;
    static final int CENTER_MENU_LEFT_PADDING = 36;
    static final int INDICATOR_BOTTOM_MARGIN = 20;
    static final int INDICATOR_SIZE = 3;
    static final int INDICATOR_SPACE = 5;
    private static final int MENU_COLUMN_NUM = 4;
    private static final int MENU_COLUMN_NUM_CENTER = 3;
    private static final int MENU_COLUMN_NUM_HORIZONTAL = 6;
    private static final int MENU_COLUMN_NUM_HORIZONTAL_CENTER = 5;
    static final int MENU_ROW_MARGIN = 20;
    static final int MENU_ROW_NUM = 2;
    static final int MENU_TOP_MARGIN = 20;
    public static int SHAREBOARD_POSITION_BOTTOM = 3;
    public static int SHAREBOARD_POSITION_CENTER = 2;
    static int SHAREBOARD_POSITION_TOP = 1;
    static final int TITLE_TEXT_SIZE_IN_SP = 16;
    static final int TITLE_TOP_MARGIN = 20;
    static final int VIEW_PAGER_LEFT_MARGIN = 10;
    int mCancelBtnBgColor;
    int mCancelBtnBgPressedColor;
    int mCancelBtnColor;
    String mCancelBtnText;
    boolean mCancelBtnVisibility;
    int mIndicatorNormalColor;
    int mIndicatorSelectedColor;
    boolean mIndicatorVisibility;
    int mMenuBgColor;
    int mMenuBgPressedColor;
    int mMenuBgShape;
    int mMenuBgShapeAngle;
    int mMenuColumnNum;
    int mMenuIconPressedColor;
    int mMenuTextColor;
    private PopupWindow.OnDismissListener mOnDismissListener;
    private ShareBoardlistener mShareBoardlistener;
    int mShareboardBgColor;
    int mShareboardPosition;
    String mTitleText;
    int mTitleTextColor;
    boolean mTitleVisibility;
    int mTopMargin;

    public ShareBoardConfig() {
        setDefaultValue();
    }

    private void setDefaultValue() {
        int color = Color.parseColor("#575A5C");
        setShareboardBackgroundColor(Color.parseColor("#E9EFF2"));
        setShareboardPostion(SHAREBOARD_POSITION_BOTTOM);
        setTitleText("选择要分享到的平台");
        setTitleTextColor(color);
        setMenuItemBackgroundShape(BG_SHAPE_ROUNDED_SQUARE, 5);
        setMenuItemBackgroundColor(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT), Color.parseColor("#22000000"));
        setMenuItemIconPressedColor(Color.parseColor("#22000000"));
        setMenuItemTextColor(color);
        setCancelButtonText("取消分享");
        setCancelButtonTextColor(color);
        setCancelButtonBackground(Color.parseColor(PLVAuthorizationBean.FCOLOR_DEFAULT), Color.parseColor("#22000000"));
        setIndicatorColor(Color.parseColor("#C2C9CC"), Color.parseColor("#0086DC"));
    }

    public int calculateMenuHeightInDp(int i2) {
        int i3 = i2 <= this.mMenuColumnNum ? 1 : 2;
        return (i3 * 75) + ((i3 - 1) * 20) + 20;
    }

    public PopupWindow.OnDismissListener getOnDismissListener() {
        return this.mOnDismissListener;
    }

    public ShareBoardlistener getShareBoardlistener() {
        return this.mShareBoardlistener;
    }

    public ShareBoardConfig setCancelButtonBackground(int i2) {
        setCancelButtonBackground(i2, 0);
        return this;
    }

    public ShareBoardConfig setCancelButtonText(String str) {
        if (TextUtils.isEmpty(str)) {
            setCancelButtonVisibility(false);
        } else {
            setCancelButtonVisibility(true);
            this.mCancelBtnText = str;
        }
        return this;
    }

    public ShareBoardConfig setCancelButtonTextColor(int i2) {
        this.mCancelBtnColor = i2;
        return this;
    }

    public ShareBoardConfig setCancelButtonVisibility(boolean z2) {
        this.mCancelBtnVisibility = z2;
        return this;
    }

    public ShareBoardConfig setIndicatorColor(int i2) {
        setIndicatorColor(i2, 0);
        return this;
    }

    public ShareBoardConfig setIndicatorVisibility(boolean z2) {
        this.mIndicatorVisibility = z2;
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundColor(int i2) {
        setMenuItemBackgroundColor(i2, 0);
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundShape(int i2) {
        setMenuItemBackgroundShape(i2, 0);
        return this;
    }

    public ShareBoardConfig setMenuItemIconPressedColor(int i2) {
        this.mMenuIconPressedColor = i2;
        return this;
    }

    public ShareBoardConfig setMenuItemTextColor(int i2) {
        this.mMenuTextColor = i2;
        return this;
    }

    public ShareBoardConfig setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
        return this;
    }

    public void setOrientation(boolean z2) {
        if (z2) {
            int i2 = this.mShareboardPosition;
            if (i2 == SHAREBOARD_POSITION_BOTTOM) {
                this.mMenuColumnNum = 6;
                return;
            } else {
                if (i2 == SHAREBOARD_POSITION_CENTER) {
                    this.mMenuColumnNum = 5;
                    return;
                }
                return;
            }
        }
        int i3 = this.mShareboardPosition;
        if (i3 == SHAREBOARD_POSITION_BOTTOM) {
            this.mMenuColumnNum = 4;
        } else if (i3 == SHAREBOARD_POSITION_CENTER) {
            this.mMenuColumnNum = 3;
        }
    }

    public void setShareBoardlistener(ShareBoardlistener shareBoardlistener) {
        this.mShareBoardlistener = shareBoardlistener;
    }

    public ShareBoardConfig setShareboardBackgroundColor(int i2) {
        this.mShareboardBgColor = i2;
        return this;
    }

    public ShareBoardConfig setShareboardPostion(int i2) {
        int i3 = SHAREBOARD_POSITION_BOTTOM;
        if (i2 != i3 && i2 != SHAREBOARD_POSITION_CENTER && i2 != SHAREBOARD_POSITION_TOP) {
            i2 = i3;
        }
        this.mShareboardPosition = i2;
        return this;
    }

    public ShareBoardConfig setStatusBarHeight(int i2) {
        this.mTopMargin = i2;
        return this;
    }

    public ShareBoardConfig setTitleText(String str) {
        if (TextUtils.isEmpty(str)) {
            setTitleVisibility(false);
        } else {
            setTitleVisibility(true);
            this.mTitleText = str;
        }
        return this;
    }

    public ShareBoardConfig setTitleTextColor(int i2) {
        this.mTitleTextColor = i2;
        return this;
    }

    public ShareBoardConfig setTitleVisibility(boolean z2) {
        this.mTitleVisibility = z2;
        return this;
    }

    public ShareBoardConfig setCancelButtonBackground(int i2, int i3) {
        this.mCancelBtnBgColor = i2;
        this.mCancelBtnBgPressedColor = i3;
        return this;
    }

    public ShareBoardConfig setIndicatorColor(int i2, int i3) {
        if (i2 != 0) {
            this.mIndicatorNormalColor = i2;
        }
        if (i3 != 0) {
            this.mIndicatorSelectedColor = i3;
        }
        setIndicatorVisibility(true);
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundColor(int i2, int i3) {
        this.mMenuBgColor = i2;
        this.mMenuBgPressedColor = i3;
        return this;
    }

    public ShareBoardConfig setMenuItemBackgroundShape(int i2, int i3) {
        if (i2 != BG_SHAPE_CIRCULAR && i2 != BG_SHAPE_ROUNDED_SQUARE) {
            i2 = BG_SHAPE_NONE;
        }
        this.mMenuBgShape = i2;
        this.mMenuBgShapeAngle = i3;
        return this;
    }
}
