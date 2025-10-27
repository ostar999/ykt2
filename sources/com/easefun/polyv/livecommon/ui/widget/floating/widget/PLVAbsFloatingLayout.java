package com.easefun.polyv.livecommon.ui.widget.floating.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.easefun.polyv.livecommon.ui.widget.floating.enums.PLVFloatingEnums;
import com.plv.foundationsdk.utils.PLVSugarUtil;
import com.plv.thirdpart.blankj.utilcode.util.ScreenUtils;

/* loaded from: classes3.dex */
public abstract class PLVAbsFloatingLayout extends FrameLayout implements IPLVFloatingLayout {
    private final String TAG;
    protected PLVFloatingEnums.AutoEdgeType autoEdgeType;
    protected boolean consumeTouchEventOnMove;
    protected Context context;
    protected boolean enableDrag;
    protected int floatWindowHeight;
    protected int floatWindowWidth;
    protected int floatingLocationX;
    protected int floatingLocationY;
    private boolean isMove;
    protected boolean isShowing;
    protected PLVFloatingEnums.ShowType showType;

    /* renamed from: x, reason: collision with root package name */
    private int f6553x;

    /* renamed from: y, reason: collision with root package name */
    private int f6554y;

    /* renamed from: com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVAbsFloatingLayout$2, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$AutoEdgeType;

        static {
            int[] iArr = new int[PLVFloatingEnums.AutoEdgeType.values().length];
            $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$AutoEdgeType = iArr;
            try {
                iArr[PLVFloatingEnums.AutoEdgeType.AUTO_MOVE_TO_LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$AutoEdgeType[PLVFloatingEnums.AutoEdgeType.AUTO_MOVE_TO_RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$AutoEdgeType[PLVFloatingEnums.AutoEdgeType.AUTO_MOVE_TO_NEAREST_EDGE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public PLVAbsFloatingLayout(@NonNull Context context) {
        this(context, null);
    }

    private void autoMoveToEdge() {
        PLVFloatingEnums.AutoEdgeType autoEdgeType;
        int i2;
        int screenWidth;
        int i3;
        if (!this.enableDrag || (autoEdgeType = this.autoEdgeType) == PLVFloatingEnums.AutoEdgeType.NO_AUTO_MOVE) {
            return;
        }
        int i4 = AnonymousClass2.$SwitchMap$com$easefun$polyv$livecommon$ui$widget$floating$enums$PLVFloatingEnums$AutoEdgeType[autoEdgeType.ordinal()];
        if (i4 == 1) {
            i2 = 0;
        } else {
            if (i4 == 2) {
                screenWidth = ScreenUtils.getScreenWidth();
                i3 = this.floatWindowWidth;
            } else if (i4 != 3) {
                i2 = this.floatingLocationX;
            } else {
                if (this.floatingLocationX + (this.floatWindowWidth / 2) >= ScreenUtils.getScreenWidth() / 2) {
                    screenWidth = ScreenUtils.getScreenWidth();
                    i3 = this.floatWindowWidth;
                }
                i2 = 0;
            }
            i2 = screenWidth - i3;
        }
        ValueAnimator valueAnimatorOfInt = ValueAnimator.ofInt(this.floatingLocationX, i2);
        valueAnimatorOfInt.setDuration(100L);
        valueAnimatorOfInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.easefun.polyv.livecommon.ui.widget.floating.widget.PLVAbsFloatingLayout.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator animation) {
                int iIntValue = ((Integer) animation.getAnimatedValue()).intValue();
                PLVAbsFloatingLayout pLVAbsFloatingLayout = PLVAbsFloatingLayout.this;
                pLVAbsFloatingLayout.updateFloatLocation(iIntValue, pLVAbsFloatingLayout.floatingLocationY);
            }
        });
        valueAnimatorOfInt.start();
    }

    public int fitInsideScreenX(int x2) {
        return PLVSugarUtil.clamp(x2, 0, ScreenUtils.getScreenWidth() - this.floatWindowWidth);
    }

    public int fitInsideScreenY(int y2) {
        return PLVSugarUtil.clamp(y2, 0, ScreenUtils.getScreenHeight() - this.floatWindowHeight);
    }

    public void init() {
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public boolean isShowing() {
        return this.isShowing;
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == 0) {
            this.f6553x = (int) event.getRawX();
            this.f6554y = (int) event.getRawY();
            this.isMove = false;
        } else if (action != 1) {
            if (action == 2) {
                int rawX = (int) event.getRawX();
                int rawY = (int) event.getRawY();
                int i2 = rawX - this.f6553x;
                int i3 = rawY - this.f6554y;
                this.f6553x = rawX;
                this.f6554y = rawY;
                if (this.enableDrag) {
                    updateFloatLocation(fitInsideScreenX(this.floatingLocationX + i2), fitInsideScreenY(this.floatingLocationY + i3));
                }
                if (Math.abs(i2) >= 5 || Math.abs(i3) >= 5) {
                    this.isMove = true;
                }
            }
        } else if (this.isMove) {
            this.isMove = false;
            autoMoveToEdge();
            if (this.consumeTouchEventOnMove) {
                return true;
            }
        }
        return super.onInterceptTouchEvent(event);
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setAutoMoveToEdge(PLVFloatingEnums.AutoEdgeType autoEdgeType) {
        this.autoEdgeType = autoEdgeType;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setConsumeTouchEventOnMove(boolean consumeTouchEventOnMove) {
        this.consumeTouchEventOnMove = consumeTouchEventOnMove;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setEnableDrag(boolean enableDrag) {
        this.enableDrag = enableDrag;
    }

    @Override // com.easefun.polyv.livecommon.ui.widget.floating.widget.IPLVFloatingLayout
    public void setShowType(PLVFloatingEnums.ShowType showType) {
        this.showType = showType;
    }

    public PLVAbsFloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PLVAbsFloatingLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.TAG = getClass().getSimpleName();
        this.enableDrag = true;
        this.consumeTouchEventOnMove = true;
        this.showType = PLVFloatingEnums.ShowType.SHOW_ONLY_BACKGROUND;
        this.autoEdgeType = PLVFloatingEnums.AutoEdgeType.AUTO_MOVE_TO_RIGHT;
        this.isShowing = false;
        init();
    }
}
