package com.lxj.xpopup;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.RequiresApi;
import com.lxj.xpopup.animator.PopupAnimator;
import com.lxj.xpopup.core.AttachPopupView;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.core.BottomPopupView;
import com.lxj.xpopup.core.CenterPopupView;
import com.lxj.xpopup.core.ImageViewerPopupView;
import com.lxj.xpopup.core.PopupInfo;
import com.lxj.xpopup.core.PositionPopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.enums.PopupPosition;
import com.lxj.xpopup.enums.PopupType;
import com.lxj.xpopup.impl.AttachListPopupView;
import com.lxj.xpopup.impl.BottomListPopupView;
import com.lxj.xpopup.impl.CenterListPopupView;
import com.lxj.xpopup.impl.ConfirmPopupView;
import com.lxj.xpopup.impl.InputConfirmPopupView;
import com.lxj.xpopup.impl.LoadingPopupView;
import com.lxj.xpopup.interfaces.OnCancelListener;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.lxj.xpopup.interfaces.OnImageViewerLongPressListener;
import com.lxj.xpopup.interfaces.OnInputConfirmListener;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.lxj.xpopup.interfaces.OnSrcViewUpdateListener;
import com.lxj.xpopup.interfaces.XPopupCallback;
import com.lxj.xpopup.interfaces.XPopupImageLoader;
import com.lxj.xpopup.util.XPermission;
import java.util.List;

/* loaded from: classes4.dex */
public class XPopup {
    private static int primaryColor = Color.parseColor("#121212");
    private static int animationDuration = 300;
    private static int statusBarBgColor = Color.parseColor("#55000000");
    private static int navigationBarColor = 0;
    private static int shadowBgColor = Color.parseColor("#7F000000");
    public static PointF longClickPoint = null;

    private XPopup() {
    }

