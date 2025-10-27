package me.dkzwm.widget.srl.indicator;

import androidx.annotation.NonNull;

/* loaded from: classes9.dex */
public interface IIndicator {
    public static final float DEFAULT_MAX_MOVE_RATIO = 0.0f;
    public static final float DEFAULT_RATIO_TO_KEEP = 1.0f;
    public static final float DEFAULT_RATIO_TO_REFRESH = 1.0f;
    public static final float DEFAULT_RESISTANCE = 1.65f;
    public static final int START_POS = 0;

    public interface IOffsetCalculator {
        float calculate(int i2, int i3, float f2);
    }

    void checkConfig();

    float getCanMoveTheMaxDistanceOfFooter();

    float getCanMoveTheMaxDistanceOfHeader();

    float getCurrentPercentOfLoadMoreOffset();

    float getCurrentPercentOfRefreshOffset();

    int getCurrentPos();

    @NonNull
    float[] getFingerDownPoint();

    int getFooterHeight();

    int getHeaderHeight();

    @NonNull
    float[] getLastMovePoint();

    int getLastPos();

    int getMovingStatus();

    float getOffset();

    int getOffsetToKeepFooterWhileLoading();

    int getOffsetToKeepHeaderWhileLoading();

    int getOffsetToLoadMore();

    int getOffsetToRefresh();

    float getRawOffset();

    boolean hasJustBackToStartPosition();

    boolean hasJustLeftStartPosition();

    boolean hasLeftStartPosition();

    boolean hasMoved();

    boolean hasTouched();

    boolean isAlreadyHere(int i2);

    boolean isJustReturnedOffsetToLoadMore();

    boolean isJustReturnedOffsetToRefresh();

    boolean isOverOffsetToKeepFooterWhileLoading();

    boolean isOverOffsetToKeepHeaderWhileLoading();

    boolean isOverOffsetToLoadMore();

    boolean isOverOffsetToRefresh();
}