    public static void fixLongClick(View view) {
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.lxj.xpopup.XPopup.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    XPopup.longClickPoint = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                }
                if ("xpopup".equals(view2.getTag()) && motionEvent.getAction() == 2) {
                    view2.getParent().requestDisallowInterceptTouchEvent(true);
                }
                if (motionEvent.getAction() == 1) {
                    view2.getParent().requestDisallowInterceptTouchEvent(false);
                    view2.setTag(null);
                }
                return false;
            }
        });
        view.setTag("xpopup");
    }

    public static int getAnimationDuration() {
        return animationDuration;
    }

    public static int getNavigationBarColor() {
        return navigationBarColor;
    }

    public static int getPrimaryColor() {
        return primaryColor;
    }

    public static int getShadowBgColor() {
        return shadowBgColor;
    }

    public static int getStatusBarBgColor() {
        return statusBarBgColor;
    }

    @RequiresApi(api = 23)
    public static void requestOverlayPermission(Context context, XPermission.SimpleCallback simpleCallback) {
        XPermission.create(context, new String[0]).requestDrawOverlays(simpleCallback);
    }

    public static void setAnimationDuration(int i2) {
        if (i2 >= 0) {
            animationDuration = i2;
        }
    }

    public static void setNavigationBarColor(int i2) {
        navigationBarColor = i2;
    }

    public static void setPrimaryColor(int i2) {
        primaryColor = i2;
    }

    public static void setShadowBgColor(int i2) {
        shadowBgColor = i2;
    }

    public static void setStatusBarBgColor(int i2) {
        statusBarBgColor = i2;
    }

    public static class Builder {
        private Context context;
        private final PopupInfo popupInfo = new PopupInfo();

        public Builder(Context context) {
            this.context = context;
        }

        public Builder animationDuration(int i2) {
            this.popupInfo.animationDuration = i2;
            return this;
        }

        public AttachListPopupView asAttachList(String[] strArr, int[] iArr, OnSelectListener onSelectListener, int i2, int i3, int i4) {
            popupType(PopupType.AttachView);
            AttachListPopupView onSelectListener2 = new AttachListPopupView(this.context, i2, i3).setStringData(strArr, iArr).setContentGravity(i4).setOnSelectListener(onSelectListener);
            onSelectListener2.popupInfo = this.popupInfo;
            return onSelectListener2;
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, int i2, boolean z2, OnSelectListener onSelectListener, int i3, int i4) {
            popupType(PopupType.Bottom);
            BottomListPopupView onSelectListener2 = new BottomListPopupView(this.context, i3, i4).setStringData(charSequence, strArr, iArr).setCheckedPosition(i2).setOnSelectListener(onSelectListener);
            onSelectListener2.popupInfo = this.popupInfo;
            return onSelectListener2;
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, int[] iArr, int i2, OnSelectListener onSelectListener, int i3, int i4) {
            popupType(PopupType.Center);
            CenterListPopupView onSelectListener2 = new CenterListPopupView(this.context, i3, i4).setStringData(charSequence, strArr, iArr).setCheckedPosition(i2).setOnSelectListener(onSelectListener);
            onSelectListener2.popupInfo = this.popupInfo;
            return onSelectListener2;
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnConfirmListener onConfirmListener, OnCancelListener onCancelListener, boolean z2, int i2) {
            popupType(PopupType.Center);
            ConfirmPopupView confirmPopupView = new ConfirmPopupView(this.context, i2);
            confirmPopupView.setTitleContent(charSequence, charSequence2, null);
            confirmPopupView.setCancelText(charSequence3);
            confirmPopupView.setConfirmText(charSequence4);
            confirmPopupView.setListener(onConfirmListener, onCancelListener);
            confirmPopupView.isHideCancel = z2;
            confirmPopupView.popupInfo = this.popupInfo;
            return confirmPopupView;
        }

        public BasePopupView asCustom(BasePopupView basePopupView) {
            if (basePopupView instanceof CenterPopupView) {
                popupType(PopupType.Center);
            } else if (basePopupView instanceof BottomPopupView) {
                popupType(PopupType.Bottom);
            } else if (basePopupView instanceof AttachPopupView) {
                popupType(PopupType.AttachView);
            } else if (basePopupView instanceof ImageViewerPopupView) {
                popupType(PopupType.ImageViewer);
            } else if (basePopupView instanceof PositionPopupView) {
                popupType(PopupType.Position);
            }
            basePopupView.popupInfo = this.popupInfo;
            return basePopupView;
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, Object obj, XPopupImageLoader xPopupImageLoader) {
            popupType(PopupType.ImageViewer);
            ImageViewerPopupView xPopupImageLoader2 = new ImageViewerPopupView(this.context).setSingleSrcView(imageView, obj).setXPopupImageLoader(xPopupImageLoader);
            xPopupImageLoader2.popupInfo = this.popupInfo;
            return xPopupImageLoader2;
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnInputConfirmListener onInputConfirmListener, OnCancelListener onCancelListener, int i2) {
            popupType(PopupType.Center);
            InputConfirmPopupView inputConfirmPopupView = new InputConfirmPopupView(this.context, i2);
            inputConfirmPopupView.setTitleContent(charSequence, charSequence2, charSequence4);
            inputConfirmPopupView.inputContent = charSequence3;
            inputConfirmPopupView.setListener(onInputConfirmListener, onCancelListener);
            inputConfirmPopupView.popupInfo = this.popupInfo;
            return inputConfirmPopupView;
        }

        public LoadingPopupView asLoading(CharSequence charSequence, int i2) {
            popupType(PopupType.Center);
            LoadingPopupView title = new LoadingPopupView(this.context, i2).setTitle(charSequence);
            title.popupInfo = this.popupInfo;
            return title;
        }

        public Builder atView(View view) {
            this.popupInfo.atView = view;
            return this;
        }

        public Builder autoDismiss(Boolean bool) {
            this.popupInfo.autoDismiss = bool;
            return this;
        }

        public Builder autoFocusEditText(boolean z2) {
            this.popupInfo.autoFocusEditText = z2;
            return this;
        }

        public Builder autoOpenSoftInput(Boolean bool) {
            this.popupInfo.autoOpenSoftInput = bool;
            return this;
        }

        public Builder borderRadius(float f2) {
            this.popupInfo.borderRadius = f2;
            return this;
        }

        public Builder customAnimator(PopupAnimator popupAnimator) {
            this.popupInfo.customAnimator = popupAnimator;
            return this;
        }

        public Builder dismissOnBackPressed(Boolean bool) {
            this.popupInfo.isDismissOnBackPressed = bool;
            return this;
        }

        public Builder dismissOnTouchOutside(Boolean bool) {
            this.popupInfo.isDismissOnTouchOutside = bool;
            return this;
        }

        public Builder enableDrag(boolean z2) {
            this.popupInfo.enableDrag = Boolean.valueOf(z2);
            return this;
        }

        public Builder enableShowWhenAppBackground(boolean z2) {
            this.popupInfo.enableShowWhenAppBackground = z2;
            return this;
        }

        public Builder hasBlurBg(boolean z2) {
            this.popupInfo.hasBlurBg = Boolean.valueOf(z2);
            return this;
        }

        public Builder hasNavigationBar(boolean z2) {
            this.popupInfo.hasNavigationBar = Boolean.valueOf(z2);
            return this;
        }

        public Builder hasShadowBg(Boolean bool) {
            this.popupInfo.hasShadowBg = bool;
            return this;
        }

        public Builder hasStatusBar(boolean z2) {
            this.popupInfo.hasStatusBar = Boolean.valueOf(z2);
            return this;
        }

        public Builder hasStatusBarShadow(boolean z2) {
            this.popupInfo.hasStatusBarShadow = Boolean.valueOf(z2);
            return this;
        }

        public Builder isCenterHorizontal(boolean z2) {
            this.popupInfo.isCenterHorizontal = z2;
            return this;
        }

        public Builder isClickThrough(boolean z2) {
            this.popupInfo.isClickThrough = z2;
            return this;
        }

        public Builder isDarkTheme(boolean z2) {
            this.popupInfo.isDarkTheme = z2;
            return this;
        }

        public Builder isDestroyOnDismiss(boolean z2) {
            this.popupInfo.isDestroyOnDismiss = z2;
            return this;
        }

        public Builder isLightNavigationBar(boolean z2) {
            this.popupInfo.isLightNavigationBar = Boolean.valueOf(z2);
            return this;
        }

        public Builder isRequestFocus(boolean z2) {
            this.popupInfo.isRequestFocus = z2;
            return this;
        }

        public Builder isThreeDrag(boolean z2) {
            this.popupInfo.isThreeDrag = z2;
            return this;
        }

        public Builder isViewMode(boolean z2) {
            this.popupInfo.isViewMode = z2;
            return this;
        }

        public Builder keepScreenOn(boolean z2) {
            this.popupInfo.keepScreenOn = z2;
            return this;
        }

        public Builder maxHeight(int i2) {
            this.popupInfo.maxHeight = i2;
            return this;
        }

        public Builder maxWidth(int i2) {
            this.popupInfo.maxWidth = i2;
            return this;
        }

        public Builder moveUpToKeyboard(Boolean bool) {
            this.popupInfo.isMoveUpToKeyboard = bool;
            return this;
        }

        public Builder navigationBarColor(int i2) {
            this.popupInfo.navigationBarColor = i2;
            return this;
        }

        public Builder offsetX(int i2) {
            this.popupInfo.offsetX = i2;
            return this;
        }

        public Builder offsetY(int i2) {
            this.popupInfo.offsetY = i2;
            return this;
        }

        public Builder popupAnimation(PopupAnimation popupAnimation) {
            this.popupInfo.popupAnimation = popupAnimation;
            return this;
        }

        public Builder popupHeight(int i2) {
            this.popupInfo.popupHeight = i2;
            return this;
        }

        public Builder popupPosition(PopupPosition popupPosition) {
            this.popupInfo.popupPosition = popupPosition;
            return this;
        }

        public Builder popupType(PopupType popupType) {
            this.popupInfo.popupType = popupType;
            return this;
        }

        public Builder popupWidth(int i2) {
            this.popupInfo.popupWidth = i2;
            return this;
        }

        public Builder positionByWindowCenter(boolean z2) {
            this.popupInfo.positionByWindowCenter = z2;
            return this;
        }

        public Builder setPopupCallback(XPopupCallback xPopupCallback) {
            this.popupInfo.xPopupCallback = xPopupCallback;
            return this;
        }

        public Builder shadowBgColor(int i2) {
            this.popupInfo.shadowBgColor = i2;
            return this;
        }

        public Builder statusBarBgColor(int i2) {
            this.popupInfo.statusBarBgColor = i2;
            return this;
        }

        public Builder watchView(View view) {
            this.popupInfo.watchView = view;
            view.setOnTouchListener(new View.OnTouchListener() { // from class: com.lxj.xpopup.XPopup.Builder.1
                @Override // android.view.View.OnTouchListener
                public boolean onTouch(View view2, MotionEvent motionEvent) {
                    if (motionEvent.getAction() != 0) {
                        return false;
                    }
                    Builder.this.popupInfo.touchPoint = new PointF(motionEvent.getRawX(), motionEvent.getRawY());
                    return false;
                }
            });
            return this;
        }

        public LoadingPopupView asLoading(CharSequence charSequence) {
            return asLoading(charSequence, 0);
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, Object obj, boolean z2, int i2, int i3, int i4, boolean z3, int i5, XPopupImageLoader xPopupImageLoader, OnImageViewerLongPressListener onImageViewerLongPressListener) {
            popupType(PopupType.ImageViewer);
            ImageViewerPopupView longPressListener = new ImageViewerPopupView(this.context).setSingleSrcView(imageView, obj).isInfinite(z2).setPlaceholderColor(i2).setPlaceholderStrokeColor(i3).setPlaceholderRadius(i4).isShowSaveButton(z3).setBgColor(i5).setXPopupImageLoader(xPopupImageLoader).setLongPressListener(onImageViewerLongPressListener);
            longPressListener.popupInfo = this.popupInfo;
            return longPressListener;
        }

        public LoadingPopupView asLoading() {
            return asLoading(null);
        }

        public AttachListPopupView asAttachList(String[] strArr, int[] iArr, OnSelectListener onSelectListener, int i2, int i3) {
            return asAttachList(strArr, iArr, onSelectListener, i2, i3, 17);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, int i2, boolean z2, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, i2, z2, onSelectListener, 0, 0);
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, int[] iArr, int i2, OnSelectListener onSelectListener) {
            return asCenterList(charSequence, strArr, iArr, i2, onSelectListener, 0, 0);
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnInputConfirmListener onInputConfirmListener) {
            return asInputConfirm(charSequence, charSequence2, charSequence3, charSequence4, onInputConfirmListener, null, 0);
        }

        public AttachListPopupView asAttachList(String[] strArr, int[] iArr, OnSelectListener onSelectListener) {
            return asAttachList(strArr, iArr, onSelectListener, 0, 0, 17);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, null, -1, true, onSelectListener);
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, OnSelectListener onSelectListener) {
            return asCenterList(charSequence, strArr, null, -1, onSelectListener);
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, OnInputConfirmListener onInputConfirmListener) {
            return asInputConfirm(charSequence, charSequence2, null, charSequence3, onInputConfirmListener, null, 0);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, -1, true, onSelectListener);
        }

        public CenterListPopupView asCenterList(CharSequence charSequence, String[] strArr, int[] iArr, OnSelectListener onSelectListener) {
            return asCenterList(charSequence, strArr, iArr, -1, onSelectListener);
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, CharSequence charSequence4, OnConfirmListener onConfirmListener, OnCancelListener onCancelListener, boolean z2) {
            return asConfirm(charSequence, charSequence2, charSequence3, charSequence4, onConfirmListener, onCancelListener, z2, 0);
        }

        public InputConfirmPopupView asInputConfirm(CharSequence charSequence, CharSequence charSequence2, OnInputConfirmListener onInputConfirmListener) {
            return asInputConfirm(charSequence, charSequence2, null, null, onInputConfirmListener, null, 0);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, int i2, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, i2, true, onSelectListener);
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, OnConfirmListener onConfirmListener, OnCancelListener onCancelListener) {
            return asConfirm(charSequence, charSequence2, null, null, onConfirmListener, onCancelListener, false, 0);
        }

        public BottomListPopupView asBottomList(CharSequence charSequence, String[] strArr, int[] iArr, boolean z2, OnSelectListener onSelectListener) {
            return asBottomList(charSequence, strArr, iArr, -1, z2, onSelectListener);
        }

        public ConfirmPopupView asConfirm(CharSequence charSequence, CharSequence charSequence2, OnConfirmListener onConfirmListener) {
            return asConfirm(charSequence, charSequence2, null, null, onConfirmListener, null, false, 0);
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, int i2, List<Object> list, OnSrcViewUpdateListener onSrcViewUpdateListener, XPopupImageLoader xPopupImageLoader) {
            return asImageViewer(imageView, i2, list, false, true, -1, -1, -1, true, Color.rgb(32, 36, 46), onSrcViewUpdateListener, xPopupImageLoader, null);
        }

        public ImageViewerPopupView asImageViewer(ImageView imageView, int i2, List<Object> list, boolean z2, boolean z3, int i3, int i4, int i5, boolean z4, int i6, OnSrcViewUpdateListener onSrcViewUpdateListener, XPopupImageLoader xPopupImageLoader, OnImageViewerLongPressListener onImageViewerLongPressListener) {
            popupType(PopupType.ImageViewer);
            ImageViewerPopupView longPressListener = new ImageViewerPopupView(this.context).setSrcView(imageView, i2).setImageUrls(list).isInfinite(z2).isShowPlaceholder(z3).setPlaceholderColor(i3).setPlaceholderStrokeColor(i4).setPlaceholderRadius(i5).isShowSaveButton(z4).setBgColor(i6).setSrcViewUpdateListener(onSrcViewUpdateListener).setXPopupImageLoader(xPopupImageLoader).setLongPressListener(onImageViewerLongPressListener);
            longPressListener.popupInfo = this.popupInfo;
            return longPressListener;
        }
    }
}
